package pgno130.obms.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class LiaBooksApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(LiaBooksApplication.class, args);
	}

}
