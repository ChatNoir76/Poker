package fr.cnam.main;

import java.util.*;
import java.awt.Point;

import fr.cnam.ihm.*;
import fr.cnam.tore.*;

public class TesterGrilleIHM 
{
    /** Méthode de test de la classe.
        Ce programme crée une grille et crée un thread qui déplace une case.
        Les boutons permettent de tester le comportement du déplacement
    */
    public static void main(String... args)
    {
        TestThread t = new TestThread();

        // DÃ©marrage du test
        t.start();
    }


}

// ===============================================================
/** Classe utilisée pour tester la classe Tore et la grille
 */
class TestThread extends Thread implements FormulaireInt
{
    private CanvasIHM grille;
    private int mode;
    private Point point;

    public TestThread()
    {
        // MOde de deplacement de l'agent par defaut
        mode=1;
        point = new Point(30,30);

        // Creation d'un formuaire qui contient des boutons et la grille
        //
        Formulaire form = new Formulaire("TESTER",this,1200,800);

        // -- les boutons
        form.setWidthButtonCour(300);
        form.addButton("DEP_ALEAT","Dep aleat");
        form.addButton("LE_PLUS_PROCHE","Le plus proche des coins");
        form.addButton("VERS_5_5","Vers le point (5,5)");
        form.addButton("VERS_POINT","Vers le point designe");
        
        // -- la grille
        // Pour verifier les actions dans la grille.
        // Etant donne que la classe herite deja de Thread on ne peut pas la faire heriter de
        //  AdaptaterControlesCanvasIHM et mettre this dans l'appel de la methode setAction.
        // On utilise une inner classe TestActions qui va heriter de l'adaptater
        //  
        grille = form.addGrilleIHM(40,40,15,new TestActions());
        
        // Affichage du formulaire
        form.afficher(50,50);

        // On met des couleurs Ã  certaines case
        grille.setMarque(1,0,0);  // Couleur 1 en case (0,0)
        grille.setMarque(2,0,1);  // Couleur 1 en case (0,1)
        grille.setMarque(3,0,2);  // Couleur 1 en case (0,2)
        grille.setMarque(4,0,3);  // Couleur 1 en case (0,3)
        grille.setMarque(5,0,4);  // Couleur 1 en case (0,4)
        grille.setMarque(6,0,5);  // Couleur 1 en case (0,5)
        grille.setMarque(7,0,6);  // Couleur 1 en case (0,6)
        grille.setMarque(8,0,7);  // Couleur 1 en case (0,7)
        grille.setMarque(9,0,8);  // Couleur 1 en case (0,8)

    }

    // Le traitement du thread
    //
    public void run()
    {
        // Pour faire du calcul aleatoire
        Random  rdm = new Random();

        // Pour faire du calcul dans un espace mathematique de Tore
        Tore espace = new Tore(grille.getNbX(),grille.getNbY());

        
        // P
        int x=grille.getNbX()/2;
        int y=grille.getNbY()/2;
        grille.setMarque(1,x,y);

        //
        while(true)
            {
                // deplacement d'une case noire aleatoirement dans les 9 directions
                if (mode==1)
                    {
                        int sensX = rdm.nextInt(3)-1;
                        int sensY = rdm.nextInt(3)-1;

                        // La methode deplacer applique le sensX sensY a x et y en tenant compte
                        //   que nous sommes dans un Tore
                        Point p = espace.deplacer(x,y,sensX,sensY);

                        // On "depalce" le carre noir dans la grille
                        grille.setMarque(0,x,y);
                        x = (int)p.x;
                        y = (int)p.y;
                        grille.setMarque(1,x,y);
                    }

                // deplacement vers le plus proche de certains points.
                // On a pris par exemple les 4 coins
                if (mode==2)
                    {
                        // On prend les 4 coins comme points
                        Vector points = new Vector();
                        points.add( (Point)(new Point(0,0) ));
                        points.add( (Point)(new Point(grille.getNbX()-1,0) ));
                        points.add( (Point)(new Point(0,grille.getNbY()-1) ));
                        points.add( (Point)(new Point(grille.getNbX()-1,grille.getNbY()-1) ));

                        // On determine le point plus proche inferieur a un seuil de 5
                        //
                        Point pointProche = espace.lePlusProche(new Point(x,y),points,5);

                        // Si il n'y en a pas alors rien
                        if (pointProche != null)
                            {
                                // Determination du sens de deplacement entre la position courante
                                //  et le point le plus proche
                                Point sens = espace.sensTore(new Point(x,y),pointProche);

                                Point p = espace.deplacer(x,y,sens.x,sens.y);

                                grille.setMarque(0,x,y);
                                x = p.x;
                                y = p.y;
                                grille.setMarque(1,x,y);
                            }
                    }

                // deplacement de la case noire de 1 pas de case en direction du point (5,5)
                //
                if (mode==3)
                    {
                        // Cette mÃ©thode determine le vecteur unitaire dans un espace de Tore du
                        //  point (x,y) vers le point du milieur de la grille
                        //
                        Point sens = espace.sensTore(new Point(x,y),new Point(5,5));

                        // Le vecteur unitaire est un sens de deplacement
                        Point p = espace.deplacer(x,y,sens.x,sens.y);

                        // On "depalce" le carre noir dans la grille
                        grille.setMarque(0,x,y);
                        x = (int)p.x;
                        y = (int)p.y;
                        grille.setMarque(1,x,y);
                    }

                // deplacement de la case noire de 1 pas de case en direction du point (30,30)
                //
                if (mode==4)
                    {
                        // Cette mÃ©thode determine le vecteur unitaire dans un espace de Tore du
                        //  point (x,y) vers le point 
                        //
                        Point sens = espace.sensTore(new Point(x,y),point);

                        // Le vecteur unitaire est un sens de deplacement
                        Point p = espace.deplacer(x,y,sens.x,sens.y);

                        // On "depalce" le carre noir dans la grille
                        grille.setMarque(0,x,y);
                        x = (int)p.x;
                        y = (int)p.y;
                        grille.setMarque(1,x,y);
                    }
                
                try{Thread.sleep(200);}catch(Exception ex){};
            }
    }

    // La methode a implementer de l'interface FormualireInt pour le formulaire
    //
    public void     submit(Formulaire form,String nom)
    {
        // Pour changer le comportement de deplacement
        //
        if (nom.equals("DEP_ALEAT")) mode = 1;
        if (nom.equals("LE_PLUS_PROCHE")) mode = 2;
        if (nom.equals("VERS_5_5")) mode = 3;
        if (nom.equals("VERS_POINT")) mode = 4;
    }

    // Etant donne que la classe herite deja de Thread on ne peut pas la faire heriter de
    //  AdaptaterControlesCanvasIHM. On cree donc une inner classe qui en herite et 
    //  
    class TestActions implements ControlesCanvasIHM
    {
        public void pointerCaseGrille(int xCase,int yCase,CanvasIHM ihm)
        {
            point = new Point(xCase,yCase);
            ihm.setMarque(4,xCase,yCase);
        }

        public void pointerCanvas(Object target,int x,int y,CanvasIHM ihm)
        {
        }

    }
}
