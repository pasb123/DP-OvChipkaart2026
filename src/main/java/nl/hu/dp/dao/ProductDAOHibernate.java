package nl.hu.dp.dao;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {
    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        try {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant save the Product "+e);
        }
        return true;
    }

    @Override
    public boolean update(Product product) {
        try {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        }
        catch (HibernateException e){
            throw new HibernateException("it seems we cant update the Product "+e);
        }
        return true;
    }

    @Override
    public boolean delete(Product product) {
        try{
            session.beginTransaction();
            for (OVChipkaart kaart : new ArrayList<>(product.getChipkaarten())) {
                kaart.removeProduct(product);
            }
            session.delete(product);
            session.getTransaction().commit();}
        catch (HibernateException e){
            throw new HibernateException("it seems we cant remove the Product "+e);
        }
        return true;

    }
    public List<Product> findByOvChipkaart(OVChipkaart chipkaart) {
        return session.createQuery(
                        "select p from Product p join p.chipkaarten c where c.kaartNummer = :kaartNummer",
                        Product.class)
                .setParameter("kaartNummer", chipkaart.getKaartNummer())
                .list();
    }
    public List<Product> findAll() {
        return session.createQuery("from Product", Product.class).list();
    }
}
