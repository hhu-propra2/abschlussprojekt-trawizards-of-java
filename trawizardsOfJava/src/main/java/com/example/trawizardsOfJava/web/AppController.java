package com.example.trawizardsOfJava.web;

import com.example.trawizardsOfJava.data.ArtikelRepository;
import com.example.trawizardsOfJava.data.BenutzerRepository;
import com.example.trawizardsOfJava.model.Artikel;
import com.example.trawizardsOfJava.model.Person;
import com.example.trawizardsOfJava.model.Ausleihe;
import com.example.trawizardsOfJava.model.Zeitraum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class AppController {

	@Autowired
	BenutzerRepository benutzerRepository;

	@Autowired
	ArtikelRepository artikelRepository;

	@GetMapping("/")
	public String uebersicht(Model model){

		List<Artikel> alleArtikel = artikelRepository.findAll();

		model.addAttribute("artikel", alleArtikel);

		return "uebersichtSeite";
	}

	@GetMapping("/benutzerverwaltung/{benutzername}")
	public String benutzerverwaltung(Model model, @PathVariable String benutzername){
		model.addAttribute("person", benutzerRepository.findByBenutzername(benutzername).get());
		return "Benutzerverwaltung";
	}

	@PostMapping("/benutzerverwaltung/{benutzername}")
	public String speicherAenderung(Person person){
		benutzerRepository.save((person));
		return "Benutzerverwaltung";
	}

	@GetMapping("/registrierung")
	public String registrierung(Model model){
		model.addAttribute("person", new Person());
		return "Registrierung";
	}

	@PostMapping("/registrierung")
	public String speicherePerson(Person person){
		benutzerRepository.save((person));
		return "BackToTheFuture";
	}

    @GetMapping("/artikel/{id}/anfrage")
    public String neueAnfrage(){
        return "ausleihe";
    }

    @PostMapping("/artikel/{id}/anfrage")
    public String speichereAnfrage(@PathVariable("id") Long id, @RequestParam LocalDate startdate, @RequestParam LocalDate enddate){
        Zeitraum zeitraum = new Zeitraum(startdate,enddate);
        Ausleihe ausleihe = new Ausleihe();
        ausleihe.setZeitraum(zeitraum);
        ausleihe.setArtikel(artikelRepository.findById(id).get());
        //ausleihe.setAusleihender();
        return "/artikel/uebersicht";
    }
}
