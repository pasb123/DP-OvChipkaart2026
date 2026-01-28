package nl.hu.dp.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Product;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartPsql implements OVChipkaartDAO {
    private final Connection conn;
    private ReizigerDAO rdao;
    private ProductDAO pdao;
    public OVChipkaartPsql(Connection conn) {
        this.conn = conn;
    }
    public ReizigerDAO getRdao() {
        return rdao;
    }
    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    public ProductDAO getPdao() {
        return pdao;
    }

    public void setPdao(ProductDAO pdao) {
        this.pdao = pdao;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(""+
                "INSERT INTO ov_chipkaart(kaart_nummer,geldig_tot,klasse,saldo,reiziger_id) VALUES (?,?,?,?,?)");
        ps.setInt(1,ovChipkaart.getKaartNummer());
        ps.setDate(2,ovChipkaart.getGeldigTot());
        ps.setDouble(3,ovChipkaart.getKlasse());
        ps.setDouble(4,ovChipkaart.getSaldo());
        ps.setInt(5,ovChipkaart.getReiziger().getReiziger_id());
        ps.execute();
        ps.close();
        if (pdao != null&& ovChipkaart.getProducten() != null) {
            for (Product product : ovChipkaart.getProducten()) {
                ps= this.conn.prepareStatement("insert into " +
                        "ov_chipkaart_product(kaart_nummer, product_nummer, status, " +
                        "last_update) VALUES(?,?,?,?)");
                ps.setInt(1, ovChipkaart.getKaartNummer());
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
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(
                "update ov_chipkaart set geldig_tot =?,klasse=?,saldo=?,reiziger_id=? where kaart_nummer=?");
        Reiziger reiziger = ovChipkaart.getReiziger();
        reiziger.removeFromOvChipkaarten(ovChipkaart);
        ps.setDate(1,ovChipkaart.getGeldigTot());
        ps.setDouble(2,ovChipkaart.getKlasse());
        ps.setDouble(3,ovChipkaart.getSaldo());
        ps.setInt(4,reiziger.getReiziger_id());
        ps.setInt(5,ovChipkaart.getKaartNummer());
        reiziger.addToOvChipkaarten(ovChipkaart);
        ps.executeUpdate();
        ps.close();
        if (pdao != null&&ovChipkaart.getProducten()!= null) {
            for (Product product : ovChipkaart.getProducten()) {
                ps= this.conn.prepareStatement("update ov_chipkaart_product set " +
                        "product_nummer=?, status=?, " +
                        "last_update=? where kaart_nummer=? and product_nummer=?");

                ps.setInt(1, product.getProductNummer());
                ps.setString(2,"actief");
                ps.setDate(3, Date.valueOf( LocalDate.now()));
                ps.setInt(4, ovChipkaart.getKaartNummer());
                ps.setInt(5, product.getProductNummer());
                ps.execute();
                ps.close();
                PreparedStatement ps2 = this.conn.prepareStatement(
                        "update product set naam=?,beschrijving=?," +
                                "prijs=?where product_nummer=?");
                ps2.setString(1, product.getNaam());
                ps2.setString(2, product.getBeschrijving());
                ps2.setDouble(3, product.getPrijs());
                ps2.setInt(4, product.getProductNummer());
                ps2.executeUpdate();
                ps2.close();
            }
        }

        return  true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(""+ "DELETE from ov_chipkaart where kaart_nummer=?");
        ps.setInt(1,ovChipkaart.getKaartNummer());
        ps.execute();
        ps.close();
        if (pdao != null&& ovChipkaart.getProducten() != null) {
            for (Product product : ovChipkaart.getProducten()) {
                ps= this.conn.prepareStatement("delete from ov_chipkaart_product where product_nummer=?");
                ps.setInt(1, product.getProductNummer());
                ps.execute();
                ps.close();


            }
        }
        return true;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(""+ "SELECT * FROM ov_chipkaart where reiziger_id=?");
        ps.setInt(1,reiziger.getReiziger_id());
        ResultSet rs = ps.executeQuery();
        List<OVChipkaart> list = new ArrayList<>();
        OVChipkaart ovChipkaart;
        while(rs.next()) {
            ovChipkaart=new OVChipkaart(
                    rs.getInt("kaart_nummer"),
                    rs.getDate("geldig_tot"),
                    rs.getInt("klasse"),
                    rs.getDouble("saldo"),
                    reiziger
            );
            list.add(ovChipkaart);
        }
        return list;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
       ResultSet rs = this.conn.createStatement().executeQuery("select * from ov_chipkaart");
       List<OVChipkaart> list = new ArrayList<>();
        OVChipkaart ovChipkaart;
        while(rs.next()) {
            ovChipkaart=new OVChipkaart(
                    rs.getInt("kaart_nummer"),
                    rs.getDate("geldig_tot"),
                    rs.getInt("klasse"),
                    rs.getDouble("saldo"),
                    rdao.findById(rs.getInt("reiziger_id"))
            );
            ovChipkaart.setProducten(pdao.findByOvChipkaart(ovChipkaart));
            list.add(ovChipkaart);
        }
        return list;
    }
}
