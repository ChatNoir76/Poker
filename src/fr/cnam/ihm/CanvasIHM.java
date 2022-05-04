package fr.cnam.ihm;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import fr.cnam.tore.*;
import fr.cnam.ihm.*;

/**
   Classe de d�finition d'un canvas dans lequel on peut afficher une grille IHM avec laquelle il est possible :<br>
   - de colorer ou effacer une case de la grille<br>
   - de realiser une action si on clique dans une des cases de la grille<br>
   - si la grille n'est pas affichee alors on peut tracer des lignes et ecrire du texte et <br>
   avoir une action quand on clique dans le canvas ou quand on deplace la souris <br>
   - on peut dessiner des polygones (2D) qui peuvent avoir une position aussi en Z. Les polygones sont affich�s en fonction de Z, avec X plus prioritaire que Y.
*/
public class CanvasIHM 
{
  private final static int    NB_MAX_MARQUE = 10;
  /*1 = Carre avec une marge<br>2 = Ronde avec une marge**/
  public static int TYPE_MARQUE = 1;       

  private JFrame              fen;
  private JPanel              _panelPP;
  private Canvas             _canvas;
  private ControlesCanvasIHM _actions;
  private int                _nbX;
  private int                _nbY;
  private int                _tailleCase;
  private int                _grille[][];
  private ArrayList<LigneCanvas>   _lignes;
  private ArrayList<TexteCanvas>   _textes;
  private ArrayList<PolygoneCanvas>   _polygones;
  private int                _xCanvas;
  private int                _yCanvas;
  private int                _width; // du canvas
  private int                _height;
  private Color[]            _couleurs;
  private boolean           _afficherGrille;


  /**
     Constructeur d'un canvas d'IHM
     @param nbX nombre de case en largeur
     @param nbY nombre de case en hauteur
     @param tailleCase taille de chaque case
  **/
  public CanvasIHM(int nbX,
                   int nbY,
                   int tailleCase)
  {
    _afficherGrille = true;
    initCanvasIHM(nbX,nbY,tailleCase);
  }

  /**
     Constructeur d'un canvas d'IHM
     @param nbX nombre de case en largeur
     @param nbY nombre de case en hauteur
  **/
  public CanvasIHM(int width,
                   int height)
  {
    _afficherGrille = false;
    initCanvasIHM(width,height,1);
  }
  
  private void initCanvasIHM(int nbX,
                                    int nbY,
                                    int tailleCase)
  {
    // Si utilisation de la methode creerCanvasIhmDansFrame
    fen=null;

    // Creation du panel principal
    _panelPP = new JPanel();
    _panelPP.setLayout(null);


    // Caract�ristiques de l'ihm
    _tailleCase  = tailleCase;
    _nbX         = nbX;
    _nbY         = nbY;
    _xCanvas     = 5;
    _yCanvas     = 5;
    _width       = _nbX * _tailleCase+1;
    _height      = _nbY * _tailleCase+1;
    _couleurs    = new Color[NB_MAX_MARQUE];
    _couleurs[0] = Color.black;
    _couleurs[1] = Color.cyan;
    _couleurs[2] = Color.blue;
    _couleurs[3] = Color.gray;
    _couleurs[4] = Color.green;
    _couleurs[5] = Color.magenta;
    _couleurs[6] = Color.orange;
    _couleurs[7] = Color.yellow;
    _couleurs[8] = Color.red;
    _couleurs[9] = Color.white;

    // Creation du canvas
    _canvas = new PaintCanvas(this);
    _canvas.resize(_width,_height);

    // Initialisation du tableau de la grille
    _grille = new int[_nbX][_nbY];
    razGrille();

    // Initialisation de la liste de ligne
    _lignes = new ArrayList<LigneCanvas>();
    _textes = new ArrayList<TexteCanvas>();
    _polygones = new ArrayList<PolygoneCanvas>();

    // Pas d'actions par defaut
    _actions = null;

    // On ajoute le canvas dans le panelPP
    // 
    _canvas.setBounds(_xCanvas,_yCanvas,_width,_height);
    _panelPP.add(_canvas);

    // Action de la souris dans le canevas
    _canvas.addMouseListener( new SourisAction(this));
  }

  // ======================================================================
  /**
     Initialise ou change les actions utilise dans le Canvas
     @param actions un objet qui impl�mente l'interface ControlesCanvasIHM
  */
  public void setActions(ControlesCanvasIHM actions)
  {
    _actions = actions;
  }
  
  // ======================================================================
  /**
     Retourne le panel prinicipal de l'IHM
     @return le panel
  */
  public JPanel getPanel()
  {
    return(_panelPP);
  }

  /** Retourne la largeur du canvas de la grille
      @return largeur 
  */
  public int getWidth(){return _width;}

  /** Retourne la hauteur du canvas de la grille
      @return largeur 
  */
  public int getHeight(){return _height;}

  // ======================================================================
  /** Efface le contenu de la grille<br>
      (La valeur 0 est mise dans chaque case)
  */
  public void razGrille()
  {
    for(int i=0;i<_nbX;i++)
      {
        for(int j=0;j<_nbY;j++)
          {
            _grille[i][j]=0;
          }
      }
  }

  // ======================================================================
  /** Affecte � une case de la grille la marque (couleur)
      @param marque une valeur de 1 � 10 (couleur) ou 0 (case vide)
      @param x coordonn�e en x de la case
      @param y coordonn�e en y de la case
  */
  public void setMarque(int marque,int x,int y)
  {
    _grille[x][y] = marque;
    marquer(marque,x,y);
  }


  // ======================================================================
  /** Ajout d'une ligne de (x1,y1) a (x2,y2) et de couleur.
      @param couleur (valeur de 1 � 10)
      @param x1 coord en X du 1er point
      @param y1 coord en Y du 1er point
      @param x2 coord en X du 2eme point
      @param y2 coors en Y du 2eme point
  */
  public void ajouterLigne(int couleur,int x1,int y1,int x2,int y2)
  {
    _lignes.add(new LigneCanvas(couleur,x1,y1,x2,y2));
    dessinerLignes();
  }

  // ======================================================================
  /** Ajout d'un texte dans le canvas a une position (x,y)
      @param texte La valeur de la chaine
      @param x coord en X du texte
      @param y coord en Y du texte
      @param couleur (valeur de 1 � 10)
  */
  public void ajouterTexte(String texte,int x,int y,int couleur)
  {
    _textes.add(new TexteCanvas(texte,x,y,couleur));
    dessinerTextes();
  }

  // ======================================================================
  /** Ajout d'un polygone p1,p2,....  et de couleur.
      @param objet objet quelconque (utilis� dans pointerCanvas)
      @param position position de l'objet en (x,y,z)
      @param couleur couleur (valeur de 1 � 10)
      @param points les points du polygone
  */
  public void ajouterPolygone(Object objet,Position position,int couleur,Point... points)
  {
    _polygones.add(new PolygoneCanvas(objet,position,couleur,points));
    dessinerPolygones();
  }

  // ======================================================================
  /** Ajout d'un polygone p1,p2,....  et de couleur.
      @param rc ressources graphiques d'affichage
      @param o objet quelconque (utilis� dans pointerCanvas)
      @param p position de l'objet en (x,y,z)
      @param points les points du polygone
  */
  public void ajouterPolygone(RessourcesCanvas rc,Object o,Position p,Point... points)
  {
    _polygones.add(new PolygoneCanvas(rc,o,p,points));
    dessinerPolygones();
  }

  // ======================================================================
  /** Retourne la marque de la case
      @param x coordonn�e en x de la case
      @param y coordonn�e en y de la case
      @return la valeur de la case (de 0 � 10)
  */
  public int getMarque(int x,int y)
  {
    return(_grille[x][y]);
  }
  
  // ======================================================================
  /** Teste si la case est libre (diff�rente de 0)
      @param x coordonn�e en x de la case
      @param y coordonn�e en y de la case
      @return true si la case est libre sinon false
  */
  public boolean siCaseLibre(int x,int y)
  {
    return( getMarque(x,y)==0 );
  }
  
  // ======================================================================
  /** Retourne le nombre de colonne de la grille
      @return le nombre de colonne
  */
  public int getNbX()
  {
    return(_nbX);
  }
  
  // ======================================================================
  /** Retourne le nombre de ligne de la grille
      @return le nombre de ligne
  */
  public int getNbY()
  {
    return(_nbY);
  }

  // ======================================================================
  /** Retourne le nombre max de couleur 
      @return nombre max
  */
  public int getNbMaxMarqueur()
  {
    return(NB_MAX_MARQUE);
  }  

  // ======================================================================
  /** Desaffichage de la grille du canvas
   */
  public void desafficherGrille()
  {
    Graphics g = _canvas.getGraphics();
    _afficherGrille=false;
    // On efface toute la grille
    g.clearRect(0,0,_width+1,_height+1);
  }

  // ======================================================================
  /** Affichage de la grille dans le canvas
   */
  public void afficherGrille()
  {
    _afficherGrille=true;
    dessinerGrille();
  }

  // ======================================================================
  /** Methode static qui permet de creer le canvas inclus dans un Frame
      @param nbX nombre de colonnes de la grille
      @param nbY nombre de lignes de la grille
      @param tailleCase taille de chaque case en pixel
      @return retourne le canvas cr��
  */
  static public CanvasIHM creerCanvasIhmDansFrame(int nbX,
                                                  int nbY,
                                                  int tailleCase
                                                  )
  {
    // On cr�e la fen�tre
    JFrame fen = new JFrame();

    // On y ajoute la grille
    CanvasIHM ihm = new CanvasIHM(nbX,nbY,tailleCase);
    ihm.fen = fen;

    // Panel in Frame
    fen.add(ihm.getPanel());

    // Pour fermer la fenetre
    GrilleWindowAdapter a = new GrilleWindowAdapter();
    fen.addWindowListener((WindowListener)a);
        
    return(ihm);
  }

  // Affiche la fenetre
  public void afficherFrame(int posX,int posY)
  {
    fen.setLocation(posX,posY);
    fen.setPreferredSize(new Dimension(_width+20,_height+20));
    fen.pack();
    fen.show();
  }

  public JFrame getFrame()
  {
    return fen;
  }

  /* Retourne les lignes du canvas
     @return ArrayList<LigneCanvas>
  */
  public ArrayList<LigneCanvas> getLignes()
  {
    return _lignes;
  }

  /* Retourne les textes du canvas
     @return ArrayList<TexteCanvas>
  */
  public ArrayList<TexteCanvas> getTextes()
  {
    return _textes;
  }

  /* Retourne les polygones du canvas
     @return ArrayList<PolygoneCanvas>
  */
  public ArrayList<PolygoneCanvas> getPolygones()
  {
    return _polygones;
  }




  // **************************** METHODES PRIVEES ************************
    
  // Code pour dessiner la grille et colorer les cases de la grille
  //
  private void dessinerGrille()
  {
    Graphics g = _canvas.getGraphics();

    if (g==null)
      {
        System.out.println("Le graphic est null. Les instrucitons de dessin doivent se faire apr�s l'affichage du formulaire qui contient le canvas.");
        return;
      }

    // On efface toute la grille
    g.clearRect(0,0,_width+1,_height+1);

    // Tracer des lignes verticales
    for(int i=0;i<_nbX+1;i++)
      {
        Point p1= new Point( (_width/_nbX)*i,0);
        Point p2= new Point( (_width/_nbX)*i,_height);
        g.drawLine(p1.x,p1.y,p2.x,p2.y);
      }

    // Tracer des lignes horizontales
    for(int i=0;i<_nbY+1;i++)
      {
        Point p1= new Point( 0,      (_height/_nbY)*i);
        Point p2= new Point( _width, (_height/_nbY)*i);
        g.drawLine(p1.x,p1.y,p2.x,p2.y);
      }

    // Tracer des �l�ments
    for(int i=0;i<_nbX;i++)
      {
        for(int j=0;j<_nbY;j++)
          {
            marquer(_grille[i][j],i,j);
          }
      }
  }

  // Dessine touts les lignes
  //
  private void dessinerLignes()
  {
    Graphics g = _canvas.getGraphics();

    if (g==null)
      {
        System.out.println("Le graphic est null. Les instrucitons de dessin doivent se faire apr�s l'affichage du formulaire qui contient le canvas.");
        return;
      }

    for(int i=0;i<_lignes.size();i++)
      {
        LigneCanvas l=_lignes.get(i);
        if (l.rc==null)
          g.setColor(_couleurs[l.marque-1]);
        else
          g.setColor(l.rc.getForeground());
        g.drawLine(l.p1.x,l.p1.y,l.p2.x,l.p2.y);
      }
  }

  // Dessine tous les polygones
  //
  private void dessinerPolygones()
  {
    Graphics g = _canvas.getGraphics();
        
    Collections.sort(_polygones);

    if (g==null)
      {
        System.out.println("Le graphic est null. Les instrucitons de dessin doivent se faire apr�s l'affichage du formulaire qui contient le canvas.");
        return;
      }

    for(int k=0;k<_polygones.size();k++)
      {
        PolygoneCanvas p = _polygones.get(k);
        if (p.rc==null)
          g.setColor(_couleurs[p.marque-1]);
        else
          g.setColor(p.rc.getBackground());
        int[] xPoints=new int[p.points.length];
        int[] yPoints=new int[p.points.length];
        int i=0;
        for(Point pt : p.points)
          {
            xPoints[i]=pt.x;
            yPoints[i]=pt.y;
            i++;
          }
        g.fillPolygon(xPoints,yPoints,p.points.length);
        if (p.rc==null)
          g.setColor(_couleurs[0]);
        else
          g.setColor(p.rc.getForeground());
        Point cour=p.points[0];
        for(int r=1;r<p.points.length;r++)
          {
            g.drawLine(cour.x,cour.y,p.points[r].x,p.points[r].y);
            cour = p.points[r];
          }
        g.drawLine(p.points[p.points.length-1].x,p.points[p.points.length-1].y,
                   p.points[0].x,p.points[0].y);
      }
  }

  // Dessine tous les textes
  //
  private void dessinerTextes()
  {
    Graphics g = _canvas.getGraphics();

    if (g==null)
      {
        System.out.println("Le graphic est null. Les instrucitons de dessin doivent se faire apr�s l'affichage du formulaire qui contient le canvas.");
        return;
      }

    for(TexteCanvas t:_textes)
      {
        if (t.rc==null)
          g.setColor(_couleurs[t.couleur-1]);
        else
          g.setColor(t.rc.getForeground());
        g.drawString(t.texte,t.p.x,t.p.y);
      }
  }

  /* Effacer tout le canvas
  **/
  public void effacerCanvas()
  {
    _lignes.clear();
    _textes.clear();
    _polygones.clear();

    Graphics g = _canvas.getGraphics();

    if (g==null)
      {
        System.out.println("Le graphic est null. Les instrucitons de dessin doivent se faire apr�s l'affichage du formulaire qui contient le canvas.");
        return;
      }

    // On efface toute la zone
    g.clearRect(0,0,_width+1,_height+1);
        
  }

  // dessiner le contenu d'une case de la grille
  //
  private void marquer(int marque,int x,int y)
  {
    Graphics g = _canvas.getGraphics();
    int niX1 = x*(_width/_nbX);
    int niX2 = (x+1)*(_width/_nbX);
    int niY1 = y*(_height/_nbY);
    int niY2 = (y+1)*(_height/_nbY);

    g.clearRect(niX1+1,niY1+1,niX2-niX1-2,niY2-niY1-2);
    if (marque >0)
      {
        g.setColor(_couleurs[marque-1]);
        if (TYPE_MARQUE==1)
          g.fillRect(niX1+2,niY1+2,niX2-niX1-4,niY2-niY1-4);
        else
          g.fillOval(niX1+2,niY1+2,niX2-niX1-4,niY2-niY1-4);
      }
  }

  // determine les corrdonees de la case en fonction du point de la souris
  //
  private Point pointToCase(Point p)
  {
    int px = p.x;
    int py = p.y;

    int x = px/_tailleCase;
    int y = py/_tailleCase;

    if ( (x<0) || (x>=_nbX) ||
         (y<0) || (y>=_nbY) )
      return(null);
    else
      return( new Point(x,y));
  }

  // determine l'objet cible du polygone en fonction du point de la souris
  //
  private Object pointToPolygone(Point p)
  {
    int px = p.x;
    int py = p.y;

    for(int i=_polygones.size()-1;i>=0;i--)
      {
        if (_polygones.get(i).polygone.contains(p))
          return(_polygones.get(i).object);
      }
    return null;
  }


  //********************   CLASSES INTERNES ************************

  // Classe interne de definition de la surcharge de paint du canvas
  //
  class PaintCanvas extends Canvas
  {
    CanvasIHM _g;
        
    public PaintCanvas(CanvasIHM g)
    {
      _g = g;
    }

    public void paint(Graphics g)
    {
      if (_afficherGrille)
        _g.dessinerGrille();
      _g.dessinerLignes();
      _g.dessinerTextes();
      _g.dessinerPolygones();
    }
  }

  // Classe interne de definition de la surcharge de la souris
  //
  class SourisAction extends MouseAdapter
  {
    CanvasIHM _ihm;

    public SourisAction(CanvasIHM ihm)
    {
      _ihm=ihm;
    }
    public void mouseClicked(MouseEvent e)
    {
      if (_afficherGrille)
        {
          // On d�termine la case s�lectionn�e
          Point p = pointToCase(e.getPoint());
          if  ( (p!=null) && (_actions!=null) )
            {
              _actions.pointerCaseGrille((int)p.x,(int)p.y,_ihm);
            }
        }
      else
        {
          Point p = e.getPoint();
          if (_actions!=null)
            {
              Object obj =  pointToPolygone(e.getPoint());
              _actions.pointerCanvas(obj,(int)p.x,(int)p.y,_ihm);
                            
            }
        }
    }

  }

    

} // Fin de CanvasIHM



// ===============================================================
// Classe privee de fermeture de la fenetre
// ===============================================================
// L'adaptateur d'une window
//
class GrilleWindowAdapter extends WindowAdapter
{
  // On ne s'interesse que a l'action de fermeture de
  // la fenetre
  public void windowClosing(WindowEvent e) 
  {
    System.exit(0);
  }
}




        