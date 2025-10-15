package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.KontoFabrik;

public class GirokontoFabrik extends KontoFabrik{

	@Override
	public Konto erzeugeKonto(Kunde inhaber) {
		return new Girokonto();
	}

}
