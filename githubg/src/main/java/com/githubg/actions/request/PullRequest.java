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
public class PullRequest {
	private String title;
	private String body;
	private String head;
	private String base;
}
