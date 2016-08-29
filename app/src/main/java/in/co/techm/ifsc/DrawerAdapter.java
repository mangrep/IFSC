package in.co.techm.ifsc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by turing on 22/7/16.
 */
public class DrawerAdapter extends ArrayAdapter<String> {
    private String[] mDdrawerMenuList;
    private Context mContext;

    public DrawerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        mDdrawerMenuList = objects;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_drawer_menu, parent, false);
        }
//        ImageView optionThumbnail = (ImageView) convertView.findViewById(R.id.option_image);
        TextView optionName = (TextView) convertView.findViewById(R.id.option_name);
        optionName.setText(getItem(position));
        return convertView;
    }
}
