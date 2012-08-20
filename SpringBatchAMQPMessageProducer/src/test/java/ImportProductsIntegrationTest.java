/*
 * Copyright (c) 2011. Written by Pronam Chatterjee. Copying and Use prohibited unless explicit permission is granted.
 */

/**
 * 
 */

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/job-context.xml", "/test-context.xml"})
public class ImportProductsIntegrationTest {
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private Job job;
	

	
	@Before
	public void setUp() throws Exception {

	}

	@Test public void importProducts() throws Exception {

		
		jobLauncher.run(job, new JobParametersBuilder()
			.addString("inputResource", "classpath:/input/MyBooks.zip")
			.addString("targetDirectory", "./target/importbooksbatch/")
			.addString("targetFile","books.txt")
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters()
		);

	}
	
	//@Test
    public void importProductsWithErrors() throws Exception {

		
		jobLauncher.run(job, new JobParametersBuilder()
			.addString("inputResource", "classpath:/input/products_with_errors.zip")
			.addString("targetDirectory", "./target/importproductsbatch/")
			.addString("targetFile","products.txt")
			.addLong("timestamp", System.currentTimeMillis())
			.toJobParameters()
		);


	}
	
}
