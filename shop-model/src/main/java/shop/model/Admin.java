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
@MyTable(tableName="t_admin")
public class Admin implements Serializable {
    @MyKey(columnName = "username", isGenerator = false)
    private String username;
    
    @MyColumn(columnName="password")
    private String password;
}
