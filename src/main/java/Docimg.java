

import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.text.DocumentException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Docimg {
    class MyLine implements ILineDrawer {
        private float lineWidth = 1;
        private float offset = 5;
        private Color color = Color.BLACK;
        @Override
        public void draw(PdfCanvas canvas, Rectangle drawArea) {
            canvas.saveState()
                    .setStrokeColor(color)
                    .setLineWidth(lineWidth)
                    .moveTo(drawArea.getX(), drawArea.getY() + lineWidth / 2 + offset)
                    .lineTo(drawArea.getX() + drawArea.getWidth(), drawArea.getY() + lineWidth / 2 + offset)
                    .stroke()
                    .restoreState();
        }

        @Override
        public float getLineWidth() {
            return lineWidth;
        }
        @Override
        public void setLineWidth(float lineWidth) {
            this.lineWidth = lineWidth;
        }
        @Override
        public Color getColor() {
            return color;
        }
        @Override
        public void setColor(Color color) {
            this.color = color;
        }
        public float getOffset() {
            return offset;
        }
        public void setOffset(float poffset) {
            this.offset = offset;
        }

    }


    public static final String DEST = "results/text/bike.pdf";

    public static void main(String[] args) throws IOException {

        String title="Default title", author="Default author",dest="results/text/docimg_document.pdf";
        ArrayList<PdfImage> pdfImages = new ArrayList<>();
        UserReader userReader = new UserReader();

        while(true){
            String action = userReader.action();
            switch(action){
                case "createTitle":
                    title = userReader.getTitle();
                    break;

                case "createAuthor":
                    author = userReader.getAuthor();
                    break;
                case "createImage":
                    PdfImage pdfImage = userReader.getPdfImage();
                    pdfImages.add(pdfImage);
                    break;
                case "createImageFromDisk":
                    pdfImages.add(userReader.getPdfImageFromDisk());
                    break;
                case "changeOrder":
                    Integer imageToChange = userReader.getImageIdToChange(pdfImages.size());
                    Integer placeAfter = userReader.getImageIdToPlaceAfter(pdfImages.size());
                    if(placeAfter > imageToChange){
                        Collections.rotate(pdfImages.subList(imageToChange-1,placeAfter),-1);
                    }else {
                        if (placeAfter < imageToChange) {
                            Collections.rotate(pdfImages.subList(placeAfter+1, imageToChange), 1);
                        }
                    }
                    break;
                case "setDestination":
                    dest = userReader.getDestination();
                    break;
                case "generatePdf":
                    PdfGenerator pdfGenerator = new PdfGenerator(title, author, pdfImages, dest);
                    try {
                        pdfGenerator.generatePdfFromPdfImages();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                    break;

            }

        }


//        run(new FileChooserTest(), 250, 110);
//        UserReader userReader = new UserReader();
//        UrlsReader urlsReader = new UrlsReader();
//        PdfGenerator pdfGenerator = new PdfGenerator(urlsReader.readUrls(), userReader);
//        try {
//            pdfGenerator.generatePdfFromUrls(DEST);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
////        File file = new File(DEST);
////        file.getParentFile().mkdirs();
////        new Docimg().createPdf(DEST);
    }
    public void createPdf(String dest) throws IOException {
        PdfDocument pdf = new PdfDocument(new PdfWriter(dest));
        PageSize pagesize = PageSize.A4;
        Document document = new Document(pdf, pagesize);

        float w = pagesize.getWidth() - document.getLeftMargin() - document.getRightMargin();
        MyLine line = new MyLine();
        List tabstops = new ArrayList();
        tabstops.add(new TabStop(w / 2, TabAlignment.CENTER, line));
        tabstops.add(new TabStop(w, TabAlignment.LEFT, line));
        Paragraph p = new Paragraph();
        p.addTabStops(tabstops);
        p.add(new Tab()).add("Text in the middle").add(new Tab());
        document.add(p);
        document.close();
    }

    private PdfImage getPdfImageFromDisk(UserReader userReader){
        return userReader.getPdfImageFromDisk();
    }



    public static void run(JFrame frame, int width, int height) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setVisible(true);
    }




}