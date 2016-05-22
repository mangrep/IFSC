package in.co.techm.ifsc.callback;

import in.co.techm.ifsc.bean.BankDetailsRes;

/**
 * Created by turing on 21/5/16.
 */
public interface BankDetailsLoadedListener {
    void onSuccessBankDetailsLoaded(BankDetailsRes bankDetails);

    void onFailureBankDetailsLoaded(String message);
}
