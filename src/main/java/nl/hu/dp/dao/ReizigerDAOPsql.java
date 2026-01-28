package nl.hu.dp.dao;

import nl.hu.dp.domain.OVChipkaart;
import nl.hu.dp.domain.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO{
    private final Connection conn;
    private  AdresDAO adresDAO;
    private OVChipkaartDAO odao;
    public ReizigerDAOPsql(Connection conn) {
        this.conn = conn;
    }
    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" +
                "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel,achternaam,geboortedatum) VALUES (?, ?, ?,?,?)");
        statement.setInt(1, reiziger.getId());
        statement.setString(2, reiziger.getVoorletters());
        statement.setString(3, reiziger.getTussenvoegsel());
        statement.setString(4, reiziger.getAchternaam());
        statement.setDate(5,reiziger.getGeboortedatum());

        statement.execute();
        statement.close();
        if (reiziger.getAdres() != null) {
        adresDAO.save(reiziger.getAdres());}
        if (!reiziger.getOvchipkaarten().isEmpty()) {
        for (OVChipkaart ovChipkaart: new ArrayList<>(reiziger.getOvchipkaarten())){
            odao.save(ovChipkaart);
        }}
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("" + "update reiziger set voorletters=?, tussenvoegsel=?, achternaam=?,geboortedatum=? Where reiziger_id=?");
        statement.setString(1, reiziger.getVoorletters());
        statement.setString(2, reiziger.getTussenvoegsel());
        statement.setString(3, reiziger.getAchternaam());
        statement.setDate(4, reiziger.getGeboortedatum());
        statement.setInt(5, reiziger.getId());

        statement.executeUpdate();
        statement.close();
        if (reiziger.getAdres() != null) {
        adresDAO.update(reiziger.getAdres());}
        if (!reiziger.getOvchipkaarten().isEmpty()) {
            for (OVChipkaart ovChipkaart : new ArrayList<>(reiziger.getOvchipkaarten())) {
                odao.update(ovChipkaart);
            }
        }

            return false;
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        if (reiziger.getAdres() != null) {
        adresDAO.delete(reiziger.getAdres());}
        if (!reiziger.getOvchipkaarten().isEmpty()) {
            for (OVChipkaart ovChipkaart: new ArrayList<>(reiziger.getOvchipkaarten())){
                odao.delete(ovChipkaart);
        }}
        PreparedStatement statement = conn.prepareStatement("" +"DELETE FROM reiziger where reiziger_id=?");
        statement.setInt(1, reiziger.getId());

        statement.execute();
        statement.close();

        return false;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger WHERE reiziger_id = ?");
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        Reiziger reiziger = null;
        if (resultSet.next()) {
            reiziger = new Reiziger(
                    resultSet.getInt("reiziger_id"),
                    resultSet.getString("voorletters"),
                    resultSet.getString("tussenvoegsel"),
                    resultSet.getString("achternaam"),
                    resultSet.getDate("geboortedatum")

            );
            if (reiziger.getAdres() != null) {
            reiziger.setAdres(adresDAO.findByReiziger(reiziger));}
            if (!reiziger.getOvchipkaarten().isEmpty()) {
            reiziger.setOvchipkaarten(odao.findByReiziger(reiziger));}
        }

        resultSet.close();
        statement.close();

        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(Date date) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
        statement.setDate(1, new java.sql.Date(date.getTime()));
        ResultSet resultSet = statement.executeQuery();
        List<Reiziger>Reizigerlist = new ArrayList();
        while (resultSet.next()) {
            Reiziger reiziger= new Reiziger(
                    resultSet.getInt("reiziger_id"),
                    resultSet.getString("voorletters"),
                    resultSet.getString("tussenvoegsel"),
                    resultSet.getString("achternaam"),
                    resultSet.getDate("geboortedatum")
            );
            if (reiziger.getAdres() != null) {
            reiziger.setAdres(adresDAO.findByReiziger(reiziger));}
            if (!reiziger.getOvchipkaarten().isEmpty()) {
            reiziger.setOvchipkaarten(odao.findByReiziger(reiziger));}
            Reizigerlist.add(reiziger);
        }
        resultSet.close();
        statement.close();
        return Reizigerlist;

    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        ResultSet resultSet =conn.createStatement().executeQuery("SELECT * FROM reiziger");
        List<Reiziger>Reizigerlist = new ArrayList();
        while (resultSet.next()){
            Reiziger reiziger= new  Reiziger(resultSet.getInt("reiziger_id"),resultSet.getString("voorletters"),resultSet.getString("tussenvoegsel"),
                    resultSet.getString("achternaam"),resultSet.getDate("geboortedatum"));
            if (reiziger.getAdres() != null) {
            reiziger.setAdres(adresDAO.findByReiziger(reiziger));}
            if (!reiziger.getOvchipkaarten().isEmpty()) {
            reiziger.setOvchipkaarten(odao.findByReiziger(reiziger));}
            Reizigerlist.add(reiziger);
        }
        return Reizigerlist;
    }

    public void setAdresDAO(AdresDAO adresDAO) {
        this.adresDAO = adresDAO;
    }

    public AdresDAO getAdresDAO() {
        return adresDAO;
    }

    public OVChipkaartDAO getOdao() {
        return odao;
    }

    public void setOdao(OVChipkaartDAO odao) {
        this.odao = odao;
    }
}
