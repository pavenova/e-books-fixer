package cz.vodnikovo;

import cz.vodnikovo.tools.files.disk.EFileEbooksFormats;
import cz.vodnikovo.tools.files.disk.FileLoaderOperations;
import cz.vodnikovo.tools.files.docs.TxtConvertor;
import cz.vodnikovo.tools.files.docs.WordDocumentsConvertor;
import cz.vodnikovo.utils.FileObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class BookConverter {
    private static final String  DOCEXTENSION = ".doc";
    private static final String  DOCXEXTENSION = ".docx";

    private static final String  TXTEXTENSION = ".txt";

    private static final String PDFEXTENSION = ".pdf";

    public static File[]  loadFilesWithinTheFolder(String path, boolean recursive){
        return FileLoaderOperations.getFolderFiles(path, recursive);
    }

    public static File[] filterFilesByExtensionFormat(File[] srcData, EFileEbooksFormats[] formats,boolean included){
        return FileLoaderOperations.getFilteredFileListByFormat(srcData,formats,included);
    }

    public static File[] convertDocToPDF(File[] source){
        List<File> converted = new ArrayList<>();

        for(File f : source){
            if(WordDocumentsConvertor.isSupportedWordDoc(f.getAbsolutePath())){
                String destPath = getDestPdfPath(f,EBookSWFormat.WORD);

                WordDocumentsConvertor.convertDocToPDF(f.getAbsolutePath(),destPath);
                converted.add(f);
            }
        }

        return FileObjectUtils.getFileArrayFromList(converted);
    }

    public static File[] convertTxtToPDF(File[] in){
        List<File> converted = new ArrayList<>();

        for (File f : in){
            System.out.println("Converting: " + f.getAbsolutePath());
            if(TxtConvertor.convertTxtToPDF(f.getAbsolutePath(),getDestPdfPath(f,EBookSWFormat.TXT))){
                converted.add(f);
            }
        }

        return FileObjectUtils.getFileArrayFromList(converted);
    }

    public static File[] deleteFiles(File[] list){
        List<File> deleted = new ArrayList<>();

        for(File f : list){
            if(FileObjectUtils.deleteFile(f)){
                deleted.add(f);
            }
        }

        return FileObjectUtils.getFileArrayFromList(deleted);
    }

    /**
     * Retrieve the destination path (incl. extension) for PDF
     * @param f source file
     * @return
     */
    private static String getDestPdfPath(File f,EBookSWFormat sourceFormat) {
        String fileAbsPath  = f.getAbsolutePath();

        String foundExtension = "";

        switch (sourceFormat){
            case WORD:
                foundExtension = getFindWordExtension(fileAbsPath);
                return  fileAbsPath.replace(foundExtension, BookConverter.PDFEXTENSION.toLowerCase());
            case TXT:
                foundExtension= getFindTxtExtension(fileAbsPath);
                return fileAbsPath.replace(foundExtension,BookConverter.PDFEXTENSION);
        }

        return null;
    }

    private static String getFindTxtExtension(String fileAbsolutePath){
        if(fileAbsolutePath.endsWith(TXTEXTENSION.toLowerCase()))
            return TXTEXTENSION.toLowerCase();
        else if(fileAbsolutePath.endsWith(TXTEXTENSION.toUpperCase()))
            return TXTEXTENSION.toUpperCase();

        //TODO no txt, TXT found
        return null;

    }
    private static String getFindWordExtension(String fileAbsolutePath){
        if(fileAbsolutePath.endsWith(BookConverter.DOCEXTENSION.toLowerCase())){
            return DOCEXTENSION.toLowerCase();
        }else if(fileAbsolutePath.endsWith(DOCEXTENSION.toUpperCase())){
            return DOCEXTENSION.toUpperCase();
        }else if(fileAbsolutePath.contains(DOCXEXTENSION.toLowerCase())){
            return  DOCXEXTENSION.toLowerCase();
        }else if(fileAbsolutePath.contains(DOCXEXTENSION.toUpperCase())){
            return  DOCXEXTENSION.toUpperCase();
        }

        //TODO no doc docx found
        return null;
    }


}
