package shop.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.orm.annotation.MyColumn;
import shop.orm.annotation.MyKey;
import shop.orm.annotation.MyTable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@MyTable(tableName="t_book")
public class Book implements Serializable {
    @MyKey(columnName = "bid", isGenerator = true)
    private int bid;
    
    @MyColumn(columnName="bookname")
    private String bookname;
    
    @MyColumn(columnName="price")
    private double price;
    
    @MyColumn(columnName="image")
    private String image;
    
    @MyColumn(columnName="stock")
    private String stock;//库存
    
}
