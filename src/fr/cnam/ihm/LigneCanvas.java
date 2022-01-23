package fr.cnam.ihm;

import java.awt.*;

/** Classe de définition d'une ligne dessinée dans le canvas
 **/
public class LigneCanvas extends ObjetCanvas
{
    /** couleur de la ligne (de 1 à 10) **/
    public int marque;
    /** Ressources graphiques **/
    public RessourcesCanvas rc;
    /** 1er point de la ligne **/
    public Point p1;
    /** 2eme point de la ligne **/
    public Point p2;
    
    /**
       Création d'une ligne
       @param marque numéro de couleur (de 1 à 10)
       @param x1 coord X du 1er point
       @param y1 coord Y du 1er point
       @param x2 coord X du 2eme point
       @param y2 coord Y du 2eme point
    **/
    public LigneCanvas(int marque,int x1,int y1,int x2,int y2)
    {
        this.marque=marque;
        this.rc=null;
        p1=new Point();
        p2=new Point();
        p1.x=x1;
        p1.y=y1;
        p2.x=x2;
        p2.y=y2;
    }

    /** Création d'une ligne
        @param rc ressources graphiques
        @param x1 coord X du 1er point
        @param y1 coord Y du 1er point
        @param x2 coord X du 2eme point
        @param y2 coord Y du 2eme point
    **/
    public LigneCanvas(RessourcesCanvas rc,int x1,int y1,int x2,int y2)
    {
        this.marque=-1;
        this.rc = rc;
        p1=new Point();
        p2=new Point();
        p1.x=x1;
        p1.y=y1;
        p2.x=x2;
        p2.y=y2;
    }
}


