package org.project.userManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUsername {
    private String email;
    private String username;
    private String password;
}
