package com.api.restapitutorial1.config.keycloak;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakAdminClientConfig {
    private Keycloak keycloakAdminClient;

    @Value("${keycloak.auth-server-url}")
    private String serverURL;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${custom.keycloak.admin_username}")
    private String adminUsername;
    @Value("${custom.keycloak.admin_password}")
    private String adminPassword;

    private void createInstance() {
        /*keycloakAdminClient = KeycloakBuilder.builder()
                .serverUrl(serverURL)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("clientid")
                .clientSecret("secret")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();*/

        keycloakAdminClient = KeycloakBuilder.builder()
                .serverUrl(serverURL)
                .grantType(OAuth2Constants.PASSWORD)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();
    }

    public RealmResource getInstance() {
        if (keycloakAdminClient == null)
            createInstance();
        return keycloakAdminClient.realm(realm);
    }
}
