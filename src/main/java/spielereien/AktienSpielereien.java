package spielereien;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import bankprojekt.verarbeitung.Aktie;
import bankprojekt.verarbeitung.Aktienkonto;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;

public class AktienSpielereien {
	public static void main(String[] args) throws GesperrtException, InterruptedException, ExecutionException {
		
		Aktie aktie1 = new Aktie("123", new Geldbetrag(10));
		Aktie aktie2 = new Aktie("456", new Geldbetrag(20));
		Aktie aktie3 = new Aktie("789", new Geldbetrag(30));
		Aktienkonto konto = new Aktienkonto(aktie1, 2);
		konto.einzahlen(new Geldbetrag(100.0));
		Future<Geldbetrag> future = konto.kaufauftrag("123", 4, new Geldbetrag(10.0));
		System.out.println("Warte auf Kauf...");
		Geldbetrag ergebnis = future.get(); // This waits for the task to finish!
		System.out.println("Gekauft für: " + ergebnis);
		System.out.println(konto.getKontostand());
		
		Future<Geldbetrag> fu = konto.verkaufauftrag("123", new Geldbetrag(10.2));
		System.out.println("Warte auf Verkauf...");
		Geldbetrag ergebnis2 = fu.get(); // This waits for the task to finish!
		System.out.println("Verkauft für: " + ergebnis2);
		System.out.println(konto.getKontostand());
		
		try {
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
			Thread.sleep(3000);
			System.out.println(aktie1.getKurs());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
