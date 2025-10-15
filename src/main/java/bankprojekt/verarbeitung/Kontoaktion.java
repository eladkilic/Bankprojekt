package bankprojekt.verarbeitung;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * ÜB 4-extra
 */
public class Kontoaktion implements Serializable{

	/**
	 * text
	 */
	private String text;
	/**
	 * betro
	 */
	private Geldbetrag betrag;
	
	/**
	 * date
	 */
	private LocalDate datum;
	
	/**
	 * konstr 1: text, betrag
	 * @param text text
	 * @param betrag	betrag
	 */
	public Kontoaktion(String text, Geldbetrag betrag) {
		this.text = text;
		this.betrag = betrag;
		this.datum = LocalDate.now();
	}
	
	/**
	 * konstr 2: text
	 * @param text text
	 */
	public Kontoaktion(String text) {
		this.text = text;
		this.betrag = new Geldbetrag(0.0);
		this.datum = LocalDate.now();
	}
	
	/**
	 * getter für text
	 * @return text
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * getter für betrag
	 * @return betrag
	 */
	public Geldbetrag getBetrag() {
		return this.betrag;
	}
	
	/**
	 * getter für datum
	 * @return datum
	 */
	public LocalDate getDatum() {
		return this.datum;
	}
	

	public String toString() {
		return  this.datum + ": "+ this.text + ": " + this.betrag;
	}
}

