package org.school21.restful.model;

import lombok.Data;

@Data
public class AuthRequest {

    private String login;
    private String password;
}
