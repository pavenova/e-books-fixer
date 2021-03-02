package cz.vodnikovo;

import cz.vodnikovo.tools.files.disk.EFileEbooksFormats;
import cz.vodnikovo.utils.FileObjectUtils;

import java.io.File;

public class Main {
    private static final boolean VERBOSE = true;
    private static final boolean CONVERTWORDDOCS = true;
    private static final boolean CONVERTTXT = true;

    public static void main(String[] args) {
	    //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně";
        String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test";
        String splitter = " ------------------------------- ";

        //LOAD data - files
        File[] sourceData = BookConverter.loadFilesWithinTheFolder(pathLoc,true);
        System.out.println("Found files:");
        printFileArr(sourceData);
        printEndMessage("Found documents");

        System.out.println(splitter);

        if(CONVERTWORDDOCS){
            //FILTER doc, docx
            EFileEbooksFormats[] wordFormats = {EFileEbooksFormats.DOCX,EFileEbooksFormats.DOC};
            File[] wordArray = BookConverter.filterFilesByExtensionFormat(sourceData,wordFormats,true);
            System.out.println("Filtered word documents (doc, docx):");
            printFileArr(wordArray);
            printEndMessage("Filtered word documents (doc, docx)");

            System.out.println(splitter);

            //transform doc, docx to PDF
            File[] wordPDFConverted= BookConverter.convertDocToPDF(wordArray);
            File[] deletedFiles = BookConverter.deleteFiles(wordPDFConverted);

            //clean converted
            System.out.println("deleted files: ");
            printFileArr(deletedFiles);
            printEndMessage("deleted files");
        }

        if(CONVERTTXT){
            EFileEbooksFormats[] txtFormats = {EFileEbooksFormats.TXT};

            //filter txt files
            File[] txtArray = BookConverter.filterFilesByExtensionFormat(sourceData,txtFormats,true);

            System.out.println("Found TXTs: " );
            printFileArr(txtArray);
            printEndMessage("Found TXT");
            System.out.println(splitter);

            //transform txt to PDF if possible
            File[] txtPDFConverted = BookConverter.convertTxtToPDF(txtArray);
            File[] deletedFiles = BookConverter.deleteFiles(txtPDFConverted);

            System.out.println("Deleted (txt): ");
            printFileArr(deletedFiles);
            printEndMessage("Deleted (txt)");
        }



    }

    private static void printFileArr(File[] in){
        if(VERBOSE){
            FileObjectUtils.printFileArr(in);
        }
    }

    private static void printEndMessage(String text){
        System.out.println("== " + text + " END ==");
    }
}
