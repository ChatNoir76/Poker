package fr.cnam.main;

import java.util.*;
import java.awt.*;
import javax.swing.*;

import fr.cnam.ihm.*;
import fr.cnam.tore.*;

/** Programme de test d'un formulaire
 */
public class TesterFormulaire implements FormulaireInt,ControlesCanvasIHM
{
    /** historique courant
         L'historique est utilise pour afficher les resultats
         des traitements **/
    private String historique;

    /** Couleur courante selectionne **/
    public int couleur;

  public JLabel image1;
    
    /** Constructeur
     **/
    public TesterFormulaire()
    {
        // Les attributs de la classe
        historique = "";
        couleur=1;

        // Affichage d'un formulaire qui calcule l'addition de deux entiers
        //
        Formulaire form = new Formulaire("TESTER",this,700,600,true);

        form.setTailleFonte(20);
        form.setHeightText(30);
        form.setAutoWidth(true);
        form.addLabel("Faire l'addition de deux entiers :");
        form.addText("val1","Valeur 1 :",true,"123");
        form.addText("val2","Valeur 2",true,"456");
        form.setWidthButtonCour(150);
        form.addButton("add","Additionner");
        form.addLabel("");


        // Selection d'une valeur dans une liste de valeurs dans un ListScroll
        //
        String[] values = { "la belle de nuit", "la fille de l'air","le garçon manqué","abcdefghijklmnopqrstuvwxyz","111","2222","3333"};
        form.addListScroll("LIST","Zone ",true,values,200,100);
        form.addButton("SELECTION","SELECTION");

        // Selection d'une valeur dans un popup menu
        //
        form.addLabel("");
        String[] l = {"HOMME","FEMME","ENFANT"};
        form.addListeChoix("CHOIX","Choisir",l,true,"FEMME");

        // Choisir un fichier texte et afficher son contenu
        //
        form.addLabel("");
        form.addButton("CHOISIR_FICHIER","Choisir un fichier");

        int xpos = form.getXCour();
        int ypos= form.getYCour();

        form.setPosition(300,10);
        form.addZoneText("zone","Historique",true,"",300,500);


        form.addButton("exit","Fermer la fenetre");
        form.setButtonFermer("exit");

        image1 = form.addImage("IMAGE 1",xpos,ypos,71,96,"images/cards_gif/ck.gif");

        
        form.afficher();   // Afficher le formulaire


        // Un autre exemple de formulaire pour montrer les possibilités de positionnement
        //
        Formulaire form2 = new Formulaire("TESTER",this,1200,600,true);
        
        form2.setPosition(10,10);
        form2.horizontal();
        form2.addText("Exemple","Exemple",true,"ABCDE");
        form2.addPosition(50,form2.getYCour());
        form2.addText("Champ1","Champ1",true,""); 
        form2.dessous(10);
        form2.vertical();
        form2.setWidthButtonCour(50);
        form2.addButton("B1","B1"); 
        form2.addButton("B2","B2");
        form2.horizontal();
        form2.addButton("B3","B3");
        form2.addButton("B4","B4"); //x=100 et en dessous par defaut
        form2.addButton("B5","B5"); 
        form2.dessous(30);
        form2.vertical();
        form2.addText("Champ2","Champ2",false,"222"); // non editable
        form2.addText("Champ3","Champ3",true,"33333");
        form2.setPosition(300,100);
        form2.vertical();
        form2.addZoneText("z1","Zone 1",true,"",300,100);
        form2.addZoneText("z2","Zone 2",true,"",100,100);
        form2.addText("z4","Champ4",true,"33333");
        form2.setPosition(650,10);
        form2.addGrilleIHM(10,10,20,this);
        form2.setWidthButtonCour(150);
        form2.addButton("Couleur1","Couleur1");
        form2.addButton("Couleur2","Couleur2");
        form2.addButton("Couleur3","Couleur3");
        form2.addButton("Couleur4","Couleur4");
        form2.afficher();

    }  // Fin du constructeur

    /** Recuperation des actions dans les boutons des
       deux formulaires
       @param form Le formulaire utilise
       @param nomSubmit nom du bouton selectionne
    **/
    public void submit(Formulaire form,String nomSubmit)
    {
        if (nomSubmit.equals("add"))
            {
                try{
                    String s1 = form.getValeurChamp("val1");
                    String s2 = form.getValeurChamp("val2");
                    int v1 = Integer.parseInt(s1);
                    int v2 = Integer.parseInt(s2);
                    
                    historique = historique+String.format("%5d + %5d = %7d\n",v1,v2,v1+v2);
                }catch(Exception ex)
                    {
                        historique = historique+ex.getMessage()+"\n";
                    }
            }
        if (nomSubmit.equals("SELECTION"))
            {
                historique = historique + ">> "+ form.getValeurChamp("LIST")+"\n";
            }
        if (nomSubmit.equals("CHOIX"))
            {
                String choix =  form.getValeurChamp("CHOIX");
                historique = historique + "CHOIX:"+choix+"\n";
            }

        if (nomSubmit.equals("B1"))
            {
                form.desactiver("B3");
            }
        if (nomSubmit.equals("B2"))
            {
                form.activer("B3");
            }
        if (nomSubmit.equals("B4"))
            {
                form.activer("Champ2");
            }
        if (nomSubmit.equals("B5"))
            {
                form.desactiver("Champ2");
            }
        if (nomSubmit.equals("Couleur1")) couleur=1;
        if (nomSubmit.equals("Couleur2")) couleur=2;
        if (nomSubmit.equals("Couleur3")) couleur=3;
        if (nomSubmit.equals("Couleur4")) couleur=4;

        if (nomSubmit.equals("CHOISIR_FICHIER"))
            {
                try{
                    String fichier = form.choisirFichier("../fr/cnam/ihm");
                    historique = historique + "NOM FICHIER : "+fichier +"\n";
                    String[] lignes = form.lireFichierTexte(fichier);
                    for(String s:lignes)
                        {
                            historique = historique + s +"\n";
                        }
                }
                catch(Exception ex)
                    {
                        historique = historique + ex + "\n";
                    }
                
            }

        if (nomSubmit.equals("IMAGE 1"))
          {
            historique = historique + "Click IMAGE 1\n";
            form.setImage(image1,"images/cards_gif/d8.gif");
          }

        // Mise a jour de la zone avec l'historique courant
        form.setValeurChamp("zone",historique);
    }

    /** Methode appele quand on clique dans une des cases de la grille
     **/
    public void pointerCaseGrille(int xCase,int yCase,CanvasIHM ihm)
    {
        //point = new Point(xCase,yCase);
        ihm.setMarque(couleur,xCase,yCase);
    }

    /** Pas utilise ici car pas de Canvas
     **/
    public void pointerCanvas(Object target,int x,int y,CanvasIHM ihm)
    {
    }

    /** Methode main
        Pour lancer le programme de test
     **/
    public static void main(String... args)
    {
        new TesterFormulaire();
    }
}
