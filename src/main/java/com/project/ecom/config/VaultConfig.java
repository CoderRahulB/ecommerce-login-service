//package com.project.ecom.config;
//
//import java.net.URI;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.vault.authentication.TokenAuthentication;
//import org.springframework.vault.client.VaultEndpoint;
//import org.springframework.vault.core.VaultTemplate;
//
//@Configuration
//public class VaultConfig {
//
//    @Bean
//    public VaultTemplate vaultTemplate() {
//        VaultEndpoint endpoint = VaultEndpoint.from(URI.create("http://127.0.0.1:8200"));
//        TokenAuthentication auth = new TokenAuthentication(); // Replace with a non-root token in production
//        return new VaultTemplate(endpoint, auth);
//    }
//}
