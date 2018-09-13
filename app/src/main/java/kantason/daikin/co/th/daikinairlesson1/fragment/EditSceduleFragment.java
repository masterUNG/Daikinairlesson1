package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.app.TimePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import kantason.daikin.co.th.daikinairlesson1.utility.MyManage;
import kantason.daikin.co.th.daikinairlesson1.utility.MyOpenHelper;

public class EditSceduleFragment extends Fragment {

    private String idString, nameString, ipAddressString, macAddressString;
    private TextView showTimeTextView;
    private String timeString, powString, stempString, f_rateString,
            f_dirString, modeString, roomtempString, mode_s, frateS, fdirS;
    private int hourAnInt, minusAnInt, secondAnInt;
    private ArrayList<String> dataStringArrayList, currentAirStringArrayList;
    private String currentAirString;
    private String idController;




    public static EditSceduleFragment editSceduleInstant(String airDataString,
                                                         String idString,
                                                         String nameString,
                                                         String ipAddressString,
                                                         String macAddressString,
                                                         String idController) {

        EditSceduleFragment editSceduleFragment = new EditSceduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("AirData", airDataString);
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
        bundle.putString("IdController", idController);
        editSceduleFragment.setArguments(bundle);
        return editSceduleFragment;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        Get Value
        getValue();

        createToolbar();

//        Get Currenttime
        getCurrenttime();

//        SetTime Controller
        setTimeController();

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


    private void getValue() {

        currentAirStringArrayList = new ArrayList<>();
        String airDataString = getArguments().getString("AirData");

        airDataString = airDataString.substring(1, airDataString.length() - 1);
        String[] strings = airDataString.split(",");
        for (String s : strings) {
            currentAirStringArrayList.add(s.trim());
        }
        Log.d("13SepV2", "airDataStringArrayList ==> " + currentAirStringArrayList.toString());

        powString = currentAirStringArrayList.get(1);

        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");
        idController = getArguments().getString("IdController");



    }


    private void setupOnOff() {

        final SwitchCompat switchCompat = getView().findViewById(R.id.switchOnOff);

        int powerInt = Integer.parseInt(currentAirStringArrayList.get(1));
        if (powerInt == 1){
            switchCompat.setChecked(true);
        }


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
        final String[] strings = myConstant.getfDirStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setSelection(Integer.parseInt(currentAirStringArrayList.get(5)));


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                f_dirString = findfDir(strings[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }   // swingspin

    private String findfDir(String string) {

        String resultString = null;

        if (string.equals("OFF")) {
            resultString = "0";
        } else if (string.equals("Vertical")) {
            resultString = "1";
        } else if (string.equals("Horizontal")) {
            resultString = "2";
        } else {
            resultString = "3";
        }

        return resultString;
    }

    private void fanspin() {
        Spinner spinner = getView().findViewById(R.id.spinnerfrate);
        MyConstant myConstant = new MyConstant();
        final String[] strings = myConstant.getFrateStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

        int position = 0;
        String currentString = currentAirStringArrayList.get(4);
        if (currentString.equals("A")) {
            position = 0;
        } else if (currentString.equals("B")) {
            position = 1;
        } else if (currentString.equals("3")) {
            position = 2;
        }else if (currentString.equals("4")) {
            position = 3;
        }else if (currentString.equals("5")) {
            position = 4;
        } else if (currentString.equals("6")) {
            position = 5;
        } else {
            position = 6;
        }

        spinner.setSelection(position);

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
        } else {
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

        int i = Integer.parseInt(currentAirStringArrayList.get(3)) - 18;


        spinner.setSelection(i);

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
        final String[] strings = myConstant.getMode_Strings();


        Log.d("10SepV1", "Selection on modespin --> " + Integer.parseInt(currentAirStringArrayList.get(2)));

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, strings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setSelection(Integer.parseInt(currentAirStringArrayList.get(2)));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                modeString = findMode(strings[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }   // modespin

    private String findMode(String string) {

        String resultString = null;

        if (string.equals("FAN")) {
            resultString = "0";
        } else if (string.equals("Cool")) {
            resultString = "1";
        } else {
            resultString = "2";
        }

        return resultString;
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

//        showTime(timeString);
        showTime(currentAirStringArrayList.get(0));


    }

    private void showTime(String timeString) {

        showTimeTextView = getView().findViewById(R.id.txtShowTime);
        showTimeTextView.setText(timeString);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemEdit) {
            saveController();

            return true;
        }

        if (item.getItemId() == R.id.itemDelete) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveController() {

//        timeString, powString, stempString, f_rateString, f_dirString ,modeString

        dataStringArrayList = new ArrayList<>();
        dataStringArrayList.add(timeString);
        dataStringArrayList.add(powString);
        dataStringArrayList.add(modeString);
        dataStringArrayList.add(stempString);
        dataStringArrayList.add(f_rateString);
        dataStringArrayList.add(f_dirString);


        Log.d("10SepV1", "dataArrayList ==> " + dataStringArrayList.toString());

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                Context.MODE_PRIVATE, null);
                sqLiteDatabase.execSQL("UPDATE controllerTABLE SET AirData='" + dataStringArrayList.toString() + "' WHERE id='" + idController + "'");

//        MyManage myManage = new MyManage(getActivity());
//        myManage.addController(idString, dataStringArrayList.toString(),"1");


        getActivity().getSupportFragmentManager().popBackStack();

    }   // saveController

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_edit_and_delete, menu);

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
