package cloud.aprender.pokemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PokemonApplication {

    static ConfigurableApplicationContext ctx;  

	public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
            public void run() {
                System.out.println("Abrupt (but fast) exit after SIGTERM.");
                PokemonApplication.ctx.close();
            }   
        });

		ctx = SpringApplication.run(PokemonApplication.class, args);
	}

}
