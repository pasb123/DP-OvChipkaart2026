package nl.hu.dp;

import nl.hu.dp.dao.*;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.List;

public class Main {
    //connection link for assignment
    public static Connection connection;

    // connection method
    private static Connection getConnection() {
        String linkJB = "jdbc:postgresql://localhost:5432/ovchip";
        String username = "postgres";
        String password = "sand66";

        try {
            connection = DriverManager.getConnection(linkJB, username, password);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return connection;
    };
    //close the connection
    public static void closeConnection() throws SQLException {
        if(connection != null){connection.close();
        }


    }
    //test the connection
    private static void testConnection() throws SQLException {
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery("SELECT * FROM reiziger");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("reiziger_id") + " "
                        + resultSet.getString("voorletters") + " "
                        + resultSet.getString("achternaam") + " "
                        + resultSet.getString("geboortedatum"));

            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        closeConnection();
    }
    /**
     * P2. main.java.nl.hu.main.java.nl.hu.dp.domain.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de main.java.nl.hu.main.java.nl.hu.dp.domain.Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        //update
        Reiziger updateSietske=new Reiziger(77, "S O", "", "Boersen", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.update() ");
        rdao.update(updateSietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        //delete
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        //findById
        System.out.println("\n[Test] Test ReizigerDAO.findById()");
        Reiziger reizigerById = rdao.findById(5);
        if (reizigerById != null) {
            System.out.println("Reiziger met ID: " + reizigerById.getId()+" "+ reizigerById.getAchternaam());
        } else {
            System.out.println("Geen reiziger gevonden");

        }// Test findByGbdatum
        System.out.println("\n[Test] Test ReizigerDAO.findByGbdatum()");
        String geboortedatum = "2002-12-03";
        Date gbDatum = Date.valueOf(geboortedatum);

        List<Reiziger> reizigersByDate = rdao.findByGbdatum(gbDatum);
        System.out.println("Reizigers met geboortedatum " + geboortedatum + ":");
        for (Reiziger reiziger : reizigersByDate) {
            System.out.println(reiziger);
        }

    }
    private static void testAdresDAO(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.ovchip.data.DAO.AdresDAO -------------");
        System.out.println("test " + adao.getClass());

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] nl.hu.ovchip.data.DAO.AdresDAO.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // Maak een nieuw adres aan en persisteer dit in de database
        String gbdatum = "1981-03-14";
        List<Reiziger> reizigers = rdao.findAll();
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        rdao.save(sietske);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na nl.hu.ovchip.data.DAO.ReizigerDAO.save() ");
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        Adres nieuwAdres = new Adres();
        nieuwAdres.setId(88);
        nieuwAdres.setPostcode("1234AB");
        nieuwAdres.setHuisnummer("123");
        nieuwAdres.setStraat("Langelaan");
        nieuwAdres.setWoonplaats("Nieuwe Stad");
        nieuwAdres.setReiziger(sietske);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.save() ");
        sietske.setAdres(nieuwAdres);
        adao.save(nieuwAdres);
        rdao.update(sietske);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.println();

        // update
        Adres updateAdres = nieuwAdres;
        updateAdres.setStraat("Heel Erg Langelaan");
        updateAdres.setWoonplaats("Nieuwegein");
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.update() ");
        sietske.setAdres(updateAdres);
        rdao.update(sietske);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        //find by reiziger
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.findByReiziger() ");
        Adres adres3= adao.findByReiziger(sietske);
        System.out.println("het adres is " + adres3);

        // delete
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na nl.hu.ovchip.data.DAO.AdresDAO.delete() ");
        adao.delete(adres3);
        rdao.delete(sietske);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
        for (Adres a : adressen) {
            System.out.println(a);
        }
    }
    private static void testOVChipkaartDAO(OVChipkaartDAO odao, ReizigerDAO rdao,ProductDAO pdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.dp.dao.OVChipkaartDAO -------------");
        System.out.println("test " + odao.getClass());

        // Haal alle OV-chipkaarten op uit de database
        List<OVChipkaart> kaarten = odao.findAll();
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende kaarten:");
        for (OVChipkaart k : kaarten) {
            System.out.println(k);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan voor de OV-chipkaart
        String gbdatum = "1995-06-15";
        Reiziger piet = new Reiziger(77, "P", "", "Jansen", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + rdao.findAll().size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(piet);
        System.out.println(rdao.findAll().size() + " reizigers\n");

        // Maak een nieuwe OV-chipkaart aan
        OVChipkaart kaart1 = new OVChipkaart(9999,Date.valueOf("2026-12-31"),1,50.00,piet);
        OVChipkaart kaart2 = new OVChipkaart(7777,Date.valueOf("2026-12-31"),1,50.00,piet);
        System.out.print("[Test] Eerst " + kaarten.size() + " kaarten, na OVChipkaartDAO.save() ");
        piet.addToOvChipkaarten(kaart1);
        piet.addToOvChipkaarten(kaart2);
        odao.save(kaart1);
        odao.save(kaart2);
        rdao.update(piet);
        kaarten = odao.findAll();
        System.out.println(kaarten.size() + " kaarten\n");
        for (OVChipkaart k : kaarten) {
            System.out.println(k);
        }
        System.out.println();

        // Update de kaart
        kaart1.setSaldo(75.0);
        kaart1.setKlasse(1);
        piet.removeFromOvChipkaarten(kaart1);
        piet.addToOvChipkaarten(kaart1);
        System.out.print("[Test] Eerst " + kaarten.size() + " kaarten, na OVChipkaartDAO.update() ");
        rdao.update(piet);
        kaarten = odao.findAll();
        System.out.println(kaarten.size() + " kaarten\n");
        for (OVChipkaart k : kaarten) {
            System.out.println(k);
        }
        System.out.println();

        // Find by reiziger
        System.out.print("[Test] FindByReiziger voor " + piet + ": ");
        List<OVChipkaart> kaartenVanPiet = odao.findByReiziger(piet);
        for (OVChipkaart k : kaartenVanPiet) {
            System.out.println(k);
        }
        System.out.println();

        // Delete de kaart en de reiziger
        System.out.print("[Test] Eerst " + kaarten.size() + " kaarten, na OVChipkaartDAO.delete() ");
        odao.delete(kaart1);
        rdao.delete(piet);
        kaarten = odao.findAll();
        System.out.println(kaarten.size() + " kaarten\n");
        for (OVChipkaart k : kaarten) {
            System.out.println(k);
        }
    }
    private static void testProductDAO(ProductDAO pdao, OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test nl.hu.dp.dao.ProductDAO -------------");
        System.out.println("test " + pdao.getClass());

        // Haal alle producten op uit de database
        List<Product> producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product p : producten) System.out.println(p);
        System.out.println();

        // Maak een nieuwe reiziger aan
        String gbdatum = "1995-06-15";
        Reiziger piet = new Reiziger(100, "P", "", "Jansen", Date.valueOf(gbdatum));
        rdao.save(piet);

        // Maak een nieuwe OV-chipkaart aan
        OVChipkaart kaart1 = new OVChipkaart(8888, Date.valueOf("2026-12-31"), 1, 50.0, piet);
        piet.addToOvChipkaarten(kaart1);
        odao.save(kaart1);
        rdao.update(piet);

        // Maak nieuwe producten aan en koppel aan OV-chipkaart
        Product product1 = new Product(101, "Gratis Utrecht", "Gratis met de bus door Utrecht", 10.0);
        Product product2 = new Product(102, "Veluwe tour", "gratis met de bus door de Veluwe", 15.0);
        kaart1.addProduct(product1);
        kaart1.addProduct(product2);

        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        pdao.save(product1);
        pdao.save(product2);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");
        for (Product p : producten) System.out.println(p);
        System.out.println();

        // Update een product
        product1.setNaam("Gratis provincie Utrecht");
        product1.setPrijs(12.5);
        product1.setBeschrijving("Vrij reizen door de hele provincie Utrecht");
        System.out.print("[Test] Update product " + product1.getProductNummer() + ": ");
        pdao.update(product1);
        producten = pdao.findAll();
        for (Product p : producten) System.out.println(p);
        System.out.println();

        //Vind producten per OV-chipkaart
        System.out.println("[Test] Producten gekoppeld aan OV-chipkaart " + kaart1.getKaartNummer() + ":");
        List<Product> productenVanKaart = pdao.findByOvChipkaart(kaart1);
        for (Product p : productenVanKaart) System.out.println(p);
        System.out.println();

        //  Delete producten
        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.delete() ");
        pdao.delete(product1);
        pdao.delete(product2);
        producten = pdao.findAll();
        System.out.println(producten.size() + " producten\n");
        for (Product p : producten) System.out.println(p);

        // verwijder OV-chipkaart en reiziger
        odao.delete(kaart1);
        rdao.delete(piet);
    }


    public static void main(String[] args) throws SQLException {
        testConnection();
        AdresDAOPsql adresDAOPsql=new AdresDAOPsql(getConnection());
        ReizigerDAOPsql reizigerDAOPsql=new ReizigerDAOPsql(getConnection());
        OVChipkaartPsql ovChipkaartPsql=new OVChipkaartPsql(getConnection());
        ProductDAOPsql productDAOPsql=new ProductDAOPsql(getConnection());
        ovChipkaartPsql.setRdao(reizigerDAOPsql);
        ovChipkaartPsql.setPdao(productDAOPsql);
        productDAOPsql.setOvChipkaartDAO(ovChipkaartPsql);
        adresDAOPsql.setRdao(reizigerDAOPsql);
        reizigerDAOPsql.setAdresDAO(adresDAOPsql);
        reizigerDAOPsql.setOdao(ovChipkaartPsql);
        testReizigerDAO(reizigerDAOPsql);
        testAdresDAO(adresDAOPsql,reizigerDAOPsql);
        testOVChipkaartDAO(ovChipkaartPsql,reizigerDAOPsql,productDAOPsql);
        testProductDAO(productDAOPsql,ovChipkaartPsql,reizigerDAOPsql);
    }
}