import com.vmware.vfabric.booksdb.batch.DecompressTasklet;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;

/**
 * @author acogoluegnes
 *
 */
public class DecompressTaskletTest {
	


	@Test
    public void execute() throws Exception {
		DecompressTasklet tasklet = new DecompressTasklet();
		tasklet.setResource(new ClassPathResource("/input/MyBooks.zip"));
		File outputDir = new File("./target/decompresstasklet");
		if(outputDir.exists()) {
			FileUtils.deleteDirectory(outputDir);
		}
		tasklet.setTargetDirectory(outputDir.getAbsolutePath());
		tasklet.setTargetFile("books.txt");
		
		tasklet.execute(null, null);
		
		File output = new File(outputDir,"books.txt");
		Assert.assertTrue(output.exists());

		
	}
	
	@Test public void corruptedArchive() throws Exception {
		DecompressTasklet tasklet = new DecompressTasklet();
		tasklet.setResource(new ClassPathResource("/input/books_corrupted.zip"));
		File outputDir = new File("./target/decompresstasklet");
		if(outputDir.exists()) {
			FileUtils.deleteDirectory(outputDir);
		}
		tasklet.setTargetDirectory(outputDir.getAbsolutePath());
		tasklet.setTargetFile("books.txt");
		
		try {
			tasklet.execute(null, null);
			Assert.fail("corrupted archive, the tasklet should have thrown an exception");
		} catch (Exception e) {
			// OK
		}
	}
	
}
