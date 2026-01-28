package nl.hu.dp.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {
    private Integer kaartNummer;
    private Date geldigTot;
    private Integer klasse;
    private Double saldo;
    private Reiziger reiziger;
    private List<Product> producten=new ArrayList<>();

    public OVChipkaart(int kaartNummer, Date geldigTot, int klasse, double saldo, Reiziger reiziger) {

        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public Integer getKaartNummer() {
        return kaartNummer;
    }

    public void setKaartNummer(Integer kaartNummer) {
        this.kaartNummer = kaartNummer;
    }

    public Date getGeldigTot() {
        return geldigTot;
    }

    public void setGeldigTot(Date geldigTot) {
        this.geldigTot = geldigTot;
    }

    public Integer getKlasse() {
        return klasse;
    }

    public void setKlasse(Integer klasse) {
        this.klasse = klasse;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    public void setReiziger(Reiziger reiziger) {
        this.reiziger = reiziger;
    }

    public List<Product> getProducten() {
        return producten;
    }

    public void setProducten(List<Product> producten) {
        this.producten = producten;
    }
    public void addProduct(Product product) {
        this.producten.add(product);
        product.addChipkaart(this);
    }
    public void removeProduct(Product product) {
        this.producten.remove(product);
        product.removeChipkaart(this);
    }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaartNummer=" + kaartNummer +
                ", geldigTot=" + geldigTot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                ", reiziger=" + reiziger.getReiziger_id() +
                ", producten=" + producten +
                '}';
    }
}

