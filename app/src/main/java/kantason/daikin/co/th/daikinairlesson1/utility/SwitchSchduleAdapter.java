package kantason.daikin.co.th.daikinairlesson1.utility;

import android.content.Context;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import kantason.daikin.co.th.daikinairlesson1.R;

public class SwitchSchduleAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> stringArrayList;

    public SwitchSchduleAdapter(Context context, ArrayList<String> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
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
        View view = layoutInflater.inflate(R.layout.listview_switch_schdule, parent, false);

        SwitchCompat switchCompat = view.findViewById(R.id.SwitchEN);
        int i = Integer.parseInt(stringArrayList.get(position).trim());

        if (i == 1) {
            switchCompat.setChecked(true);
        } else {
            switchCompat.setChecked(false);
        }


        return view;
    }
}
