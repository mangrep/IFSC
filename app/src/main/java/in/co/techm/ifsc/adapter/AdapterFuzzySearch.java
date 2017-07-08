package in.co.techm.ifsc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.techm.ifsc.R;
import in.co.techm.ifsc.bean.BankList;

/**
 * Created by livquik on 05/07/17.
 */

public class AdapterFuzzySearch extends RecyclerView.Adapter<AdapterFuzzySearch.ViewHolderQuestions> {
    LayoutInflater mInflater;
    BankList mBankList;


    public AdapterFuzzySearch(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public void updateList(BankList bankList) {
        this.mBankList = bankList;
        notifyDataSetChanged();
    }

    public String getItem(int position){
        return mBankList.getData()[position];
    }

    @Override
    public ViewHolderQuestions onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_search_row, parent, false);
        ViewHolderQuestions viewHolder = new ViewHolderQuestions(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderQuestions holder, int position) {
        String bank = mBankList.getData()[position];
        holder.bankBrachName.setText(bank);
    }


    @Override
    public int getItemCount() {
        if (mBankList == null || mBankList.getData() == null) {
            return 0;
        } else {
            return mBankList.getData().length;
        }

    }

    static class ViewHolderQuestions extends RecyclerView.ViewHolder {
        TextView bankBrachName;

        public ViewHolderQuestions(View itemView) {
            super(itemView);
            bankBrachName = (TextView) itemView.findViewById(R.id.bank_or_branch_name);
        }
    }
}