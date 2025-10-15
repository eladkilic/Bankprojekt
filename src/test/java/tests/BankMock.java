package tests;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Girokonto;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verwaltung.Bank;

class BankMock {

	//geldUeberweisen()
	@Test
	void testGeldUeberweisenNormall() throws GesperrtException {
		//setup: konstruktor fln
		//Bank-Kunde-Konto-Geldbetrag
		Bank bank = new Bank(123L);
		Kunde mockKunde = Mockito.mock();
		Girokonto mockKonto = Mockito.mock();
		Girokonto mockKontoNach = Mockito.mock();
		Geldbetrag mockBetrag = Mockito.mock();
		
		long nrVon = bank.mockEinfuegen(mockKonto);
		long nrNach = bank.mockEinfuegen(mockKontoNach);
		
		//methoden
		Mockito.when(mockKonto.getInhaber()).thenReturn(mockKunde);
		Mockito.when(mockKonto.getKontonummer()).thenReturn(nrVon);
		Mockito.when(mockKontoNach.getKontonummer()).thenReturn(nrNach);
		Mockito.when(mockBetrag.getBetrag()).thenReturn(50.0);
		
		//exercise aufruf testende methoden
		boolean result = bank.geldUeberweisen(nrVon, nrNach, mockBetrag, "hlelo");
		//verify überprüfung ergebnisse
		assertTrue(result);
	}
	
	@Test
	void testGeldUeberweisenKontoGesperrt() throws GesperrtException {
		//setup: konstruktor fln
			//Bank-Kunde-Konto-Geldbetrag
			Bank bank = new Bank(123L);
			Kunde mockKunde = Mockito.mock();
			Girokonto mockKonto = Mockito.mock();
			Girokonto mockKontoNach = Mockito.mock();
			Geldbetrag mockBetrag = Mockito.mock();
			
			long nrVon = bank.mockEinfuegen(mockKonto);
			long nrNach = bank.mockEinfuegen(mockKontoNach);
			
			//methoden
			Mockito.when(mockKonto.getInhaber()).thenReturn(mockKunde);
			Mockito.when(mockKonto.getKontonummer()).thenReturn(nrVon);
			Mockito.when(mockKontoNach.getKontonummer()).thenReturn(nrNach);
			Mockito.when(mockBetrag.getBetrag()).thenReturn(50.0);
			Mockito.when(mockKonto.isGesperrt()).thenReturn(true);
			
		//exercise aufruf testende methoden
			//doThrow(new GesperrtException(nrVon)).when(mockKonto).ueberweisungAbsenden(mockBetrag, "Kilic, Ela", nrNach, 0, "test");
			
		//verify überprüfung ergebnisse
		assertThrows(GesperrtException.class, () ->
				bank.geldUeberweisen(nrVon, nrNach, mockBetrag, "testing"));
	}
	
	@Test
	void testGeldUeberweisenNegativBetrag() throws GesperrtException {
		//setup: konstruktor fln
		//Bank-Kunde-Konto-Geldbetrag
		Bank bank = new Bank(123L);
		Kunde mockKunde = Mockito.mock();
		Girokonto mockKonto = Mockito.mock();
		Girokonto mockKontoNach = Mockito.mock();
		Geldbetrag mockBetrag = Mockito.mock();
		
		long nrVon = bank.mockEinfuegen(mockKonto);
		long nrNach = bank.mockEinfuegen(mockKontoNach);
		
		//methoden
		Mockito.when(mockBetrag.isNegativ()).thenReturn(true);
		Mockito.when(mockKonto.getInhaber()).thenReturn(mockKunde);
		Mockito.when(mockKonto.getKontonummer()).thenReturn(nrVon);
		Mockito.when(mockKontoNach.getKontonummer()).thenReturn(nrNach);
		
		//exercise aufruf testende methoden
		assertThrows(IllegalArgumentException.class, () ->
		bank.geldUeberweisen(nrVon, nrNach, mockBetrag, "testing"));
	}
	//getKontostand()
	@Test
	void testGetKontostandNormall() {
		Bank bank = new Bank(123);
		Girokonto mockKonto = Mockito.mock();
		long nr = bank.mockEinfuegen(mockKonto);
		Geldbetrag mockBetrag = Mockito.mock();
		Mockito.when(mockKonto.getKontostand()).thenReturn(mockBetrag);
		Mockito.when(mockKonto.getKontonummer()).thenReturn(nr);
		
		Geldbetrag betrag = bank.getKontostand(nr);
		
		assertEquals(betrag, mockBetrag);
		Mockito.verify(mockKonto).getKontostand();
	}
	
	@Test
	void testGetKontostandKontoExistiertNicht() {
		Bank bank = new Bank(123);
	
		assertThrows(IllegalArgumentException.class, () ->
		bank.getKontostand(11L));
	}
}
