package cz.vodnikovo.tools.files.disk;

public enum EDiacriticsChars {
    ě('e'),
    š('s'),
    č('c'),
    ř('r'),
    ž('z'),
    ý('y'),
    á('a'),
    í('i'),
    é('e'),
    ú('u'),
    ů('u');

    //private field for replacement - non diacritics char
    private char replacementChar;


    EDiacriticsChars(char replacementChar) {
        this.replacementChar = replacementChar;
    }

    /**
     * Obtain replacement - non diacritics character
     * @param value
     * @return
     */
    public static char getReplacementChar(EDiacriticsChars value){
        return value.replacementChar;
    }

    /**
     * Get Replacement char base on inchar, if not supported, return original value
     * @param inChar
     * @return
     */
    public static char getReplacementChar(char inChar){
        try {
            EDiacriticsChars found = EDiacriticsChars.valueOf(Character.toString(inChar));
            if(found != null){
                return found.replacementChar;
            }else {
                return inChar;
            }
        }catch (Exception e){
            return inChar;
        }
    }


}
