package cloud.aprender.pokemon;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Scope(scopeName = "singleton")
@Log
public class PokemonCtrl {

    private final String version;
    private final ConfigurableApplicationContext ctx;
    private final Pokemon pokemon;

    @Autowired
    public PokemonCtrl(ApplicationContext ctx, PokemonRepository repository, 
           @Value("${config.version}") String version) {
        this.ctx = (ConfigurableApplicationContext) ctx;
        this.pokemon = repository.getRandomPokemon();
        this.version = version;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.TEXT_HTML_VALUE})
    String index(Map<String, Object> model) {
        String host = System.getenv("HOST") == null ? "dev" : System.getenv("HOST");
        model.put("host", host);
        model.put("pokemon", pokemon);
        
        Set<Map.Entry<String,String>> env = System.getenv().entrySet();
        model.put("env", env);
        model.put("task", System.getenv().get("MESOS_TASK_ID"));
        
        model.put("version", version);
        return "index"; 
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    Pokemon pokemon() {
        return pokemon;
    }    
    
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseBody
    Pokemon shutdown() {
        log.warning(MessageFormat.format("{0} doesn''t want to fight, leaves the host now.", pokemon.getName()));
        new Thread(new Runnable() { 
            @Override @SneakyThrows
            public void run() { 
                Thread.sleep(1000);
                ctx.close(); 
            }
        }).start();
        return this.pokemon;
    }

    @RequestMapping(value="/", params="stress")
    @ResponseBody
    void stress() {
        for (int i=0; i < 4; i++) {
            new Thread() {
                public void run() {
                    StringBuilder sb = new StringBuilder();
                    while(true) {
                        sb.append((char) Math.random()*255);
                        if (sb.length() > 1000) sb.setLength(0);
                    }
                }
            }.start();
        }
    }
    
    
}
