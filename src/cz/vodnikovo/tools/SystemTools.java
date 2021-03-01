package cz.vodnikovo.tools;

/**
 * Class for system operations
 */
public final class SystemTools {

    /**
     * Return true if running at windows, otherwise false
     * @return
     */
    public static boolean isWindowsPlatform(){
        return ESysType.WINDOWS.equals(getSystemType());
    }

    /**
     * determine the OS type
     * @return
     */
    private static ESysType getSystemType(){
        String sysName  = System.getProperty("os.name");
        if (sysName.startsWith("Windows")){
            return ESysType.WINDOWS;
        }

        return null;
    }


}
