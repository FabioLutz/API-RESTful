package org.project.userManagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserDto {
    private String email;
    private String password;
}