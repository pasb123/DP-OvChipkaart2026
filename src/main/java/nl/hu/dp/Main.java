package nl.hu.dp;

import nl.hu.dp.dao.ReizigerDAO;
import nl.hu.dp.dao.ReizigerDAOHibernate;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import java.sql.*;
import java.util.List;

public class Main {


    // connection method
    public static Session getSession() {
        Session session;
        try{
            session= new Configuration().configure().buildSessionFactory().openSession();}
        catch (HibernateException ex){
            throw  new HibernateException(ex);
        }
        return session;
    }
    /**
     * P2. main.java.nl.hu.main.java.nl.hu.ovchip.domain.Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de main.java.nl.hu.main.java.nl.hu.ovchip.domain.Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) {
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
    public static void main(String[] args) throws SQLException {
        ReizigerDAOHibernate reizigerDAOHibernate = new ReizigerDAOHibernate(getSession());
        testReizigerDAO(reizigerDAOHibernate);
    }
}