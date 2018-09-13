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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.MyOpenHelper;
import kantason.daikin.co.th.daikinairlesson1.utility.SceduleAdapter;
import kantason.daikin.co.th.daikinairlesson1.utility.SwitchAdapter;
import kantason.daikin.co.th.daikinairlesson1.utility.SwitchSchduleAdapter;

public class SceduleFragment extends Fragment {


    private  int amountAnInt;
    private String idString, nameString, ipAddressString, macAddressString;
    private ArrayList<String> enableTStringArrayList;


    public static SceduleFragment sceduleInstance(String idString,
                                                  String nameString,
                                                  String ipAddressString,
                                                  String macAddressString) {

        SceduleFragment sceduleFragment = new SceduleFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
        sceduleFragment.setArguments(bundle);

        return sceduleFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getValueFromAgument();

//        Create Toolbar
        createToolbar();

//        Add Controller
//        addController();

//        Create ListView
        createListView();

//        SwitchEN
        switchEN();

    }  // main methord

    private void switchEN() {
        ListView listView = getView().findViewById(R.id.listViewSwitch);

        SwitchSchduleAdapter switchSchduleAdapter = new SwitchSchduleAdapter(getActivity(), enableTStringArrayList);

        listView.setAdapter(switchSchduleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String[] strings = new String[]{"0", "1"};
                String newSwitch = null;

                Log.d("13SepV1", "Current Switch ==> " + enableTStringArrayList.get(position));
                int i = Integer.parseInt(enableTStringArrayList.get(position).trim());

                switch (i) {
                    case 0:
                        newSwitch = strings[1];
                        addNewSwitchToSQlite(newSwitch, position);
                        break;
                    case 1:
                        newSwitch = strings[0];
                        addNewSwitchToSQlite(newSwitch, position);
                        break;
                }

            }
        });


    }

    private void addNewSwitchToSQlite(String newSwitch, int position) {

        Log.d("13SepV1", "newSwitch ==> " + newSwitch);
        Log.d("13SepV1", "position ==> " + position);

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("UPDATE controllerTABLE SET EnableT='" + newSwitch + "' WHERE id='" + Integer.toString(position + 1) + "'");

        createListView();
        switchEN();

    }

    private void createListView() {
        ListView listView = getView().findViewById(R.id.listViewScedule);

        SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                Context.MODE_PRIVATE, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM controllerTABLE WHERE IdAir='" + idString + "'", null);
        cursor.moveToFirst();

        final ArrayList<String> airDataStringArrayList = new ArrayList<>();
        ArrayList<String> timeStringArrayList = new ArrayList<>();
        ArrayList<String> switchStringArrayList = new ArrayList<>();
        final ArrayList<String> idStringArrayList = new ArrayList<>();
        ArrayList<String> StringArrayList = new ArrayList<>();

        enableTStringArrayList = new ArrayList<>();

        amountAnInt = cursor.getCount();

        for (int i = 0; i < cursor.getCount(); i += 1) {
            airDataStringArrayList.add(cursor.getString(2));
            idStringArrayList.add(cursor.getString(0));
            enableTStringArrayList.add(cursor.getString(3));
            cursor.moveToNext();
        }   // for
        Log.d("10SepV2", "airData ==> " + airDataStringArrayList.toString());


        for (int i = 0; i < airDataStringArrayList.size(); i += 1) {

            ArrayList<String> stringArrayList = new ArrayList<>();
            stringArrayList = myFindArrayList(airDataStringArrayList.get(i));

            timeStringArrayList.add(stringArrayList.get(0));
            switchStringArrayList.add(myFindSwitch(stringArrayList.get(1)));

        }   // for

        SceduleAdapter sceduleAdapter = new SceduleAdapter(getActivity(),
                timeStringArrayList, switchStringArrayList);
        listView.setAdapter(sceduleAdapter);

        Log.d("10SepV2", "idArrayList ==>> " + idStringArrayList.toString());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment,
                                EditSceduleFragment.editSceduleInstant(airDataStringArrayList.get(position),
                                        idString,
                                        nameString,
                                        ipAddressString,
                                        macAddressString,
                                        idStringArrayList.get(position)))
                        .addToBackStack(null)
                        .commit();

            }
        });


    }   // createListView

    private String myFindSwitch(String myString) {

        String[] strings = new String[]{"Off", "On"};
        int i = 0;

        try {

            i = Integer.parseInt(myString.trim());

        } catch (Exception e) {
            i = 0;
        }

        return strings[i];
    }

    private ArrayList<String> myFindArrayList(String arrayString) {

        ArrayList<String> stringArrayList = new ArrayList<>();

        String myString = arrayString.substring(1, arrayString.length() - 1);
        String[] strings = myString.split(",");
        for (String s : strings) {
            stringArrayList.add(s);
        }

        return stringArrayList;
    }

//    private void addController() {
//        Button button = getView().findViewById(R.id.btnAddScedule);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                getActivity()
//                        .getSupportFragmentManager()
//                        .beginTransaction()
//                        .replace(R.id.contentMainFragment, AddSceduleFragment.addSceduleInstance(
//                                idString, nameString, ipAddressString, macAddressString))
//                        .addToBackStack(null)
//                        .commit();
//
//            }
//        });
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemAddSchduleAir) {
//            to do

            getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, AddSceduleFragment.addSceduleInstance(
                                idString, nameString, ipAddressString, macAddressString))
                        .addToBackStack(null)
                        .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_list_add_schdule, menu);
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

    private void getValueFromAgument() {

        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scedule, container, false);
        return view;
    }
}
