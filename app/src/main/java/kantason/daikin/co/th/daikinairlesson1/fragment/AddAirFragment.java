package kantason.daikin.co.th.daikinairlesson1.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.MyAlert;
import kantason.daikin.co.th.daikinairlesson1.utility.MyConstant;
import kantason.daikin.co.th.daikinairlesson1.utility.MyManage;
import kantason.daikin.co.th.daikinairlesson1.utility.MyOpenHelper;

public class AddAirFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //create tool bar
        createToolBar();
    }  //  Main metohod

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemSave) {

//            To Do
            checkAndSave();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkAndSave() {
        EditText nameEditText = getView().findViewById(R.id.edtname);
        EditText ipEditText = getView().findViewById(R.id.edtIPAddress);
        EditText macEditText = getView().findViewById(R.id.edtMacAddress);

        String naneString = nameEditText.getText().toString().trim();
        String ipString = ipEditText.getText().toString().trim();
        String macString = macEditText.getText().toString().trim();

        if (naneString.isEmpty() || ipString.isEmpty() || macString.isEmpty()) {
//            Have Space
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Have Space","Please Fill all Blank");
        }
        else {
//        No Space
            MyManage myManage = new MyManage(getActivity());
            myManage.addValue(naneString,ipString,macString);

            SQLiteDatabase sqLiteDatabase = getActivity().openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                    Context.MODE_PRIVATE, null);
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM airTABLE", null);
            cursor.moveToFirst();
            cursor.moveToLast();
            String idUserString = cursor.getString(0);
            MyConstant myConstant = new MyConstant();
            myManage.addController(idUserString, myConstant.getDefaultSchedult(),"1");
            myManage.addSetback(idUserString, "2", "5");


            getActivity().getSupportFragmentManager().popBackStack();


        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_add_air, menu);
    }

    private void createToolBar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarAddAir);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Add Air Condition");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Please Fill all Blank");

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_air, container, false);

        return view;

    }
}
