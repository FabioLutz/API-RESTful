package org.project.userManagement.exception;

public class InitialAdminPasswordNotDefinedException extends RuntimeException {
    public InitialAdminPasswordNotDefinedException(String message) {
        super(message);
    }
}
