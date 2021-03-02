package cz.vodnikovo;

import cz.vodnikovo.tools.files.disk.EFileEbooksFormats;
import cz.vodnikovo.tools.files.disk.FileBooksOperations;
import cz.vodnikovo.tools.files.disk.FileLoaderOperations;
import cz.vodnikovo.tools.files.docs.WordDocumentsConvertor;
import cz.vodnikovo.utils.FileObjectUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class EBookConverter {
    private static final String  DOCEXTENSION = ".doc";
    private static final String  DOCXEXTENSION = ".docx";

    private static final String  PDFXEXTENSION = ".pdf";

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
                String destPath = getDestPdfPath(f);

                WordDocumentsConvertor.convertDocToPDF(f.getAbsolutePath(),destPath);
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
    private static String getDestPdfPath(File f) {
        String fileAbsPath  = f.getAbsolutePath();
        String destPdfPath = f.getAbsolutePath();

        String foundExtension = getFindWordExtension(fileAbsPath);
        destPdfPath = fileAbsPath.replace(foundExtension,EBookConverter.PDFXEXTENSION.toLowerCase());

        return destPdfPath;
    }

    private static String getFindWordExtension(String fileAbsolutePath){
        if(fileAbsolutePath.endsWith(EBookConverter.DOCEXTENSION.toLowerCase())){
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
