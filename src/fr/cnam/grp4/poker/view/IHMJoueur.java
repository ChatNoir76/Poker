package fr.cnam.grp4.poker.view;

import java.util.Observable;
import java.util.Observer;

import fr.cnam.grp4.poker.PokerApplication;
import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.ihm.Formulaire;
import fr.cnam.ihm.FormulaireInt;

@SuppressWarnings("deprecation")
public class IHMJoueur implements Observer, FormulaireInt {

	public static final String BT_MISER_SIMPLE = "BT_MISERS";
	public static final String BT_MISER_DOUBLE = "BT_MISERD";
	public static final String BT_MISER = "BT_MISER";
	public static final String TXT_MISER = "TXT_MISER";
	public static final String BT_TAPIS = "BT_TAPIS";
	public static final String BT_PASSER = "BT_PASSER";
	public static final String BT_ABANDONNER = "BT_ABANDONNER";
	public static final String BT_PRENDREPOT = "BT_PRENDREPOT";
	public static final String[] ALL_OTHER_BUTTON = {BT_MISER, BT_MISER_SIMPLE, BT_MISER_DOUBLE, BT_TAPIS, BT_PASSER, BT_ABANDONNER, BT_PRENDREPOT};
	public static final String BT_FLOP = "BT_FLOP";
	public static final String BT_TURN = "BT_TURN";
	public static final String BT_RIVER = "BT_RIVER";
	public static final String BT_RECOMMENCER = "BT_RECOMMENCER";
	public static final String[] ALL_DONNEUR_BUTTON = {BT_FLOP, BT_TURN, BT_RIVER, BT_RECOMMENCER};
	
	private Formulaire vue;
	private String pseudo;
	private boolean joue; 
	
	public IHMJoueur(String pseudo) {
		this.pseudo = pseudo;
		this.joue = true;
		this.vue = new Formulaire(this.pseudo, this, 500, 350);
		this.vue.addButton(BT_MISER_SIMPLE, "Miser Simple Blind");
		this.vue.addButton(BT_MISER_DOUBLE, "Miser Double Blind");
		this.vue.addButton(BT_MISER, "Miser");
		this.vue.addText(TXT_MISER, "Valeur à miser", true, "0");
		this.vue.addButton(BT_TAPIS, "Tapis");
		this.vue.addButton(BT_PASSER, "Passer");
		this.vue.addButton(BT_ABANDONNER, "Abandonner");
		this.vue.addButton(BT_PRENDREPOT, "Prendre le pot");
		
		this.vue.addButton(BT_FLOP, "Distribuer Flop");
		this.vue.addButton(BT_TURN, "Distribuer Turn");
		this.vue.addButton(BT_RIVER, "Distribuer River");
		this.vue.addButton(BT_RECOMMENCER, "Recommencer Partie");
		
		this.vue.addImage("IMAGE1", 250, 10, 71, 96, "Ressources/c1.gif");
		this.vue.addImage("IMAGE2", 326, 10, 71, 96, "Ressources/c1.gif");
	}
	/**
	 * Récupère le Pseudo du joueur
	 * @return le nom/pseudo du joueur
	 */
	public String getPseudo() {
		return pseudo;
	}
	/**
	 * Méthode permettant d'afficher la vue
	 */
	public void afficher() {
		this.vue.afficher();
	}
	/**
	 * Le joueur ne peut plus jouer
	 * Il est éliminé
	 */
	public void sortirJoueur() {
		this.joue = false;
	}
	/**
	 * donner ou retire la possibilité au joueur de jouer
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	public void setEnabledOtherFunction(boolean enabled) {
		for(String button: ALL_OTHER_BUTTON) {
			if(enabled) {
				this.vue.activer(button);
			} else {
				this.vue.desactiver(button);
			}
		}
	}
	/**
	 * donner ou retire la possibilité au joueur d'utiliser les fonctions du donneur
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	public void setEnabledDonneurFunction(boolean enabled) {
		for(String button: ALL_DONNEUR_BUTTON) {
			if(enabled) {
				this.vue.activer(button);
			} else {
				this.vue.desactiver(button);
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		System.out.println("update de l'ihm");
		if(arg instanceof JeuPoker) {
			System.out.println("Jeu poker reçu par l'ihm");
		} else {
			System.out.println("Objet inconnu reçu par l'ihm");
		}
	}

	@Override
	public void submit(Formulaire form, String nom) {
		if(this.joue) {
			switch(nom) {
			case BT_MISER:
				PokerApplication.eInstance().miserJetons(this.pseudo, Integer.valueOf(form.getValeurChamp(TXT_MISER)));
				form.setValeurChamp(TXT_MISER, "0");
				break;
			case BT_MISER_SIMPLE:
				PokerApplication.eInstance().miserSimpleBlind(this.pseudo);
				break;
			case BT_MISER_DOUBLE:
				PokerApplication.eInstance().miserDoubleBlind(this.pseudo);
				break;
			default:
				break;
			}
		}
	}
	
}
