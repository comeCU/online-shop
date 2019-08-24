package shop.orm.entity;

import java.sql.Timestamp;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.orm.annotation.MyColumn;
import shop.orm.annotation.MyKey;
import shop.orm.annotation.MyTable;
/**
 * 特别说明：这是一个实体类写法的举例
 * 其中 tableName="t_customer"，columnName = "cus_id"与数据库表字段名对应
 * @author dong
 * @date Aug 6, 2019 6:35:08 PM
 */

@MyTable(tableName="t_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @MyKey(columnName = "cus_id", isGenerator = true)
    private Long cusId;
    
    @MyColumn(columnName = "cus_name")
    private String cusName;
    
    @MyColumn(columnName = "cus_degree")
    private String cusDegree;
    
    @MyColumn(columnName = "cus_sex")
    private String cusSex;
    
    @MyColumn(columnName = "cus_career")
    private String cusCareer;
    
    @MyColumn(columnName = "cus_qq")
    private String cusQQ;
    
    @MyColumn(columnName = "cus_wechat")
    private String cusWechat;
    
    @MyColumn(columnName = "cus_phone")
    private String cusPhone;
    
    @MyColumn(columnName = "cus_source")
    private String cusSource;
    
    @MyColumn(columnName = "cus_city")
    private String cusCity;
    
    @MyColumn(columnName = "cus_purpose")
    private String cusPurpose;
    
    @MyColumn(columnName = "cus_iscus")
    private int cusIscus;
    
    @MyColumn(columnName = "cus_track")
    private int cusTrack;
    
    @MyColumn(columnName = "update_time")
    private Timestamp updateTime;
    
    @MyColumn(columnName = "cus_init_time")
    private Date cusInitTime;
    
}
