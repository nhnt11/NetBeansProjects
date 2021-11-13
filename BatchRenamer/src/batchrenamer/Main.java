package batchrenamer;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        if (args.length != 2) {
            out("Usage: java -jar BatchRenamer.jar <package name> <input directory>");
            System.exit(1);
        }
        String packageName = args[0];
        File inputDir = new File(args[1]);
        if (!inputDir.exists()) {
            out("Input directory \"" + args[1] + "\" does not exist. Aborting.");
            System.exit(0);
        }
        out("Prefixing \"" + packageName +"\" to all files in " + args[1]);
        File files[] = inputDir.listFiles();
        for (File file : files) {
            try {
                File newFile = new File(inputDir.getCanonicalPath() + System.getProperty("file.separator") + packageName + "_" + file.getName());
                file.renameTo(newFile);
            } catch (Exception e) {
                out("Something went wrong! Please send the following to nhnt11:");
                e.printStackTrace(System.out);
            }
        }
    }

    private static void out(String s) {
        System.out.println(s);
    }

}
