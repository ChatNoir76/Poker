package fr.cnam.grp4.poker.commun;

import java.io.Serializable;

public interface IRequeteIHM extends Serializable{

	public Operation getOperation();
	
	public String getPseudo();
	
	public Integer getJetons();
	
}
