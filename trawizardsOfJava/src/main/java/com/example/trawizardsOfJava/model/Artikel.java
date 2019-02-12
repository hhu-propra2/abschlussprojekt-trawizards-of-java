package com.example.trawizardsOfJava.model;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Artikel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Person verleiherName; //TODO
    String artikelName;
    String beschreibung;
    String standort;
    int preis;
    int kaution;
    Verfuegbarkeit verfuegbarkeit;
}
