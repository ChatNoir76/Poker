package fr.cnam.grp4.poker.client.communication;

import java.util.Observable;
import java.util.Observer;

import fr.cnam.grp4.poker.client.view.IHMJoueur;
import fr.cnam.grp4.poker.server.model.JeuPoker;

@SuppressWarnings("deprecation")
public class JeuPokerObserver implements Observer {

	private IHMJoueur ihm;
	
	public JeuPokerObserver(String pseudo) {
		this.ihm = new IHMJoueur(pseudo);
		this.ihm.afficher();
	}
	
	public String getPseudo() {
		return ihm.getPseudo();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof JeuPoker) {
			ihm.update((JeuPoker) arg);
		} else {
			System.out.println("Objet inconnu reçu par l'ihm");
		}
	}
}
