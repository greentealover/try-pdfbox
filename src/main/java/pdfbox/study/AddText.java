package pdfbox.study;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.util.Matrix;

public class AddText {

	public void execute(PDDocument doc, OutputStream os, InputStream fontIs) {
		try {
			PDFont font = PDType0Font.load(doc, fontIs);
			for (PDPage page : doc.getPages()) {
				PDPageContentStream contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, false);
				PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
				for (PDField field : acroForm.getFields()) {
					StringBuilder sb = new StringBuilder();
					for (PDAnnotationWidget widget : field.getWidgets()) {
						if (page.getAnnotations().stream().noneMatch(e -> e.getCOSObject() == widget.getCOSObject())) {
							continue;
						}
						if (sb.length() == 0) {
							System.out.print(field.getFullyQualifiedName());
						}
						PDRectangle rect = widget.getRectangle();
						sb.append(rect);
						contentStream.beginText();
						contentStream.setFont(font, 5);
						contentStream.setNonStrokingColor(Color.red);
						contentStream.setStrokingColor(Color.red);
						contentStream.setTextMatrix(new Matrix(1, 0, 0, 1, rect.getLowerLeftX(), rect.getLowerLeftY()));
						contentStream.showText(field.getFullyQualifiedName());
						contentStream.endText();
					}
					if (sb.length() != 0) {
						System.out.println(sb);
					}
				}
				contentStream.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
