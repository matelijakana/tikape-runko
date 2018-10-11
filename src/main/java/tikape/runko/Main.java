package tikape.runko;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.File;
import java.util.HashMap;
import spark.ModelAndView;
import spark.Spark;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.Database;
import tikape.runko.database.KysymysDao;
import tikape.runko.database.VastausvaihtoehtoDao;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Vastausvaihtoehto;

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
            kDao.save(new Kysymys(-1,kurssi, aihe, kteksti));
            res.redirect("/kysymykset");
            return "";
        });
        
        
        get("/kysymys", (req,res) -> {
            HashMap map = new HashMap<>();
            map.put("kys", kDao.findOne(1));
            map.put("vastaukset", vDao.findAll());
            return new ModelAndView(map,"kysymys");
        }, new ThymeleafTemplateEngine());
        
        post("/kysymys", (req,res) -> {
            String vteksti = req.queryParams("vteksti");
            res.redirect("/kysymys");
            return "";
        });
        
        
    }
      
}
