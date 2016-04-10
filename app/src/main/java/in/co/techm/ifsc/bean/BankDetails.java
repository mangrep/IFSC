package in.co.techm.ifsc.bean;

/**
 * Created by turing on 10/4/16.
 */
public class BankDetails {
    private String STATE;
    private String BANK;
    private String IFSC;
    private String MICRCODE;
    private String BRANCH;
    private String CONTACT;
    private String ADDRESS;
    private String CITY;
    private String DISTRICT;

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
    public String toString() {
        return "BankDetails{" +
                "STATE='" + STATE + '\'' +
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
