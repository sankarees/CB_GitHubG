package com.githubg.actions.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CreatePRRequest {
	private String owner;
	private String userName;
	private String repository;
	PullRequest pullRequest;
}
