package fr.cnam.grp4.poker.client.communication;

import java.io.Serializable;

import fr.cnam.grp4.poker.commun.IRequeteIHM;
import fr.cnam.grp4.poker.commun.Operation;

public class RequeteIHM implements IRequeteIHM, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Operation operation;
	
	private String pseudo;
	
	private Integer jetons;
	/**
	 * 
	 * @param operation
	 * @param pseudo
	 * @param jetons
	 */
	public RequeteIHM(Operation operation, String pseudo, Integer jetons) {
		this.operation = operation;
		this.pseudo = pseudo;
		this.jetons = jetons;
	}
	@Override
	public Operation getOperation() {
		return operation;
	}
	@Override
	public String getPseudo() {
		return pseudo;
	}
	@Override
	public Integer getJetons() {
		return jetons;
	}
	
}
