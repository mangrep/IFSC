package in.co.techm.ifsc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by turing on 10/4/16.
 */
public class BankDetailsRes implements Parcelable {
    private String message;
    private BankDetails data;
    private String status;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BankDetailsRes{" +
                "message='" + message + '\'' +
                ", data=" + data +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.message);
        dest.writeParcelable(this.data, flags);
        dest.writeString(this.status);
    }

    public BankDetailsRes() {
    }

    protected BankDetailsRes(Parcel in) {
        this.message = in.readString();
        this.data = in.readParcelable(BankDetails.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Parcelable.Creator<BankDetailsRes> CREATOR = new Parcelable.Creator<BankDetailsRes>() {
        @Override
        public BankDetailsRes createFromParcel(Parcel source) {
            return new BankDetailsRes(source);
        }

        @Override
        public BankDetailsRes[] newArray(int size) {
            return new BankDetailsRes[size];
        }
    };
}
