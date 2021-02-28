package cz.vodnikovo;

import cz.vodnikovo.tools.EFileFormats;
import cz.vodnikovo.tools.FileOperations;

import java.io.File;

public class Main {

    public static void main(String[] args) {
	    //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně";
        //String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\E-books\\Knihy abecedně\\P\\Pratchett, Terry";
        String pathLoc = "C:\\Users\\xxxvo\\OneDrive\\Desktop\\test";

        File[] list;
        //list = FileOperations.getFolderContentList(pathLoc,false);
        //list = FileOperations.getFolderFiles(pathLoc,false);
        list = FileOperations.getFolderFiles(pathLoc,true);

        EFileFormats[] requestedFormats = {EFileFormats.PDB};
        //list = FileOperations.getFilteredFileListByFormat(list,requestedFormats,true);
        //list = FileOperations.getFilteredFileListByFormat(list,requestedFormats,false);

        //test
        for (File file: list) {
            System.out.println(file.getName() + " , at: " + file.getAbsolutePath());
        }

    }
}
