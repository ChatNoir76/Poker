package fr.cnam.grp4.poker.server.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Observable;

import fr.cnam.grp4.poker.server.PokerApplication;
import fr.cnam.grp4.poker.server.model.JeuPoker;

@SuppressWarnings("deprecation")
public class ObservableApp extends Observable implements Runnable {
	
	private long timeToWait;
	private int port;

	public ObservableApp() {
		this.timeToWait = 0;
		this.port = -1;
	}
	/**
	 * Configure le serveur du jeu
	 * @param port Port du serveur
	 * @param timeToWait temps d'attente des clients avant de commencer
	 */
	public void setConfigServer(int port, int timeToWait) {
		this.timeToWait = (timeToWait * 1000);
		this.port = port;
	}
	/**
	 * indique les changements aux observeurs
	 */
	public void notifyIHM(JeuPoker jeu) {
		this.setChanged();
		this.notifyObservers(jeu);
	}

	@Override
	public void run() {
		ServerSocket sos = null;
		if(this.port != -1) {
			try {
				sos = new ServerSocket(port);
				Thread attente = new Thread(new AttenteClient(sos));
				attente.start();
				try {
					attente.join(timeToWait);
				} catch (InterruptedException e) {
					System.out.println("Fin de l'attente de client");
				}
				PokerApplication.getLocalInstance().startApplication();
			} catch (IOException e) {
				System.err.println("problème avec le serveur : " + e.getMessage());
			} 
		} else {
			System.err.println("le serveur n'est pas configuré");
		}
	}
}







































