package fr.cnam.ihm;

import java.awt.*;

/** Classe de définition d'une ressources graphiques **/
public class RessourcesCanvas
{
    /** Couleur de background de l'objet **/
    private Color  background;
    /** Couleur de foreground de l'objet **/
    private Color  foreground;
    /** épaisseur de la ligne **/
    private int    line;
    
    /** Création d'une ressource.
        Par défaut : background=blanc foreground=noir ligne=1 
    **/
    public RessourcesCanvas()
    {
        background = new Color(255,255,255);
        foreground = new Color(0,0,0);
        line = 1;
    }


    /** Mise à jour du background 
        @param c couleur
    **/
    public void setBackground(Color c){background=c;}

    /** Mise à jour du foreground
        @param c couleur
    **/
    public void setForeground(Color c){foreground=c;}

    /** Mise de l'épaisseur de la ligne
        @param epaisseur épaisseur de la ligne
    **/
    public void setLine(int epaisseur){line=epaisseur;}

    /** Couleur de background de la ressource.
        @return couleur
    **/
    public Color getBackground(){return background;}

    /** Couleur de foreground de la ressource.
        @return couleur
    **/
    public Color getForeground(){return foreground;}

    /** Epaisseur de la ligne
        @return epaisseur
    **/
    public int getLine(){return line;}
    
}