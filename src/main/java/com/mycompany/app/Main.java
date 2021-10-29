package com.mycompany.app;
import java.util.Scanner;
public class Main {

    Main() {
        // Empty constructor
    }

    /**
     * Main.
     * @param args
     */
    public static void main(final String[] args) {
        Scanner input = new Scanner(System.in);
        String feedback = "";
        String projectFile = args[0];
        String studentFile = args[1];
        String[] projectNames = new String[30];

        System.out.println("Do you want to import your data from Google drive? yes/no");
        while ((feedback = input.next()).compareToIgnoreCase("yes") != 0) {
            if (feedback.compareToIgnoreCase("no") == 0) {
                break;
            }
        }

        if (feedback.compareToIgnoreCase("yes") == 0) {
            try {
                ImportDrive.importFromDrive(args[0]);
                ImportDrive.importFromDrive(args[1]);
            } catch(Exception e) {
                System.out.println(e);
            }
        }

        int[] allocations = Allocator.assignStudents(projectFile, studentFile);

        if (allocations != null) {
            String students[] = Allocator.getStudents();
            String projects[] = Allocator.getProjects();
            System.out.println("Student allocation output:\n");

            for (int i = 0; i < allocations.length; i++) {
                projectNames[i] = projects[allocations[i]];
                System.out.printf("%-20s has been assigned to: %s\n",
                students[i], projects[allocations[i]]);
            }

            if (args.length == 3) {
                if (args[2].compareToIgnoreCase("pdf") == 0) {
                    if (ExportPdf.exportPdf(students, projectNames)) {
                        String message = "Export success!";
                        PrintOut.printInfo(message);
                    } else {
                        String message = "Failed to write to pdf, please try again.";
                        PrintOut.printError(message);
                    }
                } else {
                    String message = "Please select a valid export method. The results have not been saved!";
                    PrintOut.printError(message);
                }
            }
        }

    }

}
