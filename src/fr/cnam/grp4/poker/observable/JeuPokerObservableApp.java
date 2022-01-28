package fr.cnam.grp4.poker.observable;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class JeuPokerObservableApp extends Observable {

	@Override
	public void notifyObservers(Object arg) {
		super.setChanged();
		super.notifyObservers(arg);
		
	}

}
