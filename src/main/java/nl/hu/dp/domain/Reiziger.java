package nl.hu.dp.domain;


import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "reiziger")
public class Reiziger  {
    @Id
    @Column(name = "reiziger_id")
    private Integer reiziger_id;
    @Column(name = "voorletters")
    private String voorletters;
    @Column(name= "tussenvoegsel")
    private String tussenvoegsel;
    @Column(name = "achternaam")
    private String achternaam;
    @Column(name = "geboortedatum")
    private Date geboortedatum;
    @OneToOne(mappedBy = "reiziger",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private Adres adres;


    protected Reiziger(){}

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.tussenvoegsel=tussenvoegsel;
        this.achternaam=achternaam;
        this.geboortedatum=geboortedatum;
    }

    public Integer getId() {
        return reiziger_id;
    }

    public void setId(int reiziger_id) {
        this.reiziger_id = reiziger_id;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reiziger_id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", adres=" + (adres != null ? adres.getStraat() + " " + adres.getHuisnummer() : "Geen adres") +
                '}';
    }
}

