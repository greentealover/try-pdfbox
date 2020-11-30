package pdfbox.study;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class FillFormField {

	public void execute(PDDocument doc, OutputStream os, InputStream fontIs) {
		try {
			ByteArrayInputStream fontBis = new ByteArrayInputStream(IOUtils.toByteArray(fontIs));
			PDAcroForm acroForm = doc.getDocumentCatalog().getAcroForm();
			PDType0Font.load(doc, fontBis);
			fontBis.reset();
			PDType0Font font = PDType0Font.load(doc, fontBis, false);
			COSName name = acroForm.getDefaultResources().add(font);
			String defaultAppearance = "/" + name.getName() + " 5 Tf 255 0 0 rg 255 0 0 RG";
			COSString defaultAppearanceCOS = new COSString(defaultAppearance);
			for (PDField field : acroForm.getFields()) {
				System.out.print(field.getFullyQualifiedName());
				StringBuilder sb = new StringBuilder();
				List<PDAnnotationWidget> widgets = field.getWidgets();
				COSBase action = field.getCOSObject().getItem(COSName.AA);
				field.getCOSObject().removeItem(COSName.AA);
				field.getCOSObject().setString(COSName.DA, defaultAppearance);
				for (PDAnnotationWidget widget : widgets) {
					sb.append(widget.getRectangle());
					if (widget.getCOSObject().containsKey(COSName.DA)) {
						widget.getCOSObject().setItem(COSName.DA, defaultAppearanceCOS);
					}
				}
				field.setValue(field.getFullyQualifiedName());
				if (action != null) {
					field.getCOSObject().setItem(COSName.AA, action);
				}
				System.out.println(sb);
			}
			acroForm.flatten();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
