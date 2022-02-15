package fr.cnam.grp4.poker.server.communication;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

@SuppressWarnings("deprecation")
public class ClientObserver implements Observer {

	private String hostNameClient;
	private int portClient;
	
	public ClientObserver(String hostNameClient, int portClient, String pseudo) {
		super();
		this.hostNameClient = hostNameClient;
		this.portClient = portClient;
	}

	@Override
	public void update(Observable o, Object arg) {
		try{
	        // La notification consiste � �crire l'objet � notifier sur le socket du poste client
	        //
	        Socket soc = new Socket(Inet4Address.getByName(hostNameClient),portClient);
	        OutputStream os=soc.getOutputStream();
	        ObjectOutputStream oos=new ObjectOutputStream(os);
	        oos.writeObject(arg);
	      }catch(Exception ex){System.out.println(ex);ex.printStackTrace();}
	}

}
