package com.ph.ipldashboard.data;

import javax.sql.DataSource;

import com.ph.ipldashboard.models.MatchInput;
import com.ph.ipldashboard.models.MatchOutput;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    // column names in CSV
    private final String[] FIELD_NAMES = new String[] { "id", "city", "date", "player_of_match", "venue",
            "neutral_venue", "team1", "team2", "toss_winner", "toss_decision", "winner", "result", "result_margin",
            "eliminator", "method", "umpire1", "umpire2" };

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>().name("MatchItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .linesToSkip(1)
                .delimited()
                .names(FIELD_NAMES)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<MatchInput>() {
                    {
                        setTargetType(MatchInput.class);
                    }
                }).build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<MatchOutput> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<MatchOutput>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                // JPA converts camel case to underscore by default, hence table column name contains underscore
                .sql("INSERT INTO Match (id, city, date, player_of_match, venue, team1, team2, toss_winner, toss_decision, winner, result, result_margin, umpire1, umpire2)"
                        + " VALUES (:id, :city, :date, :playerOfMatch, :venue, :team1, :team2, :tossWinner, :tossDecision, :winner, :result, :resultMargin, :umpire1, :umpire2)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importUserJob(JobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory
                .get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<MatchOutput> writer) {
        return stepBuilderFactory
                .get("step1")
                .<MatchInput, MatchOutput>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

}