package bankprojekt.verwaltung;

import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;


public abstract class KontoFabrik {
	public  abstract Konto erzeugeKonto(Kunde inhaber);
}
