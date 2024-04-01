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
    Archive archive;

    private enum Command {
        ARCHIVE,
        EXTRACT,
        PREVIEW,
        ENCRYPT,
        DECRYPT
    }

    // private constructor to ensure only one instance created
    private UserInterface() {}

    public static UserInterface getInstance() {
        if (instance == null) {
            instance = new UserInterface();
        }
        return instance;
    }

    public static void main(String[] args) throws CryptoException {
        UserInterface ui = UserInterface.getInstance();

        System.out.println("Welcome to Team 3's File Archiver!");
        System.out.println("(Available commands: archive, extract, preview, encrypt)");

        System.out.println("Enter a command to start.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        ui.interpretInput(input);
    }

    private void newPrompt() throws CryptoException {
        UserInterface ui = UserInterface.getInstance();
        System.out.println("Enter a command.");
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        ui.interpretInput(input);
    }

    private void interpretInput(String input) throws CryptoException {
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
                case DECRYPT:
                    decrypt();
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
        }

        return null;
    }

    private String selectDirectory() {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            if (selectedDirectory != null) {
                return selectedDirectory.getAbsolutePath();
            }
        }

        return null;
    }

    private void archive() throws CryptoException {
        UserInterface ui = UserInterface.getInstance();
        Scanner scan = new Scanner(System.in);

        System.out.println("Select files to add to your archive.");
        File[] filesToAdd = ui.selectFiles();
        System.out.println(filesToAdd);

        System.out.println("Select a destination directory.");
        String destination = ui.selectDirectory();
        System.out.println(destination);


        CompressionStrategy compStrat = new Zip(0);

        compStrat = new Zip(0);

        Archive archive = new Archive();
        for (File file : filesToAdd) {
            archive.add(file);
        }

        archive.compress(compStrat, destination);
        this.archive = archive;

        newPrompt();
    }
    private void decrypt() throws CryptoException {
        System.out.println("Select encrypted file to decrypt");
        File[] filesToDecrypt = selectFiles(); // Assuming single selection
        if (filesToDecrypt == null || filesToDecrypt.length == 0) {
            System.out.println("No file selected.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your password: ");
        String password = scan.nextLine();

        System.out.println("Select a destination directory for the decrypted archive.");
        String destination = selectDirectory();
        if (destination == null) {
            System.out.println("No destination selected.");
            return;
        }

        Encryption encryption = new Encryption();
        String decryptedPath = destination + "/decryptedArchive.zip";
        File decryptedFile = new File(decryptedPath);
        encryption.decrypt(password, filesToDecrypt[0], decryptedFile);

        System.out.println("Successfully decrypted: " + decryptedPath);
        newPrompt();
    }


    private void extract() {
        UserInterface ui = UserInterface.getInstance();
        System.out.println("Select a destination directory.");
        String destination = ui.selectDirectory();
        archive.decompress(destination);
    }


    private void preview() {
        System.out.println(archive);
    }
    private void encrypt() throws CryptoException {
        System.out.println("Select a file to encrypt");
        File[] filesToEncrypt = selectFiles();
        if (filesToEncrypt == null || filesToEncrypt.length == 0) {
            System.out.println("No file selected.");
            return;
        }

        Scanner scan = new Scanner(System.in);
        System.out.println("Choose a password for encryption: ");
        String password = scan.nextLine();

        System.out.println("Select a destination directory for the encrypted file.");
        String destination = selectDirectory();
        if (destination == null) {
            System.out.println("No destination selected.");
            return;
        }

        Encryption encryption = new Encryption();
        String encryptedPath = destination + "/encryptedArchive.zip";
        File encryptedFile = new File(encryptedPath);
        encryption.encrypt(password, filesToEncrypt[0], encryptedFile);

        System.out.println("Successfully encrypted: " + encryptedPath);
        archive.setEncrypted(true);
        newPrompt();
    }




}
