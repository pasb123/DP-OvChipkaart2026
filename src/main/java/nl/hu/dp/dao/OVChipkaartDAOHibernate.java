package nl.hu.dp.dao;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {
    private Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart chipkaart) {
        try {
            session.beginTransaction();
            session.save(chipkaart);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the OV-Chipkaart "+e);
        }
        return true;
    }

    @Override
    public boolean update(OVChipkaart chipkaart) {
        try {

            if (session.get(OVChipkaart.class,chipkaart.getKaartNummer())!=null){
                session.beginTransaction();
                session.update(chipkaart);
                session.getTransaction().commit();
            }
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant update the OV-Chipkaart "+e);
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart chipkaart) {
        try{
            session.beginTransaction();
            for (Product product : chipkaart.getProducten()) {
               chipkaart.removeProduct(product);
            }
            session.delete(chipkaart);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the OV-Chipkaart "+e);
        }
        return true;

    }

    @Override
    public List<OVChipkaart> findAll() {
        return session.createQuery("select ovchipkaart from OVChipkaart ovchipkaart", OVChipkaart.class).list();

    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {

        List<OVChipkaart> chipkaarten;
        try{ chipkaarten= session.createQuery("select ovchipkaart from OVChipkaart ovchipkaart where ovchipkaart.reiziger.reiziger_id=:reiziger_id",OVChipkaart.class)
                .setParameter("reiziger_id",reiziger.getId()).list();}
        catch (HibernateException ex){
            throw new HibernateException("find by reiziger cannot be completed "+ ex);
        }
        return chipkaarten;
    }
}
