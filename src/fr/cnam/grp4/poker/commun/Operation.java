package fr.cnam.grp4.poker.commun;

import java.io.Serializable;

public enum Operation implements Serializable{
	recommencer,
	definirBlind,
	miserSimpleBlind,
	miserDoubleBlind,
	miserJetons,
	faireTapis,
	passer,
	voirFlop,
	voirTurn,
	voirRiver,
	abandonner,
	prendrePot
}
