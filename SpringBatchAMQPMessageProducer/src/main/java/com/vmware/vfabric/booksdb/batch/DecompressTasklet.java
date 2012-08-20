package com.vmware.vfabric.booksdb.batch;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;

import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.Resource;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipInputStream;

/**
 * Decompresses the the zip file.
 *
 * @author Pronam Chatterjee
 */
public class DecompressTasklet implements Tasklet {

    private Resource resource;

    private String targetDirectory;

    private String targetFile;


    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(resource.getInputStream()));
        File targetDirectoryAsFile = new File(targetDirectory);
        if(!targetDirectoryAsFile.exists()){
            FileUtils.forceMkdir(targetDirectoryAsFile);
        }
        
        File target = new File(targetDirectory,targetFile);
        
        BufferedOutputStream destination = null;
        while(zis.getNextEntry()!=null){
            if(!target.exists()){
                target.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(target);
            destination = new BufferedOutputStream(fos);
            IOUtils.copy(zis,destination);
            destination.flush();
            destination.close();
        }
        zis.close();

        if(!target.exists()){

            throw new IllegalStateException("Could Not decompress archive!");
        }

        return RepeatStatus.FINISHED;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }
}
