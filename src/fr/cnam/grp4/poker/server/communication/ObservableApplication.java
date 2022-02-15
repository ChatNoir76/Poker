package fr.cnam.grp4.poker.server.communication;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

import fr.cnam.grp4.poker.server.PokerApplication;
import fr.cnam.grp4.poker.server.model.JeuPoker;

@SuppressWarnings("deprecation")
public class ObservableApplication extends Observable {

	private boolean ready;
	
	public ObservableApplication() {
		this.startServer(9100);
		ready = false;
	}
	
	
	
	public boolean isReady() {
		return ready;
	}



	public void startServer(int port)
	  {
	    // Execution d'un thread sur un serveur de socket pour attendre les abonnements
	    //  des clients
	    try{
	      ServerSocket sos = new ServerSocket(port);
	      Thread t = new Thread(){
	          public void run(){
	            while(true){
	              try{
	            	System.out.println("Serveur en attente de joueur");
	                Socket soc = sos.accept();
	                
	                InputStream is=soc.getInputStream();
	                ObjectInputStream ois=new ObjectInputStream(is);
	                
	                String hostNameClient  = (String)(ois.readObject());
	                Integer portClient = (Integer)(ois.readObject());
	                String pseudo  = (String)(ois.readObject());

	                // Abonnement recu : cr�ation d'un observer d�di� au client
	                //addObserver(new ClientObserver(hostNameClient,portClient.intValue()));
	                System.out.println("Abonnement du nouveau joueur [" + pseudo + "]  sur  "+hostNameClient+":"+portClient);
	                
	                PokerApplication.getLocalInstance().ajouteJoueurViaClient(hostNameClient,portClient.intValue(),pseudo);
	                ready = true;
	              }catch(Exception ex){System.out.println(ex);ex.printStackTrace();}
	            }}};
	      t.start();
	    }catch(Exception ex){};
	  }
	
	/**
	 * indique les changements aux observeurs
	 */
	public void notifyIHM(JeuPoker jeu) {
		this.setChanged();
		this.notifyObservers(jeu);
	}
	
}
