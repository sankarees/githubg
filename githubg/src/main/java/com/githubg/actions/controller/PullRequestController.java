package com.githubg.actions.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.githubg.actions.exceptions.InvalidInputException;
import com.githubg.actions.exceptions.UnAuthorizedException;
import com.githubg.actions.request.CreatePRRequest;
import com.githubg.actions.request.MergePRRequest;
import com.githubg.actions.request.PRRequest;
import com.githubg.actions.service.PullRequestService;

@RestController
public class PullRequestController {
	private static final Logger log = LoggerFactory.getLogger(PullRequestController.class);

	@Autowired
	private PullRequestService pullRequestService;

	@GetMapping(value = "/repository/operation/pr")
	public ResponseEntity<?> getPullRequest(@RequestBody PRRequest prRequest) throws Exception {
		ResponseEntity<?> response = null;
		try {
			response = pullRequestService.getPullRequest(prRequest);
			log.debug("getPullRequest::response: ", response);
			log.debug("getPullRequest::responseBody: ", response.getBody());

		} catch (InvalidInputException exp) {
			exp.printStackTrace();
			throw new InvalidInputException(exp.getMessage());
		} catch (HttpClientErrorException exp) {
			exp.printStackTrace();
			throw new UnAuthorizedException(exp.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new Exception(exp.getMessage());
		}
		return response;
	}

	@PostMapping(value = "/repository/operation/pulls")
	public ResponseEntity<?> createPullRequest(@RequestHeader Map<String, String> headers,
			@RequestBody CreatePRRequest prRequest) throws Exception {
		ResponseEntity<?> response = null;
		try {
			response = pullRequestService.createPullRequest(headers, prRequest);
			log.debug("createPullRequest::response: ", response);
			log.debug("createPullRequest::responseBody: ", response.getBody());

		} catch (InvalidInputException exp) {
			exp.printStackTrace();
			throw new InvalidInputException(exp.getMessage());
		} catch (HttpClientErrorException exp) {
			exp.printStackTrace();
			throw new UnAuthorizedException(exp.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new Exception(exp.getMessage());
		}
		return response;
	}

	@PutMapping(value = "/repository/operation/pulls/merge")
	public ResponseEntity<?> mergePullRequest(@RequestHeader Map<String, String> headers,
			@RequestBody MergePRRequest prRequest) throws Exception {
		ResponseEntity<?> response = null;
		try {
			response = pullRequestService.mergePullRequest(headers, prRequest);
			log.debug("mergePullRequest::response: ", response);
			log.debug("mergePullRequest::responseBody: ", response.getBody());

		} catch (InvalidInputException exp) {
			exp.printStackTrace();
			throw new InvalidInputException(exp.getMessage());
		} catch (HttpClientErrorException exp) {
			exp.printStackTrace();
			throw new UnAuthorizedException(exp.getMessage());
		} catch (Exception exp) {
			exp.printStackTrace();
			throw new Exception(exp.getMessage());
		}
		return response;
	}
}