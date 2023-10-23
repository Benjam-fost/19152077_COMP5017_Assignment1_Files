package comp5017.cw1.pkg2023;

import java.util.*;
import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.*;

/**
 *
 * @author David Lightfoot 2023-09
 */

public class COMP5017CW12023 {

    static Scanner kb;
    static IStaffDB db = new StaffHash(); // change to StaffBST for Assignment 2

    private static String readNonEmpty() {
        // read non-null, non-empty string;
        String s = kb.nextLine().trim();
        while (s.isBlank() || s.isEmpty()) {
            System.out.print("must not be blank -- try again: ");
            s = kb.nextLine().trim();
        }
        assert s != null && !s.trim().isEmpty();
        return s;
    }

    public static void main(String[] args) {
        String option, name, affiliation;
        Employee resp;
        kb = new Scanner(System.in);
        loadFile();

        System.out.print("D)isplay  P)ut  G)et  C)ontains  S)ize  R)emove  Q)uit? ");
        option = readNonEmpty();
        while (option.charAt(0) != 'Q' && option.charAt(0) != 'q') {
            switch (option.charAt(0)) {
                case 'D':
                case 'd':  // display
                    db.displayDB();
                    break;

                case 'P':
                case 'p':  // put
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    System.out.print("Affiliation? ");
                    affiliation = readNonEmpty();
                    Employee member = new Employee(name, affiliation);
                    resp = db.put(member);
                    System.out.print(name);
                    if (resp == null) {
                        System.out.println(" : new member added");
                    } else {
                        System.out.println(" : member overridden");
                        System.out.println("previous was " + resp.toString());
                    }
                    break;

                case 'S':
                case 's':  //size
                    System.out.println("Size " + db.size());
                    break;
                    
                case 'G':
                case 'g':  // get
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    resp = db.get(name);
                    if (resp != null) {
                        System.out.println(resp.toString());
                    } else {
                        System.out.println(name + " not found");
                    }
                    break;

                case 'C':
                case 'c':  // contains
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    System.out.print(name);
                    if (db.containsName(name)) {
                        System.out.println(" found");
                    } else {
                        System.out.println(" not found");
                    }
                    break;

                case 'R':
                case 'r':  // remove
                    System.out.print("Name? ");
                    name = readNonEmpty();
                    resp = db.remove(name);
                    System.out.print(name);
                    if (resp != null) {
                        System.out.println(" deleted");
                    } else {
                        System.out.println(" not found");
                    }
                    break;

                default: //?
                    System.out.println("unknown option");

            } // switch
                    System.out.println();
                    System.out.print("D)isplay  P)ut  G)et  C)ontains  S)ize  R)emove  Q)uit? ");
            option = readNonEmpty();
        } // while
    }

    /**
     * Allows the user to select a file containing a description of a graph. If
     * no file is selected, the program terminates.
     *
     * @param startFolder the folder that the file chooser should start from
     * @return the file that was selected
     */
    public static File getDataFile(String startFolder) {
        File f;
        // setting up a dialogue box,
        // to select a file containing data
        JFileChooser fc = new JFileChooser(startFolder);
        fc.setDialogTitle("Choose a file containing data -- 'Cancel' for console input ");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcReturnValue = fc.showOpenDialog(null);

        // now, which file did the user select?
        if (fcReturnValue != JFileChooser.APPROVE_OPTION) {
            // user must have cancelled, or an error occurred
            System.out.println("No file selected. input from keyboard.");
            f = null;
           //System.exit(0);
            // f = fc.getSelectedFile();
        } else { // user selected a file ok
            System.out.println("input from file.");
            f = fc.getSelectedFile();
        }
        return f;
    }
    // Change this startFolder variable to (e.g. "C:\\Documents and Settings\\me\\program")
    // or wherever you have put your graph files, in order to get the file chooser to
    // start in the folder - note that in Java strings, \ is an escape character, so
    // to get a single \, you need to write \\ in the pathname of the folder.
    private static String startFolder = ".";

    private static void loadFile() {
        String cvsSplitBy = ",";
        File file;
        Scanner text;
        Employee employee;
        try {
            file = getDataFile(startFolder);
            if (file != null) {
                FileInputStream streamIn = new FileInputStream(file);
                text = new Scanner(streamIn);
                while (text.hasNextLine()) {
                    String line = text.nextLine();
                    String[] parts = line.split(cvsSplitBy);
                    String surname = parts[0].trim();
                    String firstNames = parts[1].trim();
                    String affiliation = parts[2].trim();
                    employee = new Employee(surname + ", " + firstNames, affiliation);
                    db.put(employee);
                }
                text.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Can't open chosen file" );
        }
    }

}
