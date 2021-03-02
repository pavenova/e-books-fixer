package cz.vodnikovo.tools.files.disk;

import cz.vodnikovo.utils.FileObjectUtils;

import java.io.File;
import java.util.*;

/**
 * Class for operations with files on the disk
 */
public final class FileLoaderOperations {

    /**
     * Obtain the files within the specified location
     * @param path location for content fetch
     * @param recursive
     * @return array of listed files within the given location or tree
     */
    public static File[] getFolderFiles(String path, boolean recursive) {

        if (path != null && !path.isBlank()) {
            File[] content = getFolderContent(new File(path));
            File[] contentFiles = filterFilesByType(content,ESysFileType.FILE);

            if (recursive) {
                File[] foldersRemaining = filterFilesByType(content,ESysFileType.FOLDER);

                if (foldersRemaining.length > 0){
                    List<File> foldersRemainingList = FileObjectUtils.getFileListFromArray(foldersRemaining);

                    List<File> contentFilesList = new ArrayList<>();
                    if (contentFiles.length>0){
                        contentFilesList = FileObjectUtils.getFileListFromArray(contentFiles);
                    }

                    int counter= 0;
                    do{
                        File[] currentChildContent = getFolderContent(foldersRemainingList.get(counter));
                        File[] currentChildFiles  = filterFilesByType(currentChildContent,ESysFileType.FILE);
                        File[] currentChildFolders  = filterFilesByType(currentChildContent,ESysFileType.FOLDER);

                        contentFilesList = mergeFileListArray(contentFilesList,currentChildFiles);
                        foldersRemainingList = mergeFileListArray(foldersRemainingList,currentChildFolders);

                        counter++;
                    }while(counter <= foldersRemainingList.size()-1);

                    contentFiles = FileObjectUtils.getFileArrayFromList(contentFilesList);
                }

            }

            return contentFiles;

        } else {
            //TODO location has not been passed
            return new File[]{};
        }
    }

    /**
     * Help method to merge array with existing list and returning the new list containing all the elements
     * @param inList
     * @param inArray
     * @return
     */
    private static List<File> mergeFileListArray(List<File> inList, File[] inArray){
        List<File> retList = new ArrayList<>();

        //copy List - immutable
        if(inList != null && inList.size() > 0){
            for (File file : inList) {
                retList.add(file);
            }
        }

        //add arrays elements
        if (inArray != null && inArray.length > 0){
            for(File f : inArray){
                retList.add(f);
            }
        }

        return retList;
    }

    /***
     * Will filter existing array by file type- folders or files
     * @param input
     * @param type
     * @return
     */
    private static File[] filterFilesByType(File[] input, ESysFileType type){
        List<File> retList = new ArrayList<>();

        for (File file : input){
            if (type.equals(ESysFileType.FILE) && file.isFile()){
                retList.add(file);
            }else if(type.equals(ESysFileType.FOLDER) && file.isDirectory()){
                retList.add(file);
            }
        }

        return FileObjectUtils.getFileArrayFromList(retList);
    }

    /**
     * Return unfiltered content of the given directory
     * @param fileLoc
     * @return
     */
    private static File[] getFolderContent(File fileLoc){
        File[] ret = new File[]{};

        if (fileLoc.isDirectory()){
            ret = fileLoc.listFiles();
        }
        //TODO handle invalid location
        //TODO handle location is not a folder

        return ret;

    }

    /**
     * Method to filter existing array due the parameters - include / only matching or everything else, just not given matching extensions
     * @param inputContent
     * @param fileExtensions
     * @param filterMatching
     * @return
     */
    public static File[] getFilteredFileListByFormat(File[] inputContent, EFileEbooksFormats[] fileExtensions, boolean filterMatching){
        List<File> returnList  = new ArrayList<>();
        List<EFileEbooksFormats> formatsList = Arrays.asList(fileExtensions.clone());

        for (File file: inputContent) {
            String fileExtension = FileObjectUtils.getFileExtension(file);

            if (fileExtension != null){
                EFileEbooksFormats fileFormatExtension = EFileEbooksFormats.getFromString(fileExtension);

                if(filterMatching && formatsList.contains(fileFormatExtension)){
                    returnList.add(file);
                }else if (!filterMatching && !formatsList.contains((fileFormatExtension))){
                    returnList.add(file);
                }
            }

        }
        return FileObjectUtils.getFileArrayFromList(returnList);
    }

}
