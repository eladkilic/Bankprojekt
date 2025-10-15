package bankprojekt.verwaltung;


import java.util.ArrayList;

import java.util.Comparator;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import bankprojekt.verarbeitung.Geldbetrag;
import bankprojekt.verarbeitung.GesperrtException;
import bankprojekt.verarbeitung.Konto;
import bankprojekt.verarbeitung.Kunde;
import bankprojekt.verarbeitung.UeberweisungsfaehigesKonto;

/**
 * ÜB 4
 * verwaltet Konten
 */
public class Bank implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * bankleitzahl von bank
	 */
	private long bankleitzahl;
	/**
	 * bank mit elementen: 
	 * kontonummer als key, konto als kontostand
	 */
	Map<Long, Konto> bank;
	
	/**
	 * konstruktor, erstellt ein bank
	 * @param bankleitzahl gegebene bankleitzahl
	 */
	public Bank(long bankleitzahl) {
		this.bankleitzahl = bankleitzahl;
		bank = new HashMap<Long, Konto>();
	}
	
	/**
	 * liefert bankleitzahl zurück
	 * @return
	 */
	public long getBankleitzahl() {
		return this.bankleitzahl;
		
	}
	/**
	 * ÜB 11
	 */
	public long kontoErstellen(KontoFabrik fabrik, Kunde inhaber) {
		if (inhaber == null) {
			throw new IllegalArgumentException("kunde null");
		}
		Konto konto = fabrik.erzeugeKonto(inhaber);
		long kontonr = bank.size() + 1;
		if (bank.containsKey(kontonr)) {
			kontonr++;
		}
		bank.put(kontonr, konto);
		return kontonr;
	}
	
//	/**
//	 * erstellt girokonto für gegebene kunde
//	 * erstellt neue, noch nicht vergebene kontonummer
//	 * @param inhaber inhaber von konto
//	 * @return neue kontonummer von konto
//	 */
//	public long girokontoErstellen(Kunde inhaber) throws GesperrtException {
//		if (inhaber == null) {
//			throw new IllegalArgumentException("kunde null");
//		}
//		long kontonr = bank.size() + 1;
//		if (bank.containsKey(kontonr)) {
//			kontonr++;
//		}
//		bank.put(kontonr, konto);
//		return kontonr;
//		Girokonto konto = new Girokonto();
//		konto.setInhaber(inhaber);
//		
//	}
//	
//	/**
//	 * erstellt sparbuch
//	 * @param inhaber inhaber von sparbuch
//	 * @return neue kontonummer von sparbuch
//	 */
//	public long sparbuchErstellen(Kunde inhaber) {
//		if (inhaber == null) {
//			throw new IllegalArgumentException("kunde null");
//		}
//		
//		Sparbuch konto = new Sparbuch();
//		try {
//			konto.setInhaber(inhaber);
//		} catch (GesperrtException e) {
//			e.printStackTrace();
//		}
//		long kontonr = bank.size() + 1;
//		if (bank.containsKey(kontonr)) {
//			kontonr++;
//		}
//		bank.put(kontonr, konto);
//		return kontonr;
//	}
//	
//	/**
//	 * ÜB- 5
//	 * fügt das gegebene Konto k 
//	 * @param konto konto 
//	 * @return long nr von konto
//	 */
//	public long mockEinfuegen(Konto k) {
//		if (k == null) {
//			throw new IllegalArgumentException("konto null");
//		}
//
//		long kontonr = bank.size() + 1;
//		if (bank.containsKey(kontonr)) {
//			kontonr++;
//		}
//		bank.put(kontonr, k);
//		return kontonr;
//	}
//	
	/**
	 * liefert auflistung von kontonummer und kontostand 
	 * zu jedem konto
	 * @return auflistung
	 */
	public String getAlleKonten() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Long, Konto> e : bank.entrySet()) {
			String kunde = e.getValue().getInhaber().getName();
			Geldbetrag kontostand = e.getValue().getKontostand();
			
			sb.append("Kontonummer: "+e.getKey()).
			append(System.getProperty("line.separator"))
			.append(" Inhaber: ").append(kunde)
			.append(" - Kontostand: ").append(kontostand).append(System.getProperty("line.separator"));
		}
		return sb.toString();
	}
	
	/**
	 * liefert liste aller gültigen kontonummern in bank
	 * @return liste 
	 */
	public List<Long> getAlleKontonummern(){
		List<Long> allenummer = new ArrayList<Long>();
		for (Long k : bank.keySet()) {
			allenummer.add(k);
		}
		return allenummer;
	}
	
	/**
	 * hebt betrag vom konto mit nummmer von ab 
	 * @param von konto wo geld abgehoben werden soll
	 * @param betrag abhebende betrag
	 * @return true wenn abhebung klappt
	 */
	public boolean geldAbheben(long von, Geldbetrag betrag) {
		if (betrag == null) {
				throw new IllegalArgumentException("betrag null");
		}
		if (!bank.containsKey(von)) {
			throw new IllegalArgumentException("konto not in bank");
		}
		
		Konto konto = bank.get(von);
		try {
			return konto.abheben(betrag);
		} catch (GesperrtException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * zählt angegebene betrag auf konto mit kontonr auf
	 * @param auf einzahlende konto
	 * @param betrag einzahlende betrag
	 * @throws GesperrtException 
	 */
	public void geldEinzahlen (long auf, Geldbetrag betrag) throws GesperrtException {
		if (betrag == null) {
			throw new IllegalArgumentException("betrag null");
		}
		if (!bank.containsKey(auf)) {
			throw new IllegalArgumentException("konto not in bank");
		}
		Konto konto = bank.get(auf);
		konto.einzahlen(betrag);
	}
	
	/**
	 * löscht konto mit nummer 
	 * @param nummer konto
	 * @return true wenn es klappt
	 */
	public boolean kontoLoeschen(long nummer) {
		if (!bank.containsKey(nummer)) {
			throw new IllegalArgumentException("konto not in bank");
		}
		bank.remove(nummer);
		return true;
	}
	
	/**
	 * liefert kontostand des kontos mit nummer
	 * @param nummer konto
	 * @return kontostand
	 */
	public Geldbetrag getKontostand(long nummer) {
		if (!bank.containsKey(nummer)) {
			throw new IllegalArgumentException("konto not in bank");
		}
		Konto konto = bank.get(nummer);
		Geldbetrag kontostand = konto.getKontostand();
		
		return kontostand;
	}
	
	/**
	 * überweist gegebene betrag vom überweisungsfähigen konto mit
	 * vonkontonr zum überweisungsfähigem konto nachkontonr
	 * gibt zurükc ob die überweisung geklappt hat
	 * @param vonKontonr einzahlende konto
	 * @param nachKontonr betrag geht dahin
	 * @param betrag überweisende betrag
	 * @param verwendungszweck verwendungszweck 
	 * @return true wenn überweisung klappt
	 * @throws GesperrtException 
	 */
	public boolean geldUeberweisen(long vonKontonr, long nachKontonr, 
			Geldbetrag betrag, String verwendungszweck) throws GesperrtException {
		if (!bank.containsKey(vonKontonr) || !bank.containsKey(nachKontonr)) {
			throw new IllegalArgumentException("konto not in bank");
		}
		if (betrag == null || betrag.isNegativ()) {
			throw new IllegalArgumentException("betrag null or negative");
		}
		if (verwendungszweck.isEmpty() || verwendungszweck == null) {
			throw new IllegalArgumentException("verwendungszweck null");
		}
		
		Konto kontoVon = bank.get(vonKontonr);
		String von = kontoVon.getInhaber().getName();
		Konto kontoNach = bank.get(nachKontonr);
		
		if(kontoVon.isGesperrt()) {
			throw new GesperrtException(kontoVon.getKontonummer());
		}
		if(kontoNach.isGesperrt()) {
			throw new GesperrtException(kontoNach.getKontonummer());
		}
		
		if (kontoVon instanceof UeberweisungsfaehigesKonto) {
			if (kontoNach instanceof UeberweisungsfaehigesKonto) {
				try {
					((UeberweisungsfaehigesKonto) kontoVon).ueberweisungAbsenden(betrag, von, 
							nachKontonr, 1L, verwendungszweck);
					((UeberweisungsfaehigesKonto) kontoNach).ueberweisungEmpfangen(betrag, von, 
							vonKontonr, 1L, verwendungszweck);
					return true;
				} catch (GesperrtException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	/**
	 * ÜB 7
	 * zahlt auf alle konten von kunden, 
	 * die in diesem jahr 18 werden, den betrag ein
	 * @param betrag bezahlte betrag
	 */
	public void schenkungAnNeuerwachsene(Geldbetrag betrag) {
		if (betrag == null) throw new IllegalArgumentException("betrag ist null!");
		
		int year = LocalDate.now().getYear() - 18;
	
		//List<Konto> alleKonten = new ArrayList<Konto>(bank.values());
		//alleKonten.stream()
		bank.values().stream()
		.filter(konto -> {
			Kunde kunde = konto.getInhaber();
			LocalDate bday = kunde.getGeburtstag();
			return bday.getYear() == year;
		})
		.forEach(konto -> {
			try {
				konto.einzahlen(betrag);
			} catch (GesperrtException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	/**
	 * ÜB 7
	 * liefert eine Liste aller Kunden, die ein Konto mit negativem 
	 * Kontostand haben
	 * @return liste von kunden
	 */
	public List<Kunde> getKundenMitLeeremKonto() {
		List<Kunde> kunden = bank.values().stream()
				.filter(konto -> konto.getKontostand().isNegativ())
				.map(konto -> konto.getInhaber())
				.collect(Collectors.toList());
		return kunden;
	}
	
	/**
	 * ÜB 7
	 * liefert namen und geburtstage aller kunden der bank
	 * doppelte kunden sollen dabei aussortiert werden
	 * sortieren sie die liste nach monat und tag des geburtstages(nicht nach jahr)
	 * @return string
	 */
	public String getKundengeburtstage() {
		String kundenUndBday = bank.values().stream()
				.map(Konto::getInhaber) //Stream<Kunde>
				.distinct() //duplicates eliminated
				.sorted(Comparator.comparing(Kunde::getGeburtstag)) //sorted by getgeburtstag
				.map(kunde -> kunde.getName() + ": " + kunde.getGeburtstag()) 
				.collect(Collectors.joining(System.lineSeparator()));
		return kundenUndBday;
				
		/*
		 * Map<String, LocalDate> kundenUndBday = bank.values().stream()
				.map(Konto::getInhaber)
				.distinct()
				.sorted(Comparator.comparing(Kunde::getGeburtstag))
				.collect(Collectors.toMap( //sortedtan sonra bu sirayi bozuyodu
						Kunde::getName, 
						Kunde::getGeburtstag
						));
		return kundenUndBday.entrySet().stream()
				.map(entry -> entry.getKey() + ": " + entry.getValue())
				.collect(Collectors.joining(System.lineSeparator()));
		 */
	}
	
	/**
	 * liefert die anzahl der kunden die jz mind 67 sind
	 * @return anzahl
	 */
	public long getAnzahlSenioren() {
		return bank.values().stream()
		.map(Konto::getInhaber)
		.distinct()
		.filter(k -> {
			int now = LocalDate.now().getYear();
			int kundeYear = k.getGeburtstag().getYear();
			return now - kundeYear >= 67;
		})
		.count();
	}
	
	/**
	 * liefert eine liste aller kunden, deren gesamteinlage 
	 * auf all ihren konten(kann mehr als eins sein) höchstens maximum beträgt 
	 * @param maximum
	 * @return
	 */
	public List<Kunde> getAlleArmenKunden(Geldbetrag maximum){
		 return bank.values().stream()
		            .map(Konto::getInhaber)
		            .distinct() // Nur ein Kunde pro Eintrag
		            .filter(kunde -> {
		                // Summiere alle Kontostände des Kunden
		                double gesamteinlage = bank.values().stream()
		                        .filter(konto -> kunde.equals(konto.getInhaber()))
		                        .mapToDouble(konto -> konto.getKontostand().getBetrag())
		                        .sum();
		                return gesamteinlage <= maximum.getBetrag();
		            })
		            .collect(Collectors.toList());
		
//		Map<Kunde, List<Konto>> kunden = bank.values().stream()
//				.collect(Collectors.groupingBy(Konto::getInhaber));
//		
//		List<Kunde> armenKunden = kunden.entrySet().stream()
//				.filter(entry -> {
//					double total = entry.getValue().stream()
//							.mapToDouble(k -> k.getKontostand().getBetrag())
//							.sum();
//					return total <= maximum.getBetrag();
//				})
//				.map(Map.Entry::getKey)
//				.collect(Collectors.toList());
//		
//		return armenKunden;
		
		/*
		Optional<Konto> konten = bank.entrySet().stream() //Stream<Long,Konto>
				.filter(e -> e.getValue().getKontostand().getBetrag()  
						< maximum.getBetrag()) //kontostandlari kucuk olanlari filterladik
				.map(Map.Entry::getValue) //simdi de Stream<Konto>
				.findAny(); //butun kontolari aldik buraya
		
		List<Kunde> kunden = konten.stream()
				.filter(k -> k.getInhaber()) //butun inhaberleri aldim
				.filter(k -> k.getKontonummer())
				.map(entry -> entry.getInhaber() == entry.getKontonummer().ge)
		*/
	}
	
	/**
	 * ÜB 10
	 * speichert this in den angegeben ziel-strom mit allen in der bank
	 * gespeicherten infrmationen
	 * geht etwas dabei schief, wird ioexcp geowrfen
	 * @param ziel
	 * @throws IOException
	 */
	public void speichern(OutputStream ziel) throws IOException {
		try (ObjectOutputStream oos = new ObjectOutputStream(ziel)){
			oos.writeObject(this);
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ÜB 10
	 * liest aus der angegebenen quelle ein bank-obj ein
	 * geht dabei schief, soll eine leere bank zurückgegeben werden
	 * @param quelle
	 * @return
	 */
	public static Bank einlesen(InputStream quelle) throws IOException, ClassNotFoundException{
		try {
			ObjectInputStream ois = new ObjectInputStream(quelle);
			Bank bank =(Bank) ois.readObject();
			return bank;
		} catch (IOException | ClassNotFoundException e) {
			long nr =0;
			return new Bank(nr);
		}
		
	}

	
	/**
	 * gets bank size
	 * @return size of bank
	 */
	public int getBankSize() {
		return bank.size();
	}
}
