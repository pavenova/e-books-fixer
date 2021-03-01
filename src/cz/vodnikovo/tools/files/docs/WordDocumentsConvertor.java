package cz.vodnikovo.tools.files.docs;

import cz.vodnikovo.tools.SystemTools;
import java.io.File;

/***
 * Class for operations with word documents - doc and docx
 */
public class WordDocumentsConvertor {
    private final static String OFFICE2PDFLOCATION = "src\\cz\\vodnikovo\\resources\\3rdparty\\OfficeToPDF.exe";
    private final static String CMDSTARTCOMMAND = "cmd /c";

    /**
     * Convert doc or docx to PDF
     * @param sourceLocation
     * @param destLocation
     */
    public void convertDocToPDF(String sourceLocation, String destLocation) {
        if (isSupportedWordDoc(sourceLocation)) {
            if (SystemTools.isWindowsPlatform()) {

                try {
                    // Execute transform command using inbuilt doc2pdf 3rd party app
                    Runtime rt = Runtime.getRuntime();
                    StringBuilder sb = getDocPDFConvertCommand(sourceLocation, destLocation);
                    System.out.println("Running conversion: " + sb.toString());
                    Process p = rt.exec(sb.toString());
                    p.waitFor();
                    System.out.println("Running conversion done");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }


    }

    private static StringBuilder getDocPDFConvertCommand(String sourceLocation, String destLocation) {
        StringBuilder sb = new StringBuilder(CMDSTARTCOMMAND);
        sb.append(" ");
        sb.append(OFFICE2PDFLOCATION);
        sb.append(" \"");
        sb.append(sourceLocation);
        sb.append("\" \"");
        sb.append(destLocation);
        sb.append("\"");
        return sb;
    }

    /**
     * Return if document is supported to transformation
     * @param path
     * @return
     */
    public static boolean isSupportedWordDoc(String path) {
        File source = new File(path);
        return (source.exists() && source.isFile() && (source.getName().endsWith("doc") || source.getName().endsWith("docx")));
    }

}