package cz.vodnikovo;

import cz.vodnikovo.tools.files.disk.EFileEbooksFormats;

import java.io.File;

public class Main {

    public static void main(String[] args) {
	    //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně";
        String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test";
        String splitter = " ------------------------------- ";

        //LOAD data - files
        File[] sourceData = EBookConverter.loadFilesWithinTheFolder(pathLoc,true);
        System.out.println("Found files:");
        printFileArr(sourceData);
        printEndMessage("Found documents");

        System.out.println(splitter);

        //FILTER doc, docx
        EFileEbooksFormats[] wordFormats = {EFileEbooksFormats.DOCX,EFileEbooksFormats.DOC};
        File[] wordArray = EBookConverter.filterFilesByExtensionFormat(sourceData,wordFormats,true);
        System.out.println("Filtered word documents (doc, docx):");
        printFileArr(wordArray);
        printEndMessage("Filtered word documents (doc, docx)");

        System.out.println(splitter);

        //transform doc, docx to PDF
        File[] wordPDFConverted= EBookConverter.convertDocToPDF(wordArray);
        File[] deletedFiles = EBookConverter.deleteFiles(wordPDFConverted);

        System.out.println("deleted files: ");
        printFileArr(deletedFiles);
        printEndMessage("deleted files");
    }

    private static void printFileArr(File[] in){
        for(File f : in){
            System.out.println(f.getAbsolutePath());
        }
    }

    private static void printEndMessage(String text){
        System.out.println("== " + text + " END ==");
    }
}
