package com.example.batch;

import com.example.modle.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfig {
    private final ItemReader<Transaction> reader;
    private final ItemWriter<Transaction> writer;
    private final ItemProcessor<Transaction, Transaction> processor;

    @Bean
    public Job transactionJob(Step transactionStep, JobRepository jobRepository) {
        return new JobBuilder("transactionJob", jobRepository)
                .start(transactionStep)
                .build();
    }

    @Bean
    public Step transactionStep(PlatformTransactionManager tx, JobRepository jobRepository) {
        return new StepBuilder("transactionStep", jobRepository)
                .<Transaction, Transaction>chunk(2, tx)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .transactionManager(tx)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(25);
        executor.initialize();
        return executor;
    }
}
