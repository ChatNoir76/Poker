package fr.cnam.grp4.poker.client.communication;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

@SuppressWarnings("deprecation")
public class ClientObservable extends Observable {

	public void open(String hostNameServeur,int portServeur,int portClient, String pseudo)
	  {
	    try{
	      // Abonnement au serveur
	      System.out.println("Abonnement au serveur");
	      Socket soc = new Socket(Inet4Address.getByName(hostNameServeur),portServeur);
	      OutputStream os=soc.getOutputStream();
	      ObjectOutputStream oos=new ObjectOutputStream(os);
	      oos.writeObject(hostNameServeur);
	      oos.writeObject(portClient);
	      oos.writeObject(pseudo);
	      
	      // Lancement d'un thread sur un serveur de scoket qui re�oit les notifications.
	      // Pour chaque notification re�u, cet observable notifie les observateurs (les vues)
	      //
	      ServerSocket sos = new ServerSocket(portClient);
	      Thread t = new Thread(){
	          public void run()
	          {
	            while(true)
	              {
	                try{
	                  Socket soc = sos.accept();
	                  InputStream is=soc.getInputStream();
	                  ObjectInputStream ois=new ObjectInputStream(is);
	                  Object obj  = ois.readObject();
	                  notifier(obj); // notification
	                  soc.close();
	                }catch(Exception ex){ System.out.println(ex);ex.printStackTrace();}
	              }
	          }};
	      t.start();
	    }catch(Exception ex2){System.out.println(ex2);ex2.printStackTrace();}
	  }
	
	public void notifier(Object arg){
	    setChanged();
	    notifyObservers(arg);
	}
	
}
