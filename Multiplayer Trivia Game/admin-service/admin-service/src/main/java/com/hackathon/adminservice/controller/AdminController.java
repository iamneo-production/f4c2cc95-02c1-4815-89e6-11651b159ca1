package com.hackathon.adminservice.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import com.hackathon.adminservice.dto.ErrorModel;
import com.hackathon.adminservice.dto.Question1;
import com.hackathon.adminservice.entity.Question;
import com.hackathon.adminservice.externalservice.AuthenticationExternalService;
import com.hackathon.adminservice.service.AdminService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminservice;

	@Autowired
	AuthenticationExternalService authenticationfeignclient;

	@PostMapping("/questions")
	@CircuitBreaker(name = "clientBreaker", fallbackMethod = "getClientFallBack")
	public ResponseEntity<?> addQuestion(@RequestBody Question question,
			@RequestHeader(name = "Authorization") String tokenDup) {
		String role = authenticationfeignclient.getRole(tokenDup);
		if (role.equals("PLAYER")) {
			return ResponseEntity.status(HttpStatus.OK).body(question);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ErrorModel("Error305", "User can't add questions"));
		}
	}

	public ResponseEntity<ErrorModel> getClientFallBack(@RequestBody Question question,
			@RequestHeader(name = "Authorization") String tokenDup, Exception e) {
		return ResponseEntity.status(HttpStatus.GONE).body(new ErrorModel("Error306", "server is down"));
	}

	@GetMapping("/questions")
	public List<Question1> getListOfQuestions(@RequestParam("category") String category,
			@RequestParam("level") String level, @RequestParam("limit") int limit) {
		return adminservice.getListOfQuestions(category, level, limit);
	}

	@PostMapping("/getAnswerByQid")
	public Map<Integer, String> getQuestionAnswer(@RequestBody List<Integer> questionNos) {
		return adminservice.getQuestionAnswer(questionNos);
	}


	@Autowired
	private JobLauncher jobLauncher;
	@Autowired
	private Job job;

	private static final String TEMP_STORAGE="C:/Users/saiejjipurapu/Downloads";

	@PostMapping("/importGame")
	public void importCsvToDBJob(@RequestParam("file") MultipartFile multipartFile) throws IllegalStateException, IOException {
		try {
			String originalFileName=multipartFile.getOriginalFilename();
			File fileToImport=new File(TEMP_STORAGE+originalFileName);
			multipartFile.transferTo(fileToImport);
			JobParameters jobParameters=new JobParametersBuilder()
					.addString("fullPathFileName", TEMP_STORAGE+originalFileName)
					.addLong("startAt",System.currentTimeMillis()).toJobParameters();

			jobLauncher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				 | JobParametersInvalidException e) {
			e.printStackTrace();
		}

	}

	@Value("${msg}")
	String msg;

	@GetMapping("configCheck")
	public String getMsg()
	{
		return msg;
	}

	@Value("${buurefersh}")
	String busMsg;

	@GetMapping("busRefresh")
	public String getbusRefreshMsg()
	{
		return busMsg;
	}



}
