package starter.api.model;

import lombok.Getter;

/**
 * Enum to represent some of the existing test users
 * Note: You can easily add users whose details are retrieved from a config file
 */

 @Getter
public enum Users {
    USER01("user01","secpassword*"),
    USER02("user02","secpassword*"),
    USER03("user03","secpassword*"),
    USER04("user04","secpassword*"),
    USER05("user05","secpassword*"),
    USER06("user06","secpassword*"),
    USER07("user07","secpassword*"),
    USER08("user08","secpassword*"),
    USER09("user09","secpassword*"),
    USER010("user010","secpassword*"),
    INVALIDUSER("INVALIDUSERNAME","INVALIDPASSWORD");

    private final String username;
    private final String password;

    Users(String username, String password){
        this.username = username;
        this.password = password;
    }
}
