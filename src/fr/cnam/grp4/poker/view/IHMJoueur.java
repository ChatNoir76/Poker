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
	
	private static final String BT_MISER_SIMPLE = "BT_MISERS";
	private static final String BT_MISER_DOUBLE = "BT_MISERD";
	private static final String BT_MISER = "BT_MISER";
	private static final String BT_TAPIS = "BT_TAPIS";
	private static final String BT_PASSER = "BT_PASSER";
	private static final String BT_ABANDONNER = "BT_ABANDONNER";
	private static final String BT_PRENDREPOT = "BT_PRENDREPOT";
	private static final String[] ALL_OTHER_BUTTON = {BT_MISER, BT_MISER_SIMPLE, BT_MISER_DOUBLE, BT_TAPIS, BT_PASSER, BT_ABANDONNER, BT_PRENDREPOT};
	
	private static final String BT_FLOP = "BT_FLOP";
	private static final String BT_TURN = "BT_TURN";
	private static final String BT_RIVER = "BT_RIVER";
	private static final String BT_RECOMMENCER = "BT_RECOMMENCER";
	private static final String[] ALL_DONNEUR_BUTTON = {BT_FLOP, BT_TURN, BT_RIVER, BT_RECOMMENCER};
	
	private static final String TXT_MISER = "TXT_MISER";
	private static final String TXT_INFORMATION = "TXT_InfoG";
	private static final String TXT_POT = "TXT_Pot";
	private static final String TXT_POT_JOUEUR = "TXT_Pot_1";
	private static final String TXT_MISECUMUL_JOUEUR = "TXT_Mise_1";
	
	private static final int IMAGE_HEIGTH_VALUE = 96;
	private static final int IMAGE_WIDTH_VALUE = 71;
	
	private Formulaire vue;
	private String pseudo;
	private boolean joue;
	private boolean elimination;
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
		this.elimination = false;
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
		
		this.vue.setPosition(250, 10);
		this.vue.addText(TXT_POT, "POT", false, "0");
		int y3 = this.vue.getYCour();
		
		//Cartes du plateau
		this.carteFlop1 = this.vue.addImage("image plateau 1", 250, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		this.carteFlop2 = this.vue.addImage("image plateau 2", 326, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		this.carteFlop3 = this.vue.addImage("image plateau 3", 402, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		this.carteTurn = this.vue.addImage("image plateau 4", 478, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		this.carteRiver = this.vue.addImage("image plateau 5", 554, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		//Mes cartes
		this.vue.setPosition(250, y3 + IMAGE_HEIGTH_VALUE + 5);
		this.vue.addLabel("Mes Cartes");
		y3 = this.vue.getYCour();
		this.carte1 = this.vue.addImage("image joueur 1", 250, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		this.carte2 = this.vue.addImage("image joueur 2", 326, y3, IMAGE_WIDTH_VALUE, IMAGE_HEIGTH_VALUE, "");
		this.vue.setPosition(402, y3);
		this.vue.addText(TXT_POT_JOUEUR, "POT du joueur", false, "0");
		this.vue.addText(TXT_MISECUMUL_JOUEUR, "Mise en cours : ", false, "0");
		
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
		for(String button: ALL_OTHER_BUTTON) {
			if(enabled) {
				this.vue.activer(button);
			} else {
				this.vue.desactiver(button);
			}
		}
	}
	/**
	 * donne ou retire la possibilité au joueur d'utiliser les fonctions du donneur
	 * @param enabled false pour bloquer les boutons de l'ihm
	 */
	private void peutUtiliserFonctionDonneur(boolean enabled) {
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
		
		//Traitement données du jeu
		this.vue.setValeurChamp(TXT_POT, String.valueOf(jeu.getPot()));
		
		//Traitement données du joueur
		Joueur me = jeu.getJoueur(pseudo);
		this.elimination = me.isEliminer();
		if(this.elimination) {
			peutUtiliserFonctionJeu(false);
			peutUtiliserFonctionDonneur(false);
		}else {
			peutUtiliserFonctionJeu(!me.isAbandon());
			peutUtiliserFonctionDonneur(!me.isAbandon());
		}
		
		this.vue.setValeurChamp(TXT_POT_JOUEUR, String.valueOf(me.getJetons()));
		this.vue.setValeurChamp(TXT_MISECUMUL_JOUEUR, String.valueOf(me.getMiseManche()));
		
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
		if(this.joue && !this.elimination) {
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
			case BT_RECOMMENCER:
				PokerApplication.eInstance().recommencerPartie(this.pseudo);
				break;
			case BT_PRENDREPOT:
				PokerApplication.eInstance().prendrePot(this.pseudo);
				break;
			default:
				break;
			}
		}
	}
	
}
