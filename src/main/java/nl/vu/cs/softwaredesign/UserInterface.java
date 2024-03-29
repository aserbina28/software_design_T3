package nl.vu.cs.softwaredesign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

public class UserInterface {
    private static UserInterface instance;
    // private List<> archives = new ArrayList<>();

    private enum Command {
        ARCHIVE,
        EXTRACT,
        PREVIEW,
        ENCRYPT
    }

    // private constructor to ensure only one instance created
    private UserInterface() {}

    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    public static void main(String[] args) {
        UserInterface ui = UserInterface.getInstance();

        System.out.println("Welcome to Team 3's File Archiver!");
        System.out.println("Enter a command to start.");
        System.out.println("(Available commands: archive, extract, preview, encrypt)");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        ui.interpretInput(input);
    }

    private void interpretInput(String input) {
        String[] words = input.split("\\s+");

        Command command;

        try {
            command = Command.valueOf(words[0].toUpperCase());
        }
        catch (Exception e) {
            System.out.println("Invalid command.");
            command = null;
        }

        if (command != null) {
            switch(command) {
                case ARCHIVE:
                    archive();
                    break;
                case EXTRACT:
                    extract();
                    break;
                case PREVIEW:
                    preview();
                    break;
                case ENCRYPT:
                    encrypt();
                    break;
            }
        }
    }

    private File[] selectFiles() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            return selectedFiles;
        } else {
            return null;
        }
    }

    private String selectDirectory() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showSaveDialog(null);

        // Process the selected directory
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            if (selectedDirectory != null) {
                return selectedDirectory.getAbsolutePath();
            }
        }

        return null;
    }

    private void archive() {
        UserInterface ui = UserInterface.getInstance();

        System.out.println("Enter a name for your archive: ");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        System.out.println("Select files to add to your archive.");
        File[] filesToAdd = ui.selectFiles();
        System.out.println(filesToAdd);

        System.out.println("Select a destination directory.");
        String destination = ui.selectDirectory();
        System.out.println(destination);

        System.out.println("Available compression formats are ZIP and LZ4.\n" +
                "Enter 1 for ZIP or 2 for LZ4.");
        input = scan.nextLine();
        if (input.equals("1")) {
            System.out.println("You chose ZIP");
        } else if (input.equals("2")) {
            System.out.println("You chose LZ4");
        } else {
            System.out.println("Invalid");
        }
    }
    private void extract() {
        System.out.println("extract");
    }
    private void preview() {
        System.out.println("preview");
    }
    private void encrypt() {
        System.out.println(("encrypt"));
    }


}
