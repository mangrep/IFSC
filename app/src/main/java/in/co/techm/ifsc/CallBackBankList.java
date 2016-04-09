package in.co.techm.ifsc;

import in.co.techm.ifsc.bean.BankList;

/**
 * Created by turing on 9/4/16.
 */
public interface CallBackBankList {
    void onSuccessBankList(BankList bankList);

    void onFailureBankList(String message);
}
