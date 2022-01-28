package fr.cnam.grp4.poker.view;

import java.util.Observable;
import java.util.Observer;

import fr.cnam.grp4.poker.PokerApplication;
import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.ihm.Formulaire;
import fr.cnam.ihm.FormulaireInt;

@SuppressWarnings("deprecation")
public class IHMJoueur implements Observer, FormulaireInt {

	public static final String BT_MISER = "BT_MISER";
	public static final String TXT_MISER = "TXT_MISER";
	
	private Observer observer;
	private Formulaire vue;
	private String pseudo;
	
	public IHMJoueur(String pseudo) {
		this.pseudo = pseudo;
		this.vue = new Formulaire("Joueur", this, 250, 250);
		this.vue.addText(TXT_MISER, "Valeur à miser", true, "0");
		this.vue.addButton(BT_MISER, "Miser");
	}
	
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * Méthode permettant d'afficher la vue
	 */
	public void afficher() {
		this.vue.afficher();
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
		switch(nom) {
		case BT_MISER:
			int mise = Integer.valueOf(form.getValeurChamp(TXT_MISER));
			PokerApplication.eInstance().miserJetons(this.pseudo, mise);
			break;
		default:
			break;
		}
		
	}
	
}
