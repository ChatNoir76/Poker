package fr.cnam.grp4.poker.service;

import java.util.Random;

public class Utilitaire {
	
	private static Utilitaire instance;
	
	public static Utilitaire eInstance() {
		if(instance == null) instance = new Utilitaire();
		return instance;
	}
	
	private Random rnd;

	public Utilitaire() {
		this.rnd = new Random();
	}
	/**
	 * Génère un nombre aléatoire de 0 à max - 1
	 * @param max limite du nombre aléatoire
	 * @return nombre entre 0 et max - 1
	 */
	public int getRandomInteger(int max) {
		return this.rnd.nextInt(max);
	}
}
