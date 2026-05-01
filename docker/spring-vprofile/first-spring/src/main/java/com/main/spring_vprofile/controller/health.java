package com.main.spring_vprofile.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.util.Map;

@RestController
@RequestMapping("/")
public class health {

    @GetMapping
    public Map<String, String> health() throws Exception {
        String env = System.getenv().getOrDefault("ENV_VALUE", "No env");
        String hostName = InetAddress.getLocalHost().getHostName();

        return Map.of(
                "Message", "Spring Boot Docker is healthy.",
                "Env",  env,
                "HostName", hostName
                );
    }

    @GetMapping("/info")
    public String info(){
        return "Spring Boot Docker is running.";
    }
}
