package cz.vodnikovo;

import cz.vodnikovo.tools.files.disk.FileBooksOperations;
import cz.vodnikovo.tools.files.disk.FileLoaderOperations;

import java.io.File;

public class Main {

    public static void main(String[] args) {
	    //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně";
        //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně\\P\\Pratchett, Terry";
        /*
        "C:\Users\xxxvo\OneDrive\Desktop\test\Procházka, Jiří Walker - Jackův konvoj.doc"
        * */

        String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test";

        String pathLocDOC = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test\\Procházka, Jiří Walker - Jackův konvoj.doc";
        String pathLocPDFout = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test\\Procházka, Jiří Walker - Jackův konvoj.pdf";

        String pathLocDOCX = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test\\Procházka, Jiří Walker - Jackův konvoj.docx";
        String pathLocPDFXout = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test\\Procházka, Jiří Walker - Jackův konvoj.x.pdf";

        String pathLocTXT = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test\\foooo.txt";
        String pathLocPDFTXTout = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test\\foooo.txt.pdf";



        File[] list;
        //list = FileOperations.getFolderContentList(pathLoc,false);
        //list = FileOperations.getFolderFiles(pathLoc,false);
        //list = FileLoaderOperations.getFolderFiles(pathLoc,true);

        //EFileFormats[] requestedFormats = {EFileFormats.PDB};
        //list = FileOperations.getFilteredFileListByFormat(list,requestedFormats,true);
        //list = FileOperations.getFilteredFileListByFormat(list,requestedFormats,false);

        //test
        /*

        for (File file: list) {
            System.out.println(file.getName() + " , at: " + file.getAbsolutePath());
        }*/

        /*
        WordDocumentsConvertor conv = new WordDocumentsConvertor();
        conv.convertDocToPDF(pathLocDOC,pathLocPDFout);
        conv.convertDocToPDF(pathLocDOCX,pathLocPDFXout);
        */

        //TxtConvertor.convertTxtToPDF(pathLocTXT,pathLocPDFTXTout);

        /*
        File[] content = FileLoaderOperations.getFolderFiles(pathLoc,true);
        String[] extensions = FileLoaderOperations.getUniqueExtensions(content);
        content = FileBooksOperations.filterEbooksFormats(content,true);
        content = FileBooksOperations.filterPotentialMissingAuthors(content);
        for(File f : content){
            System.out.println("test!");
        }
        /**/


    }
}
