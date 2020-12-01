package com.tryagain.batch;

import com.tryagain.batch.listeners.ImportStepListener;
import com.tryagain.batch.listeners.JobListener;
import com.tryagain.batch.listeners.MoveStepListener;
import com.tryagain.batch.model.Payment;
import com.tryagain.batch.tasklet.MoveFileTasklet;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.util.Assert;
import java.io.File;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    //@Bean
    //@StepScope
    public FlatFileItemReader<Payment> csvReader(@Value("#{jobParameters['path']}") String path) {
        return new FlatFileItemReaderBuilder<Payment>()
                .name("csvItemReader")
                .resource(new FileSystemResource(path))
                .delimited()
                .names("id", "name", "value")
                .fieldSetMapper(new BeanWrapperFieldSetMapper<Payment>() {{
                    setTargetType(Payment.class);
                }})
                .linesToSkip(1)
                .build();
    }
    //job(importStep(myWriter, path), path, name)

    @Autowired
    JobListener jobListener;

    //@Bean
    public Job job(@Qualifier("importStep") Step importStep, String path, String name) {
        return jobBuilderFactory.get("importMoveCSV " + path)
                .incrementer(new RunIdIncrementer())
                .listener(jobListener)
                .start(importStep).on("COMPLETED").to(moveStep(path, name))
                .from(importStep).on("FAILED").end()
                .end()
                //.next(moveStep(path, name))
                .build();
    }

    @Autowired
    ItemWriter<Payment> myWriter;

    @Value("${chunk.size}")
    private int chunkSize;

    @Autowired
    ImportStepListener importStepListener;

    //@Bean
    public Step importStep(ItemWriter<Payment> writer, String path) {
        return stepBuilderFactory.get("importStep " + path)
                .listener(importStepListener)
                .<Payment, Payment> chunk(chunkSize)
                .reader(csvReader(path))
                .writer(writer)
                .build();
    }

    @Value("${processed}/")
    private String processedDir;

    @Autowired
    MoveStepListener moveStepListener;

    //@Bean
    public Step moveStep(String path, String name) {
        return stepBuilderFactory.get("moveFile " + path)
                .listener(moveStepListener)
                .tasklet(moveTasklet(path, name))
                .build();
    }

    //@Bean
    public MoveFileTasklet moveTasklet(String path, String name) {
        MoveFileTasklet tasklet = new MoveFileTasklet();
        tasklet.setInputFile(path);
        tasklet.setOutput(processedDir + name);
        return tasklet;
    }


    @Autowired
    JobLauncher jobLauncher;

    @Value("${input}")
    private String inputDir;

    @Bean
    public void launchJob() {

        File dir = new File(inputDir);
        Assert.state(dir.isDirectory(), "Check your input directory path: " + dir.getPath() + " might not be a directory.");

        File[] files = dir.listFiles((d, name) -> name.matches(".*\\.csv$"));
        //System.out.println(Arrays.toString(files));

        if (files != null) {
            for (File f : files) {
                String path = f.getPath();
                String name = f.getName();
                //System.out.println(path);
                try {
                    JobParameters jobParameters = new JobParametersBuilder().addString("path", path).addString("name", name).toJobParameters();
                    //System.out.println(jobParameters);
                    jobLauncher.run(job(importStep(myWriter, path), path, name), jobParameters);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //else dir is empty or not a directory
    }
}

//@Bean
    /*
    public JdbcBatchItemWriter<Payment> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Payment>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO PAYMENT (id, name, value) VALUES (?, ?, ?)")
                .dataSource(dataSource)
                .build();
    }
    */
//TODO jdbc is faster
//TODO DI violation: job(importStep(myWriter, path), path, name)
//TODO why no pk violation exception is thrown, updates the rows instead
//TODO TEST: connection is established;
//           read rownum==written rownum==filesize;
//           кол-во jobinstance==кол-во файлов;
//           input folder size==processed folder size OR no files in input folder after processing?
//TODO logging