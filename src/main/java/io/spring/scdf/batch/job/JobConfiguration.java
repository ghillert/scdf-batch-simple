/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.spring.scdf.batch.job;

import java.util.Random;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Created by glennrenfro on 3/7/16.
 */
@Configuration
@EnableBatchProcessing
@EnableTask
public class JobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Value("${sample.jobName:}")
	private String jobName;

	@Bean
	public Job job() {

		final String jobNameToUse;

		if (StringUtils.hasText(this.jobName)) {
			jobNameToUse = this.jobName;
		}
		else {
			Random rand = new Random();
			jobNameToUse = "job" + rand.nextInt();
		}

		System.out.println("Setting up new Batch Job named: " + jobNameToUse);

		return jobBuilderFactory.get(jobNameToUse)
				.start(stepBuilderFactory.get("step1").tasklet(new HelloSimpleBatchTasklet()).build())
				.build();
	}
}
