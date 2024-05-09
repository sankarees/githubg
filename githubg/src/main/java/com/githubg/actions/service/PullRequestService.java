package com.githubg.actions.service;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.githubg.actions.config.RestConfig;
import com.githubg.actions.exceptions.InvalidInputException;
import com.githubg.actions.request.CreatePRRequest;
import com.githubg.actions.request.MergePRRequest;
import com.githubg.actions.request.PullRequest;
import com.githubg.actions.request.PRRequest;

@Service
public class PullRequestService {
	private static final Logger log = LoggerFactory.getLogger(PullRequestService.class);

	@Value("${repository.operation.git.url}")
	private String REPO_API_URL;

	private final String AUTHORIZATION = "Authorization";

	@Autowired
	private RestConfig restConfig;
	public ResponseEntity<?> getPullRequest(PRRequest prRequest) throws Exception {
		ValidatePRRequest(prRequest);

		String url = buildRepoUrl(prRequest.getUserName(), prRequest.getRepository()) + "/pulls/"
				+ prRequest.getPrNumber();
		
		log.debug("getPullRequest::url ", url);
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

		ResponseEntity<?> response = restConfig.getRestTemplate().exchange(url, HttpMethod.GET, entity, String.class);
		log.debug("PullRequests response: ", response.getBody());
		return response;
	}

	public ResponseEntity<?> createPullRequest(@RequestHeader Map<String, String> headers, CreatePRRequest prRequest)
			throws Exception {
		validateCreatePullRequest(prRequest);

		String url = buildRepoUrl(prRequest.getUserName(), prRequest.getRepository()) + "/pulls";
		log.debug("createPullRequest::url ", url);
		log.debug("createPullRequest::headers ", headers);
		log.debug("createPullRequest::headers ", prRequest.getPullRequest());
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(AUTHORIZATION, (String.join("token ", headers.get(AUTHORIZATION.toLowerCase()))));
		log.debug("createPullRequest::httpHeaders ", httpHeaders);

		HttpEntity<PullRequest> request = new HttpEntity<>(prRequest.getPullRequest(), httpHeaders);

		ResponseEntity<String> response = restConfig.getRestTemplate().exchange(url, HttpMethod.POST, request,
				String.class);
		log.debug("createPullRequest response: ", response.getBody());
		return response;
	}

	public ResponseEntity<?> mergePullRequest(@RequestHeader Map<String, String> headers, MergePRRequest prRequest)
			throws Exception {
		validateMergePullRequest(prRequest);

		String url = buildRepoUrl(prRequest.getUserName(), prRequest.getRepository()) + "/pulls/"
				+ prRequest.getPrNumber() + "/merge";
		log.debug("mergePullRequest::url ", url);
		log.debug("mergePullRequest::headers ", headers);
		log.debug("mergePullRequest::headers ", prRequest.getPullRequest());
		HttpHeaders httpHeaders = new HttpHeaders();

		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.set(AUTHORIZATION, String.join("token ", headers.get(AUTHORIZATION.toLowerCase())));
		log.debug("mergePullRequest::httpHeaders " + httpHeaders);

		HttpEntity<PullRequest> request = new HttpEntity<>(prRequest.getPullRequest(), httpHeaders);

		ResponseEntity<?> response = restConfig.getRestTemplate().exchange(url, HttpMethod.PUT, request,
				PullRequest.class);
		log.debug("mergePullRequest response: ", response.getBody());
		return response;
	}

	private void validateCreatePullRequest(CreatePRRequest prRequest) throws Exception {
		if (prRequest == null || ObjectUtils.isEmpty(prRequest) || prRequest.getPullRequest() == null
				|| ObjectUtils.isEmpty(prRequest.getPullRequest())) {
			throw new InvalidInputException(
					"Invalid Input. Given value for input is " + prRequest == null ? "" : prRequest.toString());
		}

		validateUserName(prRequest.getUserName());
		validateRepository(prRequest.getRepository());
		validateTitle(prRequest.getPullRequest().getTitle());
		validateBody(prRequest.getPullRequest().getBody());
		validateHead(prRequest.getPullRequest().getHead());
		validateBase(prRequest.getPullRequest().getBase());
	}

	private void validateMergePullRequest(MergePRRequest prRequest) throws Exception {
		if (prRequest == null || ObjectUtils.isEmpty(prRequest) || prRequest.getPullRequest() == null
				|| ObjectUtils.isEmpty(prRequest.getPullRequest())) {
			throw new InvalidInputException(
					"Invalid Input. Given value for input is " + prRequest == null ? "" : prRequest.toString());
		}

		validateUserName(prRequest.getUserName());
		validateRepository(prRequest.getRepository());
		validatePRNumber(prRequest.getPrNumber());
		validateTitle(prRequest.getPullRequest().getTitle());
		validateBody(prRequest.getPullRequest().getBody());
		validateHead(prRequest.getPullRequest().getHead());
		validateBase(prRequest.getPullRequest().getBase());
	}

	private void ValidatePRRequest(PRRequest prRequest) throws Exception {
		if (prRequest == null || ObjectUtils.isEmpty(prRequest)) {
			throw new InvalidInputException(
					"Invalid Input. Given value for input is " + prRequest == null ? "" : prRequest.toString());
		}

		validateUserName(prRequest.getUserName());
		validateRepository(prRequest.getRepository());
		validatePRNumber(prRequest.getPrNumber());
	}

	private void validatePRNumber(int prNumber) throws InvalidInputException {
		if (prNumber < 1) {
			throw new InvalidInputException("Invalid input for PR number. Given value for PR number is " + prNumber);
		}
	}

	private void validateUserName(String userName) throws InvalidInputException {
		if (StringUtils.isBlank(userName)) {
			throw new InvalidInputException("Invalid input for username. Given value for userName is " + userName);
		}
	}

	private void validateRepository(String repository) throws InvalidInputException {
		if (StringUtils.isBlank(repository)) {
			throw new InvalidInputException(
					"Invalid input for repository. Given value for repository is " + repository);
		}
	}

	private void validateTitle(String title) throws InvalidInputException {
		if (StringUtils.isBlank(title)) {
			throw new InvalidInputException("Invalid input for title. Given value for title is " + title);
		}
	}

	private void validateBody(String body) throws InvalidInputException {
		if (StringUtils.isBlank(body)) {
			throw new InvalidInputException("Invalid input for body. Given value for body is " + body);
		}
	}

	private void validateHead(String head) throws InvalidInputException {
		if (StringUtils.isBlank(head)) {
			throw new InvalidInputException("Invalid input for body. Given value for head is " + head);
		}
	}

	private void validateBase(String base) throws InvalidInputException {
		if (StringUtils.isBlank(base)) {
			throw new InvalidInputException("Invalid input for base. Given value for base is " + base);
		}
	}
	
	private String buildRepoUrl(String userName, String repository) {
		log.debug("REPO_API_URL:" + REPO_API_URL); 
		StringBuilder urlBuilder = new StringBuilder();
		urlBuilder.append(REPO_API_URL)
				  .append("/repos/")
				  .append(userName)
				  .append("/")
				  .append(repository);
		log.debug("buildRepoUrl:" + urlBuilder.toString()); 
		return urlBuilder.toString();
	}

}