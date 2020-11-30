package pdfbox.study;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AddTextTest {
	@Test
	public void testFill() throws IOException {
		String pdfResourcePath = "pdf/nta/23100051-02.pdf";
		String fontResourcePath = "font/IPAexfont00401/ipaexg.ttf";
		Utils.createTestOutputDirectory();
		File outputPdf = Utils.getTestOutputPdf(this.getClass());
		long initStart, initEnd, finiStart, finiEnd;
		initStart = System.nanoTime();
		try (InputStream is = new ClassPathResource(pdfResourcePath).getInputStream();
				OutputStream os = new FileOutputStream(outputPdf);
				InputStream fontIs = new ClassPathResource(fontResourcePath).getInputStream();
				PDDocument doc = PDDocument.load(is)) {
			doc.protect(Utils.createBasicTestProtectionPolicy());
			initEnd = System.nanoTime();
			new AddText().execute(doc, os, fontIs);
			finiStart = System.nanoTime();
			doc.save(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
//			Utils.deleteTestOutputDirectory();
		}
		finiEnd = System.nanoTime();
		System.out.println("init nanoTime :" + (initEnd - initStart));
		System.out.println("main nanoTime :" + (finiStart - initEnd));
		System.out.println("fini nanoTime :" + (finiEnd - finiStart));
		System.out.println("output size   :" + (Utils.getTestOutputPdf(this.getClass()).length()));
	}
}
