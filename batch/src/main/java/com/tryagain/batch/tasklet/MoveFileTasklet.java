package com.tryagain.batch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class MoveFileTasklet implements Tasklet {

    private String inputFile;

    private String destDir;

    public void setInputFile(String inFile) {
        this.inputFile = inFile;
    }

    public void setOutput(String outDir) {
        this.destDir = outDir;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        try {
            Files.move(Paths.get(inputFile), Paths.get(destDir));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return RepeatStatus.FINISHED;
    }
}