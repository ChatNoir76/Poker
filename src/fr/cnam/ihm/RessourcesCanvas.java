package fr.cnam.ihm;

import java.awt.*;

/** Classe de d�finition d'une ressources graphiques **/
public class RessourcesCanvas
{
    /** Couleur de background de l'objet **/
    private Color  background;
    /** Couleur de foreground de l'objet **/
    private Color  foreground;
    /** �paisseur de la ligne **/
    private int    line;
    
    /** Cr�ation d'une ressource.
        Par d�faut : background=blanc foreground=noir ligne=1 
    **/
    public RessourcesCanvas()
    {
        background = new Color(255,255,255);
        foreground = new Color(0,0,0);
        line = 1;
    }


    /** Mise � jour du background 
        @param c couleur
    **/
    public void setBackground(Color c){background=c;}

    /** Mise � jour du foreground
        @param c couleur
    **/
    public void setForeground(Color c){foreground=c;}

    /** Mise de l'�paisseur de la ligne
        @param epaisseur �paisseur de la ligne
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