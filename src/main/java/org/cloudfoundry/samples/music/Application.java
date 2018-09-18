package org.cloudfoundry.samples.music;

import org.cloudfoundry.samples.music.config.SpringApplicationContextInitializer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.logging.Logger;


@SpringBootApplication
public class Application extends SpringBootServletInitializer {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).
                initializers(new SpringApplicationContextInitializer())
                .application()
                .run(args);
    }
}