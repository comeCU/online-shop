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
@MyTable(tableName="t_order")
public class Order implements Serializable {
    @MyKey(columnName = "oid", isGenerator = true)
    private int oid;
    
    @MyColumn(columnName="username")
    private String username;
    
}
