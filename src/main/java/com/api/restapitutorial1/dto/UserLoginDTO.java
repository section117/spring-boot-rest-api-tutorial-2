package com.api.restapitutorial1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;
}
