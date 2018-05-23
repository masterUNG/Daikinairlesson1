package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kantason.daikin.co.th.daikinairlesson1.R;

public class ControlFragment extends Fragment{


//    Expplicit
    private int powerAnInt = 0;
    private  String urlAir = "http://192.168.1.108/aircon/set_control_info?";
    private  String prePower = "pow=";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        OnOff Controller
        onOffController();


    }   // main method

    private void onOffController() {
        final SwitchCompat switchCompat = getView().findViewById(R.id.switchOnOff);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchCompat.isChecked()) {
                    powerAnInt = 1;
                } else {
                    powerAnInt = 0;
                }

                Log.d("21MayV1","powerAnint ==>" + powerAnInt);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control,container,false);
        return view;
    }
}
