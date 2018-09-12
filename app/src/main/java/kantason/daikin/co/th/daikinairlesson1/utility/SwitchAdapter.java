package kantason.daikin.co.th.daikinairlesson1.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import kantason.daikin.co.th.daikinairlesson1.R;

public class SwitchAdapter extends BaseAdapter{



    private Context context;
    private int sourceImageAnInt,amountAnInt;


    public SwitchAdapter(Context context, int sourceImageAnInt, int amountAnInt) {
        this.context = context;
        this.sourceImageAnInt = sourceImageAnInt;
        this.amountAnInt = amountAnInt;
    }

    @Override
    public int getCount() {
        return amountAnInt;
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
        View view = layoutInflater.inflate(R.layout.listview_switch, parent, false);



        ImageView imageView = view.findViewById(R.id.imvSwitch);
        imageView.setImageResource(sourceImageAnInt);

        return view;
    }
}
