//package tests;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import bankprojekt.geld.Waehrung;
//import bankprojekt.verarbeitung.Geldbetrag;
//import bankprojekt.verarbeitung.GesperrtException;
//import bankprojekt.verarbeitung.Girokonto;
//import bankprojekt.verarbeitung.Konto;
//import bankprojekt.verarbeitung.Kunde;
//import bankprojekt.verwaltung.Bank;
//
//class BankTest {
//
//	Bank bank = new Bank(123);
//	
//	@Test
//	void testBankErstellen() {
//		long result = bank.getBankleitzahl();
//		assertEquals(123, result);
//	}
//	
//	@Test
//	void testGirokontoErstellen() throws GesperrtException {
//		long result = bank.girokontoErstellen(Kunde.MUSTERMANN);
//		assertEquals(1, result);
//	}
//
//	@Test
//	void testGirokontoErstellen4() throws GesperrtException {
//		bank.girokontoErstellen(Kunde.MUSTERMANN);
//		bank.girokontoErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		long result = 
//				bank.girokontoErstellen(new Kunde("Cemre","Kilic","Istanbul", LocalDate.now()));
//		assertEquals(4, result);
//	}
//	
//	@Test
//	void testSparbuchErstellen() {
//		long result = bank.sparbuchErstellen(Kunde.MUSTERMANN);
//		assertEquals(1, result);
//	}
//
//	@Test
//	void testSparbuchErstellen4() {
//		bank.sparbuchErstellen(Kunde.MUSTERMANN);
//		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		bank.sparbuchErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		long result = 
//				bank.sparbuchErstellen(new Kunde("Cemre","Kilic","Istanbul", LocalDate.now()));
//		assertEquals(4, result);
//	}
//	
//	@Test
//	void testSparbuchGirokontoErstellen() throws GesperrtException {
//		bank.sparbuchErstellen(Kunde.MUSTERMANN);
//		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		bank.sparbuchErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		long result = 
//				bank.sparbuchErstellen(new Kunde("Cemre","Kilic","Istanbul", LocalDate.now()));
//		assertEquals(5, result);
//	}
//	
//	@Test
//	void testBankSize() throws GesperrtException {
//		bank.sparbuchErstellen(Kunde.MUSTERMANN);
//		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		bank.sparbuchErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		bank.sparbuchErstellen(new Kunde("Cemre","Kilic","Istanbul", LocalDate.now()));
//		int result = bank.getBankSize();
//		assertEquals(5, result);
//	}
//	
////	@Test
////	void testBankGetAlleKonten() {
////		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
////		bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
////		String result = bank.getAlleKonten();
////		String exp = "Kontonummer: 1\n Inhaber: Tan, Günes - Kontostand: 0.00 €";
////		exp += System.getProperty("line.separator") + "Kontonummer: 2 \nInhaber: Kilic, Ela - Kontostand: 0.00 €";
////		assertEquals(exp, result);
////	}
//	
//	
//	@Test
//	void testBankGetAlleKontonummer() throws GesperrtException {
//		long nr1 = bank.sparbuchErstellen(Kunde.MUSTERMANN);
//		long nr2 = bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		long nr3 = bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		long nr4 = bank.sparbuchErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		
//		List<Long> result = bank.getAlleKontonummern();
//		List<Long> exp = new ArrayList<>();
//		exp.add(0, nr1);
//		exp.add(1, nr2);
//		exp.add(2, 3L);
//		exp.add(3, nr4);
//		assertEquals(exp, result);
//	}
//	
//	@Test
//	void testBankGeldAbheben() throws GesperrtException {
//		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		Kunde kunde = new Kunde("Ela","Kilic","Istanbul", LocalDate.now());
//		long nr = bank.girokontoErstellen(kunde);
//		boolean result = bank.geldAbheben(nr, new Geldbetrag(10.0));
//		assertTrue(result);
//		Geldbetrag resultKontostand = bank.getKontostand(nr);
//		Geldbetrag exptKontostand = new Geldbetrag(-10.0);
//		assertEquals(exptKontostand, resultKontostand);
//	}
//	
//	@Test
//	void testBankGeldEinzahlen() throws GesperrtException {
//		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		Kunde kunde = new Kunde("Ela","Kilic","Istanbul", LocalDate.now());
//		long nr = bank.girokontoErstellen(kunde);
//		bank.geldEinzahlen(nr, new Geldbetrag(100.0));
//		Geldbetrag resultKontostand = bank.getKontostand(nr);
//		Geldbetrag exptKontostand = new Geldbetrag(100.0);
//		assertEquals(exptKontostand, resultKontostand);
//	}
//	
//	@Test
//	void testBankUeberweisung() throws GesperrtException {
//		long von = bank.girokontoErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		long nach = bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		
//		boolean result = bank.geldUeberweisen(von, nach, new Geldbetrag(5.0), "al sana para");
//		assertTrue(result);
//	}
//	
//	@Test
//	void testBankUeberweisungNICHTUeberweisungsfaehig() throws GesperrtException {
//		long von = bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		long nach = bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		
//		boolean result = bank.geldUeberweisen(von, nach, new Geldbetrag(5.0), "al sana para");
//		assertFalse(result);
//	}
//	
//	@Test
//	void testBankKontoLoeschen() throws GesperrtException {
//		bank.girokontoErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		long von =bank.girokontoErstellen(new Kunde("Ela","Kilic","Istanbul", LocalDate.now()));
//		bank.sparbuchErstellen(new Kunde("Günes","Tan","Istanbul", LocalDate.now()));
//		boolean result = bank.kontoLoeschen(von);
//		assertTrue(result);
//		
//		List<Long> resulto = bank.getAlleKontonummern();
//		List<Long> exp = new ArrayList<>();
//		exp.add(0, 1L);
//		exp.add(1, 3L);
//		assertEquals(exp, resulto);
//	}
//	
////_--------------------Konto
//	@Test
//	void testKontoauszugEintragKonstruktor() {
//		Kunde kunde = new Kunde("Ela","Kilic","Istanbul", LocalDate.now());
//		Konto konto = new Girokonto(kunde, 123, new Geldbetrag(20.0));
//		String s= konto.getKontoauszug();
//		String exp = "2025-07-17: Konto 123 wurde erstellt mit: 0.00 €";
//		assertEquals(exp, s);
//	}
//	
//	@Test
//	void testKontoauszugEintragWaehrungwechsel() {
//		Kunde kunde = new Kunde("Ela","Kilic","Istanbul", LocalDate.now());
//		Konto konto = new Girokonto(kunde, 123, new Geldbetrag(20.0));
//		konto.waehrungswechsel(Waehrung.ESCUDO);
//		String s= konto.getKontoauszug();
//		String exp = "2025-07-17: Konto 123 wurde erstellt mit: 0.00 €";
//		exp += System.lineSeparator() + "2025-07-17: Währung wurde gewechselt zu ESCUDO: 0.00 €";
//		assertEquals(exp, s);
//	}
//	
//	@Test
//	void testKontoauszugEintragSetInhaber() throws GesperrtException {
//		Kunde kunde = new Kunde("Ela","Kilic","Istanbul", LocalDate.now());
//		Konto konto = new Girokonto();
//		konto.setInhaber(kunde);
//		String s= konto.getKontoauszug();
//		String exp = "2025-07-17: Konto 99887766 wurde erstellt mit: 0.00 €";
//		exp += System.lineSeparator() + "2025-07-17: Inhaber wurde erstellt Kilic, Ela: 0.00 €";
//		assertEquals(exp, s);
//	}
//	
//	@Test
//	void testKontoauszugEintragEinzahlen() throws GesperrtException {
//		Konto konto = new Girokonto();
//		konto.einzahlen(new Geldbetrag(50.0));
//		String s= konto.getKontoauszug();
//		String exp = "2025-07-17: Konto 99887766 wurde erstellt mit: 0.00 €";
//		exp += System.lineSeparator() + "2025-07-17: Einzahlung von: 50.00 €";
//		assertEquals(exp, s);
//	}
//	
//	@Test
//	void testKontoauszugEintragSperren() throws GesperrtException {
//		Konto konto = new Girokonto();
//		konto.sperren();
//		String s= konto.getKontoauszug();
//		String exp = "2025-07-17: Konto 99887766 wurde erstellt mit: 0.00 €";
//		exp += System.lineSeparator() + "2025-07-17: Konto wurde gesperrt: 0.00 €";
//		assertEquals(exp, s);
//	}
//	
//	@Test
//	void testKontoauszugEintragEntsperren() throws GesperrtException {
//		Konto konto = new Girokonto();
//		konto.sperren();
//		konto.entsperren();
//		String s= konto.getKontoauszug();
//		String exp = "2025-07-17: Konto 99887766 wurde erstellt mit: 0.00 €";
//		exp += System.lineSeparator() + "2025-07-17: Konto wurde gesperrt: 0.00 €";
//		exp += System.lineSeparator() + "2025-07-17: Konto wurde entsperrt: 0.00 €";
//		assertEquals(exp, s);
//	}
//}
