package fr.cnam.grp4.poker.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

import fr.cnam.grp4.poker.PokerApplication;
import fr.cnam.grp4.poker.model.JeuPoker;
import fr.cnam.grp4.poker.model.Joueur;
import fr.cnam.ihm.Formulaire;
import fr.cnam.ihm.FormulaireInt;

@SuppressWarnings("deprecation")
public class IHMJoueur implements Observer, FormulaireInt {

	public static final String BT_MISER_SIMPLE = "BT_MISERS";
	public static final String BT_MISER_DOUBLE = "BT_MISERD";
	public static final String BT_MISER = "BT_MISER";
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
	
	public static final String TXT_MISER = "TXT_MISER";
	public static final String TXT_INFORMATION = "TXT_InfoG";
	
	private Formulaire vue;
	private String pseudo;
	private boolean joue;
	private JLabel carte1;
	private JLabel carte2;
	
	private JLabel carteFlop1;
	private JLabel carteFlop2;
	private JLabel carteFlop3;
	private JLabel carteTurn;
	private JLabel carteRiver;
	
	public IHMJoueur(String pseudo) {
		this.pseudo = pseudo;
		this.joue = true;
		this.vue = new Formulaire(this.pseudo, this, 1000, 450);
		this.vue.setPosition(5, 5);
		this.vue.addLabel("Action du JOUEUR");
		this.vue.addButton(BT_MISER_SIMPLE, "Miser Simple Blind");
		this.vue.addButton(BT_MISER_DOUBLE, "Miser Double Blind");
		int x1 = this.vue.getXCour();
		int y1 = this.vue.getYCour();
		this.vue.addButton(BT_MISER, "Miser");
		this.vue.addButton(BT_TAPIS, "Tapis");
		this.vue.addButton(BT_PASSER, "Passer");
		this.vue.addButton(BT_ABANDONNER, "Abandonner");
		this.vue.addButton(BT_PRENDREPOT, "Prendre le pot");
		
		int x2 = this.vue.getXCour();
		int y2 = this.vue.getYCour();
		this.vue.setPosition(x2, y2 + 50);
		this.vue.addLabel("Action du DONNEUR");
		this.vue.addButton(BT_FLOP, "Distribuer Flop");
		this.vue.addButton(BT_TURN, "Distribuer Turn");
		this.vue.addButton(BT_RIVER, "Distribuer River");
		this.vue.addButton(BT_RECOMMENCER, "Recommencer Partie");
	
		this.vue.setPosition(640, 10);
		this.vue.addZoneText(TXT_INFORMATION, "Info du jeu", false, "", 300, 350);
		this.vue.setPosition(x1 + 55, y1);
		this.vue.addText(TXT_MISER, "", true, "0");
		
		this.carte1 = this.vue.addImage("image joueur 1", 250, 10, 71, 96, "");
		this.carte2 = this.vue.addImage("image joueur 2", 326, 10, 71, 96, "");
		
		this.carteFlop1 = this.vue.addImage("image plateau 1", 250, 111, 71, 96, "");
		this.carteFlop2 = this.vue.addImage("image plateau 2", 326, 111, 71, 96, "");
		this.carteFlop3 = this.vue.addImage("image plateau 3", 402, 111, 71, 96, "");
		this.carteTurn = this.vue.addImage("image plateau 4", 478, 111, 71, 96, "");
		this.carteRiver = this.vue.addImage("image plateau 5", 554, 111, 71, 96, "");
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
	 * donner ou retire la possibilité au joueur de jouer
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	private void peutUtiliserFonctionJeu(boolean enabled) {
		if(joue) {
			for(String button: ALL_OTHER_BUTTON) {
				if(enabled) {
					this.vue.activer(button);
				} else {
					this.vue.desactiver(button);
				}
			}
		}
	}
	/**
	 * donne ou retire la possibilité au joueur d'utiliser les fonctions du donneur
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	private void peutUtiliserFonctionDonneur(boolean enabled) {
		if(joue) {
			for(String button: ALL_DONNEUR_BUTTON) {
				if(enabled) {
					this.vue.activer(button);
				} else {
					this.vue.desactiver(button);
				}
			}
		}
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof JeuPoker) {
			traitementInfoJeu((JeuPoker) arg);
		} else {
			System.out.println("Objet inconnu reçu par l'ihm");
		}
	}
	
	private void afficheMessages(String[] messages) {
		StringBuilder str = new StringBuilder();
		for(String message: messages) {
			str.append(message).append('\n');
		}
		this.vue.setValeurChamp(TXT_INFORMATION, str.toString());
	}
	
	private void traitementInfoJeu(JeuPoker jeu) {
		
		afficheMessages(jeu.getMessages());
		
		Joueur me = jeu.getJoueur(pseudo);
		//si le joueur a abandonné, il ne peux plus jouer
		if(me.isAbandon() && this.joue == true) {
			peutUtiliserFonctionJeu(false);
			peutUtiliserFonctionDonneur(false);
			this.joue = false;
		}
		
		//affichage des cartes du joueur
		this.vue.setImage(carte1, me.getCartes()[0].getLienFace());
		this.vue.setImage(carte2, me.getCartes()[1].getLienFace());
		
		//affichage des cartes du plateau
		this.vue.setImage(carteFlop1, jeu.getFlop()[0].estVisible() ? jeu.getFlop()[0].getLienFace() : jeu.getFlop()[0].getLienDos());
		this.vue.setImage(carteFlop2, jeu.getFlop()[1].estVisible() ? jeu.getFlop()[1].getLienFace() : jeu.getFlop()[1].getLienDos());
		this.vue.setImage(carteFlop3, jeu.getFlop()[2].estVisible() ? jeu.getFlop()[2].getLienFace() : jeu.getFlop()[2].getLienDos());
		this.vue.setImage(carteTurn, jeu.getTurn().estVisible() ? jeu.getTurn().getLienFace() : jeu.getTurn().getLienDos());
		this.vue.setImage(carteRiver, jeu.getRiver().estVisible() ? jeu.getRiver().getLienFace() : jeu.getRiver().getLienDos());
		
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
			case BT_FLOP:
				PokerApplication.eInstance().voirFlop();
				break;
			case BT_TURN:
				PokerApplication.eInstance().voirTurn();
				break;
			case BT_RIVER:
				PokerApplication.eInstance().voirRiver();
				break;
			case BT_ABANDONNER:
				PokerApplication.eInstance().abandonner(this.pseudo);
				break;
			case BT_PASSER:
				PokerApplication.eInstance().passer(this.pseudo);
				break;
			default:
				break;
			}
		}
	}
	
}
