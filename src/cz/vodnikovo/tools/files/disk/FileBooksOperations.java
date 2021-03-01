package cz.vodnikovo.tools.files.disk;

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
     * Filter potencial ebooks / file formats
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
                if(isMissingAuthorCandidate(f)){
                    retList.add(f);
                }
            }
        }

        return FileLoaderOperations.getFileArrayFromList(retList);
    }

    /**
     * Try to determine if the filename is missing an author
     * @param f
     * @return
     */
    private static boolean isMissingAuthorCandidate(File f){
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
    //diacritics
    //incorrect format - underscores, 2 followed spaces
    //incorrect format - minor - space around dash,etc..



}
