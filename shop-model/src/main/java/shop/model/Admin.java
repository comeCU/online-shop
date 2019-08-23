package shop.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Admin implements Serializable {
    private String username;
    private String password;
    
}
