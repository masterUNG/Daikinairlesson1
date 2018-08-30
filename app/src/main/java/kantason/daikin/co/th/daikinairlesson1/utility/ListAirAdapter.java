package kantason.daikin.co.th.daikinairlesson1.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kantason.daikin.co.th.daikinairlesson1.R;

public class ListAirAdapter extends BaseAdapter{

        private Context context;
        private ArrayList<String> titleStringArrayList;

    public ListAirAdapter(Context context,
                          ArrayList<String> titleStringArrayList) {
        this.context = context;
        this.titleStringArrayList = titleStringArrayList;
    }

    @Override
    public int getCount() {
        return titleStringArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_air,parent,false);

        TextView textView = view.findViewById(R.id.txtTitle);
        textView.setText(titleStringArrayList.get(position));

        return view;
    }
}
