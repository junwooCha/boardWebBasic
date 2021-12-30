package com.koreait.basic.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResult {
    private final int result;
    private UserEntity loginUser;

}
