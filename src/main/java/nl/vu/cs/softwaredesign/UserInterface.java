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
        //ui.interpretInput(input);
        ui.selectFiles();
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

    private void selectFiles() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File[] selectedFiles = fileChooser.getSelectedFiles();
            if (selectedFiles.length > 0) {
                System.out.println("Selected files:");
                for (File file : selectedFiles) {
                    System.out.println(file.getAbsolutePath());
                }
            } else {
                System.out.println("No files selected.");
            }
        }
    }

    private void archive() {
        System.out.println("archive");
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
