package com.githubg.actions.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import com.githubg.actions.exceptions.InvalidInputException;
import com.githubg.actions.exceptions.UnAuthorizedException;
import com.githubg.actions.request.CreatePRRequest;
import com.githubg.actions.request.MergePRRequest;
import com.githubg.actions.request.PRRequest;
import com.githubg.actions.response.ResponseDetails;
import com.githubg.actions.service.PullRequestService;

@RestController
@RequestMapping("/repository/operation")
public class PullRequestController {
	private static final Logger log = LoggerFactory.getLogger(PullRequestController.class);

	@Autowired
	private PullRequestService pullRequestService;

	@GetMapping(value = "/pr")
	public ResponseDetails getPullRequest(@RequestBody PRRequest prRequest) throws Exception {
		ResponseEntity<?> response = null;
		ResponseDetails responseDetails = null;
		try {
			response = pullRequestService.getPullRequest(prRequest);
			responseDetails = buildResponse(response);
			log.debug("getPullRequest::responseDetails: ", responseDetails);
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
		return responseDetails;
	}

	@PostMapping(value = "/pulls")
	public ResponseDetails createPullRequest(
			@RequestHeader(name = "Authorization", required = true) String authToken,
			@RequestBody CreatePRRequest prRequest) throws Exception {
		ResponseEntity<?> response = null;
		ResponseDetails responseDetails = null;
		try {
			response = pullRequestService.createPullRequest(authToken, prRequest);
			responseDetails = buildResponse(response);
			log.debug("createPullRequest::response: ", response);
			log.debug("createPullRequest::responseDetails: ", responseDetails);
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
		return responseDetails;
	}

	@PutMapping(value = "/pulls/merge")
	public ResponseDetails mergePullRequest(@RequestHeader(name = "Authorization", required = true) String authToken,
			@RequestBody MergePRRequest prRequest) throws Exception {
		ResponseEntity<?> response = null;
		ResponseDetails responseDetails = null;
		try {
			response = pullRequestService.mergePullRequest(authToken, prRequest);
			responseDetails = buildResponse(response);
			log.debug("mergePullRequest::response: ", response);
			log.debug("mergePullRequest::responseDetails: ", responseDetails);
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
		return responseDetails;
	}

	private ResponseDetails buildResponse(ResponseEntity<?> response) {
		String message = null;
		if(response == null)
			return new ResponseDetails();
	
			message = response.getStatusCode().is2xxSuccessful() ? "success" : "failed";
		return new ResponseDetails(new Date(), response.getStatusCodeValue(), message, response.getBody().toString());
	}
}