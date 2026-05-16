package org.example.lab7;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab7Application {

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
        System.out.println("Serverul Spring Boot a pornit.");
    }
}
