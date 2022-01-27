package fr.cnam.grp4.poker;

import fr.cnam.grp4.poker.model.Joueur;

public interface IPokerApplication {
	
	public void recommencerPartie(Joueur joueur);
	public void definirBlind(int nbjeton);
	public void miserSimpleBlind(Joueur joueur);
	public void miserDoubleBlind(Joueur joueur);
	public void miserJetons(Joueur joueur);
	public void miserJetons(int nbjetons);
	public void faireTapis(Joueur joueur);
	public void passer(Joueur joueur);
	public void voirFlop();
	public void voirTurn();
	public void voirRiver();
	
}
