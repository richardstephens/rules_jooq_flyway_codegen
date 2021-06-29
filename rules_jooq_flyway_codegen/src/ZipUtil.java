package dev.richst.jooq_bazel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {
    /**
     * This method zips the directory
     *
     * @param dir
     * @param zipDirName
     */
    public static void zipDirectory(File dir, String zipDirName) throws IOException {
        List<String> filesListInDir = traversePath(dir);
        // now zip files one by one
        // create ZipOutputStream to write to the zip file
        try (FileOutputStream fos = new FileOutputStream(zipDirName);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            for (String filePath : filesListInDir) {
                ZipEntry ze = new ZipEntry(filePath.substring(dir.getAbsolutePath().length() + 1));
                zos.putNextEntry(ze);
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                }
                zos.closeEntry();
            }
        }
    }

    private static List<String> traversePath(File dir) {
        List<String> result = new ArrayList<>();
        Stack<File> stack = new Stack<>();
        stack.push(dir);
        while (!stack.empty()) {
            File[] files = stack.pop().listFiles();
            for (File child : files) {
                if (child.isFile()) {
                    result.add(child.getAbsolutePath());
                } else if (child.isDirectory()) {
                    stack.push(child);
                }
            }
        }
        return result;
    }
}
