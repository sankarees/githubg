package com.githubg.actions;

import org.springframework.beans.factory.annotation.Autowired;
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
public class GithubgApplication {

	public GithubgApplication() {
		restTemplate = new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(GithubgApplication.class, args);
		System.out.println("SANKAR...");
		GithubgApplication githubgApplication = new GithubgApplication();
		githubgApplication.mergePullRequest("sankarees", "githubg", 3);
		githubgApplication.getUserInfo("sankarees");
		githubgApplication.getUserRepositories("sankarees");
		githubgApplication.getPullRequest("sankarees", "githubg", "3");
//        githubgApplication.createPullRequest("sankarees", "githubg", "PR sankar code", "github-init-dev", "main");
	}

	private RestTemplate restTemplate;

	@Autowired
	GithubgApplication(RestTemplateBuilder builder) {
		restTemplate = builder.build();
	}

	private final String GITHUB_API_URL = "https://api.github.com";
	private final String ACCESS_TOKEN = "ghp_ dg8OudwVYzHWHx33DU8NZph4JOv29i0gNFSb";

	// Get user information
	public void getUserInfo(String username) {
		String url = GITHUB_API_URL + "/users/" + username;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("User Info:");
		System.out.println(response.getBody());
	}

	// Get repositories of a user
	public void getUserRepositories(String username) {
		String url = GITHUB_API_URL + "/users/" + username + "/repos";
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("User Repositories:");
		System.out.println(response.getBody());
	}

	// Get repositories of a user
	public void getPullRequest(String username, String repository, String prNo) {
		String url = GITHUB_API_URL + "/repos/" + username + "/" + repository + "/pulls/" + prNo;
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		System.out.println("PullRequests: ");
		System.out.println(response.getBody());
	}

	// Create a pull request
	public void createPullRequest(String owner, String repo, String title, String head, String base) {
		String url = GITHUB_API_URL + "/repos/" + owner + "/" + repo + "/pulls";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "token " + ACCESS_TOKEN);
		headers.set("Content-Type", "application/json");

		String requestBody = String.format("{\"title\": \"%s\", \"head\": \"%s\", \"base\": \"%s\"}", title, head,
				base);
		HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		System.out.println("Pull request created:");
		System.out.println(response.getBody());
	}

	public void mergePullRequest(String owner, String repo, int pullNumber) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "token " + ACCESS_TOKEN);

			String requestBody = String.format("{\"commit_title\": \"%s\", \"commit_message\": \"%s\"}", "merge pr",
					"commit code");
			HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

			String mergeUrl = GITHUB_API_URL + "/repos/" + owner + "/" + repo + "/pulls/" + pullNumber + "/merge";

			ResponseEntity<String> response = restTemplate.exchange(mergeUrl, HttpMethod.PUT, entity, String.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				System.out.println("Pull request #" + pullNumber + " merged successfully.");
			} else {
				System.out.println(
						"Failed to merge pull request #" + pullNumber + ". Status code: " + response.getStatusCode());
			}
		} catch (Exception exp) {
			exp.printStackTrace();
		}
	}
}