package nl.hu.dp.dao;

import nl.hu.dp.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            session.clear();
            session.beginTransaction();
            session.save(reiziger);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the traveler "+e);
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            if (session.get(Reiziger.class,reiziger.getId())!=null){
                session.clear();
                session.beginTransaction();
                session.update(reiziger);
                session.getTransaction().commit();
            }
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant find the traveler "+e);
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try{
            session.clear();
            session.beginTransaction();
            session.delete(reiziger);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the traveler "+e);
        }
        return true;
    }

    @Override
    public Reiziger findById(int id) {
        return session.createQuery("select reiziger from Reiziger reiziger where reiziger.id = :id",
                Reiziger.class).setParameter("id", id).uniqueResult();
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) {
        return session.createQuery("select reiziger from Reiziger reiziger where reiziger.geboortedatum= :date",
                        Reiziger.class).setParameter("date",date).list();
    }

    @Override
    public List<Reiziger> findAll() {
        return session.createQuery("select reiziger from Reiziger reiziger", Reiziger.class).list();
    }
}
