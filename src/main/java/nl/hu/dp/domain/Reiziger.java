package nl.hu.dp.domain;


import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Reiziger  {
    private int reiziger_id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovchipkaarten = new ArrayList<OVChipkaart>();

    protected Reiziger(){}

    public Reiziger(int reiziger_id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.reiziger_id=reiziger_id;
        this.voorletters=voorletters;
        this.tussenvoegsel=tussenvoegsel;
        this.achternaam=achternaam;
        this.geboortedatum=geboortedatum;
    }

    public int getId() {
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

    public int getReiziger_id() {
        return reiziger_id;
    }

    public Adres getAdres() {
        return adres;
    }

    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    public List<OVChipkaart> getOvchipkaarten() {
        return ovchipkaarten;
    }

     public void addToOvChipkaarten(OVChipkaart ovchipkaart) {
        ovchipkaarten.add(ovchipkaart);
     }
     public void removeFromOvChipkaarten(OVChipkaart ovchipkaart) {
        ovchipkaarten.removeIf(oldCard-> oldCard.getKaartNummer().equals(ovchipkaart.getKaartNummer()));
     }

    public void setOvchipkaarten(List<OVChipkaart> ovchipkaarten) {
        this.ovchipkaarten = ovchipkaarten;
    }

    @Override
    public String toString() {
        return "Reiziger{" +
                "reiziger_id=" + reiziger_id +
                ", voorletters='" + voorletters + '\'' +
                ", tussenvoegsel='" + tussenvoegsel + '\'' +
                ", achternaam='" + achternaam + '\'' +
                ", geboortedatum=" + geboortedatum +
                ", adres=" + adres +
                ", ovchipkaarten=" + ovchipkaarten +
                '}';
    }
}

