# Student-Project Allocator

The student project allocation program uses an algorithm I designed myself, but with the inspiration of the algorithm of [David J.Abrahama, Robert W.Irving and David F.Manlov](https://www.sciencedirect.com/science/article/pii/S1570866706000207#sec001). The pseudocode that was used as the base is the following: [SPA algorithm](https://ars.els-cdn.com/content/image/1-s2.0-S1570866706000207-gr005.gif)

## Getting Started

- Check your current Java version and make sure it is Java 8.
```
java -version
```
- If you do not have Java 8 installed, simply run the following commands.
```
sudo apt-get update
sudo apt-get install openjdk-8-jdk
```

## Running the unit tests

The unit tests can be executed with `mvn test` and is triggered every time the pipeline is running.
It will build and test all of the functions in the program as well
as run one sample input test where the program is given a list of projects and
students with projects preferences with the optimal allocation given as output.

The second part of the testing is handled by cucumber where we look at specific scenario testing. Currently it
is expected that the algorithm will at all times assign a project to all students and that the students
will receive one of the five projects they indicated as their preference. Thus the one cucumber test
checks if at least 70% of the of the students have received one of their preferred projects to ensure
the quality of the algorithm.

- NOTE: There is a direct connotation between the quality of the input preference data and the
percentage of students that receive their preferred project. Duplicates will skew the statistic
and result in inadequate allocations.

## Running the program

The program can be executed with the following command.
```
mvn clean compile
mvn exec:java -Dexec.args="project_list.txt student_list.txt"
OR
mvn exec:java -Dexec.args="project_list.txt student_list.txt pdf"

```
- NOTE: `arg1` refers to the file name where the projects and corresponding lecturers are saved, and
`arg2` refers to the file name of the student name and their list of preferences.
- NOTE: Only `.csv` and `.txt` file extensions will work as input.
- NOTE: By adding the `pdf` tag the output will be written to a PDF file.

### Google Drive file import

<<< This Will not work any more since the credentials.json file has been removed due to security reasons, please generate your own credentials.json file from -- `Select "no" when prompted to import from Google Drive` >>>

After running the program you will be asked if you would want to import files from
your Google Drive account or use the local files on your computer.

If you selected `yes` then the browser will open up to log you in via Google authentication services.
The provided file names will be used as the files to search for.

- NOTE: Only `.csv` and `.txt` file extensions will work as import files.
- NOTE: Google saves your encrypted authentication key locally in the `tokens` folder and acts as a way
  to remember the previous user. To log in as a new user, Delete the `tokens` folder.

## Git CI/CD

The pipeline currently consists of 2 stages of which one build the program and the other
runs the tests.

### Build stage

All that the build stage does is it executes the `mvn compile` command to compile
the program. If that fails then the while pipeline will fail.

### Test stage

The test stage consists of multiple jobs running in parallel that all perform tests that are
independent of one another. A description of each  of the following can be found in the `gitlab-ci.yml` file.

- Class file check
- Style test
- "target" file check
- Unit tests

### Jacoco line coverage and PIT mutation testing

The Jacoco covrage and PIT mutation was done on all classes except `Main`, `PrintOut` and `ImportDrive`. The reason for excluding these classes are as follows:
 - `Main.java` : The reason for excluding the main class is because it only calls all the other components of the program and is responsible for print outs.
 - `PrintOut.java` : This class is only responsible for displaying output to the user and it would not be sensible to test it.
 - `ImportDrive.java` : Although it would most definitely be important to test whether or not the importing from Google Drive feature does indeed work. It proved troublesome to write tests for it since it does require manual user input to login and give access to their account. This is mostly due to the authentication method used and can thus be improved in the future.

 <<< [Link to github pages](https://computer-science.pages.cs.sun.ac.za/rw344/2020/20789629-tuts/) >>>

### References
- https://developers.google.com/drive/api/v2/reference
- https://developers.google.com/drive/api/v3/quickstart/java
- https://developers.google.com/api-client-library/java/google-api-java-client/setup
- https://kb.itextpdf.com/home/it7kb/examples/101-a-very-simple-table
- https://www.baeldung.com/java-pdf-creation
- https://www.mikesdotnetting.com/article/82/itextsharp-adding-text-with-chunks-phrases-and-paragraphs
