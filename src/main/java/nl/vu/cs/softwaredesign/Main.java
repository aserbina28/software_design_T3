package nl.vu.cs.softwaredesign;

public class Main {
    public static void main (String[] args){
        System.out.println("Welcome to Software Design!");
        UserInterface ui = UserInterface.getInstance();

        String[] arguments = {};
        UserInterface.main(arguments);
    }
}
