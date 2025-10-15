package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.KontoFabrik;

public class SparbuchFabrik extends KontoFabrik{

	@Override
	public Konto erzeugeKonto(Kunde inhaber) {
		return new Sparbuch();
	}

}
