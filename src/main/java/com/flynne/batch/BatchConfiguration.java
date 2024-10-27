package com.flynne.batch;

import com.flynne.model.Order;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.batch.MyBatisCursorItemReader;
import org.mybatis.spring.batch.builder.MyBatisCursorItemReaderBuilder;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Autowired
    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/*.xml"));  // Adjust path as necessary
        return sessionFactory.getObject();
    }

    @Bean
    @StepScope // Ensure this is a step-scoped bean
    public MyBatisCursorItemReader<Order> reader(@Value("#{jobParameters['param1']}") String param1,
                                                 @Value("#{jobParameters['param2']}") String param2) throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("param1", param1);
        params.put("param2", param2);

        return new MyBatisCursorItemReaderBuilder<Order>()
                .sqlSessionFactory(sqlSessionFactory(null)) // Call without null
                .queryId("com.flynne.mapper.OrderMapper.selectAll")
//                .parameterValues(params)
                .build();
    }

    @Bean
    public ItemProcessor<Order, Order> processor() {
        return order -> {
            order.setAmount(order.getAmount().multiply(new BigDecimal("0.9"))); // Apply a 10% discount
            return order;
        };
    }

    @Bean
    public MyBatisBatchItemWriter<Order> writer() throws Exception {
        return new MyBatisBatchItemWriterBuilder<Order>()
                .sqlSessionFactory(sqlSessionFactory(null))
                .statementId("com.flynne.mapper.OrderMapper.insert")
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Order, Order>chunk(10)
                .reader(reader(null, null)) // This will now correctly resolve at runtime
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job importOrderJob() throws Exception {
        return jobBuilderFactory.get("importOrderJob")
                .flow(step1())
                .end()
                .build();
    }
}