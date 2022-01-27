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
	private String lienImageDos;

	public String getLienImageDos() {
		return lienImageDos;
	}

	public void setLienImageDos(String lienImageDos) {
		this.lienImageDos = lienImageDos;
	}

	@Override
	public String getLienDos() {
		return getLienImageDos();
	}
	
	
	
}
