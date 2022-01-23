package fr.cnam.ihm;

import java.io.*;

/** Classe de définition d'un position 3D (x,y,z)
 **/
public class Position
{
    /** coordonnée X **/
    public int x;
    /** coordonnée Y **/
    public int y;
    /** coordonnée Z **/
    public int z;

    /** Création d'un polygone vide */
    public Position(){}

    /** Création d'un polygone.
        @param x coordonnée en X
        @param y coordonnée en Y
        @param z coordonnée en Z
    **/
    public Position(int x,int y, int z)
    {
        this.x=x;this.y=y;this.z=z;
    }
    
    /** clone d'une Position 
        @return retourne le clone de l'objet 
    **/
    public Position clone()
    {
        return new Position(x,y,z);
    }

    /** Teste si deux positions ont égales
        @param p positionà tester
        @return vrai si égal sinon faux
     **/
    public boolean equals(Position p)
    {
        return (x==p.x)&&(y==p.y)&&(z==p.z);
    }

    /** Ecrire dans un flot de texte le polygone 
        @param ps flot d'écriture texte
        @throws Exception si erreur d'écriture
     **/
    public void ecrire(PrintStream ps) throws Exception
    {
        ps.println(String.format("%d;%d;%d",x,y,z));
    }

    /** Lire dans un flot de texte le polygone 
        @param br flot de lecture texte
        @throws Exception si erreur de lecture
     **/
    public void lire(BufferedReader br) throws Exception
    {
        String ligne = br.readLine();
        String[] ts = ligne.split("[;]");
        x=Integer.parseInt(ts[0]);
        y=Integer.parseInt(ts[1]);
        z=Integer.parseInt(ts[2]);
    }

    /** Position en chaine
        @return chaine
    **/
    public String toString()
    {
        return String.format("[%d %d %d]",x,y,z);
    }
}