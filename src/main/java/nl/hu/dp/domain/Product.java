package nl.hu.dp.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "product_nummer")
    private Integer productNummer;
    @Column(name = "naam")
    private String naam;
    @Column(name = "beschrijving")
    private String beschrijving;
    @Column(name = "prijs")
    private Double prijs;
    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> chipkaarten= new ArrayList<>();

    public Product(Integer productNummer, String naam, String beschrijving, Double prijs) {
        this.productNummer = productNummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    protected Product() {

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
