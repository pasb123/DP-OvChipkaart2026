package nl.hu.dp.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartPsql implements OVChipkaartDAO {
    private final Connection conn;
    private ReizigerDAO rdao;
    public OVChipkaartPsql(Connection conn) {
        this.conn = conn;
    }
    public ReizigerDAO getRdao() {
        return rdao;
    }
    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
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

        return  true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement ps = this.conn.prepareStatement(""+ "DELETE from ov_chipkaart where kaart_nummer=?");
        ps.setInt(1,ovChipkaart.getKaartNummer());
        ps.execute();
        ps.close();
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
            list.add(ovChipkaart);
        }
        return list;
    }
}
