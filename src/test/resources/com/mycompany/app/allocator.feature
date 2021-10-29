Feature: Allocator

    Scenario: If I have a list of students and a list of projects, I want to ensure that at least 70% of them gets one of their preferred projects
        Given I have a list of students, "student_list.txt"
        Given I have a list of projects, "project_list.txt"
        When each student has a project
        Then at least 0.7 of students has a preferred project
