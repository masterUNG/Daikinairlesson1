package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.nfc.NfcAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.ListAirAdapter;
import kantason.daikin.co.th.daikinairlesson1.utility.MyConstant;
import kantason.daikin.co.th.daikinairlesson1.utility.MyManage;
import kantason.daikin.co.th.daikinairlesson1.utility.MyOpenHelper;
import kantason.daikin.co.th.daikinairlesson1.utility.PostData;
import kantason.daikin.co.th.daikinairlesson1.utility.SwitchAdapter;

public class ListAirFragment extends Fragment {

    private  int amountAnInt;
    private String[] idStrings,nameStrings,ipAddressStrings, macAddressStrings;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();

//        Create listview
        createListview();


//        Switch Off
        switchOff();

//        SwitchOn
        switchOn();

    }   // Main method

    private void myThreadIOT(String urlString) {

        try {

            PostData postData = new PostData(getActivity());
            postData.execute(urlString);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void switchOn() {
        ListView listView =getView().findViewById(R.id.listViewAirOn);
        SwitchAdapter switchAdapter = new SwitchAdapter(getActivity(),
                R.drawable.ic_action_switch_on,amountAnInt);
        listView.setAdapter(switchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyConstant myConstant = new MyConstant();
                String urlString = "http://"+ ipAddressStrings[position] +
                        myConstant.getUrlSetPowerString() + "1";
                Log.d("30AugV1", "url ==" + urlString);

                myThreadIOT(urlString);

            }
        });
    }

    private void switchOff() {
        ListView listView = getView().findViewById(R.id.listViewAirOff);
        SwitchAdapter switchAdapter = new SwitchAdapter(getActivity(),
                R.drawable.ic_action_switch_off,amountAnInt);
        listView.setAdapter(switchAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MyConstant myConstant = new MyConstant();
                String urlString = "http://"+ ipAddressStrings[position] +
                        myConstant.getUrlSetPowerString() + "0";
                Log.d("30AugV1", "url ==" + urlString);

                myThreadIOT(urlString);

            }
        });


    }


    private void createListview() {
        MyManage myManage = new MyManage(getActivity());
        try {

            SQLiteDatabase sqLiteDatabase = getActivity()
                    .openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING, Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM airTABLE", null);
            cursor.moveToFirst();

             idStrings = new String[cursor.getCount()];
             nameStrings = new String[cursor.getCount()];
             ipAddressStrings = new String[cursor.getCount()];
             macAddressStrings = new String[cursor.getCount()];

            amountAnInt = cursor.getCount();

            for (int i = 0; i < cursor.getCount(); i += 1) {

                idStrings[i] = cursor.getString(0);
                nameStrings[i] = cursor.getString(1);
                ipAddressStrings[i] = cursor.getString(2);
                macAddressStrings[i] = cursor.getString(3);

                Log.d("23MayV1","Name["+ i +"] ==> " + nameStrings[i]);
                cursor.moveToNext();
            }
            ListView listView = getView().findViewById(R.id.listViewAir);

//            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
//                    android.R.layout.simple_list_item_1,nameStrings);
//            listView.setAdapter(stringArrayAdapter);

            ArrayList<String> stringArrayList = new ArrayList<>();
            for (int i=0;i<nameStrings.length;i+=1) {
                stringArrayList.add(nameStrings[i]);

            }
            ListAirAdapter listAirAdapter = new ListAirAdapter(getActivity(),stringArrayList);
            listView.setAdapter(listAirAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.contentMainFragment,
                                    ControlFragment.controlInstance(idStrings[position],
                                            nameStrings[position],ipAddressStrings[position],macAddressStrings[position]))
                            .addToBackStack(null)
                            .commit();

                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemAddAir) {
//            to do

            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment, new AddAirFragment())
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_list_air, menu);
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarListAir);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Air Conditioning");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Daikin Air Condition");

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_air, container, false);
        return view;
    }

}
