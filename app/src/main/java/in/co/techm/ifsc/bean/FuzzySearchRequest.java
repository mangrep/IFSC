package in.co.techm.ifsc.bean;

import java.io.Serializable;

/**
 * Created by techm on 05/07/17.
 */

public class FuzzySearchRequest implements Serializable {
    private String bankName;
    private String branchName;
    private String source;
    private int pageLength;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPageLength() {
        return pageLength;
    }

    public void setPageLength(int pageLength) {
        this.pageLength = pageLength;
    }

    @Override
    public String toString() {
        return "FuzzySearchRequest{" +
                "bankName='" + bankName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", source='" + source + '\'' +
                ", pageLength=" + pageLength +
                '}';
    }
}
