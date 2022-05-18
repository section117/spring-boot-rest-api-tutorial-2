package com.api.restapitutorial1.service;

import com.api.restapitutorial1.config.keycloak.KeycloakAdminClientConfig;
import com.api.restapitutorial1.dao.UserRepository;
import com.api.restapitutorial1.model.User;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Collections;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KeycloakAdminClientConfig keycloakAdminClientConfig;

    @Transactional(rollbackFor = Exception.class)
    public Object addUser(String name, String email, String password, String role) throws Exception {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);
        userRepository.save(user);

        //Add the User to Keycloak IAM Server
        long user_id = user.getId();

        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setUsername(Long.toString(user_id));
        keycloakUser.setFirstName(name);
        keycloakUser.setEmail(email);
        keycloakUser.setEnabled(true);
        keycloakUser.setEmailVerified(true);
        keycloakUser.setRealmRoles(Collections.singletonList(role));

        //Setup password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setTemporary(false);
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));

        //Save user in Keycloak server
        UsersResource usersResource = keycloakAdminClientConfig.getInstance().users();
        Response response = usersResource.create(keycloakUser);

        //Associate the role with the user
        RoleRepresentation realmRoleUser = keycloakAdminClientConfig.getInstance().roles().get(role).toRepresentation();
        String createdId = CreatedResponseUtil.getCreatedId(response);
        UserResource userResource = usersResource.get(createdId);
        userResource.roles().realmLevel().add(Collections.singletonList(realmRoleUser));

        if(response.getStatus() != 201)
            throw new Exception("Failed to add the user to Keycloak " + response.readEntity(String.class));

        return "User Added successfully.";
    }
}
