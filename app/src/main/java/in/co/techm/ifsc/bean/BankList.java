package in.co.techm.ifsc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by turing on 9/4/16.
 */
public class BankList implements Parcelable {
    public static final Creator<BankList> CREATOR = new Creator<BankList>() {
        @Override
        public BankList createFromParcel(Parcel in) {
            return new BankList(in);
        }

        @Override
        public BankList[] newArray(int size) {
            return new BankList[size];
        }
    };
    private String[] data;//bank name list
    private String message;
    private String status;

    public BankList() {
        super();
    }

    protected BankList(Parcel in) {
        super();
        data = in.createStringArray();
        message = in.readString();
        status = in.readString();
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(data);
        dest.writeString(message);
        dest.writeString(status);
    }

    @Override
    public String toString() {
        return "BankList{" +
                "data=" + Arrays.toString(data) +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
