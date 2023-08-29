package com.hackathon.adminservice.config;

import java.io.File;


import com.hackathon.adminservice.entity.Question;
import com.hackathon.adminservice.repo.AdminRepository;
import com.hackathon.adminservice.serviceimpl.MyTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
@EnableBatchProcessing
@SuppressWarnings("deprecation")
public class SpringBatchConfig {



//    @Autowired
//    //@SuppressWarnings("deprecation")
//    private JobBuilderFactory jobBuilderFactory = new JobBuilderFactory(AdminRepository);
//
//    //@Autowired
//   // private JobRegistry jobRegistry;
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private AdminRepository adminRepository;



    @Bean
    @StepScope
    public FlatFileItemReader<Question> reader(@Value("#{jobParameters[fullPathFileName]}")String pathToFile) {
        FlatFileItemReader<Question> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(new File(pathToFile)));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;



    }



    private LineMapper<Question> lineMapper() {
        DefaultLineMapper<Question> linemapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("category","correct_answer","level","opt1","opt2","opt3","opt4","question_description");
        BeanWrapperFieldSetMapper<Question> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Question.class);
        linemapper.setLineTokenizer(lineTokenizer);
        linemapper.setFieldSetMapper(fieldSetMapper);
        return linemapper;
    }




    @Bean
    public RepositoryItemWriter<Question> writer() {
        RepositoryItemWriter<Question> writer = new RepositoryItemWriter<>();
        writer.setRepository(adminRepository);
        writer.setMethodName("save");
        return writer;



    }



//    @Bean
//    public Step step1(FlatFileItemReader<Question> reader) {
//        return stepBuilderFactory.get("csv-step").<Question, Question>chunk(10).reader(reader)
//                .writer(writer()).build();
//    }

    @Bean
    public Job myJob(JobRepository jobRepository, Step step) {
        return new JobBuilder("csv-step", jobRepository)
                .start(step)
                .build();
    }

    @Bean
    public Tasklet myTasklet() {
        return new MyTasklet();
    }



    @Bean
    public Step myStep(JobRepository jobRepository, Tasklet myTasklet, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importCustomers", jobRepository)
                .tasklet(myTasklet, transactionManager) // or .chunk(chunkSize, transactionManager)
                .build();
    }


//    @Bean
//    public Job runJob(FlatFileItemReader<Question> reader) throws NoSuchJobException {
//        return jobRegistry.getJob("importCustomers");
//                //jobBuilderFactory.get("importCustomers").flow(step1(reader)).end().build();
//    }





}
