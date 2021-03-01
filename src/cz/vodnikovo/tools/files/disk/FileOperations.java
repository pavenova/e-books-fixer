package cz.vodnikovo.tools.files.disk;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class for operations with files on the disk
 */
public final class FileOperations {
    private static final String EXTENSION_SEPARATOR = ".";

    /**
     * Obtain the files within the specified location
     * @param path location for content fetch
     * @param recursive
     * @return arraz of listed files within the given location or tree
     */
    public static File[] getFolderFiles(String path, boolean recursive) {

        if (path != null && !path.isBlank()) {
            File[] content = getFolderContent(new File(path));
            File[] contentFiles = filterFilesByType(content,ESysFileType.FILE);

            if (recursive) {
                File[] foldersRemaining = filterFilesByType(content,ESysFileType.FOLDER);

                if (foldersRemaining.length > 0){
                    List<File> foldersRemainingList = getFileListFromArray(foldersRemaining);

                    List<File> contentFilesList = new ArrayList<>();
                    if (contentFiles.length>0){
                        contentFilesList = getFileListFromArray(contentFiles);
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

        return getFileArrayFromList(retList);
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
    public static File[] getFilteredFileListByFormat(File[] inputContent,EFileFormats[] fileExtensions,boolean filterMatching){
        List<File> returnList  = new ArrayList<>();
        List<EFileFormats> formatsList = Arrays.asList(fileExtensions.clone());

        for (File file: inputContent) {
            String fileExtension = getFileExtension(file);

            if (fileExtension != null){
                EFileFormats fileFormatExtension = EFileFormats.getFromString(fileExtension);

                if(filterMatching && formatsList.contains(fileFormatExtension)){
                    returnList.add(file);
                }else if (!filterMatching && !formatsList.contains((fileFormatExtension))){
                    returnList.add(file);
                }
            }

        }
        return getFileArrayFromList(returnList);
    }

    /***
     * Retrieve the extension of the given file
     * @param file
     * @return
     */
    private static String getFileExtension(File file){
        int separatorIndex = file.getName().indexOf(EXTENSION_SEPARATOR);

        if(file.isFile() && separatorIndex > 0){
            //from separator till end
            return file.getName().substring(separatorIndex+1);
        }else{
            //TODO fix if no extension
            //TODO fix is not file
            //TODO param null
            return null;
        }

    }

    /**
     * Transform list to array
     * @param in
     * @return
     */
    private static File[] getFileArrayFromList(List<File> in){
        return in.toArray(new File[in.size()]);
    }

    /**
     * Transform array to list
     */
    private static List<File> getFileListFromArray(File[] in){
        List<File> ret = new ArrayList<>();
        if (in != null && in.length > 0){
            ret = Arrays.asList(in.clone());
        }
        return ret;
    }
}
