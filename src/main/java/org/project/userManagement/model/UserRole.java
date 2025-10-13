package org.project.userManagement.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum UserRole {
    USER;

    public String getAuthority() {
        return "ROLE_" + name();
    }
}
