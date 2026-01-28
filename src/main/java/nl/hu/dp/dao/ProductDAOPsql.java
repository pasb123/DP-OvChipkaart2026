package nl.hu.dp.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDAOPsql implements ProductDAO {
    private final Connection conn;
    private OVChipkaartDAO ovChipkaartDAO;

    public ProductDAOPsql(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement( ""+
                "INSERT INTO product(product_nummer,naam,beschrijving,prijs)VALUES(?,?,?,?)" );
        ps.setInt(1, product.getProductNummer());
        ps.setString(2, product.getNaam());
        ps.setString(3, product.getBeschrijving());
        ps.setDouble(4, product.getPrijs());
        ps.execute();
        ps.close();
        if (ovChipkaartDAO != null&& product.getChipkaarten() != null) {
            for (OVChipkaart chipkaart : product.getChipkaarten()) {
                ps= this.conn.prepareStatement("insert into " +
                        "ov_chipkaart_product(kaart_nummer, product_nummer, status, " +
                        "last_update) VALUES(?,?,?,?)");
                ps.setInt(1, chipkaart.getKaartNummer());
                ps.setInt(2, product.getProductNummer());
                ps.setString(3,"actief");
                ps.setDate(4, Date.valueOf( LocalDate.now()));
                ps.execute();
                ps.close();


            }
        }
       return true;
    }
    @Override
    public boolean update(Product product) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(
                "update product set naam=?,beschrijving=?," +
                        "prijs=? where product_nummer=?");
        ps.setString(1, product.getNaam());
        ps.setString(2, product.getBeschrijving());
        ps.setDouble(3, product.getPrijs());
        ps.setInt(4, product.getProductNummer());
        ps.executeUpdate();
        ps.close();
        if (ovChipkaartDAO != null&& product.getChipkaarten() != null) {
            for (OVChipkaart chipkaart : product.getChipkaarten()) {
                ps= this.conn.prepareStatement("update ov_chipkaart_product set " +
                        "product_nummer=?, status=?, " +
                        "last_update=? where kaart_nummer=? and product_nummer=?");
                ps.setInt(1, product.getProductNummer());
                ps.setString(2,"actief");
                ps.setDate(3, Date.valueOf( LocalDate.now()));
                ps.setInt(4, chipkaart.getKaartNummer());
                ps.setInt(5, product.getProductNummer());
                ps.execute();
                ps.close();
                PreparedStatement ps2 = this.conn.prepareStatement(
                        "update ov_chipkaart set geldig_tot =?,klasse=?,saldo=?,reiziger_id=? where kaart_nummer=?");
                Reiziger reiziger = chipkaart.getReiziger();
                reiziger.removeFromOvChipkaarten(chipkaart);
                ps2.setDate(1,chipkaart.getGeldigTot());
                ps2.setDouble(2,chipkaart.getKlasse());
                ps2.setDouble(3,chipkaart.getSaldo());
                ps2.setInt(4,reiziger.getReiziger_id());
                ps2.setInt(5,chipkaart.getKaartNummer());
                reiziger.addToOvChipkaarten(chipkaart);
                ps2.executeUpdate();
                ps2.close();
            }
        }

        return true;
    }
    @Override
    public boolean delete(Product product) throws SQLException {
        if (ovChipkaartDAO != null&& product.getChipkaarten() != null) {
        for (OVChipkaart chipkaart : product.getChipkaarten()) {
            PreparedStatement ps= this.conn.prepareStatement("delete from ov_chipkaart_product where kaart_nummer=?");
            ps.setInt(1, chipkaart.getKaartNummer());
            ps.execute();
            ps.close();


        }}
        PreparedStatement ps = this.conn.prepareStatement(
                "delete from product where product_nummer=?"
        );
        ps.setInt(1,product.getProductNummer());
        ps.execute();
        ps.close();


        return true;
    }

    public OVChipkaartDAO getOvChipkaartDAO() {
        return ovChipkaartDAO;
    }

    public void setOvChipkaartDAO(OVChipkaartDAO ovChipkaartDAO) {
        this.ovChipkaartDAO = ovChipkaartDAO;
    }

    @Override
    public List<Product> findByOvChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        String findByOV = "SELECT ov_chipkaart_product.kaart_nummer, product.product_nummer, product.naam, product.beschrijving, product.prijs " +
                "FROM product " +
                "JOIN ov_chipkaart_product " +
                "ON ov_chipkaart_product.product_nummer = product.product_nummer " +
                "WHERE ov_chipkaart_product.kaart_nummer = ? " +
                "ORDER BY kaart_nummer, product_nummer";
        PreparedStatement ps = this.conn.prepareStatement(findByOV);
        ps.setInt(1, ovChipkaart.getKaartNummer());
        ResultSet rs = ps.executeQuery();
        Product product;
        List<Product> producten = new ArrayList<Product>();
        while (rs.next()) {
            product=new Product(rs.getInt("product_nummer"),
                                rs.getString("naam"),
                                rs.getString("beschrijving"),
                                rs.getDouble("prijs"));
            product.addChipkaart(ovChipkaart);
            producten.add(product);
        }
        return producten;
    }
    public List<Product> findAll() throws SQLException {
        String findAllSQL = "SELECT ov_chipkaart_product.kaart_nummer, ov_chipkaart.geldig_tot, ov_chipkaart.klasse, ov_chipkaart.saldo, product.product_nummer, product.naam, product.beschrijving, product.prijs " +
                "FROM product " +
                "JOIN ov_chipkaart_product " +
                "ON ov_chipkaart_product.product_nummer = product.product_nummer " +
                "JOIN ov_chipkaart " +
                "ON ov_chipkaart_product.kaart_nummer = ov_chipkaart.kaart_nummer " +
                "ORDER BY kaart_nummer, product_nummer;";

        PreparedStatement ps = this.conn.prepareStatement(findAllSQL);
        ResultSet rs = ps.executeQuery();
        Map<Integer, Product> productMap = new HashMap<>();


        Map<Integer, OVChipkaart> kaartMap = new HashMap<>();
        for (OVChipkaart k : ovChipkaartDAO.findAll()) {
            kaartMap.put(k.getKaartNummer(), k);
        }

        while (rs.next()) {
            int productNummer = rs.getInt("product_nummer");
            int kaartNummer   = rs.getInt("kaart_nummer");

            Product product = productMap.get(productNummer);
            if (product == null) {
                product = new Product(
                        productNummer,
                        rs.getString("naam"),
                        rs.getString("beschrijving"),
                        rs.getDouble("prijs")
                );
                productMap.put(productNummer, product);
            }

            OVChipkaart kaart = kaartMap.get(kaartNummer);
            if (kaart != null) {
                product.addChipkaart(kaart);
            }
        }

        return new ArrayList<>(productMap.values());
    }
}
