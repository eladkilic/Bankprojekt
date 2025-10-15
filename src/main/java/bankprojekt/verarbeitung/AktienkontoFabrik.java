package bankprojekt.verarbeitung;

import bankprojekt.verwaltung.KontoFabrik;

public class AktienkontoFabrik extends KontoFabrik{

	@Override
	public Konto erzeugeKonto(Kunde inhaber) {
		return new Aktienkonto();
	}

}
