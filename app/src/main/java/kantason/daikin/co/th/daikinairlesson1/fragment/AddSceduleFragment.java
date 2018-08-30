package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;

public class AddSceduleFragment extends Fragment{

    private String idString, nameString, ipAddressString, macAddressString;
    private TextView showTimeTextView;
    private String timeString;
    private int hourAnInt, minusAnInt, secondAnInt;



    public static  AddSceduleFragment addSceduleInstance(String idString,
                                                         String nameString,
                                                         String ipAddressString,
                                                         String macAddressString) {

        AddSceduleFragment addSceduleFragment = new AddSceduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",idString);
        bundle.putString("Name",nameString);
        bundle.putString("IpAddress",ipAddressString);
        bundle.putString("MacAddress",macAddressString);
        addSceduleFragment.setArguments(bundle);

        return addSceduleFragment;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getValueFromAgument();

        createToolbar();

//        Get Currenttime
        getCurrenttime();

//        SetTime Controller
        setTimeController();


    }   // Main method

    private void setTimeController() {
        Button button = getView().findViewById(R.id.bntSetTime);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTimePickerDialog();


            }
        });
    }

    private void showTimePickerDialog() {

        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        hourAnInt = hourOfDay;
                        minusAnInt = minute;


                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                    calendar.set(Calendar.MINUTE,minute);

                        DateFormat dateFormat = new SimpleDateFormat("HH:MM");
                        timeString = dateFormat.format(calendar.getTime());

                        showTime(timeString);


                    }
                },hourAnInt,minusAnInt,true);
        timePickerDialog.show();

    }   //show timepicker

    private void getCurrenttime() {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        timeString = dateFormat.format(calendar.getTime());

        hourAnInt = calendar.get(Calendar.HOUR_OF_DAY);
        minusAnInt = calendar.get(Calendar.MINUTE);
        secondAnInt = calendar.get(Calendar.SECOND);

        showTime(timeString);
    }

    private void showTime(String timeString) {

        showTimeTextView = getView().findViewById(R.id.txtShowTime);
        showTimeTextView.setText(timeString);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSave) {
            saveController();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveController() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_add_scedule,menu);

    }

    private void getValueFromAgument() {

        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarScedule);

        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(nameString);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle(ipAddressString);


        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        setHasOptionsMenu(true);
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_scedule, container, false);
        return view;
    }
}
