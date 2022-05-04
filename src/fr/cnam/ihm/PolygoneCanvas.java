package fr.cnam.ihm;

import java.awt.*;
import java.util.*;

import fr.cnam.ihm.*;

/** Classe de dÃ©finition d'un polygone
 **/
public class PolygoneCanvas extends ObjetCanvas implements Comparable<PolygoneCanvas>
{
    /** marque de couleur de 1 à 10 **/
    public int marque;
    /** ressource graphique **/
    public RessourcesCanvas rc;
    /** les points du polygone **/
    public Point[] points;
    /** le polygone java **/
    public Polygon polygone;
    /** Position en 3D du polygone */
    public Position pos;
    /** Objet associé au polygone **/
    public Object object;
    
    /** Création d'un polygone dans le canvas
        @param o objet quelconque (voir pointerCanvas)
        @param p position en 3D du polygone
        @param marque couleur de 1 à 10
        @param points les points du polygone
    **/
    public PolygoneCanvas(Object o,Position p,int marque,Point... points)
    {
        this.object=o;
        this.points = points;
        this.marque=marque;
        this.rc=null;
        this.pos=p;
        int[] xpoints = new int[points.length];
        int[] ypoints = new int[points.length];
        for(int i=0;i<points.length;i++)
            {
                xpoints[i]=points[i].x;
                ypoints[i]=points[i].y;
            }
        this.polygone = new Polygon(xpoints,ypoints,points.length);
    }

    /** Création d'un polygone dans le canvas
        @param rc ressources graphiques du polygone
        @param o objet quelconque (voir pointerCanvas)
        @param p position en 3D du polygone
        @param points les points du polygone
    **/
    public PolygoneCanvas(RessourcesCanvas rc,Object o,Position p,Point... points)
    {
        this.object=o;
        this.points = points;
        this.marque=-1;
        this.rc = rc;
        this.pos=p;
        int[] xpoints = new int[points.length];
        int[] ypoints = new int[points.length];
        for(int i=0;i<points.length;i++)
            {
                xpoints[i]=points[i].x;
                ypoints[i]=points[i].y;
            }
        this.polygone = new Polygon(xpoints,ypoints,points.length);
    }

    /** méthode utilisé pour trier les polygones avant de les afficher.
        L'ordre est decroissante sur Z, Y puis Z.
        Cela garantit un effet 3D des polygones.
        @param p Le polygone a compara avec this
        @return retourne l'ordre (-1 inferieur 1 supérieur 0 égal)
    **/
    public int compareTo(PolygoneCanvas p)
    {
        if (pos.z<p.pos.z) return -1;
        else if (pos.z>p.pos.z) return 1;
        else if (pos.y>p.pos.y) return -1;
        else if (pos.y<p.pos.y) return 1;
        else if (pos.x<p.pos.x) return 1;
        else if (pos.x>p.pos.x) return -1;
        else return 0;
    }
}
