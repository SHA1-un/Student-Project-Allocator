package com.mycompany.app;
import java.io.File;
import java.util.Scanner;

final class Allocator {
    /**Predetermined number of projects.*/
    private static final int NUMBER_PROJECTS = 60;
    /**Predetermined number of students.*/
    private static final int NUMBER_STUDENTS = 30;
    /**Predetermined number of preferences.*/
    private static final int NUMBER_PREFERENCES = 5;
    /**Global array declaration.*/
    private static int[] studentHasProject = new int[NUMBER_STUDENTS];
    /**Global array declaration.*/
    private static int[] projectHasStudent = new int[NUMBER_PROJECTS];
    /**Global array declaration.*/
    private static int[] projectHasStudentPrefRating = new int[NUMBER_PROJECTS];
    /**Global array declaration.*/
    private static String[] students = new String[NUMBER_STUDENTS];
    /**Global array declaration.*/
    private static String[][] preferences = new String[NUMBER_STUDENTS]
    [NUMBER_PREFERENCES];
    /**Global array declaration.*/
    private static String[] projects = new String[NUMBER_PROJECTS];

    Allocator() {
            // Empty constructor
    }

    /**
     * The assignment of the students is given in an array where the index
     * corresponds to the index in the student array and the value of that index
     * is the index of the assigned project which can be found in the project
     * array.
     * @param projectFilename
     * @param preferencesFilename
     * @return studentHasProject
     */
    public static int[] assignStudents(final String projectFilename,
    final String preferencesFilename) {
        String[] allProjects = readFile(projectFilename);
        String[] studentPref = readFile(preferencesFilename);

        // Check if the input is valid
        if (validProjFile(allProjects) && validStuFile(studentPref)) {
            // Processes all the input
            for (int i = 0; i < NUMBER_PROJECTS; i++) {
                String[] temp = allProjects[i].split(",");
                projects[i] = temp[0];
            }

            for (int i = 0; i < NUMBER_STUDENTS; i++) {
                String[] temp = studentPref[i].split(",");
                students[i] = temp[0];
                String pref = "";
                for (int j = 0; j < NUMBER_PREFERENCES + 1; j++) {
                    if (j + 1 < NUMBER_PREFERENCES + 1) {
                        preferences[i][j] = temp[j + 1];
                    }
                }
            }

            /* Student-Project-Assignment */
            // Assign all the sudents to have no project
            for (int i = 0; i < NUMBER_STUDENTS; i++) {
                studentHasProject[i] = -1;
            }
            // Assign all the projects to have no student
            for (int i = 0; i < NUMBER_PROJECTS; i++) {
                projectHasStudent[i] = -1;
            }

            // It first needs to repeat untill all the projects that are listed on a
            // given student's pref list are assigned to some student and those who
            // cant be assigned a proj pased on their list are left out.
            while (studentWithoutProj() != -1) {
                int stuIndexWOProj = studentWithoutProj();

                for (int curentPrefIndex = 0; curentPrefIndex < NUMBER_PREFERENCES;
                curentPrefIndex++) {
                    int currProjIndex = (Integer.parseInt(preferences[stuIndexWOProj]
                    [curentPrefIndex])) - 1;

                    // if it is -1 then there is no student allocated to proj
                    if (projectHasStudent[currProjIndex] == -1) {
                        projectHasStudent[currProjIndex] = stuIndexWOProj;
                        studentHasProject[stuIndexWOProj] = currProjIndex;
                        projectHasStudentPrefRating[currProjIndex] = curentPrefIndex;
                        break;
                    } else {
                        // If the current student has lower pref of an already allocated
                        // proj. Then the current student should replace the old student.
                        if (curentPrefIndex < projectHasStudentPrefRating[currProjIndex]) {
                            studentHasProject[projectHasStudent[currProjIndex]] = -1;
                            projectHasStudent[currProjIndex] = stuIndexWOProj;
                            studentHasProject[stuIndexWOProj] = currProjIndex;
                            projectHasStudentPrefRating[currProjIndex] = curentPrefIndex;
                            break;
                        }
                    }
                }
            }


            // Assign projects to the remaining left out students based on first come
            // first serve basis
            for (int i = 0; i < NUMBER_STUDENTS; i++) {
                if (studentHasProject[i] == -1) {
                    for (int j = 0; j < NUMBER_PROJECTS; j++) {
                        if (projectHasStudent[j] == -1) {
                            projectHasStudent[j] = i;
                            studentHasProject[i] = j;
                            break;
                        }
                    }
                }
            }

        } else {
                String message = "Invalid project file format!";
                PrintOut.printError(message);
                studentHasProject = null;
        }
        return studentHasProject;
    }

    /**
     * Check to see if there is a student without a project and return ONLY
     * if he still has an unallocated proj in pref list.
     * @return studentIndex
     */
    public static int studentWithoutProj() {
        int studentIndex = -1;

        for (int i = 0; i < NUMBER_STUDENTS; i++) {
            int numPrefProjNotAvail = 0;
            if (studentHasProject[i] == -1) {
                //Check how many of the students pref proj is allocated
                for (int j = 0; j < NUMBER_PREFERENCES; j++) {
                    int currProjIndex = (Integer.parseInt(preferences[i][j]) - 1);
                    if (projectHasStudent[currProjIndex] != -1) {
                        numPrefProjNotAvail += 1;
                    }
                }
                if (numPrefProjNotAvail != NUMBER_PREFERENCES) {
                    studentIndex = i;
                    break;
                }
            }
        }
        return studentIndex;
    }

    /**
     * Reads in files from the resources folder.
     * @param filename
     * @return projectsArr
     */
    public static String[] readFile(final String filename) {
        String[] projectsArr = new String[NUMBER_PROJECTS];
        int index = 0;
        try {
            File input = new File("src/main/resources/" + filename);
            Scanner myScanner = new Scanner(input);
            while (myScanner.hasNextLine()) {
                projectsArr[index] = myScanner.nextLine();
                index++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return projectsArr;
    }

    /**
     * A check to see if the project file that is read in, is valid.
     * @param proj
     * @return isValid
     */
    public static boolean validProjFile(final String[] proj) {
        boolean isValid = true;
        for (int i = 0; i < proj.length; i++) {
            if (proj[i] == null) {
                isValid = false;
                break;
            } else {
                String[] temp = proj[i].split(",");
                if (temp.length != 2) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }

    /**
     * A check to see if the student file that is read in, is valid.
     * @param stu
     * @return isValid
     */
    public static boolean validStuFile(final String[] stu) {
            boolean isValid = true;
            for (int i = 0; i < NUMBER_STUDENTS; i++) {
                if (stu[i] == null) {
                    isValid = false;
                    break;
                } else {
                    String[] temp = stu[i].split(",");
                    if (temp.length != (NUMBER_PREFERENCES + 1)) {
                        isValid = false;
                        break;
                    } else {
                        for (int j = 1; j < NUMBER_PREFERENCES + 1; j++) {
                            if (!(Integer.parseInt(temp[j]) <= 60)) {
                                isValid = false;
                                break;
                            }
                        }
                    }
                }
            }
        return isValid;
    }

    /**
     * Checks how many students has been assigned one of their prefferences
     * @return num
     */
    public static double fractionStuHasPref() {
        double num = 0;

        for (int i = 0; i < NUMBER_STUDENTS; i++) {
            for (int j = 0; j < NUMBER_PREFERENCES; j++) {
                if (studentHasProject[i] + 1 == Integer.parseInt(preferences[i][j])) {
                    num++;
                    break;
                }
            }
        }

        return (num / NUMBER_STUDENTS);
    }

    /**
     * Checks if all the students has a project assigned to them
     * @return check
     */
    public static boolean allStuHasProj() {
        boolean check = true;

        for (int i = 0; i < NUMBER_STUDENTS; i++) {
            if (studentHasProject[i] == -1) {
                check = false;
                break;
            }
        }

        return check;
    }

// -----------------------Output, Tests and Debugging-------------------------
    /**
     * A getter for the test class.
     *
     * @return projects
     */
    public static String[] getProjects() {
        return projects;
    }

    /**
     * A getter for the test class.
     *
     * @return students
     */
    public static String[] getStudents() {
        return students;
    }
//----------------------------------------------------------------------------
}
