package demomongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Run implements CommandLineRunner {

	@Autowired
	private MongoDBExample mongoDBExample;

	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}

	public void run(String... args) throws Exception {

	}

}
