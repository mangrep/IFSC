package in.co.techm.ifsc.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by turing on 10/4/16.
 */
public class BankDetails implements Parcelable {
    private String _id;
    private String STATE;
    private String BANK;
    private String IFSC;
    private String MICRCODE;
    private String BRANCH;
    private String CONTACT;
    private String ADDRESS;
    private String CITY;
    private String DISTRICT;

    public BankDetails() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getBANK() {
        return BANK;
    }

    public void setBANK(String BANK) {
        this.BANK = BANK;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public String getMICRCODE() {
        return MICRCODE;
    }

    public void setMICRCODE(String MICRCODE) {
        this.MICRCODE = MICRCODE;
    }

    public String getBRANCH() {
        return BRANCH;
    }

    public void setBRANCH(String BRANCH) {
        this.BRANCH = BRANCH;
    }

    public String getCONTACT() {
        return CONTACT;
    }

    public void setCONTACT(String CONTACT) {
        this.CONTACT = CONTACT;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getDISTRICT() {
        return DISTRICT;
    }

    public void setDISTRICT(String DISTRICT) {
        this.DISTRICT = DISTRICT;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.STATE);
        dest.writeString(this.BANK);
        dest.writeString(this.IFSC);
        dest.writeString(this.MICRCODE);
        dest.writeString(this.BRANCH);
        dest.writeString(this.CONTACT);
        dest.writeString(this.ADDRESS);
        dest.writeString(this.CITY);
        dest.writeString(this.DISTRICT);
    }

    protected BankDetails(Parcel in) {
        this._id = in.readString();
        this.STATE = in.readString();
        this.BANK = in.readString();
        this.IFSC = in.readString();
        this.MICRCODE = in.readString();
        this.BRANCH = in.readString();
        this.CONTACT = in.readString();
        this.ADDRESS = in.readString();
        this.CITY = in.readString();
        this.DISTRICT = in.readString();
    }

    public static final Creator<BankDetails> CREATOR = new Creator<BankDetails>() {
        @Override
        public BankDetails createFromParcel(Parcel source) {
            return new BankDetails(source);
        }

        @Override
        public BankDetails[] newArray(int size) {
            return new BankDetails[size];
        }
    };

    @Override
    public String toString() {
        return "BankDetails{" +
                "_id='" + _id + '\'' +
                ", STATE='" + STATE + '\'' +
                ", BANK='" + BANK + '\'' +
                ", IFSC='" + IFSC + '\'' +
                ", MICRCODE='" + MICRCODE + '\'' +
                ", BRANCH='" + BRANCH + '\'' +
                ", CONTACT='" + CONTACT + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", CITY='" + CITY + '\'' +
                ", DISTRICT='" + DISTRICT + '\'' +
                '}';
    }
}
