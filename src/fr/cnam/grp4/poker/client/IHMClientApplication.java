package fr.cnam.grp4.poker.client;

import fr.cnam.grp4.poker.client.communication.ClientObservable;
import fr.cnam.grp4.poker.client.communication.JeuPokerObserver;

public class IHMClientApplication {

	public static void main(String[] args) {
		
		if(args.length == 4) {
			String hostNameServeur = args[0];
		    int portServeur = Integer.parseInt(args[1]);
		    int portClient= Integer.parseInt(args[2]);
		    String pseudo = args[3];
		    
		    JeuPokerObserver ihm = new JeuPokerObserver(pseudo);
		    ClientObservable obj = new ClientObservable();
		    obj.open(hostNameServeur, portServeur, portClient, pseudo);
		    obj.addObserver(ihm);
		} else {
			System.out.println("Utilisation: HostNameServer portServer portClient pseudoJoueur");
		}
		
	}

}
