package in.co.techm.ifsc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by turing on 9/4/16.
 */
public class BaseBean implements Parcelable {
    private String message;

    protected BaseBean(Parcel in) {
        message = in.readString();
    }

    public static final Creator<BaseBean> CREATOR = new Creator<BaseBean>() {
        @Override
        public BaseBean createFromParcel(Parcel in) {
            return new BaseBean(in);
        }

        @Override
        public BaseBean[] newArray(int size) {
            return new BaseBean[size];
        }
    };

    public BaseBean() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(message);
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
