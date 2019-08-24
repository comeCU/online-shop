package shop.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.orm.annotation.MyColumn;
import shop.orm.annotation.MyKey;
import shop.orm.annotation.MyTable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MyTable(tableName="t_item")
public class Item implements Serializable {
    @MyKey(columnName = "bid", isGenerator = true)
    private int iid;
    
    @MyColumn(columnName="oid")
    private int oid;
    
    @MyColumn(columnName="bid")
    private int bid;
    
    @MyColumn(columnName="createdate")
    private Date createdate;
    
    @MyColumn(columnName="price")
    private double price;
    
    @MyColumn(columnName="totalPrice")
    private double totalPrice;
    
    @MyColumn(columnName="count")
    private int count;
    
}
