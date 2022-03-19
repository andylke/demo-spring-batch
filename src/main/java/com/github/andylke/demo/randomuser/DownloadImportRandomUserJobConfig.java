package com.github.andylke.demo.randomuser;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties({DownloadImportRandomUserProperties.class})
@Import({DownloadRandomUserStepConfig.class, ImportRandomUserStepConfig.class})
public class DownloadImportRandomUserJobConfig {

  @Autowired private JobBuilderFactory jobBuilderFactory;

  @Autowired private Step downloadRandomUserStep;

  @Autowired private Step importRandomUserStep;

  @Bean
  public Job downloadImportRandomUserJob() {
    return jobBuilderFactory
        .get("downloadImportRandomUser")
        .incrementer(new RunIdIncrementer())
        .start(downloadRandomUserStep)
        .next(importRandomUserStep)
        .build();
  }
}
