package com.githubg.actions;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GHPullRequest;

public class PullRequestCreator {

    public void createPullRequest() {
        try {
            // Initialize GitHub API client
            GitHub github = GitHub.connectUsingOAuth("Bearer github_pat_    11AWFOWWQ0GgMyuOhHXI9O_yRM5tsgxO1ZSpn5yqbkLu5OUWGUHPAt1a0cRFwERDA4B6U57DS4zIMxUQ0x");

            // Get the repository where you want to create the pull request
            GHRepository repository = github.getRepository("sankarees/githubg");

            // Create a new pull request
            GHPullRequest pullRequest = repository.createPullRequest(
                    "PRSankarCode",
                    "develop", // The branch you're creating the pull request from --head
                    "main", // The branch you're creating the pull request to --base
                    "Pull request description" // Optional description
            );

            System.out.println("Pull request created: " + pullRequest.getHtmlUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}