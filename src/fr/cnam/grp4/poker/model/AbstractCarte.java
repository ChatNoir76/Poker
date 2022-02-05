package fr.cnam.grp4.poker.model;
/**
 * Classe abstraite qui définie une Carte
 * @author chatnoir
 *
 */
public abstract class AbstractCarte implements ICarte {
	/**
	 * Contient le lien de l'image de la face caché de la carte
	 */
	private final String lienImageDos = "Ressources/dos.gif";

	public String getLienImageDos() {
		return lienImageDos;
	}

	@Override
	public String getLienDos() {
		return getLienImageDos();
	}
	
	
	
}
