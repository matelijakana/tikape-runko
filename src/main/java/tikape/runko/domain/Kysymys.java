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
public class Kysymys {
    
    public Integer id;
    public String kurssi;
    public String aihe;
    public String kteksti;
    
    public Kysymys(Integer id, String kurssi, String aihe, String teksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kteksti = teksti;
    }
    
    public String getKteksti() {
        return kteksti;
    }
    
    public Integer getId() {
        return id;
    }
    
 
    
}
