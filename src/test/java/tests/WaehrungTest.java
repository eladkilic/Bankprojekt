//package tests;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import bankprojekt.geld.Waehrung;
//import bankprojekt.verarbeitung.Geldbetrag;
//import bankprojekt.verarbeitung.GesperrtException;
//import bankprojekt.verarbeitung.Girokonto;
//import bankprojekt.verarbeitung.Konto;
//
//class WaehrungTest {
//
//	@Test
//	void testWaehrungWerte() {
//		Waehrung waehrung = Waehrung.EURO;
//		String name = waehrung.name();
//		assertEquals("EURO", name);
//	}
//	
//	@Test
//	void testWaehrungKurs() {
//		Waehrung waehrung = Waehrung.EURO;
//		double result = waehrung.getKurs();
//		assertEquals(1.0, result);
//	}
//	@Test
//	void testWaehrungKursDOBRA() {
//		Waehrung waehrung = Waehrung.DOBRA;
//		double result = waehrung.getKurs();
//		assertEquals(24304.7429, result);
//	}
//	
//	@Test
//	void testgetAllCurrencies() {
//		String result = Waehrung.getAllCurrencies();
//		String expected = "EURO, ESCUDO, DOBRA, FRANC";
//		assertEquals(expected, result);
//	}
//	
//	@Test
//	void testgetMessage() {
//		Waehrung waehrung = Waehrung.DOBRA;
//		String result = waehrung.getMessage();
//		assertEquals("Currency is DOBRA", result);
//	}
//	
//	//----in Geldbetrag
//	@Test
//	void testUmrechnenGeldbetragFRANC() {
//		Geldbetrag betrag = new Geldbetrag(10.0);
//		Geldbetrag n = betrag.umrechnen(Waehrung.FRANC);
//		assertEquals(4909.2, n.getBetrag());
//	}
//
//	@Test
//	void testUmrechnenGeldbetragESCUDO() {
//		Geldbetrag betrag = new Geldbetrag(10.0);
//		Geldbetrag n = betrag.umrechnen(Waehrung.ESCUDO);
//		assertEquals(1098.27, n.getBetrag());
//	}
//	
//	@Test
//	void testUmrechnenGeldbetragDOBRA() {
//		Geldbetrag betrag = new Geldbetrag(10.0);
//		Geldbetrag n = betrag.umrechnen(Waehrung.DOBRA);
//		assertEquals(243047.43, n.getBetrag());
//	}
//	
//	@Test
//	void testUmrechnenGeldbetragEURO() {
//		Geldbetrag betrag = new Geldbetrag(10.0);
//		Geldbetrag n = betrag.umrechnen(Waehrung.EURO);
//		assertEquals(10, n.getBetrag());
//	}
////----------------------------------------
//	@Test
//	void testMinusGeldbetragWaehrungGLEICH() {
//		Geldbetrag betrag = new Geldbetrag(10.0);
//		Geldbetrag neu = betrag.minus(new Geldbetrag(5.0));
//		assertEquals(5.0,neu.getBetrag());
//	}
//	
//	@Test
//	void testMinusGeldbetragWaehrungEuroInFranc() {
//		Geldbetrag betrag = new Geldbetrag(10.0);
//		Geldbetrag francBetrag = new Geldbetrag(10.0, Waehrung.FRANC);
//		Geldbetrag neu = betrag.minus(francBetrag);
//		assertEquals(9.98,neu.getBetrag());
//	}
//	
//	@Test
//	void testMinusGeldbetragWaehrungEscudoInFranc() {
//		Geldbetrag betrag = new Geldbetrag(10.0, Waehrung.ESCUDO);
//		Geldbetrag francBetrag = new Geldbetrag(10.0, Waehrung.FRANC);
//		Geldbetrag neu = betrag.minus(francBetrag);
//		assertEquals(7.76,neu.getBetrag());
//	}
//	
//	@Test
//	void testMinusGeldbetragWaehrungDobraInEuro() {
//		Geldbetrag betrag = new Geldbetrag(500000, Waehrung.DOBRA);
//		Geldbetrag euroBetrag = new Geldbetrag(10.0);
//		Geldbetrag neu = betrag.minus(euroBetrag);
//		assertEquals(256952.57,neu.getBetrag());
//	}
//	
//	//----------in Konto
//	@Test 
//	void testWaehrungGet() {
//		Konto konto = new Girokonto();
//		Waehrung result = konto.getWaehrung();
//		assertEquals(Waehrung.EURO, result);
//	}
//	
//	@Test
//	void testWaehrungWechsel() {
//		Konto konto = new Girokonto();
//		konto.waehrungswechsel(Waehrung.DOBRA);
//		Waehrung result = konto.getWaehrung();
//		assertEquals(Waehrung.DOBRA, result);
//	}
//	
//	@Test
//	void testWaehrungEuroKontostand() {
//		Konto konto = new Girokonto();
//		konto.einzahlen(new Geldbetrag(20.0));
//		double result = konto.getKontostand().getBetrag();
//		assertEquals(20.0, result);
//	}
//	
//	@Test
//	void testWaehrungGewechseltToFrancKontostand() {
//		Konto konto = new Girokonto();
//		konto.einzahlen(new Geldbetrag(20.0));
//		konto.waehrungswechsel(Waehrung.FRANC);
//		double result = konto.getKontostand().getBetrag();
//		assertEquals(9818.4, result);
//	}
//	
//	@Test
//	void testWaehrungGewechseltFromEscudoToFrancKontostand() {
//		Konto konto = new Girokonto();
//		konto.waehrungswechsel(Waehrung.ESCUDO);
//		konto.einzahlen(new Geldbetrag(20.0, Waehrung.ESCUDO));
//		konto.waehrungswechsel(Waehrung.FRANC);
//		double result = konto.getKontostand().getBetrag();
//		assertEquals(89.4, result);
//	}
//	
//	@Test
//	void testDispo() {
//		Girokonto konto = new Girokonto();
//		double n = 500.0;
//		assertEquals(n, konto.getDispo().getBetrag());
//	}
//	
//	@Test
//	void testDispoWaehrungsWechsel() {
//		Girokonto konto = new Girokonto();
//		konto.waehrungswechsel(Waehrung.FRANC);
//		double n = 245460;
//		assertEquals(n, konto.getDispo().getBetrag());
//	}
//}
