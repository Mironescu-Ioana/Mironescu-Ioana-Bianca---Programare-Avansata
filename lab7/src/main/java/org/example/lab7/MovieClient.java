package org.example.lab7;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.sql.Date;
import java.util.Map;

@Component
public class MovieClient {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {
            String baseUrl = "http://localhost:6767/api/movies";

            System.out.println("-Start:-");

            // Adauga un film nou (POST)
            Movie newMovie = new Movie(null, "Inception", Date.valueOf("2010-07-16"), 148, 8.8, 1);
            String postResponse = restTemplate.postForObject(baseUrl, newMovie, String.class);
            System.out.println("POST Response: " + postResponse);

            // Obtine toate filmele
            String getResponse = restTemplate.getForObject(baseUrl, String.class);
            System.out.println("GET Response: " + getResponse);

            // Înlocuiește "1" în URL-ul de mai jos cu ID-ul real generat de secvența/trigger-ul bazei de date Oracle
            int sampleId = 1;

            // Modifica toate atributele filmului (PUT)
            Movie updatedMovie = new Movie(sampleId, "Inception (Extended)", Date.valueOf("2010-07-16"), 155, 8.9, 1);
            restTemplate.put(baseUrl + "/" + sampleId, updatedMovie);
            System.out.println("PUT Request executed.");

            // 4. Schimba doar scorul (PATCH)
            Map<String, Double> patchPayload = Map.of("score", 9.5);
            String patchResponse = restTemplate.patchForObject(baseUrl + "/" + sampleId + "/score", patchPayload, String.class);
            System.out.println("PATCH Response: " + patchResponse);

            // 5. Sterge filmul (DELETE)
            restTemplate.delete(baseUrl + "/" + sampleId);
            System.out.println("DELETE Request executed.");

            System.out.println("-Gata-");
        };
    }
}
