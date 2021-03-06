package de.trawizardsOfJava.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
@Entity
public class ArtikelKaufen {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String verkaeufer;
	private String artikelName;
	private String beschreibung;
	private String standort;
	private int preis;
	private ArrayList<String> fotos;
	boolean verkauft = false;
}
