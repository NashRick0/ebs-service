package com.utd.ti.soa.ebs_service.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.utd.ti.soa.ebs_service.model.Client;
import com.utd.ti.soa.ebs_service.utils.Auth;

@RestController
@RequestMapping("/api/v2/esb")
public class ESBcontrollerClient {
    private final WebClient webClient = WebClient.create();
    private final Auth auth = new Auth();

    @PostMapping("/client")
    public ResponseEntity<?> createClient(@RequestBody Client client, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Request Body: " + client);
        System.out.println("Token recibido: " + token);

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token invalido o no proporcionado");
        }

        String jwtToken = token.replace("Bearer ", "").trim();

        if (!auth.validationToken(jwtToken)) {
            return ResponseEntity.status(401).body("Token invalido o expirado");
        }

        String response = webClient.post()
                .uri("http://clients.railway.internal:5003/api/client")
                .bodyValue(client)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok().body(response);
    }

    @PatchMapping("/client/{id}")
    public ResponseEntity<String> updateClient(@PathVariable String id, @RequestBody Client client, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Request Body: " + client);
        System.out.println("Token recibido: " + token);

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token invalido o no proporcionado");
        }

        String jwtToken = token.replace("Bearer ", "").trim();

        if (!auth.validationToken(jwtToken)) {
            return ResponseEntity.status(401).body("Token invalido o expirado");
        }

        String response = webClient.patch()
                .uri("http://clients.railway.internal:5003/api/client/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(client))
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> System.out.println("Error: " + e.getMessage()))
                .block();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable String id, @RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Token recibido: " + token);

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token invalido o no proporcionado");
        }

        String jwtToken = token.replace("Bearer ", "").trim();

        if (!auth.validationToken(jwtToken)) {
            return ResponseEntity.status(401).body("Token invalido o expirado");
        }

        String response = webClient.delete()
                .uri("http://clients.railway.internal:5003/api/client/" + id)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/client")
    public ResponseEntity<String> getAllClients(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        System.out.println("Token recibido: " + token);

        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Token invalido o no proporcionado");
        }

        String jwtToken = token.replace("Bearer ", "").trim();

        if (!auth.validationToken(jwtToken)) {
            return ResponseEntity.status(401).body("Token invalido o expirado");
        }

        String response = webClient.get()
                .uri("http://clients.railway.internal:5003/api/client")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return ResponseEntity.ok(response);
    }

}
