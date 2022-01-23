package fr.cnam.ihm;

/** 
 Classe de définition d'un objet d'un canvas : Polygone, Ligne et Texte
**/
public class ObjetCanvas
{
    private RessourcesCanvas rc;
    
    /** Création d'un objet canvas **/
    public ObjetCanvas()
    {
        rc = new RessourcesCanvas();
    }

    /** Maj des ressources graphiques d'un objet du canvas 
        @param rc ressources graphiques
     **/
    public void setRessourcesCanvas(RessourcesCanvas rc)
    {
        this.rc=rc;
    }
}