package fr.cnam.ihm;

/**
   Interface utilisée par le constructeur de CanvasHM permettant de traiter les clics dans la grille et dans le canvas
 */
public interface ControlesCanvasIHM
{
    /** 
        Methode appellee quand la grille est affichee et quand on clique dans une des cases de la grille
        @param xCase coordonnee en X de la case dans la grille
        @param yCase coordonnee en Y de la case dans la grille
        @param ihm l'instance de la CanvasIHM. Permet d'utiliser les méthodes publiques de CanvasIHM dans l'action.
    */
    public void pointerCaseGrille(int xCase,int yCase,CanvasIHM ihm);

    /**
       Methode appellee quand la grille n'est pas affichee et quand on clique dans le canvas.
       @param target objet associe a l'objet geometrique
       @param x coordonnee en pixel X de la position de la souris
       @param y coordonnee en pixel Y de la position de la souris
       @param ihm l'instance de la CanvasIHM. Permet d'utiliser les méthodes publiques de CanvasIHM dans l'action.
    */
    public void pointerCanvas(Object target,int x,int y,CanvasIHM ihm);
    
}
