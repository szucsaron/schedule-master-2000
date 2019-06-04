package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoginService;
import com.codecool.web.service.exception.ServiceException;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

//import javafx.concurrent.Service;
import javax.sql.rowset.serial.SerialException;
import java.sql.SQLException;
import java.util.Collections;


public final class SimpleLoginService implements LoginService {

    private final UserDao userDao;

    public SimpleLoginService(UserDao userDao) {
        this.userDao = userDao;
    }


    public void loginUser(String token) throws SQLException, ServiceException {
        try {
            JacksonFactory jacksonFactory = new JacksonFactory();
            HttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList("59889217720-mkvqq0odl79dop79as4ivko6lsovk3fq.apps.googleusercontent.com"))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();
                System.out.println("User ID: " + userId);

                // Get profile information from payload
                String email = payload.getEmail();
                boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                String locale = (String) payload.get("locale");
                String familyName = (String) payload.get("family_name");
                String givenName = (String) payload.get("given_name");

                // Use or store profile information
                // ...

            } else {
                System.out.println("Invalid ID token.");
            }
        } catch (IllegalArgumentException | GeneralSecurityException | IOException ex) {
            throw new ServiceException(ex.getMessage());
        }
    }
}

