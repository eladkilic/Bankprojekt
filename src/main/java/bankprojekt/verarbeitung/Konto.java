package bankprojekt.verarbeitung;

import bankprojekt.geld.Waehrung;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

/**
 * stellt ein allgemeines Bank-Konto dar
 */
public abstract class Konto implements Comparable<Konto>, Serializable
{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * gibt a us
	 */
	public void ausgeben() {
		System.out.println(this.toString());
	}
	
	/** 
	 * der Kontoinhaber
	 */
	private Kunde inhaber;

	/**
	 * die Kontonummer
	 */
	private final long nummer;

	/**
	 * der aktuelle Kontostand
	 */
	private ReadOnlyObjectWrapper<Geldbetrag> kontostand = new ReadOnlyObjectWrapper<Geldbetrag>();

	private BooleanProperty imPlus = new SimpleBooleanProperty();
	
	public boolean getImPlus() {
		return imPlus.get();
	}
	
	public void setImPlus() {
		if (this.kontostand.get().isNegativ()) {
			imPlus.set(false);
		}
		imPlus.set(true);
	}
	
	public BooleanProperty imPlusProperty() {
		return imPlus;
	}
	
	/**
	 * ÜB 2
	 * waehrung des Kontos
	 */
	private Waehrung waehrung;
	
	/**
	 * 
	 */
	private List<Kontoaktion> eintraege = new LinkedList<>();
	
	/**
	 * setzt den aktuellen Kontostand
	 * @param kontostand neuer Kontostand, darf nicht null sein
	 */
	protected void setKontostand(Geldbetrag kontostandneu) {
		if(kontostandneu != null)
			kontostand.set(kontostandneu);
	}

	/**
	 * Wenn das Konto gesperrt ist (gesperrt = true), können keine Aktionen daran mehr vorgenommen werden,
	 * die zum Schaden des Kontoinhabers wären (abheben, Inhaberwechsel)
	 */
	private BooleanProperty gesperrt = new SimpleBooleanProperty();

	/**
	 * Setzt die beiden Eigenschaften kontoinhaber und kontonummer auf die angegebenen Werte,
	 * der anfängliche Kontostand wird auf 0 gesetzt.
	 *
	 * @param inhaber der Inhaber
	 * @param kontonummer die gewünschte Kontonummer
	 * @throws IllegalArgumentException wenn der inhaber null ist
	 */
	public Konto(Kunde inhaber, long kontonummer) {
		if(inhaber == null)
			throw new IllegalArgumentException("Inhaber darf nicht null sein!");
		this.inhaber = inhaber;
		this.nummer = kontonummer;
		kontostand.set( Geldbetrag.NULL_EURO);
		gesperrt.set(false);;
		this.waehrung = Waehrung.EURO; //ÜB 2
		//->ÜB4-extra
		eintragAddErstellungKonto();
	}
	
	/**
	 * ÜB 4- extra
	 * helper methode für eintrag erstellung
	 */
	private void eintragAddErstellungKonto() {
		Kontoaktion eintrag = new Kontoaktion("Konto " + this.nummer + " wurde erstellt mit", kontostand.get());
		eintraege.add(eintrag);
	}
	/**
	 * ÜB 4-extra
	 * gibt alle kontoaktionen als string
	 * @return string
	 */
	public String getKontoauszug() {
		StringBuilder sb = new StringBuilder();
		for (Kontoaktion k : eintraege) {
			sb.append(k).append(System.lineSeparator());
		}
		return sb.toString().trim();
	}
	/**
	 * setzt alle Eigenschaften des Kontos auf Standardwerte
	 */
	public Konto() {
		this(Kunde.MUSTERMANN, 1234567);
		eintragAddErstellungKonto(); //ÜB 4
	}

	/**
	 * ÜB 2
	 * wechselt die währung, in der das konto aktuell geführt wird
	 * @param neu neue waehrung
	 */
	public void waehrungswechsel(Waehrung neu) {
		if (this.waehrung == neu) {
			return;
		}
		this.kontostand.set(this.kontostand.get().umrechnen(neu));
		this.waehrung = neu;
		
		//ÜB 4-extra
		Kontoaktion eintrag = new Kontoaktion("Währung wurde gewechselt zu " + neu);
		eintraege.add(eintrag);
		
	}
	
	/**
	 * ÜB 2
	 * gibt währung des kontos zurück
	 * @return waehrung getter
	 */
	public Waehrung getWaehrung() {
		return this.waehrung;
	}
	
	/**
	 * liefert den Kontoinhaber zurück
	 * @return   der Inhaber
	 */
	public Kunde getInhaber() {
		return this.inhaber;
	}
	
	/**
	 * setzt den Kontoinhaber
	 * @param kinh   neuer Kontoinhaber
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn kinh null ist
	 */
	public void setInhaber(Kunde kinh) throws GesperrtException{
		if (kinh == null)
			throw new IllegalArgumentException("Der Inhaber darf nicht null sein!");
		if(isGesperrt())
			throw new GesperrtException(this.nummer);        
		this.inhaber = kinh;

		//ÜB4-extra
		Kontoaktion eintrag = new Kontoaktion("Inhaber wurde erstellt " + kinh.getName());
		eintraege.add(eintrag);
	}
	
	/**
	 * liefert den aktuellen Kontostand
	 * @return   Kontostand
	 */
	public Geldbetrag getKontostand() {
		return kontostand.get();
	}
	
	public ReadOnlyObjectProperty<Geldbetrag> kontostandProperty(){
		return kontostand.getReadOnlyProperty();
	}

	/**
	 * liefert die Kontonummer zurück
	 * @return   Kontonummer
	 */
	public long getKontonummer() {
		return nummer;
	}

	/**
	 * liefert zurück, ob das Konto gesperrt ist oder nicht
	 * @return true, wenn das Konto gesperrt ist
	 */
	public boolean isGesperrt() {
		return gesperrt.get();
	}
	
	public BooleanProperty gesperrtProperty() {
		return gesperrt;
	}
	
	/**
	 * Erhöht den Kontostand um den eingezahlten Betrag.
	 *
	 * @param betrag double
	 * @throws GesperrtException 
	 * @throws IllegalArgumentException wenn der betrag negativ ist 
	 */
	public void einzahlen(Geldbetrag betrag) throws GesperrtException {
		if (betrag == null || betrag.isNegativ()) {
			throw new IllegalArgumentException("Falscher Betrag");
		}
		if(this.isGesperrt()) {
			throw new GesperrtException(this.getKontonummer());
		}
		setKontostand(getKontostand().plus(betrag));
		
		//ÜB4-extra
		Kontoaktion eintrag = new Kontoaktion("Einzahlung von", betrag);
		eintraege.add(eintrag);
	}
	
	@Override
	public String toString() {
		String ausgabe;
		ausgabe = "Kontonummer: " + this.getKontonummerFormatiert()
				+ System.getProperty("line.separator");
		ausgabe += "Inhaber: " + this.inhaber;
		ausgabe += "Aktueller Kontostand: " + getKontostand() + " ";
		ausgabe += this.getGesperrtText() + System.getProperty("line.separator");
		return ausgabe;
	}

	/**
	 * Mit dieser Methode wird der geforderte Betrag vom Konto abgehoben, wenn es nicht gesperrt ist
	 * und die speziellen Abheberegeln des jeweiligen Kontotyps die Abhebung erlauben
	 *
	 * @param betrag abzuhebender Betrag
	 * @throws GesperrtException wenn das Konto gesperrt ist
	 * @throws IllegalArgumentException wenn der betrag negativ oder unendlich oder NaN ist 
	 * @return true, wenn die Abhebung geklappt hat, 
	 * 		   false, wenn sie abgelehnt wurde
	 */
	public final boolean abheben(Geldbetrag betrag) 
								throws GesperrtException{
		//ÜB 11
		//implementierung von abheben
		if (this.isGesperrt()) {
			throw new GesperrtException(this.getKontonummer());
		}
		if (betrag.getBetrag() < 0) {
			throw new IllegalArgumentException("Betrag negativ!");
		}
		//teil1();
		this.kontoGedeckt(betrag);
		//teil2();
		this.abhebeBetrag(betrag);
		//hook
		this.spezielleRegeln(betrag);
		return true;
	}
	
	/**
	 * ÜB11
	 * @param betrag
	 */
	protected void abhebeBetrag(Geldbetrag betrag) {
		setKontostand(getKontostand().minus(betrag));
	}
	
	/**
	 * ÜB 11
	 * teil 1
	 * @return
	 */
	protected abstract boolean kontoGedeckt(Geldbetrag betrag);
	
	/**
	 * ÜB 11
	 * teil 2
	 */
	protected void spezielleRegeln(Geldbetrag betrag) {
	}
	
	/**
	 * sperrt das Konto, Aktionen zum Schaden des Benutzers sind nicht mehr möglich.
	 */
	public void sperren() {
		gesperrt.set(true);;
		
		//ÜB4-extra
		Kontoaktion eintrag = new Kontoaktion("Konto wurde gesperrt");
		eintraege.add(eintrag);
	}

	/**
	 * entsperrt das Konto, alle Kontoaktionen sind wieder möglich.
	 */
	public void entsperren() {
		gesperrt.set(false);
		
		//ÜB4-extra
		Kontoaktion eintrag = new Kontoaktion("Konto wurde entsperrt");
		eintraege.add(eintrag);
	}
	
	/**
	 * ÜB4-extra
	 * löscht alte einträge
	 * @param date datum
	 */
	public void alteEintraegeLoeschen(LocalDate date) {
		eintraege.removeIf(k -> k.getDatum().isBefore(date));
	}
	
	/**
	 * liefert eine String-Ausgabe, wenn das Konto gesperrt ist
	 * @return "GESPERRT", wenn das Konto gesperrt ist, ansonsten ""
	 */
	public String getGesperrtText()
	{
		if (isGesperrt())
		{
			return "GESPERRT";
		}
		else
		{
			return "";
		}
	}
	
	/**
	 * liefert die ordentlich formatierte Kontonummer
	 * @return auf 10 Stellen formatierte Kontonummer
	 */
	public String getKontonummerFormatiert()
	{
		return String.format("%10d", this.nummer);
	}
	
	/**
	 * Vergleich von this mit other; Zwei Konten gelten als gleich,
	 * wen sie die gleiche Kontonummer haben
	 * @param other das Vergleichskonto
	 * @return true, wenn beide Konten die gleiche Nummer haben
	 */
	@Override
	public boolean equals(Object other)
	{
		if(this == other)
			return true;
		if(other == null)
			return false;
		if(this.getClass() != other.getClass())
			return false;
		if(this.nummer == ((Konto)other).nummer)
			return true;
		else
			return false;
	}
	
	@Override
	public int hashCode()
	{
		return 31 + (int) (this.nummer ^ (this.nummer >>> 32));
	}

	@Override
	public int compareTo(Konto other)
	{
		if(other.getKontonummer() > this.getKontonummer())
			return -1;
		if(other.getKontonummer() < this.getKontonummer())
			return 1;
		return 0;
	}
}
