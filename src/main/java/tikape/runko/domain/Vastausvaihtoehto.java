/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author anna
 */
public class Vastausvaihtoehto {
    public Integer id;
    public Integer kysymys_id;
    public String vteksti;
    public String oikein;
    
    public Vastausvaihtoehto(Integer id, Integer kysymys_id, String vteksti, String oikein) {
        this.id = id;
        this.kysymys_id = kysymys_id;
        this.vteksti = vteksti;
        this.oikein = oikein;
    }
    
    public int getKysymys_id() {
        return kysymys_id;
    }
    
    public int getId() {
        return id;
    }
    
    public String getVteksti() {
        return this.vteksti;
    }
}
