package nl.hu.dp.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "adres")
public class Adres {
    @Id
    @Column(name = "adres_id")
    private Integer id;
    @Column(name = "postcode")
    private String postcode;
    @Column(name = "straat")
    private String straat;
    @Column(name = "woonplaats")
    private String woonplaats;
    @Column(name = "huisnummer")
    private String huisnummer;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;



    public String  getHuisnummer() {
        return huisnummer;
    }
    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }
    public void setHuisnummer(String huisnummer) {
        this.huisnummer = huisnummer;
    }

    public Adres() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    @Override
    public String toString() {
        return "Adres{" +
                "id=" + id +
                ", postcode='" + postcode + '\'' +
                ", straat='" + straat + '\'' +
                ", woonplaats='" + woonplaats + '\'' +
                ", huisnummer='" + huisnummer + '\'' +
                ", reiziger=" + (reiziger != null ? reiziger.getId() : "Geen reiziger") +
                '}';
    }
}
