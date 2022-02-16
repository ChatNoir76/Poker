package fr.cnam.grp4.poker.server.communication;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import fr.cnam.grp4.poker.server.PokerApplication;
/**
 * Classe d'attente des clients avant de commencer le jeu
 * @author antitrust
 *
 */
public class AttenteClient implements Runnable {
	
	private ServerSocket sos;

	/**
	 * Création d'un thread d'attente client de 30 secondes
	 * @param sos Serveur de socket
	 */
	public AttenteClient(ServerSocket sos) {
		this.sos = sos;
	}
	
	@Override
	public void run() {
		int nbJoueur = 0;
		while(nbJoueur < 4) {
			try {
				System.out.println("Serveur en attente de joueur [présent:" + nbJoueur + "]");
				Socket soc = sos.accept();
				InputStream is=soc.getInputStream();
	            ObjectInputStream ois=new ObjectInputStream(is);
	            
				String hostNameClient = (String)(ois.readObject());
				Integer portClient = (Integer)(ois.readObject());
	            String pseudo  = (String)(ois.readObject());
	            System.out.println("Abonnement du nouveau joueur [" + pseudo + "]  sur  "+hostNameClient+":"+portClient);
	            PokerApplication.getLocalInstance().ajouteJoueurViaClient(hostNameClient,portClient.intValue(),pseudo);
	            nbJoueur++;
	            
	            //lance le récepteur de donnée du client qui vient de s'ajouter à la partie
	            RecepteurClient recepteur = new RecepteurClient(ois);
	            recepteur.start();
	            
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("*** Le Serveur lance le jeu de poker car nous sommes complet ***");
	}

}
