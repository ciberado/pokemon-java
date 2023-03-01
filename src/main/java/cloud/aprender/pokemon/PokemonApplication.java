package cloud.aprender.pokemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokemonApplication {

	public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
        @Override
            public void run() {
                System.out.println("Abrupt (but fast) exit after SIGTERM.");
				System.exit(0);
            }   
        }); 

		SpringApplication.run(PokemonApplication.class, args);
	}

}
