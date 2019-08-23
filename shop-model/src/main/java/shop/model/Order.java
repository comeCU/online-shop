package shop.model;

import java.io.Serializable;

import lombok.Data;
@Data
public class Order implements Serializable {
    private int oid;
    private String username;
    
}
