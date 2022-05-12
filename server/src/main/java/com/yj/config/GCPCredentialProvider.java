package com.yj.config;

import com.google.api.gax.core.CredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


@Component
public class GCPCredentialProvider implements CredentialsProvider {

    private GoogleCredentials credentials = null;

    @Autowired
    public GCPCredentialProvider(@Value("${spring.cloud.gcp.credentials.location}") String jsonPath) {
        try {
            File file = ResourceUtils.getFile(jsonPath);
            credentials = GoogleCredentials.fromStream(new FileInputStream(file))
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Credentials getCredentials() throws IOException {
        return credentials;
    }
}
