package cz.vodnikovo.tools.files.disk;

public enum EFileFormats {
    PDF,PDB,EPUB,MOBI;

    public static EFileFormats getFromString(String stringValue){
        for(EFileFormats format : EFileFormats.values()){
            if (format.toString().equalsIgnoreCase(stringValue)){
                return format;
            }
        }
        //TODO fix not supported
        return null;
    }
}


