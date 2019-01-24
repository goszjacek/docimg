import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfOutline;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.property.TabAlignment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.layout.element.TabStop;

import javax.swing.text.StyledEditorKit;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class PdfGenerator {
    private ArrayList<String> urls;
    private float marginLeft = 50f, marginBottom = 50f, marginRight = 50f,  marginTop = 50f;
    String title, author, dest;
    ArrayList<PdfImage> pdfImages;

    PdfGenerator(ArrayList<String> urls, UserReader userReader){
        this.urls = urls;
    }

    PdfGenerator(String title,String  author,ArrayList<PdfImage> pdfImages,String dest){
        this.title=title;
        this.author=author;
        this.pdfImages=pdfImages;
        this.dest=dest;
    }

    void generatePdfFromPdfImages() throws DocumentException {
        Document document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        } catch (DocumentException |FileNotFoundException e) {
            e.printStackTrace();
        }
        document.open();
        document.newPage();


        Paragraph pTitle = new Paragraph(title, new Font(Font.FontFamily.HELVETICA,30,Font.BOLD, BaseColor.BLACK));
        pTitle.setAlignment(Element.ALIGN_CENTER);
        Paragraph pAuthor = new Paragraph(author,new Font(Font.FontFamily.HELVETICA,20,Font.NORMAL, BaseColor.BLACK));
        pAuthor.setAlignment(Element.ALIGN_CENTER);
        document.add(pTitle);
        document.add(pAuthor);
        document.newPage();
        Image img;
        int counter = 0;
        try {
            for (PdfImage pdfImage : pdfImages) {
                img = Image.getInstance(pdfImage.getUrl());
                if (counter % 2 == 0)
                    document.newPage();
                document.getPageSize();
                //img.setAbsolutePosition(0, 0);
//                float w = PageSize.A4.getWidth() - document.leftMargin() - document.rightMargin();
//                List tabstops = new ArrayList();
//                tabstops.add(new TabStop(w / 2, TabAlignment.CENTER));
//                tabstops.add(new TabStop(w, TabAlignment.LEFT));
//                Paragraph p = new Paragraph();
//                p.addTabStops(tabstops);
//                p.add(new Tab()).add(scaleImageToPage(img, PageSize.A4, marginLeft, marginRight, marginTop, marginBottom)).add(new Tab());
//                document.add(p);
                img.setAlignment(Element.ALIGN_CENTER);
                document.add(scaleImageToPage(img, PageSize.A4, marginLeft, marginRight, marginTop, marginBottom));

                Paragraph description = new Paragraph("Image " + (counter+1) + ": "+pdfImage.getDescription(), new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK));
                description.setAlignment(Element.ALIGN_CENTER);
                document.add(description);
                counter++;
            }
        } catch (DocumentException | IOException e){
            e.printStackTrace();
        }
        document.close();
    }

    void generatePdfFromUrls(String dest) throws DocumentException {
        Document document = new Document(PageSize.A4, marginLeft, marginRight, marginTop, marginBottom);
        Image img = null;

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(dest));
        } catch (DocumentException |FileNotFoundException e) {
            e.printStackTrace();
        }
//        try {
//            generateTitlePage(document);
//
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
        document.open();
        document.newPage();

        /*
           insert title

         */
        Paragraph title = new Paragraph("Dokumentacja rowerow", new Font(Font.FontFamily.TIMES_ROMAN,30,Font.BOLD, BaseColor.BLACK));
        title.setAlignment(Element.ALIGN_CENTER);
        Paragraph author = new Paragraph("Jacek Gosztyla",new Font(Font.FontFamily.TIMES_ROMAN,20,Font.NORMAL, BaseColor.BLACK));
        author.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(author);
        document.newPage();

        int counter = 0;
        try {
            for (String image : urls) {
                img = Image.getInstance(image);
                if (counter % 2 == 0)
                    document.newPage();
                document.getPageSize();
                //img.setAbsolutePosition(0, 0);
//                float w = PageSize.A4.getWidth() - document.leftMargin() - document.rightMargin();
//                List tabstops = new ArrayList();
//                tabstops.add(new TabStop(w / 2, TabAlignment.CENTER));
//                tabstops.add(new TabStop(w, TabAlignment.LEFT));
//                Paragraph p = new Paragraph();
//                p.addTabStops(tabstops);
//                p.add(new Tab()).add(scaleImageToPage(img, PageSize.A4, marginLeft, marginRight, marginTop, marginBottom)).add(new Tab());
//                document.add(p);
                img.setAlignment(Element.ALIGN_CENTER);
                document.add(scaleImageToPage(img, PageSize.A4, marginLeft, marginRight, marginTop, marginBottom));
                counter++;
            }
        } catch (DocumentException | IOException e){
            e.printStackTrace();
        }
        document.close();
    }

    Image scaleImageToPage(Image image, Rectangle pagesize, float marginLeft, float marginRight, float marginTop, float marginBottom){
        float leftHeight = pagesize.getHeight() - marginTop - marginBottom;
        float leftWidth = pagesize.getWidth() - marginLeft - marginRight;
        float scale = 0.5f*leftHeight/image.getHeight()*100*0.8f;
        image.scalePercent(scale);
        return image;
    }




}
