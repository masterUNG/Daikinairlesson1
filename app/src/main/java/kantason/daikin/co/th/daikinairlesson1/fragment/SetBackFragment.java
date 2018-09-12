package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.MyConstant;
import kantason.daikin.co.th.daikinairlesson1.utility.MyManage;
import kantason.daikin.co.th.daikinairlesson1.utility.MyOpenHelper;


public class SetBackFragment extends Fragment {

    private String idString, nameString, ipAddressString,macAddressString;
    private String settempString;
    private String SetbackAirString,EnableSString;

    public static SetBackFragment setbackInstance (String idString,
                                                   String nameString,
                                                   String ipAddressString,
                                                   String macAddressString ) {

        SetBackFragment setbackFragment = new SetBackFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id",idString);
        bundle.putString("Name",nameString);
        bundle.putString("IpAddress",ipAddressString);
        bundle.putString("MacAddress",macAddressString);
        setbackFragment.setArguments(bundle);

        return setbackFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        getValueFromAgument
        getValueFromAgument();

//        Create Toolbar
        createToolbar();


//        SQLITEREAD
        SQLITEREAD();

//        setback
        setbackspin();


    }  // main methord

    private void SQLITEREAD() {
        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM setbackTABLE", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i += 1) {

            if (idString.equals(cursor.getString(1))) {
                SetbackAirString = cursor.getString(2);
                EnableSString = cursor.getString(3);
            }

            cursor.moveToNext();

        }   // for

        Log.d("12SepV1", "SQLITEREAD");
        Log.d("12SepV1", "SetbackAirString2 ==> " + SetbackAirString);
        Log.d("12SepV1", "EnableSString3 ==> " + EnableSString);

    }

    private void setbackspin() {
        Spinner spinner = getView().findViewById(R.id.spinnersetemp);
        MyConstant myConstant = new MyConstant();
        final String[] strings = myConstant.getSettemp_String();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,strings);
        spinner.setAdapter(stringArrayAdapter);



        final int i = Integer.parseInt(SetbackAirString);
        Log.d("12SepV2", "SetbackAirString ==> " + SetbackAirString);


        spinner.setSelection(i);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // do something
                settempString = strings[position];

                int settint =  Integer.parseInt(settempString)- 20 ;
                String setString = Integer.toString(settint);

                MyManage myManage = new MyManage(getActivity());
                myManage.addSetback(idString, "1",setString.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarSetback);

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

    private void getValueFromAgument() {

        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setback, container, false);
        return view;
    }
}
