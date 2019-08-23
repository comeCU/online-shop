package shop.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Data;

@Data
public class Item implements Serializable {
    private int iid;
    private int oid;
    private int bid;
    private Date createDate;
    private double price;
    private double totalPrice;
    private int count;
    
}
