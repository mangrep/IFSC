package in.co.techm.ifsc.callback;

import in.co.techm.ifsc.bean.BankList;

/**
 * Created by turing on 21/5/16.
 */
public interface BankListLoadedListener {
    void onSuccessBankListLoaded(BankList bankList);

    void onFailureBankListLoaded(String message);
}
