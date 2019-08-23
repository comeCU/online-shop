package shop.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Book implements Serializable {
    private final String bid_seq = "BS_BOOKID.nextVal"; //数据库，做自增长
    private int bid;
    private String bookname;
    private double price;
    private String image;
    private String stock;//库存
    private int count = 0;
    
}
