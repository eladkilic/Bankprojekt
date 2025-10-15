package bankprojekt.verarbeitung;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * ÜB 12
 */
public class AktienBeobachter implements PropertyChangeListener{

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		System.out.println("Änderung im Aktiendepot: " + evt.getNewValue());
	}

}
