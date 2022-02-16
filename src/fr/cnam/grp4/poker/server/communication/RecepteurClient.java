package fr.cnam.grp4.poker.server.communication;

import java.io.ObjectInputStream;

import fr.cnam.grp4.poker.client.communication.RequeteIHM;
import fr.cnam.grp4.poker.commun.IRequeteIHM;
import fr.cnam.grp4.poker.server.PokerApplication;
/**
 * Classe qui réceptionne les données des clients
 * @author antitrust
 *
 */
public class RecepteurClient extends Thread {

	private ObjectInputStream ois;
	/**
	 * Thread de réception des requetes clients
	 * @param ois
	 */
	public RecepteurClient(ObjectInputStream ois) {
		this.ois = ois;
	}

	@Override
	public void run() {
		while(true) {
			try {
				Object obj = ois.readObject();
				if(obj instanceof RequeteIHM) {
					IRequeteIHM data = (IRequeteIHM) obj;
					switch(data.getOperation()) {
					case miserJetons:
						PokerApplication.eInstance().miserJetons(data.getPseudo(), data.getJetons());
						break;
					case miserSimpleBlind:
						PokerApplication.eInstance().miserSimpleBlind(data.getPseudo());
						break;
					case miserDoubleBlind:
						PokerApplication.eInstance().miserDoubleBlind(data.getPseudo());
						break;
					case abandonner:
						PokerApplication.eInstance().abandonner(data.getPseudo());
						break;
					case definirBlind:
						PokerApplication.eInstance().definirBlind(data.getJetons());
						break;
					case faireTapis:
						break;
					case passer:
						PokerApplication.eInstance().passer(data.getPseudo());
						break;
					case prendrePot:
						PokerApplication.eInstance().prendrePot(data.getPseudo());
						break;
					case recommencer:
						PokerApplication.eInstance().recommencerPartie(data.getPseudo());
						break;
					case voirFlop:	
						PokerApplication.eInstance().voirFlop();
						break;
					case voirRiver:
						PokerApplication.eInstance().voirRiver();
						break;
					case voirTurn:
						PokerApplication.eInstance().voirTurn();
						break;
					default:
						break;
					}
					System.out.println("Donnée reçue du client [" + data.getPseudo() + "] => " + data.getOperation().toString());
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
}
