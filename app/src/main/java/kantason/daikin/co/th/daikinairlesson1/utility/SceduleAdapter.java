package kantason.daikin.co.th.daikinairlesson1.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kantason.daikin.co.th.daikinairlesson1.R;

public class SceduleAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<String> timeStringArrayList, switchStringArrayList;

    public SceduleAdapter(Context context, ArrayList<String> timeStringArrayList, ArrayList<String> switchStringArrayList) {
        this.context = context;
        this.timeStringArrayList = timeStringArrayList;
        this.switchStringArrayList = switchStringArrayList;
    }

    @Override
    public int getCount() {
        return timeStringArrayList.size();
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
        View view = layoutInflater.inflate(R.layout.listview_scedule, parent, false);

        TextView timeTextView = view.findViewById(R.id.txtShowTime);
        TextView switchTextView = view.findViewById(R.id.txtShowSwitch);

        timeTextView.setText(timeStringArrayList.get(position));
        switchTextView.setText(switchStringArrayList.get(position));

        return view;
    }
}
