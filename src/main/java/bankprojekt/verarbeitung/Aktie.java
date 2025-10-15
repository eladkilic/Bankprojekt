package bankprojekt.verarbeitung;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

/**
 * ÜB 9
 * Eine Aktie, die ständig ihren Kurs verändert
 * @author Doro
 *
 */
public class Aktie {
	
	private static Map<String, Aktie> alleAktien = new HashMap<>();
	private String wkn;
	private volatile Geldbetrag kurs;
	/**
	 * ÜB 9
	 */
	private Lock aktienLock = new ReentrantLock();
	/**
	 * ÜB 9
	 */
	private Condition aktieGeaendert = aktienLock.newCondition();
	
	private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	/**
	 * ÜB 9
	 * @return aktiegeändert
	 */
	public Condition getAktieGeaendert() {
		return this.aktieGeaendert;
	}
	
	/**
	 * ÜB 9
	 * @return lock
	 */
	public Lock getAktienLock() {
		return this.aktienLock;
	}
	
	/**
	 * gibt die Aktie mit der gewünschten Wertpapierkennnummer zurück
	 * @param wkn Wertpapierkennnummer
	 * @return Aktie mit der angegebenen Wertpapierkennnummer oder null, wenn es diese WKN
	 * 			nicht gibt.
	 */
	public static Aktie getAktie(String wkn)
	{
		return alleAktien.get(wkn);
	}
	
	/**
	 * erstellt eine neu Aktie mit den angegebenen Werten
	 * @param wkn Wertpapierkennnummer
	 * @param k aktueller Kurs
	 * @throws IllegalArgumentException wenn einer der Parameter null bzw. negativ ist
	 * 		                            oder es eine Aktie mit dieser WKN bereits gibt
	 */
	public Aktie(String wkn, Geldbetrag k) {
		if(wkn == null || k == null || k.isNegativ() || alleAktien.containsKey(wkn))
			throw new IllegalArgumentException();	
		this.wkn = wkn;
		this.kurs = k;
		alleAktien.put(wkn, this);
		kursAendern();
	}

	private void setKurs() {
		aktienLock.lock();
		try {
			double aenderung = getRandomPercentage();
			double neukurs = this.kurs.getBetrag() * (1 + aenderung);
			this.kurs = new Geldbetrag(neukurs);
			aktieGeaendert.signalAll();
		} finally {
			aktienLock.unlock();
		}	
	}
	
	private void kursAendern() {
		int x = getRandomNumber(1, 5);
		Runnable kursTask = () -> setKurs();
		//Future<?> taskm = 
		scheduler.scheduleWithFixedDelay(kursTask, x, x, TimeUnit.SECONDS);
	}
	
	private int getRandomNumber(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	private double getRandomPercentage() {
		return ThreadLocalRandom.current().nextDouble(-0.03, 0.03);
	}


	/**
	 * Wertpapierkennnummer
	 * @return WKN der Aktie
	 */
	public String getWkn() {
		return wkn;
	}

	/**
	 * aktueller Kurs
	 * @return Kurs der Aktie
	 */
	public Geldbetrag getKurs() {
		return kurs;
	}
}
