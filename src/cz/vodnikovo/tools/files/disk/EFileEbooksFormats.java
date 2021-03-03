package cz.vodnikovo.tools.files.disk;

public enum EFileEbooksFormats {
    PDF,PDB,EPUB,MOBI,DOC,DOCX,TXT,RTF;

    public static EFileEbooksFormats getFromString(String stringValue){
        for(EFileEbooksFormats format : EFileEbooksFormats.values()){
            if (format.toString().equalsIgnoreCase(stringValue)){
                return format;
            }
        }
        //not found
        return null;
    }


}


