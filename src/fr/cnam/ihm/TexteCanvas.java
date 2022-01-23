package fr.cnam.ihm;

import java.awt.*;

/** Classe de définition d'un texte dessiné dans le canvas
 **/
public class TexteCanvas extends ObjetCanvas
{
    /** Valeur du texte **/
    public String texte;
    /** Cooord du texte **/
    public Point p;
    /** Marque de couleur du texte (de 1 à 10) **/
    public int   couleur;  // de 1 à  10
    /** Ressources graphique du texte **/
    public RessourcesCanvas rc;
    /** font du texte **/
    public Font  font;
    
    /** Création d'un texte dans le canvas
        @param texte valeur du texte
        @param x position en x du texte
        @param y position en y du texte
        @param couleur la couleur du texte (de 1 à 10)
    **/
    public TexteCanvas(String texte,int x,int y,int couleur)
    {
        this.texte=texte;
        this.p=new Point();
        this.p.x=x;
        this.p.y=y;
        this.couleur=couleur;
        this.rc=null;
        this.font=null;
    }

    /** Création d'un texte dans le canvas
        @param rc ressources graphiques du texte
        @param texte valeur du texte
        @param x position en x du texte
        @param y position en y du texte
    **/
    public TexteCanvas(RessourcesCanvas rc,String texte,int x,int y)
    {
        this.texte=texte;
        this.p=new Point();
        this.p.x=x;
        this.p.y=y;
        this.couleur=-1;
        this.rc=rc;
        this.font=null;
    }
}

