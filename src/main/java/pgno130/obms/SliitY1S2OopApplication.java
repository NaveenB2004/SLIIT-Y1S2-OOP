package pgno130.obms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class SliitY1S2OopApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(SliitY1S2OopApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SliitY1S2OopApplication.class);
    }
}
