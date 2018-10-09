/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Kysymys;


/**
 *
 * @author anna
 */
public class KysymysDao implements Dao<Kysymys,Integer>{
     private Database database;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String kteksti = rs.getString("kteksti");
        Kysymys k = new Kysymys(id, kurssi, aihe, kteksti);


        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList<>();
        while (rs.next()) {
             Integer id = rs.getInt("id");
            String kurssi = rs.getString("kurssi");
            String aihe = rs.getString("aihe");
            String kteksti = rs.getString("kteksti");
            Kysymys k = new Kysymys(id, kurssi, aihe, kteksti);


            kysymykset.add(k);
        }

        rs.close();
        stmt.close();
        connection.close();

        return kysymykset;
    }

       @Override
    public void delete(Integer key) throws SQLException {
       Connection conn = database.getConnection();
       PreparedStatement stmt = conn.prepareStatement("DELETE * FROM Kysymys WHERE id = ?");
       stmt.setObject(1,key);
       stmt.executeQuery();
       
       stmt.close();
       conn.close();
    }
    
    @Override
    public Kysymys save(Kysymys kysymys) throws SQLException{
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (id, kurssi, aihe, kteksti) VALUES (?,?,?,?)");
        stmt.setObject(1, kysymys.id);
        stmt.setObject(2,kysymys.kurssi);
        stmt.setObject(2, kysymys.aihe);
        stmt.setObject(4, kysymys.kteksti);
        stmt.executeQuery();
        stmt.close();
        conn.close();
        return kysymys;
        
    }
}
