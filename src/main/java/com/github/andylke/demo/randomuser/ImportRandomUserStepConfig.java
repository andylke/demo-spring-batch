package com.github.andylke.demo.randomuser;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.github.andylke.demo.user.User;
import com.github.andylke.demo.user.UserRepository;

@Configuration
public class ImportRandomUserStepConfig {

  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Autowired private UserRepository userRepository;

  @Autowired private DownloadRandomUserProperties properties;

  @Bean
  public Step importRandomUserStep() {
    return stepBuilderFactory
        .get("importRandomUserStep")
        .<RandomUser, User>chunk(properties.getChunkSize())
        .reader(randomUserFileReader())
        .processor(randomUserToUserProcessor())
        .writer(userWriter())
        .build();
  }

  @Bean
  @StepScope
  public FlatFileItemReader<? extends RandomUser> randomUserFileReader() {
    return new FlatFileItemReaderBuilder<RandomUser>()
        .name("randomUserFileReader")
        .resource(new FileSystemResource(DownloadRandomUserStepConfig.RANDOM_USER_FILE_PATH))
        .linesToSkip(1)
        .delimited()
        .names(DownloadRandomUserStepConfig.RANDOM_USER_FIELD_NAMES)
        .targetType(RandomUser.class)
        .build();
  }

  @Bean
  public RandomUserToUserProcessor randomUserToUserProcessor() {
    return new RandomUserToUserProcessor();
  }

  @Bean
  @StepScope
  public RepositoryItemWriter<? super User> userWriter() {
    return new RepositoryItemWriterBuilder<User>().repository(userRepository).build();
  }
}
