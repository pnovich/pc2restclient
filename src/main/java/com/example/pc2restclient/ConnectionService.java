package com.example.pc2restclient;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ConnectionService {

    public String remoteTempUrl = "http://192.168.0.167:8080/inventory/";
    public String localhostUrl = "http://localhost:8091/inventory/";
    public String checkInventoryService(String id){
        RestTemplate restTemplate = new RestTemplate();
        String inventoryResourceUrl
                = localhostUrl + "check";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inventoryResourceUrl + "/" + id, String.class);
        return response.toString();
    }

    public String lockInventoryService(String id){
        RestTemplate restTemplate = new RestTemplate();
        String inventoryResourceUrl
                = localhostUrl  + "setlocktrue";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inventoryResourceUrl + "/" + id, String.class);
        return response.toString();
    }

    public String unlockInventoryService(String id){
        RestTemplate restTemplate = new RestTemplate();
        String inventoryResourceUrl
                = localhostUrl + "setlockfalse";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inventoryResourceUrl + "/" + id, String.class);
        return response.toString();
    }

    public String prepareAndLockInventory(String id){
        RestTemplate restTemplate = new RestTemplate();
        String inventoryResourceUrl
                = localhostUrl + "setlocktrue";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inventoryResourceUrl + "/" + id, String.class);
        return response.toString();
    }

    public String commitAndUnockInventory(String id){
        RestTemplate restTemplate = new RestTemplate();
        String inventoryResourceUrl
                = localhostUrl + "reserve";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inventoryResourceUrl + "/" + id, String.class);
        return response.toString();
    }

    public String rollback(String id){
        RestTemplate restTemplate = new RestTemplate();
        String inventoryResourceUrl
                = localhostUrl + "rollback";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inventoryResourceUrl + "/" + id, String.class);
        return response.toString();

    }


}
