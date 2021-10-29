package com.mycompany.app;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.*;


public class StepDefinitions {
    private String project_filename = "";
    private String student_filename = "";
    private int stu_proj_list[];

    Allocator allocate = new Allocator();
    @Given("I have a list of students, {string}")
    public void I_have_a_list_of_students(String filename) {
        student_filename = filename;
    }

    @Given("I have a list of projects, {string}")
    public void I_have_a_list_of_projects(String filename) {
        project_filename = filename;
    }

    @When("each student has a project")
    public void each_student_has_project() throws Exception{
        allocate.assignStudents(project_filename, student_filename);
        assertTrue(allocate.allStuHasProj());
    }

    @Then("at least {double} of students has a preferred project")
    public void has_preferred_project(double fraction) throws Exception{
        double fracStuHasPrefProj = allocate.fractionStuHasPref();
        boolean check = false;
        if (fracStuHasPrefProj >= fraction) {
            assertTrue(true);
        } else {
            assertTrue(false);
        }

    }
}
