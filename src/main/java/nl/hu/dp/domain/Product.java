package nl.hu.dp.domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private Integer productNummer;
    private String naam;
    private String beschrijving;
    private Double prijs;
    private List<OVChipkaart> chipkaarten= new ArrayList<>();

    public Product(Integer productNummer, String naam, String beschrijving, Double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public Integer getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(Integer productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }

    public List<OVChipkaart> getChipkaarten() {
        return chipkaarten;
    }

    public void setChipkaarten(List<OVChipkaart> chipkaarten) {
        this.chipkaarten = chipkaarten;
    }
    public void addChipkaart(OVChipkaart chipkaart) {
        this.chipkaarten.add(chipkaart);
    }
    public void removeChipkaart(OVChipkaart chipkaart) {
        this.chipkaarten.remove(chipkaart);
    }

    @Override
    public String toString() {
        List<Integer> chipkaartids= new ArrayList<>();
        for (OVChipkaart chipkaart : chipkaarten) {
            chipkaartids.add(chipkaart.getKaartNummer());
        }
        return "Product{" +
                "productNummer=" + productNummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                ", chipkaarten=" + chipkaartids +
                '}';
    }
}
