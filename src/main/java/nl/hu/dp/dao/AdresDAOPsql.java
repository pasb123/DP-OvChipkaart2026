package nl.hu.dp.dao;

import nl.hu.dp.domain.Adres;
import nl.hu.dp.domain.Reiziger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    private final Connection conn ;
    private ReizigerDAO rdao;

    public AdresDAOPsql( Connection conn) {
        this.conn = conn;
    }

        @Override
    public boolean save(Adres adres) throws SQLException {
            PreparedStatement statement = conn.prepareStatement("" +
                    "INSERT INTO adres (adres_id, postcode, huisnummer,straat,woonplaats,reiziger_id) VALUES (?, ?, ?,?,?,?)");
            statement.setInt(1, adres.getId());
            statement.setString(2, adres.getPostcode());
            statement.setString(3, adres.getHuisnummer());
            statement.setString(4, adres.getStraat());
            statement.setString(5,adres.getWoonplaats());
            statement.setInt(6, adres.getReiziger().getId());
            statement.execute();
            statement.close();
            return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement(
                "update adres set straat = ?, huisnummer = ?, postcode = ?, woonplaats = ?, reiziger_id = ? where adres_id = ?"
        );
        Reiziger reiziger =adres.getReiziger();
        statement.setString(1, adres.getStraat());
        statement.setString(2, adres.getHuisnummer());
        statement.setString(3, adres.getPostcode());
        statement.setString(4, adres.getWoonplaats());
        statement.setInt(5, reiziger.getId());
        statement.setInt(6, adres.getId());
        reiziger.setAdres(adres);
        statement.executeUpdate();
        statement.close();

        return  true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        PreparedStatement statement = this.conn.prepareStatement("" +"DELETE FROM adres where adres_id=?");
        statement.setInt(1, adres.getId());
        statement.execute();
        statement.close();
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement preparedStatement =conn.prepareStatement(""+"SELECT * FROM adres where reiziger_id=?");
        preparedStatement.setInt(1,reiziger.getId());
        ResultSet resultSet= preparedStatement.executeQuery();
        Adres adres= new Adres();
        while (resultSet.next()){
            adres.setId(resultSet.getInt("adres_id"));
            adres.setPostcode(resultSet.getString("postcode"));
            adres.setHuisnummer(resultSet.getString("huisnummer"));
            adres.setStraat(resultSet.getString("straat"));
            adres.setWoonplaats(resultSet.getString("woonplaats"));
            adres.setReiziger(reiziger);


        }
        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException {
        ResultSet resultSet = conn.createStatement().executeQuery("SELECT * FROM adres");
        List<Adres> adresList = new ArrayList<>();

        while (resultSet.next()){
            Adres adres = new Adres();
            adres.setId(resultSet.getInt("adres_id"));
            adres.setPostcode(resultSet.getString("postcode"));
            adres.setHuisnummer(resultSet.getString("huisnummer"));
            adres.setStraat(resultSet.getString("straat"));
            adres.setWoonplaats(resultSet.getString("woonplaats"));
            adres.setReiziger(rdao.findById( resultSet.getInt("reiziger_id")));
            adresList.add(adres);
        }
        return adresList;
    }

    public void setRdao(ReizigerDAOPsql rdao) {
        this.rdao=rdao;
    }
}
