package bankprojekt.verarbeitung;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * ÜB 9
 */
public class Aktienkonto extends Konto{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Aktie aktie;
	private int anzahl;
	private Map<Aktie, Integer> aktienDepot = new HashMap<>();
	private final ExecutorService executor = Executors.newCachedThreadPool();
	/**
	 * ÜB 12
	 */
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	
	public Aktienkonto() {
	}
	
	public Aktienkonto(Aktie aktie, int anzahl) {
		this.aktie = aktie;
		this.anzahl = anzahl;
		aktienDepot.put(aktie, anzahl);
	}
	
	/**
	 * ÜB 12
	 * @param pcl
	 */
	public void anmelden(PropertyChangeListener pcl) {
		support.addPropertyChangeListener(pcl);
	}
	
	/**
	 * ÜB 12
	 * @param pcl
	 */
	public void abmelden(PropertyChangeListener pcl) {
		support.removePropertyChangeListener(pcl);
	}
	
	/**
	 * ÜB 12
	 * @param s
	 * @param alt
	 * @param neu
	 */
	protected void benachrichtigen(String s, Object alt, Object neu) {
		support.firePropertyChange(s, alt, neu);
	}
	
	/**
	 * Die Methode wartet asynchron, 
	 * bis der Preis der Aktie mit der gewünschten 
	 * Wertpapierkennnummer unter den angegebenen Höchstpreis gefallen ist. 
	 * Dann kauft sie anzahl Stück davon 
	 * (d.h. vermindert den Kontostand des Kontos entsprechend) und
	 * speichert sie im Depot des Kontos. 
	 * Ist der Kontostand zu diesem Zeitpunkt allerdings
	 * nicht ausreichend (oder gibt es die Wertpapierkennnummer 
	 * überhaupt nicht), wird nichts gekauft. Die 
	 * Methode gibt den bezahlten Gesamtkaufpreis zurück.
	 * @param wkn
	 * @param anzahl
	 * @param hoechstpreis
	 * @return
	 * @throws GesperrtException 
	 */
	public Future<Geldbetrag> kaufauftrag(String wkn, int anzahl,
			Geldbetrag hoechstpreis) throws GesperrtException{
		
		Callable<Geldbetrag> kaufTask = () -> {
			Aktie ak = Aktie.getAktie(wkn);
			if (ak == null) {
                return new Geldbetrag(0.0); //kein aktien
            }
			ak.getAktienLock().lock();
			try {
				Geldbetrag betrag = ak.getKurs();
				while (betrag.getBetrag() >= hoechstpreis.getBetrag()) {
					try {
						ak.getAktieGeaendert().await();
					} catch (InterruptedException e) {
						return new Geldbetrag(0.0); //interrupted
					}
					betrag = ak.getKurs();
				}
				
				Geldbetrag gesamt = new Geldbetrag(betrag.getBetrag() * anzahl);
				if (this.getKontostand().getBetrag() < gesamt.getBetrag()) {
					return new Geldbetrag(0.0); //not enough money
				}
				this.aktienDepot.put(ak, anzahl);
				this.abheben(gesamt);
				benachrichtigen("Kauf von", ak, ak);
				return gesamt;
			} finally {
				ak.getAktienLock().unlock();
			}
		};
		
		Future<Geldbetrag> s = executor.submit(kaufTask);
		return s;
	}
	
	/**
	 * Wenn es keine Aktie mit der 
	 * gewünschten Wertpapierkennnummer im Depot gibt,
	 * findet kein Verkauf statt. Ansonsten wartet die Methode asynchron,
	 * bis der Kurs der Aktie den gewünschten Minimalpreis erreicht hat 
	 * und verkauft dann den kompletten Depotbestand dieser 
	 * Aktie (d.h. erhöht den Kontostand entsprechend). 
	 * Sie liefert den Gesamterlös zurück. 
	 * Ist die Aktie nicht im Depot vorhanden, 
	 * wird also 0 zurückgegeben.
	 * @param wkn
	 * @param minimalpreis
	 * @return
	 */
	public Future<Geldbetrag> verkaufauftrag(String wkn, 
			Geldbetrag minimalpreis){
		
		Callable<Geldbetrag> verkaufTask = () -> {
			Aktie ak = Aktie.getAktie(wkn);
			if (ak == null) {
                return new Geldbetrag(0.0); //kein aktien
            }
			ak.getAktienLock().lock();
			try {
				Geldbetrag betrag = ak.getKurs();
				while (betrag.getBetrag() <= minimalpreis.getBetrag()) {
					try {
						ak.getAktieGeaendert().await();
					} catch (InterruptedException e) {
						return new Geldbetrag(0.0); //interrupted
					}
					betrag = ak.getKurs();
				}
				int anz = aktienDepot.get(ak);
				Geldbetrag gesamt = new Geldbetrag(betrag.getBetrag() * anz);
			
				this.aktienDepot.remove(ak);
				this.einzahlen(gesamt);
				benachrichtigen("Verkauf von", ak, ak);
				return gesamt;
			} finally {
				ak.getAktienLock().unlock();
			}
		};
		
		Future<Geldbetrag> s = executor.submit(verkaufTask);
		return s;
	}

	@Override
	protected boolean kontoGedeckt(Geldbetrag betrag) {
		if (!getKontostand().minus(betrag).isNegativ()) {
			return true;
		}
		return false;
	}
}
