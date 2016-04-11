package io.spring.scdf.batch;

import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.spring.scdf.batch.support.MyJobLauncherCommandLineRunner;

@SpringBootApplication
public class TestTaskBatchApplication {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private JobExplorer jobExplorer;

	@Value("${jobParams:null}")
	private String additionalJobParams;

	public static void main(String[] args) {
		SpringApplication.run(TestTaskBatchApplication.class, args);
	}

	@Bean
	public MyJobLauncherCommandLineRunner commandLineRunner() {
		return new MyJobLauncherCommandLineRunner(jobLauncher, jobExplorer, additionalJobParams);
	}
}
