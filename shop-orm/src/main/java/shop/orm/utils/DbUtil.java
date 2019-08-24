package shop.orm.utils;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 数据库连接工具类
 * @author dong
 * @date Aug 24, 2019 1:58:44 PM
 */
public class DbUtil {

    static String url = "jdbc:mysql://127.0.0.1:3306/jdbc2";
    static String username = "root";
    static String pwd = "16050555108";
    static String driver = "com.mysql.jdbc.Driver";
    static final ThreadLocal<Connection> connOne = new ThreadLocal<>(); // 用于获取同一个数据库连接对象

    // 加载驱动
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return 数据库连接
     */
    public static Connection getConn() {
        try {
            return DriverManager.getConnection(url, username, pwd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取ThreadLocal<Connection>中同一个数据库连接
     * @return
     */
    public static Connection getConnOne() {
        try {
            Connection conn = connOne.get();
            if (conn == null) {
                conn = DriverManager.getConnection(url, username, pwd);
                connOne.set(conn);
            }

            return conn;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源
     * @param conn
     * @param stme
     * @param rs
     */
    public static void close(Connection conn, Statement stme, ResultSet rs) {
        try {
            if (conn != null) {
                conn.close();
            }
            if (stme != null) {
                stme.close();
            }
            if (rs != null) {
                rs.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除ThreadLocal<Connection>中连接
     */
    public static void closeOne() {
        Connection conn = connOne.get();
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        connOne.remove();
    }

    public static void close(Connection conn) {
        DbUtil.close(conn, null, null);
    }

    public static void close(Connection conn, Statement stmt) {
        DbUtil.close(conn, stmt, null);
    }
}
