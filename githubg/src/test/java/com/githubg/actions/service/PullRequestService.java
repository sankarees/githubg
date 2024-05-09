package com.githubg.actions.service;

/*import static org.mockito.BDDMockito.verify;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;*/
import org.springframework.http.ResponseEntity;

import com.githubg.actions.controller.PullRequestController;
import com.githubg.actions.request.PRRequest;

//@ExtendWith(MockitoExtension.class)
public class PullRequestService {

	private PullRequestController pullRequestController = new PullRequestController();
	private PullRequestService pullRequestService = new PullRequestService();

//	@Test
	ResponseEntity<?> getPullRequest() throws Exception {
		ResponseEntity<?> response = pullRequestController.getPullRequest(null);
		return response;
	}
}