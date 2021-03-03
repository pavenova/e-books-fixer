package cz.vodnikovo;

import cz.vodnikovo.tools.files.disk.EFileEbooksFormats;
import cz.vodnikovo.tools.files.docs.WordDocumentsConvertor;
import cz.vodnikovo.utils.FileObjectUtils;

import java.io.File;

public class Main {
    private static final boolean VERBOSE = true;

    private static final boolean CONVERTWORD = true;
    private static final boolean CONVERTTXT = true;

    public static void main(String[] args) {
        //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně";
        String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test";

        String logBase = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test-log";

           //LOAD data - files
        File[] sourceData = BookConverter.loadFilesWithinTheFolder(pathLoc,true);
        BookConverter.logFileArrayToFile(logBase+"\\data-base.txt",sourceData);

        if(CONVERTWORD){
            //FILTER doc, docx
            EFileEbooksFormats[] wordFormats = {EFileEbooksFormats.DOCX,EFileEbooksFormats.DOC};

            File[] wordArray = BookConverter.filterFilesByExtensionFormat(sourceData,wordFormats,true);
            BookConverter.logFileArrayToFile(logBase+"\\data-word.txt",wordArray);

            //transform doc, docx to PDF
            File[] wordPDFConverted= BookConverter.convertDocToPDF(wordArray);
            File[] deletedFiles = BookConverter.deleteFiles(wordPDFConverted);
            BookConverter.logFileArrayToFile(logBase+"\\data-word-deleted.txt",deletedFiles);
        }

        if(CONVERTTXT){
            EFileEbooksFormats[] txtFormats = {EFileEbooksFormats.TXT};
            //filter txt files
            File[] txtArray = BookConverter.filterFilesByExtensionFormat(sourceData,txtFormats,true);
            BookConverter.logFileArrayToFile(logBase+"\\data-txt.txt",txtArray);

            //transform txt to PDF if possible
            File[] txtPDFConverted = BookConverter.convertTxtToPDF(txtArray);
            File[] deletedFiles = BookConverter.deleteFiles(txtPDFConverted);
            BookConverter.logFileArrayToFile(logBase+"\\data-txt-deleted.txt",deletedFiles);
        }



    }



}
