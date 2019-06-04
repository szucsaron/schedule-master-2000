package com.codecool.web.service.utils;

import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

public class GoogleUserDecryptor {
    private final String CLIENT_ID = "59889217720-mkvqq0odl79dop79as4ivko6lsovk3fq.apps.googleusercontent.com";

    public User fetch(String token) throws ServiceException {
        try {
            JacksonFactory jacksonFactory = new JacksonFactory();
            HttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();

                // Print user identifier
                String userId = payload.getSubject();


                // Get profile information from payload
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                return new User(name, email, Integer.toString(name.hashCode()));

            } else {
                throw new ServiceException("Invalid id token");
            }
        } catch (GeneralSecurityException | IOException e) {
            throw new ServiceException("Google authentication error");
        }
    }
}
