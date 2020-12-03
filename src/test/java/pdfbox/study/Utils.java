package pdfbox.study;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;

public class Utils {

	public static File getTestOutputDirectory() {
		return new File("test_output");
	}

	public static File createTestOutputDirectory() {
		File result = getTestOutputDirectory();
		if (!result.exists()) {
			result.mkdir();
		}
		if (result.isFile()) {
			throw new RuntimeException("conflict with existing file");
		}
		return result;
	}

	public static void deleteTestOutputDirectory() {
		try {
			FileUtils.deleteDirectory(getTestOutputDirectory());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static File getTestOutputPdf(Class<?> clazz) {
		return new File(getTestOutputDirectory(), getTestOutputPdfFileName(clazz));
	}

	private static String getTestOutputPdfFileName(Class<?> clazz) {
		return clazz.getSimpleName() + ".pdf";
	}

	public static File getTestOutputImageFile(Class<?> clazz, String formatName) {
		return new File(getTestOutputDirectory(), getTestOutputImageFileName(clazz, formatName));
	}

	private static String getTestOutputImageFileName(Class<?> clazz, String formatName) {
		return clazz.getSimpleName() + "." + formatName;
	}

	public static StandardProtectionPolicy createBasicTestProtectionPolicy() {
		AccessPermission accessPermission = new AccessPermission();
		StandardProtectionPolicy result = new StandardProtectionPolicy("1234", "", accessPermission);
		accessPermission.setCanPrint(true);
		accessPermission.setCanModify(false);
		accessPermission.setCanModifyAnnotations(false);
		accessPermission.setCanFillInForm(false);
		accessPermission.setCanAssembleDocument(false);
		accessPermission.setCanExtractContent(false);
		accessPermission.setCanExtractForAccessibility(true);
		accessPermission.setCanPrintDegraded(false);
		result.setEncryptionKeyLength(128);
		result.setPreferAES(true);
		result.setPermissions(accessPermission);
		return result;
	}
}
