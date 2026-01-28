package nl.hu.dp.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;

import java.util.List;

public interface ProductDAO {
    boolean save(Product product);
    boolean delete(Product product);
    boolean update(Product product);
    List<Product> findByOvChipkaart(OVChipkaart ovChipkaart);
    List<Product> findAll();
}
