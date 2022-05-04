package fr.cnam.ihm;

import java.lang.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.io.*;


/**
   Classe de définition d'un formulaire JAVA permettant de faire à 
   minima une IHM Java pour saisir des informations et faire des 
   actions via des boutons.<BR>
   Pour cela, il faut que l'applicatif implémente les méthodes de 
   l'interface FormulaireInt.<BR><BR>
*/
public class Formulaire
{
  private FormulaireInt             app;
  private JFrame                    frame;
  private int                       widthFrame;
  private int                       heightFrame;
  private JPanel                    panelPP;
  private ArrayList<JButton>        buttons;
  private String                    buttonFermer;
  private Exception                 exceptionForm;
  private int                       xCour;
  private int                       yCour;
  private int                       widthLabelCour;
  private int                       widthGapCour;
  private int                       widthTextCour;
  private int                       widthButtonCour;
  private boolean                   sensHorizontal;
  private boolean                   sensVertical;
  private String                    font;     
  private boolean                   autoWidth;

  private int tailleFonte  = 11;
  private int heightText   = 20;

  // Les éléments IHM du formulaire
  private Hashtable<String,JComponent>  elements;
    
  /**
     Constructeur d'un formulaire.<br>
     @param titre Titre affiché dans le bandeau de la fenetre
     @param app Un objet dont la classe implémente l'interface FormualaireInt
     @param width longueur du formulaire
     @param height hauteur du formulaire
  */
  public Formulaire(String titre, 
                    FormulaireInt app,
                    int width,
                    int height)
  {
    initFormulaire(titre,app,width,height,true);
  }

  /**
     Constructeur d'un formulaire.<br>
     @param titre Titre affiché dans le bandeau de la fenetre
     @param app Un objet dont la classe implémente l'interface FormualaireInt
     @param width longueur du formulaire
     @param height hauteur du formulaire
     @param avecFrame si false alors pas de frame cree : que le panel. Ceci permet d'incruster un formulaire dans une IHM existante (un autre formulaire)
  */
  public Formulaire(String titre, 
                    FormulaireInt app,
                    int width,
                    int height,
                    boolean avecFrame)
  {
    initFormulaire(titre,app,width,height,avecFrame);
  }

  private void initFormulaire(String titre, 
                              FormulaireInt app,
                              int width,
                              int height,
                              boolean avecFrame)
  {
    this.app             = app;
    if (avecFrame)
      {
        this.frame           = new JFrame(titre);
        this.widthFrame      = width;
        this.heightFrame     = height;
      }
    else
      this.frame       = null;
    this.buttonFermer    = "";
    this.font            = "Courier";
        
    this.panelPP     = new JPanel();
    this.panelPP.setLayout(null);

    if (this.frame != null)
      {
        this.frame.add(panelPP);
        this.frame.addWindowListener(new FormulaireWindowListener());
      }

    this.xCour               = 0;
    this.yCour               = 0;
    this.widthLabelCour      = 100;
    this.widthGapCour        = 0;
    this.widthTextCour       = 100;
    this.widthButtonCour     = 100;
    this.sensVertical        = true;
    this.sensHorizontal      = false;
    this.autoWidth           = true;       

    this.elements = new Hashtable<String,JComponent>();
  }

  /** Initialise l'applicatif.<br>Cette méthode est utilisée dans le cas où quand on crée le formulaire on ne connait pas encore l'applicatif qui gère le formulaire. On appelle alors cette méthode pour initialiser l'applicatif du formulaire.
      @param app Un objet qui implémente l'interface Formulaireint */
  public void setApp(FormulaireInt app)
  {
    this.app = app;
  }

  /**
     Retourne le panel principal du formulaire
     @return panel principal du formulaire
  */
  public JPanel getPanel()
  {
    return this.panelPP;
  }

  /** Retourne la position courante en X de la position des éléments
      @return int la valeur X */
  public int getXCour(){return xCour;}

  /** Retourne la position courante en Y de la position des éléments
      @return int la valeur Y */
  public int getYCour(){return yCour;}

  /** Change la position courante 
      @param x position en abscisse (horizontal) de la fenetre 
      @param y position en ordonnée (vertical) de la fenetre 
  */
  public void setPosition(int x,int y){xCour=x;yCour=y;}

  /** Change la position courante par delta
      @param deltax position en abscisse (horizontal) de la fenetre 
      @param deltay position en ordonnée (vertical) de la fenetre 
  */
  public void addPosition(int deltax,int deltay)
  {
    xCour=xCour+deltax;
    yCour=yCour+deltay;
  }

  /** Change la taille de la fonte courante d'un text ou d'un lable ou d'un bouton
      @param int taille de la fonte (8, 9, 10, 11, ....)
  */
  public void setTailleFonte(int taille){
    this.tailleFonte = taille;
  }

  /** Change la hauteur courante d'un text ou d'un label ou bouton
      @param int hauteur en pixel
   */
  public void setHeightText(int height){
    this.heightText=height;
  }

  /** Change la position courante en passant a la ligne suivante en position posx
      @param posx nouvelle position courante en x */
  public void dessous(int posx)
  {
    this.xCour = posx;
    this.yCour = this.yCour+this.heightText+3;
  }

  /** change le sens en HORIZONTAL */
  public void horizontal()
  {
    this.sensHorizontal=true;
    this.sensVertical=false;
  }

  /** change le sens en VERTICAL */
  public void vertical()
  {
    this.sensHorizontal=false;
    this.sensVertical=true;
  }

  /** Le contour des textes de Label Text et Button sont autoamtiquement ou pas ajuster a la taille du texte
      @param auto boolean
  */
  public void setAutoWidth(boolean auto){autoWidth=auto;}


  /** Change la largeur courante des label
      @param width nouvelle largeur courante
  */
  public void setWidthLabelCour(int width){widthLabelCour=width;}

  /** Change la largeur courante des textes de saisi
      @param width nouvelle largeur courante
  */
  public void setWidthTextCour(int width){widthTextCour=width;}


  /** Change la largeur courante de séparation entre le label et le texte des champs de saisi
      @param width nouvelle largeur courante
  */
  public void setWidthGapCour(int width){widthGapCour=width;}

  /** Change la largeur courante des boutons
      @param width nouvelle largeur courante
  */
  public void setWidthButtonCour(int width){widthButtonCour=width;}



  /** Affichage du formulaire.<br>
  */
  public void afficher()
  {
    this.frame.setPreferredSize(new Dimension(this.widthFrame+15,this.heightFrame+40));
    this.frame.pack();
    this.frame.show();
  }

  /** Affichage du formulaire en x,y de l'ecran
      @param x position en X du formulaire
      @param y position en Y du formulaire
  */
  public void afficher(int x,int y)
  {
    this.frame.setLocation(x,y);
    afficher();
  }

  /** Permet de fermer le formulaire
   */
  public void fermer()
  {
    frame.dispose();
  }


  /** Ajout d'un label dans le formulairel.<br>
      @param label Valeur du label
  */
  public void addLabel(String label)
  {
    int xChamp=this.xCour;
    int yChamp=this.yCour;
    int widthLabel=this.widthLabelCour;

    JLabel l = new JLabel(label);

    JPanel p = new JPanel();
    p.setLayout(null);
    Font f = new Font(font,Font.BOLD,tailleFonte);
    l.setFont(f);

    if (autoWidth) widthLabel = l.getFontMetrics(f).stringWidth(label); 

    l.setBounds(0,0,widthLabel,this.heightText);

    p.add(l);

    p.setBounds(xChamp,yChamp,widthLabel,this.heightText);

    this.panelPP.add(p);
    this.panelPP.repaint();
    if (this.frame!=null)
      this.frame.repaint();
    
    if (sensHorizontal) 
        this.xCour=this.xCour+this.widthLabelCour;
      if (sensVertical)
        this.yCour=yChamp+this.heightText+3;
  }
    


  /** Ajout dans le formulaire un texte de saisie composé d'un label 
      et d'une zone de saisie.<br>
      @param nom Le nom du champ
      @param label Chaine qui précéde la zone de saisie
      @param editable détermine si la zone de saisie est éditable 
      @param value valeur initiale dans la zone de saisie
  */
  public void addText(String nom,
                      String label,
                      boolean editable,
                      String value)
  {
    int xChamp=this.xCour;
    int yChamp=this.yCour;
    int widthLabel=this.widthLabelCour;
    int widthGap=this.widthGapCour;
    int widthText=this.widthTextCour;

    JLabel l = new JLabel(label);
    JTextField tf = new JTextField();
    tf.setEditable(editable);
    tf.setText(value);
    elements.put(nom,tf);
        

    JPanel p = new JPanel();
    p.setLayout(null);
    Font f = new Font(font,Font.BOLD,tailleFonte);
    l.setFont(f);
    tf.setFont(f);

    if (autoWidth) widthLabel = l.getFontMetrics(f).stringWidth(label)+3; 

    l.setBounds(0,0,widthLabel,this.heightText);
    tf.setBounds(widthLabel+widthGap,0,widthText,this.heightText);

    p.add(l);
    p.add(tf);

    p.setBounds(xChamp,yChamp,widthLabel+widthGap+widthText,this.heightText);

    this.panelPP.add(p);
    this.panelPP.repaint();
    if (this.frame!=null) this.frame.repaint();
    
    if (this.sensHorizontal)
        this.xCour=xChamp+widthLabel+widthGap+widthText+3;
      if (this.sensVertical)
        this.yCour=yChamp+this.heightText+3;
  }

  /** Ajout dans le formulaire d'une liste de choix de saisie composé d'un label 
      et d'une liste de choix.<br>
      @param nom Le nom du champ
      @param label Chaine qui précéde la zone de saisie
      @param liste tableau de la liste de choix
      @param action si vrai alors l'action est réalisée dès que l'on clique
      @param valinit valeur inital sélectionnée
  */
  public void addListeChoix(String nom,
                            String label,
                            String[] liste,
                            boolean action,
                            String valinit)
  {
    int xChamp=this.xCour;
    int yChamp=this.yCour;
    int widthLabel=this.widthLabelCour;
    int widthGap=this.widthGapCour;
    int widthText=this.widthTextCour;

    JLabel l = new JLabel(label);
    JComboBox<String> cb = new JComboBox<String>();
    for(String s:liste)cb.addItem(s);
    elements.put(nom,cb);

    for(int i=0;i<cb.getItemCount();i++)
      if(cb.getItemAt(i).equals(valinit))
        cb.setSelectedIndex(i);

    if(action)cb.addActionListener(new SubmitListener(this,nom));
        

    JPanel p = new JPanel();
    p.setLayout(null);
    Font f = new Font(font,Font.BOLD,tailleFonte);
    l.setFont(f);
    cb.setFont(f);

    if (autoWidth) widthLabel = l.getFontMetrics(f).stringWidth(label)+3; 

    l.setBounds(0,0,widthLabel,this.heightText);
    cb.setBounds(widthLabel+widthGap,0,widthText,this.heightText);
        

    p.add(l);
    p.add(cb);

    p.setBounds(xChamp,yChamp,widthLabel+widthGap+widthText,this.heightText);

    this.panelPP.add(p);
    this.panelPP.repaint();
    if (this.frame!=null) this.frame.repaint();
    
    if (this.sensHorizontal)
        this.xCour=xChamp+widthLabel+widthGap+widthText+3;
      if (this.sensVertical)
        this.yCour=yChamp+this.heightText+3;
  }

  /** Ajout dans le formulaire d'un texte de saisie multi-ligne composé d'un label 
      et d'une zone de saisie.<br>
      @param nom Le nom du champ
      @param label Chaine qui précéde la zone de saisie
      @param editable détermine si la zone de saisie est éditable 
      @param value valeur initiale dans la zone de saisie 
      @param width longueur de la zone de texte
      @param height hauteur de la zone de texte
  */
  public void addZoneText(String nom,
                          String label,
                          boolean editable,
                          String value,
                          int width,
                          int height)
  {
    int xChamp=this.xCour;
    int yChamp=this.yCour;

    if (this.sensHorizontal)
      this.xCour=xChamp + width+3;
    if (this.sensVertical)
      this.yCour=yChamp+height+3;
        
    JLabel l = new JLabel(label);
    l.setFont(new Font(font,Font.BOLD,tailleFonte));
    l.setBounds(0,0,width,this.heightText);

    JTextArea tf = new JTextArea();
    tf.setEditable(editable);
    tf.setText(value);
    tf.setFont(new Font(font,Font.BOLD,tailleFonte));

    elements.put(nom,tf);

    JScrollPane scrollbar = new JScrollPane(tf);
    scrollbar.setBounds(0,0+this.heightText+3,width,height-this.heightText-3);
    scrollbar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
    scrollbar.setPreferredSize(new Dimension(width,height-this.heightText-3));

    JPanel p = new JPanel();
    p.setLayout(null);

    p.add(l);
    p.add(scrollbar);
        
    p.setBounds(xChamp,yChamp,width,height);

    this.panelPP.add(p);
    this.panelPP.repaint();
    if (this.frame!=null) this.frame.repaint();
  }
    
  /** Ajout dans le formulaire d'une liste scrollable de valeurs.<br>
      @param nom Le nom de la liste scrollable
      @param titre Titre qui précéde la liste
      @param editable détermine si la zone de saisie est éditable 
      @param values valeur initiale dans la zone de saisie 
      @param width longueur de la zone (ou -1)
      @param height hauteur de la zone 
  */
  public void addListScroll(String nom,
                            String titre,
                            boolean editable,
                            String[] values,
                            int width,
                            int height)
  {
    int xChamp=this.xCour;
    int yChamp=this.yCour;

    if (this.sensHorizontal)
      this.xCour=xChamp + width+3;
    if (this.sensVertical)
      this.yCour=yChamp+height+3;
        
    JLabel titrel = new JLabel(titre);
    titrel.setFont(new Font(font,Font.BOLD,tailleFonte));
    titrel.setBounds(0,0,width,this.heightText);

    JList liste = new JList(new DefaultListModel());
    liste.setFont(new Font(font,Font.BOLD,tailleFonte));
    JScrollPane scroll = new JScrollPane(liste);
    scroll.setBounds(0,0+this.heightText+3,width,height-this.heightText-3);

    liste.setEnabled(editable);
    Vector<String> v = new Vector<String>();
    if (values!=null)
      Collections.addAll(v,values);

    elements.put(nom,liste);


    JPanel p = new JPanel(new BorderLayout());
    p.add(titrel,BorderLayout.NORTH);
    p.add(scroll,BorderLayout.CENTER);

    p.setBounds(xChamp,yChamp,width,height);

    liste.setListData(v);

    this.panelPP.add(p);
    this.panelPP.repaint();
    if (this.frame!=null)this.frame.repaint();
  }

  /** Ajout d'une image
      @param le nom de l'image (nom de l'action si clique dans l'image)
      @param x position en x de l'image
      @param y position en y de l'image
      @param width taille en longueur de l'image
      @param height taille eh hauteur de l'image
      @param path path du fichier de l'image (.jpg, .gif, .bmp, ....)
      @return JLabel le label créé contenant l'image
   */
  public JLabel addImage(String nom,int x,int y,int width,int height,String pathImage)
  {
    JLabel label = new JLabel();
    label.setBounds(x,y,width,height);
    ImageIcon path = new ImageIcon(pathImage);
    Image img = path.getImage();
    Image newImg = img.getScaledInstance(width,height, Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    label.setIcon(image);

    this.panelPP.add(label);

    label.addMouseListener(new SubmitListener(this,nom));

    return label;
  }

  /** Changer l'image d'un JLabel
      @param label le JLabel dont on veut changerl'image
      @param pathImage path de l'image
   */
  public void setImage(JLabel label,String pathImage)
  {
    Rectangle r = label.getBounds(null);
    ImageIcon path = new ImageIcon(pathImage);
    Image img = path.getImage();
    Image newImg = img.getScaledInstance((int)(r.getWidth()),(int)(r.getHeight()), Image.SCALE_SMOOTH);
    ImageIcon image = new ImageIcon(newImg);
    label.setIcon(image);
  }
    
  /** Designe le bouton qui ferme la fenetre
      @param nomButton le nom du bouton
  */
  public void setButtonFermer(String nomButton)
  {
    this.buttonFermer = nomButton;
  }

  /** Ajout dans le formulaire d'un bouton.<br>
      @param nom Le nom du bouton
      @param button Texte du bouton 
  */
  public void addButton(String nom,
                        String button)
  {
    int xChamp=this.xCour;
    int yChamp=this.yCour;
    int width =this.widthButtonCour;
        
    JButton b = new JButton(button);
    Font f = new Font(font,Font.BOLD,tailleFonte);
    b.setFont(f);
    elements.put(nom,b);
    b.setMargin(new Insets(0, 0, 0, 0));
    if (autoWidth) width = b.getFontMetrics(f).stringWidth(button)+20;

    b.setBounds(xChamp,yChamp,width,this.heightText);

    b.setPreferredSize(new Dimension(width,this.heightText));
    b.addActionListener(new SubmitListener(this,nom));

    this.panelPP.add(b);
    this.panelPP.repaint();
    if (this.frame!=null)this.frame.repaint();
    
    if (this.sensHorizontal)
        this.xCour=xChamp+width+3;
      if (this.sensVertical)
        this.yCour=yChamp+this.heightText+3;

  }

  /** Ajout d'un Panel dans le formulaire
      @param panel le panel a ajouter
      @param width largeur du panel
      @param height hauteur du panel
  */
  public void addPanel(JPanel panel,int width, int height)
  {
    int posx = this.xCour;
    int posy = this.yCour;

    if (this.sensHorizontal)
      this.xCour=posx+width+3;
    if (this.sensVertical)
      this.yCour=posy+height+3;

    panel.setBounds(posx,posy,width,height);
    this.panelPP.add(panel);
    this.panelPP.repaint();
    if (this.frame!=null)this.frame.repaint();
  }

  /** Methode qui ajoute un canvas de grille
      @param nbLigne nombre de ligne de la grille
      @param nbColonne nombre de colonne de la grille
      @param tailleCase taille de la case (en pixel)
      @param controle objet dont la classe surcharge les méthodes de l'interface ControlesCanvasIHM. Il permet de réaliser les actions réalisées dans la grille.
      @return retourne la grille d'IHM
  */
  public CanvasIHM addGrilleIHM(int nbLigne,
                                int nbColonne,
                                int tailleCase,
                                ControlesCanvasIHM controle)
  {
    CanvasIHM grille;
        
    grille = new CanvasIHM(nbLigne,nbColonne,tailleCase);
    grille.setActions(controle);
    addPanel(grille.getPanel(),
             grille.getWidth()+30,grille.getHeight()+30);
        
    return(grille);
  }

  /** Methode qui ajoute un canvas de grille
      @param width largeur du canvas
      @param height hauteur du canvas
      @param controle objet dont la classe surcharge les méthodes de l'interface ControlesCanvasIHM. Il permet de réaliser les actions réalisées dans la grille.
      @return retourne la grille d'IHM
  */
  public CanvasIHM addCanvasIHM(int width,
                                int height,
                                ControlesCanvasIHM controle)
  {
    CanvasIHM grille;
        
    grille = new CanvasIHM(width,height);
    grille.getPanel().setBackground(Color.yellow);
    grille.setActions(controle);
    addPanel(grille.getPanel(),
             grille.getWidth()+30,grille.getHeight()+30);

    return(grille);
  }

  /** Méthode qui retourne la valeur d'un champ.
      @param nom Le nom du champ
      @return valeur la nouvelle valeur du champ */
  public String getValeurChamp(String nom)
  {
    String ret="";
    JComponent comp = elements.get(nom);
            
    try{
      if ( comp instanceof JList )
        {
          JList l = (JList)comp;
          if (! l.isSelectionEmpty())
            ret = (String)(l.getSelectedValue());
        }
      if (comp instanceof JComboBox )
        {
          JComboBox cb = (JComboBox)comp;
          ret=(String)(cb.getSelectedItem());
        }
      else
        {
          JTextComponent ct = (JTextComponent)comp;
          ret=ct.getText();
        }
    }catch(Exception ex){}
    return ret;
  }

  /** Méthode qui change la valeur d'un champ.
      @param nom Le nom du champ
      @param valeur la nouvelle valeur du champ */
  public void setValeurChamp(String nom,String valeur)
  {
    JComponent comp = elements.get(nom);
    try{
      JTextComponent ct = (JTextComponent)comp;
      ct.setText(valeur);
    }catch(Exception ex){}
    try{
      JComboBox cb = (JComboBox)comp;
      for(int i=0;i<cb.getItemCount();i++)
        if(cb.getItemAt(i).equals(valeur))
          cb.setSelectedIndex(i);
    }catch(Exception ex){}
  }


  /** Méthode qui change les valeurs d'une liste de scroll
      @param nom Le nom du champ
      @param values la nouvelle liste de valeur */
  public void setListData(String nom,String[] values)
  {
    try{
      JComponent comp = elements.get(nom);
      JList l = (JList)comp;
      Vector<String> v = new Vector<String>();
      if (values!=null)
        Collections.addAll(v,values);
      l.setListData(v);
    }catch(Exception ex){}
  }


  // Classe d'action des boutons du formulaire
  class SubmitListener extends MouseAdapter implements ActionListener
  {
    private Formulaire form;
    private String     nomSubmit;

    public SubmitListener(Formulaire form, String nomSubmit)
    {
      this.form      = form;
      this.nomSubmit = nomSubmit;
    }

    public void actionPerformed(ActionEvent e)
    {
      try{
        if (app!=null)   // NE PAS UTILISER this.app : on est dans une inner class
          app.submit(form,nomSubmit);
                
        if (this.nomSubmit.equals( buttonFermer))
          {
            frame.dispose();
          }
      }
      catch(Exception ex)
        {
          ex.printStackTrace();
        }
    }

    public void mouseClicked(MouseEvent e)
    {
      try{
        if (app!=null)   // NE PAS UTILISER this.app : on est dans une inner class
          app.submit(form,nomSubmit);
        
        if (this.nomSubmit.equals( buttonFermer))
          {
            frame.dispose();
          }
      }
      catch(Exception ex)
        {
          ex.printStackTrace();
        }
    }
  }
  
  // Classe pour fermer le formulaire
  class FormulaireWindowListener extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      frame.dispose();
    }
  }


  /**
     desactiver un des composants du formulaire
     @param nom Nom du composant
  */
  public void desactiver(String nom) throws RuntimeException
  {
    JComponent e = elements.get(nom);
    if (e!=null)
      {
        if (e.getClass().getName().equals("javax.swing.JButton"))
          e.setEnabled(false);
        if (e.getClass().getName().equals("javax.swing.JTextField"))
          {
            JTextField tf = (JTextField)e;
            tf.setEditable(false);
          }
      }
    else
      throw new RuntimeException(nom+" n'existe pas");
  }

  /**
     activer un des composants du formulaire
     @param nom Nom du composant
  */
  public void activer(String nom) throws RuntimeException
  {
    JComponent e = elements.get(nom);
    if (e!=null)
      {
        if (e.getClass().getName().equals("javax.swing.JButton"))
          e.setEnabled(true);
        if (e.getClass().getName().equals("javax.swing.JTextField"))
          {
            JTextField tf = (JTextField)e;
            tf.setEditable(true);
          }
      }
    else
      throw new RuntimeException(nom+" n'existe pas");
  }

  /**
     Cette methode permet de choisir un fichier sur le disque
     @param directory nom relatif ou absolu du répertoire initial
     @return retourne le nom absolu fichier sélectionné  ou null si annuler
  **/
  public String choisirFichier(String directory)
  {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setCurrentDirectory(new File(directory));
    int result = fileChooser.showOpenDialog(frame);
    if (result == JFileChooser.APPROVE_OPTION) {
      File selectedFile = fileChooser.getSelectedFile();
      return selectedFile.getPath();
    }
    else
      return null;
  }

  /**
     Cette methode statique permet de lire un fichier texte
     @param nomFichier le path relatif ou absolu du fichier a lire
     @return un tableau de caractere contenant chaque ligne du fichier
  **/
  public static String[] lireFichierTexte(String nomFichier)
  {
    try{
      File fichier = new File(nomFichier);
      if (! fichier.exists())
        {
          throw new RuntimeException("Le fichier "+nomFichier+" n'existe pas ou n'est pas accessible");
        }
      FileInputStream fis = new FileInputStream(new File(nomFichier));
            
      byte[] buffer = new byte[(int)fichier.length()];
      fis.read(buffer);
      fis.close();
      String str = new String(buffer);

      // On enleve le caractère '\r' code 13 qui est ajouté en Windows
      // Les fins de ligne dans un fichier texte créé sous Windows
      //  se termine par \r\n.
      // Il faut enlever le \r car il a des effets perturbant sur
      //  la méthode System.out.print et est pris comme un caractère de plus
      //  qu'il faut éliminer
      //  
      String texte = str.replaceAll(""+(char)(13),"");
            
      // Les lignes du fichier sont mises dans un tableau
      //
      String[] mots = texte.split("\n");

      return(mots);
    }
    catch(Exception ex)
      {
        throw new RuntimeException("Erreur dans la lecture du fichier :"+nomFichier);
      }
  }
}


    
