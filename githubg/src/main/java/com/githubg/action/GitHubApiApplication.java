package com.githubg.action;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GitHubApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Autowired
    private RestTemplate restTemplate;

    private final String baseUrl = "https://api.github.com";
    private final String token = "YOUR_GITHUB_TOKEN";

    // Get user information
    public void getUserInfo(String username) {
        String url = baseUrl + "/users/" + username;
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("User Info:");
        System.out.println(response.getBody());
    }

    // Get repositories of a user
    public void getUserRepositories(String username) {
        String url = baseUrl + "/users/" + username + "/repos";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println("User Repositories:");
        System.out.println(response.getBody());
    }

    // Create a pull request
    public void createPullRequest(String owner, String repo, String title, String head, String base) {
        String url = baseUrl + "/repos/" + owner + "/" + repo + "/pulls";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + token);
        headers.set("Content-Type", "application/json");

        String requestBody = String.format("{\"title\": \"%s\", \"head\": \"%s\", \"base\": \"%s\"}", title, head, base);
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        System.out.println("Pull request created:");
        System.out.println(response.getBody());
    }
}