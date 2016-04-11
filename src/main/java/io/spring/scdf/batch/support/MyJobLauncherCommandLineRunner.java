package io.spring.scdf.batch.support;

import java.util.Properties;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;

public class MyJobLauncherCommandLineRunner extends JobLauncherCommandLineRunner {

	final Properties additionalJobParams;

	public MyJobLauncherCommandLineRunner(JobLauncher jobLauncher,
			JobExplorer jobExplorer, String additionalJobParamsAsString, boolean makeParametersUnique) {
		super(jobLauncher, jobExplorer);
		ExpandedJobParametersConverter converter = new ExpandedJobParametersConverter();
		converter.setMakeParametersUnique(makeParametersUnique);
		additionalJobParams = converter.getJobParametersForJsonString(additionalJobParamsAsString).toProperties();
	}

	@Override
	protected void launchJobFromProperties(Properties properties) throws JobExecutionException {

		final Properties propertiesToUse;

		if (properties == null) {
			propertiesToUse = new Properties();
		}
		else {
			propertiesToUse = properties;
		}

		propertiesToUse.putAll(additionalJobParams);

		super.launchJobFromProperties(propertiesToUse);
	}

}
