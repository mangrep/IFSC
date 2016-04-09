package in.co.techm.ifsc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Arrays;

/**
 * Created by turing on 9/4/16.
 */
public class BankList extends BaseBean implements Parcelable {
    private String[] data;//bank name list

    protected BankList(Parcel in) {
        super();
        data = in.createStringArray();
    }

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

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(data);
    }

    @Override
    public String toString() {
        return "BankList{" +
                "data=" + Arrays.toString(data) +
                '}';
    }
}
