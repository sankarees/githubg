package com.githubg.actions.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PRRequest {
	private String owner;
	private String userName;
	private String repository;
	private int prNumber;
}
