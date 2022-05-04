package fr.cnam.grp4.poker.server;

public interface IPokerApplication {
	
	public void recommencerPartie(String joueur);
	public void definirBlind(int nbjeton);
	public void miserSimpleBlind(String joueur);
	public void miserDoubleBlind(String joueur);
	public void miserJetons(String joueur, int nbjetons);
	public void faireTapis(String joueur);
	public void passer(String joueur);
	public void voirFlop();
	public void voirTurn();
	public void voirRiver();
	public void abandonner(String joueur);
	public void prendrePot(String joueur);
}
