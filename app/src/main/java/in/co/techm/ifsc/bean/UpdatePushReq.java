package in.co.techm.ifsc.bean;

/**
 * Created by turing on 1/8/16.
 */
public class UpdatePushReq {
    private String IMSI;
    private String IMEI;
    private String mobileNumber;
    private String userName;
    private String pushId;
    private String deviceInfo;
    private String carrier;
    private int API_LEVEL;
    private String device;
    private String model;
    private String product;

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public int getAPI_LEVEL() {
        return API_LEVEL;
    }

    public void setAPI_LEVEL(int API_LEVEL) {
        this.API_LEVEL = API_LEVEL;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public String toString() {
        return "UpdatePushReq{" +
                "IMSI='" + IMSI + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", pushId='" + pushId + '\'' +
                ", deviceInfo='" + deviceInfo + '\'' +
                ", carrier='" + carrier + '\'' +
                '}';
    }
}
