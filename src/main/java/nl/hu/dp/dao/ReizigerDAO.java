package nl.hu.dp.dao;

import nl.hu.dp.domain.Reiziger;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger) ;
    boolean update(Reiziger reiziger) ;
    boolean delete(Reiziger reiziger);
    Reiziger findById(int id);
    List<Reiziger> findByGbdatum(Date date);
    List<Reiziger> findAll();

}

