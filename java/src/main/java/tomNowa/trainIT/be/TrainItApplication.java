package tomNowa.trainIT.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.Entity;

@SpringBootApplication
public class TrainItApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainItApplication.class, args);
	}

}
