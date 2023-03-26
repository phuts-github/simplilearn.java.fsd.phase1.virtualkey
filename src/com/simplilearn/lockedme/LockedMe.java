package com.simplilearn.lockedme;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class LockedMe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printWelcomeMessage();
        File folder = new File(Constants.WORKING_DIRECTORY_PATH);
        File[] listOfFolderFiles = null;
        String selectedMainMenuOption = "";
        if (folder.exists()) {
//          loading initial file list. The list will be refreshed based on functions selected.
            listOfFolderFiles = retrieveFileList(folder,null);
        }
        else {
            printMissingWorkingDirectory();
            selectedMainMenuOption = Constants.Q_QUIT_APPLICATION;
        }
        while (!selectedMainMenuOption.equals(Constants.Q_QUIT_APPLICATION)) {
            printMainMenu();
            System.out.print(Constants.ENTER_MENU_CHOICE);
            selectedMainMenuOption = sc.nextLine().toUpperCase();
            switch (selectedMainMenuOption) {
                case Constants.R_RETRIEVE_FILE_LIST: {
                    listOfFolderFiles = retrieveFileList(folder, listOfFolderFiles);
                    printRetrievedFileList(listOfFolderFiles);
                    break;
                }
                case Constants.F_FILE_HANDLING: {
                    fileHandling(folder, listOfFolderFiles);
                    break;
                }
                case Constants.Q_QUIT_APPLICATION: {
                    closeApp();
                    break;
                }
                default: {
                    System.out.println(Constants.INVALID_CHOICE_ENTERED);
                }
            }
        }
    }
    private static void closeApp() {
        System.out.println(Constants.CLOSING_APP);
    }
    private static File[] retrieveFileList(File folder, File[] listOfFolderFiles) {
        listOfFolderFiles = folder.listFiles();
        if (listOfFolderFiles != null) {
            int countOfFilesFound = listOfFolderFiles.length;
            if (countOfFilesFound > 1) {
                Arrays.stream(listOfFolderFiles).sorted();
            }
            return listOfFolderFiles;
        } else {
            System.out.println(Constants.FOLDER_HAS_NO_FILES + folder);
        }
        return null;
    }
    private static void printRetrievedFileList(File[] listOfFolderFiles) {
        int countOfFilesFound = listOfFolderFiles.length;
        System.out.println(Constants.FOLDER_FILE_COUNT + countOfFilesFound);
        for (int i = 0; i < countOfFilesFound; i++) {
            if (listOfFolderFiles[i].isFile()) {
                System.out.println("\t" + (i+1) + Constants.FILE + "\t: " + listOfFolderFiles[i].getName());
            } else if (listOfFolderFiles[i].isDirectory()) {
                System.out.println("\t" + (i+1) + Constants.FOLDER + "\t: " + listOfFolderFiles[i].getName());
            }
        }
    }
    private static void printMissingWorkingDirectory() {
        for (String directoryMissing : Constants.WORKING_DIRECTORY_MISSING) {
            System.out.print(directoryMissing);
        }
        System.out.println();
    }
    private static void printWelcomeMessage() {
        for (String welcomeText : Constants.ARRAY_WELCOME_TEXT) {
            System.out.print(welcomeText);
        }
        System.out.println();
    }
    private static void printMainMenu() {
        System.out.println(Constants.MAIN_MENU_TEXT);
        for (String mainMenu : Constants.ARRAY_MAIN_MENU) {
            System.out.println(mainMenu);
        }
    }

    private static void printFileHandlingMenu() {
        System.out.println(Constants.FILE_HANDLING_MENU_TEXT);
        for (String fileHandlingMainMenu : Constants.ARRAY_FILE_HANDLING_MENU) {
            System.out.println(fileHandlingMainMenu);
        }
    }
    private static void fileHandling(File folder, File[] listOfFolderFiles) {
        Scanner sc = new Scanner(System.in);
        String selectedFileMenuOption = "";
        while (!selectedFileMenuOption.equals(Constants.M_MAIN_MENU)) {
            printFileHandlingMenu();
            System.out.print(Constants.ENTER_MENU_CHOICE);
            selectedFileMenuOption = sc.nextLine().toUpperCase();
            switch (selectedFileMenuOption) {
                case Constants.A_ADD_FILE: {
                    if (addFile(folder)) {
                        listOfFolderFiles = retrieveFileList(folder, listOfFolderFiles);
                    }
                    break;
                }
                case Constants.D_DELETE_FILE: {
                    if (deleteFile(folder)) {
                        listOfFolderFiles = retrieveFileList(folder, listOfFolderFiles);
                    }
                    break;
                }
                case Constants.S_SEARCH_FILE: {
                    searchFile(listOfFolderFiles);
                    break;
                }
                case Constants.M_MAIN_MENU: {
                    break;
                }
                default: {
                    System.out.println(Constants.INVALID_CHOICE_ENTERED);
                }
            }
        }
    }
    private static boolean addFile( File folder) {
        System.out.print(Constants.ENTER_FILE_NAME_TO_ADD);
        Scanner sc = new Scanner(System.in);
        String inputFileName = sc.nextLine();
        File f1 = new File(folder + Constants.FORWARD_SLASH + inputFileName);
        if (f1.exists()) {
            System.out.println(Constants.FILE + " '" + inputFileName + "'" + Constants.ALREADY_EXISTS);
            return false;
        } else {
            try {
                f1.createNewFile();
                System.out.println(Constants.FILE + " '" + inputFileName + "'" + Constants.CREATED);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static boolean deleteFile(File folder) {
        System.out.print(Constants.ENTER_FILE_NAME_TO_DELETE);
        Scanner sc = new Scanner(System.in);
        String inputFileName = sc.nextLine();
        File f1 = new File(folder + Constants.FORWARD_SLASH + inputFileName);
        if (f1.exists()) {
            f1.delete();
            System.out.println(Constants.FILE + Constants.DELETED);
            return true;
        } else {
            System.out.println(Constants.FILE + " '" + inputFileName + "'" + Constants.NOT_FOUND);
            return false;
        }
    }
    private static void searchFile(File[] listOfFolderFiles) {
        int arrayFileListLength;
        arrayFileListLength = listOfFolderFiles.length;
        if (arrayFileListLength > 0) {
            System.out.print(Constants.ENTER_FILE_NAME_TO_SEARCH);
            Scanner sc = new Scanner(System.in);
            String inputFileName = sc.nextLine();
            int i = 0;
            for (i = 0; i < arrayFileListLength; i++) {
                if (listOfFolderFiles[i].getName().equals(inputFileName)) {
                    break;
                }
            }
            if (i != arrayFileListLength) {
                System.out.println(Constants.FILE + " '" + inputFileName + "'" + Constants.FOUND);
            }else {
                System.out.println(Constants.FILE + " '" + inputFileName + "'" + Constants.NOT_FOUND);
            }
        } else {
            System.out.println(Constants.NO_FILES_TO_SEARCH_FROM);
        }
    }
}
