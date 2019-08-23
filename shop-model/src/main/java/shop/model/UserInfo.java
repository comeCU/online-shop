package shop.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class UserInfo implements Serializable {
    private String username;
    private String password;
    private String email;
    
}
