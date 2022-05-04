package fr.cnam.grp4.poker.client;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

import fr.cnam.grp4.poker.client.view.IHMJoueur;

@SuppressWarnings("deprecation")
public class IHMClientApplication extends Observable{

	private ObjectOutputStream oos;
	
	public static void main(String[] args) throws Exception {
		
		if(args.length == 4) {
			String hostNameServeur = args[0];
		    int portServeur = Integer.parseInt(args[1]);
		    int portClient= Integer.parseInt(args[2]);
		    String pseudo = args[3];
   
		    IHMClientApplication obj = new IHMClientApplication();
		    obj.open(hostNameServeur, portServeur, portClient, pseudo);
		    IHMJoueur ihm = new IHMJoueur(pseudo, obj.oos);
			ihm.afficher();
		    obj.addObserver(ihm);
		    
		} else {
			System.out.println("Utilisation: HostNameServer portServer portClient pseudoJoueur");
		}
		
	}
	
	public void open(String hostNameServeur,int portServeur,int portClient, String pseudo)
	  {
	    try{
	      // Abonnement au serveur
	      System.out.println("Abonnement au serveur");
	      Socket soc = new Socket(hostNameServeur,portServeur);
	      OutputStream os=soc.getOutputStream();
	      this.oos=new ObjectOutputStream(os);
	      this.oos.writeObject(hostNameServeur);
	      this.oos.writeObject(portClient);
	      this.oos.writeObject(pseudo);
	      
	      ServerSocket sos = new ServerSocket(portClient);
	      Thread t = new Thread(){
	          public void run()
	          {
	            while(true)
	              {
	                try{
		                Socket soacc = sos.accept();
		                InputStream is=soacc.getInputStream();
		                ObjectInputStream ois=new ObjectInputStream(is);
		                Object obj  = ois.readObject();
		                notifierCore(obj);
		                soacc.close();
	                }catch(Exception ex){ System.err.println(ex);}
	              }
	          }};
	      t.start();
	    }catch(Exception ex2){
	    	System.err.println(ex2);
	    }
	  }
	
	
	public void notifierCore(Object arg){
	    setChanged();
	    notifyObservers(arg);
	}
	

}
