package shop.orm.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import shop.orm.exception.MyException;
/**
 * ORM映射dao层接口
 * @author dong
 * @date Aug 24, 2019 2:03:12 PM
 */
public interface DbDao {
    /**
     * 查询操作
     * add by dong 2019/08/24
     * @param sql
     * @param colums
     * @return list集合
     */
    public List query(String sql,String[] colums);
    
    /**
     * 查询操作
     * @param sql sql语句
     * @param params sql中占位符对应的参数列表
     * @return list集合
     */
    public List<Map<String, Object>> executeSelect(String sql, Object[] params);
    
    /**
     * 将一个对象保存到数据库中
     * @param obj
     * @throws MyException 
     */
    public void save(Object obj) throws MyException;
    
    /**
     * 根据传入的对象更新一条数据库记录，必须含有主键所对应的属性值
     * @param obj 
     * @throws MyException 
     */
    public void update(Object obj) throws MyException;
    
    /**
     * 根据传入的对象删除一条数据库记录
     * @param obj
     * @throws MyException 
     */
    public void delete(Object obj) throws MyException;
    
    /**
     * 通过主键获取一个对象
     * @param key
     * @param clazz
     * @throws MyException 
     */
    public Object getObjectById(Object key, Class clazz) throws MyException;
    
    /**
     * map中的key存放"sql"字符串，则value存放sql语句字符串
     * 否则，key存放"params"，value为传入的参数
     * 例如：map.put("sql", "insert into t_student values(?,?,?)");
     *      map.put("parmas", new Object[] {"", "", ""});
     *   sqls.add(map)
     * @param sqls 需要执行的sql语句，以及参数
     * @return
     */
    public Long executeSql(List<Map<String, Object>> sqls);
    
    /**
     * 执行sql包括：insert、delete、update 操作，
     * 可通过多次调用此方法执行多条sql，自行维护单个connection对象(可使用ThreadLocal<Connection>)，可以保证事务的一致性。
     * @param sql 需要执行的sql
     * @param params 与？对应的参数值
     * @param isGenerateKey 是否需要返回主键的值
     * @param isTransaction 是否需要开启事务，默认为false，true为开启
     * @return 如果isGenerateKey为true，返回自增长主键的当前值，false，则返回受影响行数
     */
    public Long executeSql(String sql, Object[] params, boolean isGenerateKey, boolean isTransaction);

    /**
     * 执行sql包括：insert、delete、update 操作，
     * 可通过多次调用此方法执行多条sql，自行维护单个connection对象(可使用ThreadLocal<Connection>)，可以保证事务的一致性。
     * @param sql 需要执行的sql
     * @param params 与？对应的参数值
     * @param isGenerateKey 是否需要返回主键的值
     * @param conn 如果不为null，则需要执行多条sql，并保证事务的一致性。
     *             由外部传入的数据库连接对象，也由调用者自行关闭。
     * @return 如果isGenerateKey为true，返回自增长主键的当前值，false，则返回受影响行数
     */
    public Long executeSql(String sql, Object[] params, boolean isGenerateKey, Connection conn);
    
    /**
     * 执行sql包括：insert、delete、update 操作
     * @param sql 需要执行的sql
     * @param params 与？对应的参数值
     * @param isGenerateKey 是否需要返回主键的值
     * @return 如果isGenerateKey为true，返回自增长主键的当前值，false，则返回受影响行数
     */
    public Long executeSql(String sql, Object[] params, boolean isGenerateKey);
    
    /**
     * 执行sql包括：insert、delete、update 操作
     * @param sql 需要执行的sql
     * @param params 与？对应的参数值
     * @return 受影响行数
     */
    public Long executeSql(String sql, Object[] params);
    
    /**
     * (可变类型参数) 执行sql包括：insert、delete、update 操作
     * @param sql 需要执行的sql
     * @param params 与？对应的参数值
     * @return 受影响行数
     */
    public Long execSql(String sql, Object ...params);
   
}
