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
import tikape.runko.domain.Vastausvaihtoehto;
/**
 *
 * @author anna
 */
public class VastausvaihtoehtoDao implements Dao<Vastausvaihtoehto,Integer>{
    private Database database;

    public VastausvaihtoehtoDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastausvaihtoehto findOne(Integer key) throws Exception {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastausvaihtoehto WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        Integer kysymys_id = rs.getInt("kysymys_id");
        String vteksti = rs.getString("vteksti");
        String oikein = rs.getString("oikein");
        Vastausvaihtoehto vastaus = new Vastausvaihtoehto(id,kysymys_id,vteksti,oikein);

        rs.close();
        stmt.close();
        connection.close();

        return vastaus;
    }

     @Override
    public List<Vastausvaihtoehto> findAll() throws Exception {
        return null;
    }
    
    public ArrayList<Vastausvaihtoehto> findAllForOneQuestion(Integer id) throws Exception {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastausvaihtoehto WHERE kysymys_id=?");
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        ArrayList<Vastausvaihtoehto> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer iidee = rs.getInt("id");
            Integer kysymys_id = rs.getInt("kysymys_id");
            String vteksti = rs.getString("vteksti");
            String oikein = rs.getString("oikein");
            Vastausvaihtoehto vastaus = new Vastausvaihtoehto(iidee,kysymys_id,vteksti,oikein);

            vastaukset.add(vastaus);
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws Exception {
       Connection conn = database.getConnection();
       PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastausvaihtoehto WHERE id = ? ");
       stmt.setInt(1,key);
       stmt.executeUpdate();
       
       stmt.close();
       conn.close(); 
    }
    
    @Override
    public Vastausvaihtoehto save(Vastausvaihtoehto vastaus) throws Exception{
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Vastausvaihtoehto (vteksti, oikein, kysymys_id) VALUES (?,?,?)");
        stmt.setString(1, vastaus.vteksti);
        stmt.setString(2, vastaus.oikein);
        stmt.setInt(3,vastaus.kysymys_id);
        stmt.executeUpdate();
        stmt.close();
        
        PreparedStatement st = conn.prepareStatement("SELECT * FROM Vastausvaihtoehto WHERE vteksti = ?" );
        st.setString(1, vastaus.vteksti);
        
        ResultSet rs = st.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }
        Vastausvaihtoehto v = new Vastausvaihtoehto(rs.getInt("id"), rs.getInt("kysymys_id"),rs.getString("vteksti"), rs.getString("oikein"));
        conn.close();
      
        return v;
        
        
    }
}
