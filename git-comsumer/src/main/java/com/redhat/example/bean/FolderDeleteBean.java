package com.redhat.example.bean;

import java.io.File;
import java.io.IOException;

// Spring
import org.springframework.stereotype.Component;

@Component
public class FolderDeleteBean {

    // Delete Folder
    public String deleteFolder(String folderpath) {
        File parentDirectory = new File(folderpath);
        if (parentDirectory.exists() && parentDirectory.isDirectory()) {
            File[] subDirectories = parentDirectory.listFiles();
            if (subDirectories != null) {
                for (File subDirectory : subDirectories) {
                    deleteDirectory(subDirectory);
                }
            }
        }
        return "Folder deleted successfully";
    }
    
    // フォルダとその中身を再帰的に削除するメソッド
    private static void deleteDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    deleteDirectory(file);
                }
            }
        }
        directory.delete();
    }
}
