package in.co.techm.ifsc.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import in.co.techm.ifsc.R;

/**
 * Created by turing on 26/7/16.
 */
public class CustomAdapter extends ArrayAdapter<String> {
    List<String> mBankList;
    List<String> mOriginalList;

    public CustomAdapter(Context context, List<String> bankList) {
        super(context, 0, bankList);
        this.mBankList = bankList;
    }

    public void setmOriginalList(List<String> mOriginalList) {
        this.mOriginalList = mOriginalList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_bank, parent, false);
        }
        TextView bankName = (TextView) convertView.findViewById(R.id.bankName);
        bankName.setText(getItem(position));
        return convertView;
    }

    @Override
    public String getItem(int position) {
        return mBankList.get(position);
    }

    @Override
    public int getCount() {
        if (mBankList == null || mBankList.size() == 0) {
            return 0;
        } else {
            return mBankList.size();
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                constraint = constraint.toString().toLowerCase();
                FilterResults result = new FilterResults();

                if (constraint != null && constraint.toString().length() > 0) {
                    List<String> founded = new ArrayList<String>();
                    List<String> likeList = new ArrayList<String>();

                    for (String item : mOriginalList) {
                        if (item.toLowerCase().startsWith(constraint.toString())) {
                            founded.add(item);//exact match
                        } else if (item.toLowerCase().contains(constraint.toString())) {
                            likeList.add(item);//like match
                        }
                    }
                    Collections.sort(likeList);//sort like match
                    founded.addAll(likeList);//append like match at the end of exact matched list

                    result.values = founded;
                    result.count = founded.size();
                } else {
                    result.values = mOriginalList;
                    result.count = mOriginalList.size();
                }
                return result;
            }

            @Override
            protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
                clear();
                for (String item : (List<String>) results.values) {
                    add(item);
                }
                notifyDataSetChanged();
            }
        };
    }
}
