package tikape.runko;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.util.HashMap;
import static jdk.nashorn.internal.objects.NativeFunction.function;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausvaihtoehtoDao;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Vastausvaihtoehto;
import spark.template.thymeleaf.ThymeleafTemplateEngine; 

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        File tiedosto = new File("db", "opiskelijat.db");
        Database database = new Database("jdbc:sqlite:" + tiedosto.getAbsolutePath());

        KysymysDao kDao = new KysymysDao(database);
        VastausvaihtoehtoDao vDao = new VastausvaihtoehtoDao(database);
        
        get("/kysymykset", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kDao.findAll());

            return new ModelAndView(map, "index");
        }, new ThymeleafTemplateEngine());

        post("/kysymykset", (req,res) -> {
            String kurssi = req.queryParams("kurssi");
            String aihe = req.queryParams("aihe");
            String kteksti = req.queryParams("kteksti");
            if (!kteksti.equals("") && !aihe.equals("") && !kurssi.equals("")) {
                kDao.save(new Kysymys(-1,kurssi, aihe, kteksti));
            }
            res.redirect("/kysymykset");
            return "";
        });
        
       post("/poistakysymys/:id", (req,res) -> {
          Integer id = Integer.parseInt(req.params(":id"));
          kDao.delete(id);
          res.redirect("/kysymykset"); 
          return ""; 
       });
       
       post("/lisaavastaus/:id", (req,res) -> {
            Integer kysymys_id = Integer.parseInt(req.params(":id"));
            String oikein = "Oikein";  
            if (req.queryParams("oikein") == null) {
              oikein = "Väärin";
            }
            
            String vteksti = req.queryParams("vteksti");
            if (!vteksti.equals("")) {
                vDao.save(new Vastausvaihtoehto(-1, id, vteksti, oikein));
            };
            res.redirect("/kysymykset/"+kysymys_id);
            return "";
        });
       
       post("poistavastaus/:id", (req,res) -> {
          Integer id = Integer.parseInt(req.params(":id"));
          Integer kysymys_id = Integer.parseInt(req.queryParams("kysymys_id"));
          vDao.delete(id);
          res.redirect("/kysymykset/"+kysymys_id); 
          return "";
       });
            
        
        
        get("/kysymykset/:id", (req,res) -> {
            HashMap map = new HashMap<>();
            Integer id= Integer.parseInt(req.params(":id"));
            map.put("kys", kDao.findOne(id));
            map.put("vastaukset", vDao.findAllForOneQuestion(id));
            return new ModelAndView(map,"kysymys");
        }, new ThymeleafTemplateEngine());
        
        
        
        
    }
      
}
