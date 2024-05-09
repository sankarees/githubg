package com.githubg.actions.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MergePRRequest {
	private String owner;
	private String userName;
	private String repository;
	private int prNumber;
	PullRequest pullRequest;
}
