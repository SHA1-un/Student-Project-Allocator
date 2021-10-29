
package com.mycompany.app;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.Drive.Files;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class ImportDrive {
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Arrays.asList("https://www.googleapis.com/auth/drive");
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = ImportDrive.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
            .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
            .setAccessType("offline")
            .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    /**
     * Searched currently logged on user's drve account for given filename and
     * downloads the file to be used locally.
     * @param filename
     * @throws IOException If the credentials.json file cannot be found.
     * @throws GeneralSecurityException If the credentials.json file is not valid.
     */
    public static void importFromDrive(String filename) throws IOException, GeneralSecurityException {
        String req = "fullText contains '" + filename + "'";
        String fileId = "";
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();


        FileList searchAllFiles = service.files().list()
            .setQ(req)
            .setSpaces("drive")
            .setFields("nextPageToken, files(id, name)")
            .setPageToken(null)
            .execute();

        List<File> files = searchAllFiles.getFiles();
        if (files == null || files.isEmpty()) {
            PrintOut.printError(String.format("File (%s) not found in any Google Drive folders!", filename));
        } else if (files.size() != 1) {
            PrintOut.printError("Multiple files with the same name!");
        } else {
            PrintOut.printInfo("File found!");
        }

        for (File file : files) {
            fileId = file.getId();
        }

        if (filename.contains(".txt")) {
            OutputStream outputStream = new FileOutputStream("src/main/resources/" + filename);
            service.files().get(fileId)
            .executeMediaAndDownloadTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } else if (filename.contains(".csv")) {
            OutputStream outputStream = new FileOutputStream("src/main/resources/" + filename);
            service.files().get(fileId)
            .executeMediaAndDownloadTo(outputStream);
            outputStream.flush();
            outputStream.close();
        } else {
            PrintOut.printError("Invalid File!");
        }

    }
}
