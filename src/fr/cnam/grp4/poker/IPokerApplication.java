package fr.cnam.grp4.poker;

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
	
}
