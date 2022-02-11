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
	/**
	 * Détermine le lien vers l'image de la carte
	 * @param Symbole Symbole de la couleur
	 * @param number valeur de la carte 1 à 13
	 * @return le lien de la carte (voir dossier ressources)
	 */
	protected String getLienCarteStandard(String Symbole, int number) {
		String other = ""+number;
		if(number == 11) {
			other = "j";
		}else if (number == 13) {
			other = "k";
		}else if (number == 12) {
			other = "q";
		}
		return "Ressources/" + Symbole + other + ".gif";
	}
	/**
	 * Détermine le nom depuis sa valeur
	 * @param valeur valeur de la carte
	 * @return Nom de la carte
	 */
	protected String findNameFromValeur(int valeur) {
		if(valeur == 1) return "As";
		if(valeur == 11) return "Valet";
		if(valeur == 13) return "Roi";
		if(valeur == 12) return "Reine";
		return ""+valeur;
	}
}
