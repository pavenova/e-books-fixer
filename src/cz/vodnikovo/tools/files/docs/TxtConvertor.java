package cz.vodnikovo.tools.files.docs;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class for txt files operations
 * sourced from https://stackoverflow.com/questions/4696463/convert-text-into-pdf
 */
public final class TxtConvertor {

    /**
     * Allows conversion of txt to PDF file
     */
    public static void convertTxtToPDF(String source, String dest){
        BufferedReader input = null;
        Document output = null;

        try {
            input = new BufferedReader (new FileReader(source));

            output = new Document(PageSize.LETTER, 40, 40, 40, 40);
            PdfWriter.getInstance(output, new FileOutputStream(dest));

            System.out.println("Converting : " + source);
            writePDFfile(input, output);
            input.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Write PDF file to disk
     * @param input
     * @param output
     * @throws IOException
     * @throws DocumentException
     */
    private static void writePDFfile(BufferedReader input, Document output) throws IOException, DocumentException {
        output.open();

        String line = "";
        while(null != (line = input.readLine())) {
            //System.out.println(line);
            Paragraph p = new Paragraph(line);
            p.setAlignment(Element.ALIGN_JUSTIFIED);
            output.add(p);
        }
        //System.out.println("Done.");
        output.close();
    }

}
