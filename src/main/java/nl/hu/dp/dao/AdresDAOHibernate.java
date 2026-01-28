package nl.hu.dp.dao;
import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;
public class AdresDAOHibernate implements AdresDAO {
    private Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            session.beginTransaction();
            session.save(adres);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the adress "+e);
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        try {

            if (session.get(Adres.class,adres.getId())!=null){
                session.beginTransaction();
                session.update(adres);
                session.getTransaction().commit();
            }
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant update the adres "+e);
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        try{
            session.beginTransaction();
            session.delete(adres);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the adres "+e);
        }
        return true;

    }

    @Override
    public List<Adres> findAll() {
        return session.createQuery("select adres from Adres adres", Adres.class).list();
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {

        Adres adres;
        try{ adres= session.createQuery("select adres from Adres adres where adres.reiziger.reiziger_id=:reiziger_id",Adres.class)
                .setParameter("reiziger_id",reiziger.getId()).getSingleResult();}
        catch (HibernateException ex){
            throw new HibernateException("find by reiziger cannot be completed "+ ex);
        }
        return adres;
    }}
