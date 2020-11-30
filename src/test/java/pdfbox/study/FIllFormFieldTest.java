package pdfbox.study;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FIllFormFieldTest {
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
			StandardProtectionPolicy policy = Utils.createBasicTestProtectionPolicy();
			policy.getPermissions().setCanFillInForm(true);
			doc.protect(policy);
			initEnd = System.nanoTime();
			new FillFormField().execute(doc, os, fontIs);
			finiStart = System.nanoTime();
			doc.save(os);
			finiEnd = System.nanoTime();
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
//			Utils.deleteTestOutputDirectory();
		}
		System.out.println("init nanoTime :" + (initEnd - initStart));
		System.out.println("main nanoTime :" + (finiStart - initEnd));
		System.out.println("fini nanoTime :" + (finiEnd - finiStart));
		System.out.println("output size   :" + (Utils.getTestOutputPdf(this.getClass()).length()));
	}
}
