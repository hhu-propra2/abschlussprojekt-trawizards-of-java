package de.trawizardsOfJava.mail;

import de.trawizardsOfJava.model.Ausleihe;
import de.trawizardsOfJava.model.Kauf;
import de.trawizardsOfJava.model.Konflikt;
import de.trawizardsOfJava.model.Rueckgabe;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Message {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String absender;
	private String empfaenger;
	private String nachricht;
	private boolean neueNachricht = false;

	public Message(String empfaenger, String parameter) {
		this.nachricht = "Der Artikel wurde erfolgreich " + parameter;
		this.absender = "System";
		this.empfaenger = empfaenger;
	}

	public Message(Ausleihe ausleihe, String parameter) {
		this.nachricht = "Anfrage von " + ausleihe.getAusleihender() + " um " + ausleihe.getArtikel().getArtikelName() + " auszuleihen, wurde " + parameter;
		if("angefragt".equals(parameter)) {
			this.absender = ausleihe.getAusleihender();
			this.empfaenger = ausleihe.getVerleiherName();
		} else {
			this.absender = ausleihe.getVerleiherName();
			this.empfaenger = ausleihe.getAusleihender();
		}
	}

	public Message(Rueckgabe rueckgabe, String parameter) {
		this.nachricht = "Rückgabe von " + rueckgabe.getAusleihender() + " um " + rueckgabe.getArtikel().getArtikelName() + " zurückzugeben, wurde " + parameter;
		if("angefragt".equals(parameter)) {
			this.absender = rueckgabe.getAusleihender();
			this.empfaenger = rueckgabe.getVerleiherName();
		} else {
			this.absender = rueckgabe.getVerleiherName();
			this.empfaenger = rueckgabe.getAusleihender();
			if("abgelehnt".equals(parameter)) {
				this.nachricht += ", da der Artikel in einem mangelhaftem Zustand zurückgegeben wurde. Die Konfliktlösestelle wurde kontaktiert";
			}
		}
	}

	public Message(Kauf kauf, String parameter){

		if("angenommen".equals(parameter)){
			this.nachricht = "Kauf von Artikel " + kauf.getArtikel().getArtikelName() + " von " + kauf.getVerkaeufer() + " wurde angenommen.";
			this.absender = kauf.getVerkaeufer();
			this.empfaenger = kauf.getKaeufer();
		}
		if("angefragt".equals(parameter)){
			this.nachricht = "Sie haben eine Kaufanfrage für den Kauf von " + kauf.getArtikel().getArtikelName() + " von " + kauf.getVerkaeufer() + " gestellt.";
			this.absender = "System";
			this.empfaenger = kauf.getKaeufer();
		}
		if("angefragtVerkäufer".equals(parameter)){
			this.nachricht = "Es wurde eine Kaufanfrage für " + kauf.getArtikel().getArtikelName() + " von " + kauf.getKaeufer() + " gestellt.";
			this.absender = "System";
			this.empfaenger = kauf.getVerkaeufer();
		}
		if("abgelehnt".equals(parameter)) {
			this.nachricht = "Kauf von Artikel " + kauf.getArtikel().getArtikelName() + " von " + kauf.getVerkaeufer() + " wurde abgelehnt.";
			this.absender = kauf.getVerkaeufer();
			this.empfaenger = kauf.getKaeufer();
		}
	}

	private Message(Konflikt konflikt, String parameter, String empfaenger) {
		Rueckgabe rueckgabe = konflikt.getRueckgabe();
		this.absender = "Admin";
		this.empfaenger = empfaenger;
		this.nachricht = "Die Kaution von " + rueckgabe.getArtikel().getArtikelName() + " geht an den " + parameter;
		if("Verleihenden".equals(parameter)) {
			this.nachricht += "(" + rueckgabe.getVerleiherName() + ")";
		} else {
			this.nachricht += "(" + rueckgabe.getAusleihender() + ")";
		}
	}

	public Message(){}

	public static Message[] konfliktMessages(Konflikt konflikt, String parameter) {
		Message[] messages = new Message[2];
		messages[0] = new Message(konflikt, parameter, konflikt.getRueckgabe().getVerleiherName());
		messages[1] = new Message(konflikt, parameter, konflikt.getRueckgabe().getAusleihender());
		return messages;
	}

	public boolean schauenObNeueNachricht(){
		return this.neueNachricht;
	}
}