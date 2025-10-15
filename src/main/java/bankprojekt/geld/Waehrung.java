package bankprojekt.geld;

/**
 * ÜB 2
 */
public enum Waehrung {
	/**
	 * currencies
	 */
	EURO(1.0), 
	/**
	 * escudo
	 */ESCUDO(109.8269), 
	 /**
	  * dobra
	  */
	 DOBRA(24304.7429), 
	 /**
	  * franc
	  */
	 FRANC(490.92);
	
	/**
	 * umrechnungskurs
	 */
	private double umrechnungsKurs;
	
	/**
	 * waehrung konstr
	 * @param kurs gegebene kurs
	 */
	private Waehrung(double kurs) {
		if(kurs < 0) throw new IllegalArgumentException("Kurs cant be negative");
		this.umrechnungsKurs = kurs;
	}
	
	/**
	 * getter für kurs
	 * @return umrechnugnskurs
	 */
	public double getKurs() {
		return this.umrechnungsKurs;
	}
	
	/**
	 * rechnet um
	 * @param betrag gegebene betrag
	 * @param zielwaehrung neue währung
	 * @return betrag umgerechnet
	 */
	public double umrechnen(double betrag, Waehrung zielwaehrung) {
		if (zielwaehrung == null) {
			throw new IllegalArgumentException("Cant convert->Currency null");
		}
		if (this == EURO) { //falls betrag schon in euro 
			return betrag * zielwaehrung.umrechnungsKurs;
		} 
		double betragInEuro = betrag / this.umrechnungsKurs; //erstmal in euro 
		betragInEuro = betragInEuro * zielwaehrung.umrechnungsKurs;
		return betragInEuro;
	}
	
	/**
	 * getting all currencies
	 * @return string
	 */
	public static String getAllCurrencies() {
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<Waehrung.values().length; i++) {
			sb.append(Waehrung.values()[i]);
			if (i < Waehrung.values().length - 1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	/**
	 * getting a msg
	 * @return string 
	 */
	public String getMessage() {
		return "Currency is " + this.toString();
	}    
}
