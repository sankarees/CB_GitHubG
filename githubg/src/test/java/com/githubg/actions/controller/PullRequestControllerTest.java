package com.githubg.actions.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Date;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.githubg.actions.request.CreatePRRequest;
import com.githubg.actions.request.MergePRRequest;
import com.githubg.actions.request.PRRequest;
import com.githubg.actions.request.PullRequest;
import com.githubg.actions.response.ResponseDetails;

@ExtendWith(MockitoExtension.class)
@WebMvcTest
@AutoConfigureMockMvc
public class PullRequestControllerTest {

	private String owner = "test";
	private String userName = "test";
	private String repository = "test";
	private int prNumber = 1;
	private String title = "PR";
	private String body = "PR Body";
	private String head = "PR Head";
	private String base = "PR base";
	private final String PR_GET_ENDPOINT = "/repository/operation/pr";
	private final String PR_CREATE_ENDPOINT = "/repository/operation/pulls";
	private final String PR_MERGE_ENDPOINT = "/repository/operation/pulls/merge";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PullRequestController pullRequestController;

	@Test
	public void testPullRequestSuccess() throws Exception {
		Mockito.when(pullRequestController.getPullRequest(any(PRRequest.class))).thenReturn(buildResponseSuccess());
		mockMvc.perform(MockMvcRequestBuilders.get(PR_GET_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(getPRRequestInJson())).andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testPullRequestBadRequest() throws Exception {
		Mockito.when(pullRequestController.getPullRequest(any(PRRequest.class))).thenReturn(buildResponseSuccess());
		mockMvc.perform(MockMvcRequestBuilders.get(PR_GET_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(new PRRequest().toString())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testCreatePullRequestSuccess() throws Exception {
		Mockito.when(pullRequestController.createPullRequest(anyString(), any(CreatePRRequest.class)))
				.thenReturn(buildResponseSuccess());
		mockMvc.perform(MockMvcRequestBuilders.post(PR_CREATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "my_token").content(getCreatePRRequestInJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testCreatePullRequestBadRequest() throws Exception {
		Mockito.when(pullRequestController.createPullRequest(anyString(), any(CreatePRRequest.class)))
				.thenReturn(buildResponseSuccess());
		mockMvc.perform(MockMvcRequestBuilders.post(PR_CREATE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(getCreatePRRequestInJson())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testMergePullRequestSuccess() throws Exception {
		Mockito.when(pullRequestController.mergePullRequest(anyString(), any(MergePRRequest.class)))
				.thenReturn(buildResponseSuccess());
		mockMvc.perform(MockMvcRequestBuilders.put(PR_MERGE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", "my_token").content(getMergePRRequestInJson()))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void testMergePullRequestBadRequest() throws Exception {
		Mockito.when(pullRequestController.mergePullRequest(anyString(), any(MergePRRequest.class)))
				.thenReturn(buildResponseSuccess());
		mockMvc.perform(MockMvcRequestBuilders.put(PR_MERGE_ENDPOINT).contentType(MediaType.APPLICATION_JSON)
				.content(getMergePRRequestInJson())).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	private String getCreatePRRequestInJson() throws JsonProcessingException {
		CreatePRRequest createPRRequest = new CreatePRRequest(owner, userName, repository,
				new PullRequest(title, body, head, base));
		return new ObjectMapper().writeValueAsString(createPRRequest);
	}

	private String getMergePRRequestInJson() throws JsonProcessingException {
		MergePRRequest mergePRRequest = new MergePRRequest(owner, userName, repository, prNumber,
				new PullRequest(title, body, head, base));
		return new ObjectMapper().writeValueAsString(mergePRRequest);
	}

	private String getPRRequestInJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(new PRRequest(owner, userName, repository, prNumber));
	}

	private ResponseDetails buildResponseSuccess() {
		return new ResponseDetails(new Date(), 200, "success", "");
	}
}
