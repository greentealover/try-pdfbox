package pdfbox.study;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RasterizePDFTest {
	@Test
	public void testFill() throws IOException {
		String pdfResourcePath = "pdf/nta/23100051-02.pdf";
		Utils.createTestOutputDirectory();
		String formatName = "jpeg";
		int pageIndex = 0;
		float dpi = 1000;
		ImageType imageType = ImageType.RGB;

		File outputImageFile = Utils.getTestOutputImageFile(this.getClass(), formatName);
		try (InputStream is = new ClassPathResource(pdfResourcePath).getInputStream();
				OutputStream os = new FileOutputStream(outputImageFile);
				PDDocument doc = PDDocument.load(is)) {
			new RasterizePDF().execute(doc, os, pageIndex, dpi, imageType, formatName);
			os.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
//			Utils.deleteTestOutputDirectory();
		}
	}
}
