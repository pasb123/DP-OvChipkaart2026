package nl.hu.dp;

import nl.hu.dp.dao.*;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
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
    private static void testReizigerDAO(ReizigerDAO rdao, AdresDAO adao, OVChipkaartDAO odao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO en AdresDAO en ovchipkaartDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();
        // Haal alle adressen op
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende adressen:");
        List<Adres> adressen=adao.findAll();
        for (Adres a : adressen) {
            System.out.println(a);
        }
        // haal alle ovchipkaarten op
        System.out.println("[Test] OVchipkaartDAO.findAll() geeft de volgende adressen:");
       List<OVChipkaart> ovchipkaart=odao.findAll();
       for (OVChipkaart o : ovchipkaart) {
           System.out.println(o);
       }
        // Maak een nieuwe reiziger+adres+ ovchipkaart aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save()  en AdresDAO.save en OvchipkaartDAO.save() ");
        Adres adres=new Adres();
        adres.setReiziger(sietske);
        adres.setHuisnummer("1");
        adres.setId(78);
        adres.setPostcode("3951BE");
        adres.setStraat("Tuindorpweg");
        adres.setWoonplaats("Baarn");
        sietske.setAdres(adres);
        OVChipkaart ovChipkaart= new OVChipkaart(44,Date.valueOf("2030-01-01"),1,12.99,sietske);
        OVChipkaart ovChipkaart2 = new OVChipkaart(56,Date.valueOf("2030-01-01"),1,25.00,sietske);
        sietske.addToOvChipkaarten(ovChipkaart);
        sietske.addToOvChipkaarten(ovChipkaart2);
        rdao.save(sietske);

        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }

        //update reiziger+adres
        Reiziger updateSietske=new Reiziger(77, "S O", "", "Boersen", java.sql.Date.valueOf(gbdatum));
        Adres updateAdres=adres;
        updateAdres.setWoonplaats("Maarn");
        updateSietske.setAdres(updateAdres);
        OVChipkaart updateOvChipkaart=ovChipkaart;
        updateOvChipkaart.setGeldigTot(Date.valueOf("2034-01-01"));
        updateSietske.removeFromOvChipkaarten(ovChipkaart);
        updateSietske.addToOvChipkaarten(ovChipkaart);
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.update() en AdresDAO.update()en OvchipkaartDAO.update()");
        rdao.update(updateSietske);

        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        //delete reiziger+adres
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() en AdresDAO.delete() en OvchipkaartDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        //findById
        System.out.println("\n[Test] Test ReizigerDAO.findById() en AdresDAO.getByReiziger() en OvchipkaartDAO.getByReiziger() ");
        Reiziger reizigerById = rdao.findById(5);
        if (reizigerById != null) {
            System.out.println("Reiziger met ID: "+ reizigerById);
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
    public static void main(String[] args) throws SQLException {
        testConnection();
        AdresDAOPsql adresDAOPsql=new AdresDAOPsql(getConnection());
        ReizigerDAOPsql reizigerDAOPsql=new ReizigerDAOPsql(getConnection());
        OVChipkaartPsql ovChipkaartPsql=new OVChipkaartPsql(getConnection());
        ovChipkaartPsql.setRdao(reizigerDAOPsql);
        adresDAOPsql.setRdao(reizigerDAOPsql);
        reizigerDAOPsql.setAdresDAO(adresDAOPsql);
        reizigerDAOPsql.setOdao(ovChipkaartPsql);
        testReizigerDAO(reizigerDAOPsql,adresDAOPsql,ovChipkaartPsql);
    }
}