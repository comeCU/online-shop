package shop.orm.exception;
/**
 * 自定义异常类
 * @author dong
 * @date Aug 24, 2019 2:04:31 PM
 */
public class MyException extends Exception {
    /**
     * 消息码
     */
    private int code;
    /**
     * 异常消息
     */
    private String message;
    
    public MyException(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
