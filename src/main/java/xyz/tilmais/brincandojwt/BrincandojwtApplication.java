package xyz.tilmais.brincandojwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@SpringBootApplication
public class BrincandojwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrincandojwtApplication.class, args);
    }

    @Bean("appProperties")
    public Properties carregarProperties() {
        try {
            Properties appProperties = new Properties();
            appProperties.load(new FileInputStream(new File("properties/localhost.properties")));
            return appProperties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
