package com.example.vscanner.Config.Token.Impl;

import com.example.vscanner.Config.Token.AccessToken;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
@Getter
public class AccessTokenImpl implements AccessToken {
    private final String username;
    private final Long userID;
    private final String role;

    public AccessTokenImpl(String username, Long userID, String role){
        this.username = username;
        this.userID = userID;
        this.role = role;
    }
}
