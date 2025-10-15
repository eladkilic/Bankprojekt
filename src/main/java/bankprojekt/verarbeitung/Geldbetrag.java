package bankprojekt.verarbeitung;

import java.io.Serializable;

import org.decimal4j.util.DoubleRounder;

import bankprojekt.geld.Waehrung;

/**
 * Ein Geldbetrag mit Währung
 */
public class Geldbetrag implements Comparable<Geldbetrag>, Serializable{
	/**
	 * Betrag in der in waehrung angegebenen Währung
	 */
	private double betrag;
	/**
	 * ÜB 2
	 * Die Währung
	 */
	private Waehrung waehrung;
	
	/**
	 * 0 €
	 */
	public static final Geldbetrag NULL_EURO = new Geldbetrag(0);

	
	/**
	 * erstellt einen Geldbetrag in der Währung Euro
	 * @param betrag Betrag in €
	 * @throws IllegalArgumentException wenn betrag unendlich oder NaN ist
	 */
	public Geldbetrag(double betrag)
	{
		if(!Double.isFinite(betrag))
			throw new IllegalArgumentException();
		this.betrag = betrag;
		this.waehrung = Waehrung.EURO;
	}

	/**
	 * ÜB 2
	 * erstellt einen Geldbetrag in der gegebenen Währung
	 * @param betrag betrag
	 * @param waehrung waehrung
	 */
	public Geldbetrag(double betrag, Waehrung waehrung) {
		if (!Double.isFinite(betrag)) throw new IllegalArgumentException();
		if(waehrung == null) throw new IllegalArgumentException("waehrung null");
		this.betrag = betrag;
		this.waehrung = waehrung;
	}
	
	/**
	 * ÜB 2
	 * @return waehrung getter
	 */
	public Waehrung getWaehrung() {
		return this.waehrung;
	}
	
	/**
	 * Betrag von this
	 * @return Betrag in der Währung von this
	 */
	public double getBetrag() {
		return betrag;
	}
	
	/**
	 * ÜB 2
	 * rechnet this in die angegebene Währung um
	 * @param zielwaehrung neue wawehrung
	 * @return geldbetrag in umgerechnete summe
	 */
	public Geldbetrag umrechnen(Waehrung zielwaehrung) {
		if (zielwaehrung == null) throw new IllegalArgumentException("zielwaehrung null");
		if (this.waehrung == zielwaehrung) {
			 return new Geldbetrag(this.betrag, this.waehrung);
		} 
		double neuBetrag = this.waehrung.umrechnen(this.betrag, zielwaehrung);
		
		neuBetrag = DoubleRounder.round(neuBetrag, 2);
				//Math.round(neuBetrag * 100.0) / 100.0;
		return new Geldbetrag(neuBetrag, zielwaehrung);
	}
	
	/**
	 * ÜB2-added waehrung part
	 * rechnet this + summand
	 * @param summand zu addierender Betrag
	 * @return this + summand in der Währung von this
	 * @throws IllegalArgumentException wenn summand null ist
	 */
	public Geldbetrag plus(Geldbetrag summand) {
		if(summand == null)
			throw new IllegalArgumentException();
		
		if (this.waehrung == summand.waehrung) {
			return new Geldbetrag(this.betrag + summand.betrag, this.waehrung);
		} 
		summand = summand.umrechnen(this.waehrung);
		return new Geldbetrag(this.betrag + summand.betrag, this.waehrung);
	}
	
	/**
	 * ÜB2-added waehrung part
	 * rechnet this - siúbtrahend
	 * @param subtrahend abzuziehender Betrag
	 * @return this - subtrahend in der Währung von this
	 * @throws IllegalArgumentException wenn subtrahend null ist
	 */
	public Geldbetrag minus(Geldbetrag subtrahend)
	{
		if(subtrahend == null)
			throw new IllegalArgumentException();
		if (subtrahend.waehrung == this.waehrung) {
			return new Geldbetrag(this.betrag - subtrahend.betrag, this.waehrung);
		} 
		subtrahend = subtrahend.umrechnen(this.waehrung);
		return new Geldbetrag(this.betrag - subtrahend.betrag, this.waehrung);
	}

	/**
	 * multipliziert this mit faktor
	 * @param faktor Faktor der Multiplikation
	 * @return das faktor-Fache von this
	 * @throws IllegalArgumentException wenn faktor nicht finit ist
	 */
	public Geldbetrag mal(double faktor)
	{
		if(!Double.isFinite(faktor))
			throw new IllegalArgumentException();
		return new Geldbetrag(this.betrag * faktor);
	}

	@Override
	public int compareTo(Geldbetrag o) {
		return Double.compare(this.betrag, o.betrag);
	}

	@Override
	public boolean equals(Object o)
	{
		if(!(o instanceof Geldbetrag)) return false;
		if(o == this) return true;
		return this.compareTo((Geldbetrag) o) == 0;
	}
	
	/**
	 * prüft, ob this einen negativen Betrag darstellt
	 * @return true, wenn this negativ ist
	 */
	public boolean isNegativ()
	{
		return this.betrag < 0;
	}
	
	@Override
	public String toString()
	{
		return String.format("%,.2f €", this.betrag);
	}
}
