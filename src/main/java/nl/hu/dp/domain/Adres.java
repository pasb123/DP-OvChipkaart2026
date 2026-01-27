package nl.hu.dp.domain;

public class Adres {
    private int id;
    private String postcode;
    private String straat;
    private String woonplaats;
    private String huisnummer;
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
                ", huisnummer=" + huisnummer +
                '}';
    }
}
