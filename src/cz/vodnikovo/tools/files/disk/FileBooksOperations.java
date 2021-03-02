package cz.vodnikovo.tools.files.disk;

import cz.vodnikovo.utils.FileObjectUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for file operations, assuming the files are ebooks already
 */
public final class FileBooksOperations {
    private static final Character EXPECTED_AUTHORTITLE_SEPARATOR = '-';
    private static final Character EXPECTED_AUTHORNAME_SEPARATOR = ',';
    private static final String WORDLISTPATHBASE ="src\\cz\\vodnikovo\\resources\\3rdparty\\names-list.txt";

    /**
     * Filter potential ebooks / file formats
     * @param sourceData
     * @param matching
     * @return
     */
    public static File[] filterEbooksFormats(File[] sourceData,boolean matching){
        return FileLoaderOperations.getFilteredFileListByFormat(sourceData,EFileEbooksFormats.values(),matching);
    }

    /**
     * Retrieve files - book with potentially missing authors
      * @param source
     * @return
     */
    public static File[] filterPotentialMissingAuthors(File[] source){
        List<File> retList  = new ArrayList<>();

        if(source != null && source.length > 0){
            for(File f : source){
                if(isAuthorMissingCandidate(f)){
                    retList.add(f);
                }
            }
        }

        return FileObjectUtils.getFileArrayFromList(retList);
    }

    /**
     * Try to determine if the filename is missing an author
     * @param f
     * @return
     */
    private static boolean isAuthorMissingCandidate(File f){
        if(f.isFile() && f.exists()){

            if(f.getName().split(EXPECTED_AUTHORTITLE_SEPARATOR.toString()).length == 1){
                //no dash
                return true;
            }

            if(!f.getName().split(EXPECTED_AUTHORTITLE_SEPARATOR.toString())[0].contains(EXPECTED_AUTHORNAME_SEPARATOR.toString())){
                //no comma
                return true;
            }

            //author expected
            String firstToken = f.getName().split(EXPECTED_AUTHORTITLE_SEPARATOR.toString())[0];
            if(!isContainingWordlistName(firstToken)){
                return true;
            }

        }

        return false;
    }

    /**
     * Iterate over the wordlist of the names and try to find the name within the given text
     * @param lookAt
     * @return
     */
    //https://www.javatpoint.com/how-to-read-file-line-by-line-in-java
    private static boolean isContainingWordlistName(String lookAt) {
        try{
            File file = new File(WORDLISTPATHBASE);    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if(lookAt.contains(line)){
                    return true;
                }
            }
        }catch(IOException e)
        {
            e.printStackTrace();
            //TODO file reading failed
            return true;
        }
        return false;
    }

    //incorrect author format
    public static File[] filterPotentiallyIncorrectAuthorFormat(File[] source){
        List<File> retList  = new ArrayList<>();

        if(source != null && source.length > 0){
            for(File f : source){
                if(isAuthorInvalidFormatCandidate(f)){
                    retList.add(f);
                }
            }
        }

        return FileObjectUtils.getFileArrayFromList(retList);
    }

    public static File[] getContainDiacritics(File[] in){
        List<File> retList  = new ArrayList<>();

        if(in != null && in.length > 0){
            for(File f : in){
                if(isFileNameContainingDiacritics(f)){
                    retList.add(f);
                }
            }
        }

        return FileObjectUtils.getFileArrayFromList(retList);

    }

    /**
     * Will mark potential candidate with author in invalid format
     * @param f
     * @return
     */
    private static boolean isAuthorInvalidFormatCandidate(File f){
        String fName = f.getName();
        String[] tokens = fName.split(EXPECTED_AUTHORTITLE_SEPARATOR.toString());
        if(tokens == null || tokens.length < 2 || tokens[0].split(EXPECTED_AUTHORNAME_SEPARATOR.toString()).length < 2){
            return true;
        }

        String separatorWrappedBySpaces = " " + EXPECTED_AUTHORNAME_SEPARATOR + " ";
        if(tokens[0].split(separatorWrappedBySpaces).length < 2){
            return true;
        }

        return false;
    }

    private static boolean isFileNameContainingDiacritics(File f){
        if(f.exists() && f.isFile()){
            String name = f.getName();

            EDiacriticsChars[] invalidChars = EDiacriticsChars.values();
            for(EDiacriticsChars c : invalidChars){
                if(name.contains(c.name())){
                    return true;
                }
            }
        }

        return false;
    }
    //diacritics
    //incorrect format - underscores, 2 followed spaces
    //incorrect format - minor - space around dash,etc..



}
