package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.MyConstant;

public class AddSceduleFragment extends Fragment {

    private String idString, nameString, ipAddressString, macAddressString;
    private TextView showTimeTextView;
    private String timeString, powString, stempString, f_rateString,
            f_dirString, modeString, roomtempString, mode_s, frateS, fdirS;
    private int hourAnInt, minusAnInt, secondAnInt;
    private ArrayList<String> dataStringArrayList;


    public static AddSceduleFragment addSceduleInstance(String idString,
                                                        String nameString,
                                                        String ipAddressString,
                                                        String macAddressString) {

        AddSceduleFragment addSceduleFragment = new AddSceduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
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


//        setsomethimg


//        Mode
        modespin();


//        Temp
        tempspin();


//        Fan
        fanspin();


//        Swing
        swingspin();

//        Setup OnOff
        setupOnOff();


    }   // Main method

    private void setupOnOff() {
        final SwitchCompat switchCompat = getView().findViewById(R.id.switchOnOff);
        switchCompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (switchCompat.isChecked()) {
                    powString = "1";
                } else {
                    powString = "0";
                }

            }
        });
    }

    private void swingspin() {
        Spinner spinner = getView().findViewById(R.id.spinnerfdir);
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getfDirStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

//        spinner.setSelection(Integer.parseInt(f_dirString.trim()));
    }

    private void fanspin() {
        Spinner spinner = getView().findViewById(R.id.spinnerfrate);
        MyConstant myConstant = new MyConstant();
        final String[] strings = myConstant.getFrateStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_rateString = findRate(strings[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private String findRate(String string) {

        String resultString = null;

        if (string.equals("Auto")) {
            resultString = "A";
        } else if (string.equals("Silent")) {
            resultString = "B";
        } else if (string.equals("Level 1")) {
            resultString = "3";
        } else if (string.equals("Level 2")) {
            resultString = "4";
        } else if (string.equals("Level 3")) {
            resultString = "5";
        } else if (string.equals("Level 4")) {
            resultString = "6";
        }else  {
            resultString = "7";
        }


        return resultString;
    }

    private void tempspin() {
        Spinner spinner = getView().findViewById(R.id.spinnertemp);
        MyConstant myConstant = new MyConstant();
        final String[] strings = myConstant.getTemp_String();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stempString = strings[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }   // tempspin

    private void modespin() {
        Spinner spinner = getView().findViewById(R.id.spinnermode);
        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getMode_Strings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);


//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String modes = parent.getItemAtPosition(position).toString();
//
//                if (modes.equals("FAN")) {
//                    mode_s = "0";
//                } else if (modes.equals("Cool")) {
//                    mode_s = "1";
//                } else {
//                    mode_s = "2";
//                }
//
//                if (frateS.equals(modeString)) {
//
//                }
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

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
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                        timeString = dateFormat.format(calendar.getTime());

                        showTime(timeString);


                    }
                }, hourAnInt, minusAnInt, true);
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

//        timeString, powString, stempString, f_rateString, f_dirString ,modeString

        dataStringArrayList = new ArrayList<>();
        dataStringArrayList.add(timeString);
        dataStringArrayList.add(powString);
        dataStringArrayList.add(stempString);
        dataStringArrayList.add(f_rateString);
        dataStringArrayList.add(f_dirString);
        dataStringArrayList.add(modeString);

        Log.d("10SepV1", "dataArrayList ==> " + dataStringArrayList.toString());


    }   // saveController

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_add_scedule, menu);

    }

    private void getValueFromAgument() {

        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");

    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarScedule);

        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
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
