package cz.vodnikovo.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class FileObjectUtils {
    private static final String EXTENSION_SEPARATOR = ".";

    /**
     * Transform array to list
     */
    public static List<File> getFileListFromArray(File[] in){
        List<File> ret = new ArrayList<>();
        if (in != null && in.length > 0){
            ret = Arrays.asList(in.clone());
        }
        return ret;
    }

    /**
     * Transform list to array
     * @param in
     * @return
     */
    public static File[] getFileArrayFromList(List<File> in){
        return in.toArray(new File[in.size()]);
    }

    /***
     * Retrieve the extension of the given file
     * @param file
     * @return
     */
    public static String getFileExtension(File file){
        int separatorIndex = file.getName().lastIndexOf(EXTENSION_SEPARATOR);

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
     * Obtain unique file extensions within the given array
     * @param input
     * @return
     */
    public static String[] getUniqueExtensions(File[] input){
        List<String> uniqueList = new ArrayList<>();

        for(File f : input){
            String extension = getFileExtension(f);
            if(!uniqueList.contains(extension)){
                uniqueList.add(extension);
            }
        }
        Collections.sort(uniqueList);
        return uniqueList.toArray(new String[uniqueList.size()]);

    }

    /**
     * Delete the file if exists
     * @param f
     * @return true if deleted, false if not deleted from some reason
     */
    public static boolean deleteFile(File f){
        if(f.exists() && f.isFile()){
            System.out.println("deletion of: " + f.getAbsolutePath());
            f.delete();
            return true;
        }

        return false;
    }

    public static boolean isExistingFileNotEmpty(File f){
        Path fPath = Paths.get(f.getAbsolutePath());
        boolean nonEmpty = false;

        try {
            long bytesSize = Files.size(fPath);
            if(bytesSize > 0) nonEmpty=true;
        } catch (IOException e) {
            //e.printStackTrace();
            //TODO lets assume nonempty
        }

        return f.exists() && f.isFile();
    }

    public static boolean hasOneOfExtensions(File f, String[] allowedExtArray){
        for(String s : allowedExtArray){
            String fileExtension = getFileExtension(f);
            if(fileExtension.equalsIgnoreCase(s)){
                return true;
            }
        }

        return false;
    }

    public static void printFileArr(File[] in){
        for(File f : in){
            System.out.println(f.getAbsolutePath());
        }
    }

    public static boolean logArrayToFile(String location, File[] data){
        File target = new File(location);

        try {
            if(!target.exists()){
                target.createNewFile();
            }

            PrintWriter writer =  new PrintWriter(location, "UTF-8");
            for(File f : data){
                writer.write(f.getAbsolutePath() + "\n");
            }
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
