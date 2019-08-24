package shop.orm.dao.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shop.orm.annotation.MyColumn;
import shop.orm.annotation.MyKey;
import shop.orm.annotation.MyTable;
import shop.orm.dao.DbDao;
import shop.orm.exception.MyException;
import shop.orm.utils.DbUtil;

/**
 * 对数据库操作dao层实现类
 * 
 * @author dong
 * @date Aug 24, 2019 2:05:47 PM
 */
public class DbDaoImpl implements DbDao {
    
    @Override
    public List query(String sql,String[] colums) {
        List list = new ArrayList();
        Map map = null;
        Connection conn = DbUtil.getConn();
        PreparedStatement prstmt = null;
        ResultSet rs = null;
        try {
            prstmt = conn.prepareStatement(sql);
            rs = prstmt.executeQuery();
            while(rs.next()) {
                map = new HashMap();
                for (int i = 0; i < colums.length; i++) {
                    //根据列名取数据库的值
                    map.put(colums[i], rs.getObject(colums[i]));
                }
                list.add(map);
            }
        } catch (Exception e) {
            if(e.getMessage().equals("列名无效")) {
                System.out.println("当前查找的列名不存在");
            } else {
                e.printStackTrace();
            }
        } finally {
            DbUtil.close(conn, prstmt, rs);
        }       
        
        return list;
    }
    
    @Override
    public List<Map<String, Object>> executeSelect(String sql, Object[] params) {
        Connection conn = DbUtil.getConn();

        PreparedStatement prstmt = null;
        ResultSet rs = null;
        List<Map<String, Object>> datas = new ArrayList<>(); // 保存每一条存放在map中的记录

        try {
            prstmt = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                prstmt.setObject(i + 1, params[i]);
            }
            rs = prstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> data = new HashMap<>(); // 每一行记录放在一个新的map中
                for (int i = 1; i <= columnCount; i++) {
                    String columnLabel = metaData.getColumnLabel(i);
                    data.put(columnLabel, rs.getObject(columnLabel)); // 根据别名
                }
                datas.add(data);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.close(conn, prstmt, rs);
        }
        return datas;
    }

    @Override
    public void save(Object obj) throws MyException {
        // insert into t_student(stu_name, stu_no, stu_age) values(?,?,?)
        // 1.获取参数对应类的字节码
        Class clazz = obj.getClass();

        Field fieldKey = null; // 保存主键的值，后面需要设值

        boolean isGenerator = false; // 标志是否为自增主键

        // 2.创建StringBuffer拼接 sql，分3段：字段名与 ？号对应拼接，注意末尾去除 ,号 和 )号
        StringBuffer sqlHead = new StringBuffer("insert into ");
        StringBuffer sqlColumn = new StringBuffer(" (");
        StringBuffer sqlValues = new StringBuffer(" values( ");

        List<Object> params = new ArrayList<>(); // 存放执行sql的参数，后面需要执行sql

        // 2.1 获取表名
        MyTable myTable = (MyTable) clazz.getAnnotation(MyTable.class);
        System.out.println("myTable打印：" + myTable);
        if (myTable != null) { // 数据库是否真的存在这张表需要调用者在entity类的@MyTable注解中明确
            String tableName = myTable.tableName();
            sqlHead.append(tableName);

            // 2.2 获取属性
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);

                // 2.3 获取属性上的注解
                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    Object value = null;
                    try {
                        value = field.get(obj); // 返回字段中的值
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // 3. 判断属性是普通字段还是主键，主键不是自增长则需要拼接，否则不需要拼接
                    if (annotation instanceof MyColumn && value != null) {
                        MyColumn myColumn = (MyColumn) annotation;
                        String columnName = myColumn.columnName();
                        sqlColumn.append(columnName).append(", "); // 添加字段名

                        sqlValues.append(" ?").append(","); // 添加对应的参数 ？占位符

                        params.add(value); // 保存到参数列表中
                    } else if (annotation instanceof MyKey) { // 如果该字段是主键
                        fieldKey = field;
                        MyKey myKey = (MyKey) annotation;
                        String columnName = myKey.columnName();

                        isGenerator = myKey.isGenerator(); // 获取是否为自增字段

                        if (!isGenerator) { // 不是自增主键则需要拼接 设值记录
                            sqlColumn.append(columnName).append(", ");
                            sqlValues.append(" ? ").append(", ");

                            if (value != null) {
                                params.add(value); // 保存到参数列表中
                            }
                        }
                    }
                }
            }
            sqlColumn.append(")");
            sqlValues.append(")");
            // 删除最后一个 , 号
            sqlColumn.deleteCharAt(sqlColumn.lastIndexOf(","));
            sqlValues.deleteCharAt(sqlValues.lastIndexOf(","));
            // 最后拼接
            sqlHead.append(sqlColumn).append(sqlValues);

            System.out.println("输出整条sql：" + sqlHead);
            System.out.println("sql中的参数：" + params.toString());

            // 4. 执行sql，如果主键
            if (isGenerator) { // 如果主键是自增的，按如下方式执行
                Long key = executeSql(sqlHead.toString(), params.toArray(), true);

                if (fieldKey != null) {
                    fieldKey.setAccessible(true);
                    try {
                        // 给数据库表主键对应的java对象属性赋值
                        if (fieldKey.getType() == int.class || fieldKey.getType() == Integer.class) {
                            fieldKey.set(obj, key.intValue());
                        } else {
                            fieldKey.set(obj, key);
                        }
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            } else {
                executeSql(sqlHead.toString(), params.toArray(), false);
            }

        } else { // 如果调用者 在entity类中没有写 @MyTable(tableName = "t_person")注解将执行如下代码
            throw new MyException(-1, "表不存在！");
        }
    }

    @Override
    public void update(Object obj) throws MyException {
        // update tableName set stu_name = ?, stu_age = ? where stu_id = ?
        // 1. 获取参数对应类的字节码
        Class clazz = obj.getClass();

        // 2. StringBuffer拼接sql，分三段
        StringBuffer sql = new StringBuffer("update ");
        StringBuffer sqlColumn = new StringBuffer(" set ");
        StringBuffer sqlKey = new StringBuffer("where ");

        List<Object> params = new ArrayList<>(); // 存放执行sql的参数，后面需要执行sql

        // 2.1 获取表名 ，update tableName set
        MyTable myTable = (MyTable) clazz.getAnnotation(MyTable.class);
        System.out.println("myTable打印：" + myTable);
        if (myTable != null) {
            String tableName = myTable.tableName();
            sql.append(tableName);

            // 2.2 获取需要更新的字段(属性)名上的注解，set_name = ?,
            Field[] declaredFields = clazz.getDeclaredFields();

            long keyValue = 0; // 用于保存主键值，主键值最后插入参数列表中
            for (Field field : declaredFields) {

                field.setAccessible(true);

                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    Object value = null;

                    try {
                        value = field.get(obj);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    // 如果是属性
                    if (annotation instanceof MyColumn && value != null) {
                        MyColumn myColumn = (MyColumn) annotation;
                        String columnName = myColumn.columnName();
                        sqlColumn.append(columnName).append(" = ?, ");

                        params.add(value); // 添加对应参数
                    } else if (annotation instanceof MyKey) { // 如果是主键
                        // 2.3 获取主键名，where stu_id = ?
                        MyKey myKey = (MyKey) annotation;
                        String columnName = myKey.columnName();
                        sqlKey.append(columnName).append(" = ?");

                        keyValue = (long) value; // 接收主键值
                        // params.add(value);
                    }
                }
            }

            sqlColumn.deleteCharAt(sqlColumn.lastIndexOf(","));

            // 3. List params存放参数
            params.add(keyValue);

            // 最后拼接
            sql.append(sqlColumn).append(sqlKey);
            System.out.println("输出整条sql：" + sql);
            System.out.println("sql中的参数：" + params.toString());

            // 4. 执行sql
            executeSql(sql.toString(), params.toArray());

        } else { // 如果调用者 在entity类中没有写 @MyTable(tableName = "t_person")注解将执行如下代码
            throw new MyException(-1, "表不存在！");
        }
    }

    @Override
    public void delete(Object obj) throws MyException {
        // delete from t_student where stu_id = ?
        // 1. 获取参数对应类字节码
        Class clazz = obj.getClass();

        // 2. StringBuffer拼接
        StringBuffer sql = new StringBuffer("delete from ");
        StringBuffer sqlKey = new StringBuffer(" where ");
        List params = new ArrayList<>();

        // 2.1 获取表名
        MyTable myTable = (MyTable) clazz.getAnnotation(MyTable.class);
        System.out.println("myTable打印：" + myTable);
        if (myTable != null) {
            String tableName = myTable.tableName();
            sql.append(tableName);

            // 2.2 获取主键
            Field[] declaredFields = clazz.getDeclaredFields();

            for (Field field : declaredFields) {
                field.setAccessible(true);

                Annotation[] annotations = field.getAnnotations();

                for (Annotation annotation : annotations) {
                    Object value = null;

                    try {
                        value = field.get(obj);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    if (annotation instanceof MyKey) { // 如果是主键
                        //
                        MyKey myKey = (MyKey) annotation;
                        String columnName = myKey.columnName();
                        sqlKey.append(columnName).append(" = ?");

                        params.add(value); // 添加主键参数值
                    }
                }
            }

            sql.append(sqlKey);
            System.out.println("输出整条sql：" + sql);
            System.out.println("sql中的参数：" + params.toString());

            executeSql(sql.toString(), params.toArray());
        } else {
            throw new MyException(-1, "表不存在!");
        }
    }

    @Override
    public Object getObjectById(Object key, Class clazz) throws MyException {
        // select * from t_student where stu_id = ?
        // select stu_id stuId, stu_name stuName, stu_no stuNo, stu_age stuAge,
        // stu_birth stuBirth from t_student where stu_id = ?

        // 1. Stringbuffer 拼接sql
        StringBuffer sql = new StringBuffer("select ");
        StringBuffer sqlTable = new StringBuffer(" from ");
        StringBuffer sqlKey = new StringBuffer(" where ");

        List<Map<String, Object>> result = null;

        // 2. 获取表名
        MyTable myTable = (MyTable) clazz.getAnnotation(MyTable.class);
        System.out.println("myTable打印：" + myTable);

        if (myTable != null) {
            String tableName = myTable.tableName();
            sqlTable.append(tableName);

            // 3. 获取属性
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                String name = field.getName(); // 获得属性名

                Annotation[] annotations = field.getAnnotations();
                for (Annotation annotation : annotations) {
                    if (annotation instanceof MyColumn) {
                        MyColumn myColumn = (MyColumn) annotation;
                        String columnName = myColumn.columnName();

                        sql.append(columnName).append(" " + name).append(", ");
                    } else if (annotation instanceof MyKey) { // 4. 添加主键参数
                        MyKey myKey = (MyKey) annotation;
                        String columnName = myKey.columnName();

                        sql.append(columnName).append(" " + name).append(", "); // 前后查询字段 和 条件 都需要拼接

                        sqlKey.append(columnName).append(" = ");
                        sqlKey.append(" ?"); // 拼接主键值
                    }
                }
            }

            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(sqlTable).append(sqlKey);
            System.out.println("输出整条sql：" + sql);

            // 5. 执行
            result = executeSelect(sql.toString(), new Object[] { key });
        } else {
            throw new MyException(-1, "表不存在！");
        }

        return result;
    }

    @Override
    public Long executeSql(String sql, Object[] params, boolean isGenerateKey) {
        long result = -1; // 用于执行成功后接收返回的行数，否则返回-1
        Connection connOne = DbUtil.getConnOne(); // 与ServiceProxy代理类中是同一个连接

        PreparedStatement prstmt = null;
        ResultSet generatedKeys = null;
        try {
            if (isGenerateKey) { // 需要返回主键的情况
                prstmt = connOne.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                prstmt = connOne.prepareStatement(sql);
            }

            for (int i = 0; i < params.length; i++) {
                prstmt.setObject(i + 1, params[i]);
            }

            result = prstmt.executeUpdate(); // 执行sql

            if (isGenerateKey) {
                generatedKeys = prstmt.getGeneratedKeys();
                while (generatedKeys.next()) {
                    Object object = generatedKeys.getObject(1); // 只有一个自增长主键
                    result = (long) object;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connOne.rollback(); // 事务回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            DbUtil.close(null, prstmt, generatedKeys);
        }

        return result;
    }

    @Override
    public Long executeSql(String sql, Object[] params, boolean isGenerateKey, boolean isTransaction) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long executeSql(List<Map<String, Object>> sqls) {
        String sql = "";
        Object[] params = new Object[] {};
        for (Map<String, Object> map : sqls) {
            sql = (String) map.get("sql");
            params = (Object[]) map.get("params");
            executeSql(sql, params, false);
        }

        try {
            DbUtil.getConnOne().commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DbUtil.closeOne();
        }

        return 0L;
    }

    @Override
    public Long executeSql(String sql, Object[] params, boolean isGenerateKey, Connection conn) {
        long result = -1; // 用于执行成功后接收返回的行数，否则返回-1
        boolean flag = false; // 标志是否需要同时执行多条sql，用于控制事务提交

        if (conn == null) { // 表示只有一条sql执行
            conn = DbUtil.getConn();
        } else {
            flag = true; // 标志，下面需要开启手动事务，并提交，且conn资源在此方法外部由调用者关闭
            try {
                conn.setAutoCommit(false);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        PreparedStatement prstmt = null;
        ResultSet generatedKeys = null;
        try {
            if (isGenerateKey) { // 需要返回主键的情况
                prstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                prstmt = conn.prepareStatement(sql);
            }

            for (int i = 0; i < params.length; i++) {
                prstmt.setObject(i + 1, params[i]);
            }

            result = prstmt.executeUpdate(); // 执行sql

            if (isGenerateKey) {
                generatedKeys = prstmt.getGeneratedKeys();
                while (generatedKeys.next()) {
                    Object object = generatedKeys.getObject(1); // 只有一个自增长主键
                    result = (long) object;
                }
            }

            if (!flag) { // 最后由调用者提交事务
                conn.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback(); // 事务回滚
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            if (flag) {
                DbUtil.close(null, prstmt, generatedKeys);
            } else {
                DbUtil.close(conn, prstmt, generatedKeys);
            }
        }

        return result;
    }

    @Override
    public Long executeSql(String sql, Object[] params) {

        return executeSql(sql, params, false);
    }

    @Override
    public Long execSql(String sql, Object... params) {
        // TODO Auto-generated method stub
        return executeSql(sql, params);
    }
}
