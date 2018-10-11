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
import java.util.concurrent.atomic.AtomicLong;
import tikape.runko.domain.Kysymys;


/**
 *
 * @author anna
 */
public class KysymysDao implements Dao<Kysymys,Integer>{
     private Database database;
     private static Integer idCounter = 0;

    public KysymysDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setInt(1, key);

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
        
        stmt.close();
        rs.close();
        connection.close();

        return k;
    }

    @Override
    public List<Kysymys> findAll() throws Exception {

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
    public void delete(Integer key) throws Exception {
       Connection conn = database.getConnection();
       PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");
       stmt.setObject(1,key);
       stmt.executeQuery();
       
       stmt.close();
       conn.close();
    }
    
    @Override
    public Kysymys save(Kysymys kysymys) throws Exception{
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Kysymys (kurssi, aihe, kteksti) VALUES (?,?,?)");
        stmt.setString(1,kysymys.kurssi);
        stmt.setString(2, kysymys.aihe);
        stmt.setString(3, kysymys.kteksti);
        stmt.executeUpdate();
        stmt.close();
        
        PreparedStatement st = conn.prepareStatement("SELECT * FROM Kysymys WHERE kteksti = ? AND kurssi = ?" );
        st.setString(1, kysymys.kteksti);
        st.setString(2, kysymys.kurssi);
        ResultSet rs = st.executeQuery();
        Kysymys k = new Kysymys(rs.getInt("id"), rs.getString("kurssi"),rs.getString("aihe"), rs.getString("kteksti"));
        conn.close();
        return k;
        
    }
    
}
