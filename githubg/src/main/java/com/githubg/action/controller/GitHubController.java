package com.githubg.action.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GitHubController {

	@Autowired
	private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/repositories")
	public String getRepositories() {
		OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient("github");
		String token = client.getAccessToken().getTokenValue();
		String url = "https://api.github.com/user/repos";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		return result;
	}
}