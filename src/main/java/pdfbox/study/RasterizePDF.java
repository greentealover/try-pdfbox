package pdfbox.study;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class RasterizePDF {

	public void execute(PDDocument doc, OutputStream os, int pageIndex, float dpi, ImageType imageType, String formatName) {
		try {
			PDFRenderer pdfRenderer = new PDFRenderer(doc);
			PDPage page = doc.getPage(pageIndex);
			BufferedImage bim;
			bim = pdfRenderer.renderImageWithDPI(pageIndex, dpi, imageType);
			ImageIO.write(bim, formatName, os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
