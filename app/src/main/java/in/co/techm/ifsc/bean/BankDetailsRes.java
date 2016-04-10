package in.co.techm.ifsc.bean;

/**
 * Created by turing on 10/4/16.
 */
public class BankDetailsRes {
    private String message;
    private BankDetails data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BankDetails getData() {
        return data;
    }

    public void setData(BankDetails data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BankDetailsRes{" +
                "message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
