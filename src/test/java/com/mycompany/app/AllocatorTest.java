package com.mycompany.app;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AllocatorTest {
    // The following test was done by working out the best assignment of the projects by
    // hand and then comparing the output of the program with the predetermined output array
    @Test
    public void testAssignStudents() {
        int output[] = Allocator.assignStudents("test1_proj.txt", "test1_pref.txt");
        int expectedOutput[] = {6,0,14,13,24,30,36,42,48,54,1,2,3,4,5,29,7,8,9,10,11,12,15,16,17,18,19,20,21,39};
        boolean isValid = true;
        for (int i = 0; i < output.length; i++) {
            if (output[i] != expectedOutput[i]) {
                isValid = false;
                break;
            }
        }
        assertEquals(true, isValid);

        String students[] = Allocator.getStudents();
        String projects[] = Allocator.getProjects();
        System.out.println("Student allocation output for test case\n");
        for (int i = 0; i < output.length; i++) {
            System.out.printf("%-10s has been assigned to: %s\n", students[i], projects[output[i]]);
        }

}

    @Test
    public void testReadFile() {
        String testStr = "This is a test to see if the file reader works.";
        String readInputFile[] = Allocator.readFile("fileReaderTest.txt");
        boolean check = false;

        assertEquals(testStr, readInputFile[0]);
    }

    // Given a correct input file the, validity of the file should be returned as true
    @Test
    public void testValidProjFileValid() {
        String readInputFile[] = Allocator.readFile("test1_proj.txt");

        assertEquals(true, Allocator.validProjFile(readInputFile));
    }

    // Given an incorrect input file the, validity of the file should be returned as false
    @Test
    public void testValidProjFile() {
        String readInputFile[] = Allocator.readFile("empty.txt");

        assertEquals(false, Allocator.validProjFile(readInputFile));
    }

    // Given a correct input file the, validity of the file should be returned as true
    @Test
    public void testValidStuFileValid() {
        String readInputFile[] = Allocator.readFile("test1_pref.txt");

        assertEquals(true, Allocator.validStuFile(readInputFile));

    }
    // Given an incorrect input file the, validity of the file should be returned as false
    @Test
    public void testValidStuFile() {
        String readInputFile[] = Allocator.readFile("test1_pref_invalid.txt");

        assertEquals(false, Allocator.validStuFile(readInputFile));

    }
}
