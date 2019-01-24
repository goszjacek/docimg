import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class UserReader {
    TextIO textIO;

    public UserReader() {
        this.textIO = TextIoFactory.getTextIO();
    }

    String action(){
        return textIO.newStringInputReader().read("Provide action (createAuthor/createTitle/createImage/changeOrder/createDestination/generatePdf)");
    }

    String getUrls(){
        return textIO.newStringInputReader()
                .read("Provide url to file with photos urls");
        ///media/jacek/D/d/Dokumenty/studia/po/docimg/urls
    }

    String getTitle(){
        return textIO.newStringInputReader()
                .withDefaultValue("Document title")
                .read("Provide document title:");
    }

    String getAuthor(){
        return textIO.newStringInputReader()
                .withDefaultValue("Document title")
                .read("Provide author of document:");
    }

    PdfImage getPdfImage(){
        String url = textIO.newStringInputReader()
                .read("Provide new image url:");
        String desc = textIO.newStringInputReader().read("Provide new image description:");
        return new PdfImage(desc,url);
    }

    PdfImage getPdfImageFromDisk(){
        String url = new String();
        new FileChooserTest( url).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
            }
        });
        System.out.println("reader: "+url);
        String desc = textIO.newStringInputReader().read("Privide image description");
        return new PdfImage(desc,url);
    }

    Integer getImageIdToChange(int s){
        return textIO.newIntInputReader()
                .withMaxVal(s-1)
                .withMinVal(0)
                .read("Provide image id, which  order you want to change:");
    }
    Integer getImageIdToPlaceAfter(int s){
        return textIO.newIntInputReader()
                .withMaxVal(s-1)
                .withMinVal(0)
                .read("Provide image id, after that you want to place above image:");
    }

    String getDestination(){
        return textIO.newStringInputReader()
                .withDefaultValue("results/text/docimg_document.pdf")
                .read("Provide a destination for the generated document.");
    }


}
