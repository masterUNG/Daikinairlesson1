package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.MyManage;
import kantason.daikin.co.th.daikinairlesson1.utility.MyOpenHelper;

public class EditAndDelete extends Fragment {

    //    Explicit
    private String idString, nameString, ipAddressString, macAddressString;
    private EditText nameEditAText, ipAddressEditText, macAddressEditText;

    public static EditAndDelete editAndDeleteInstance(String idString,
                                                      String nameString,
                                                      String ipAddressString,
                                                      String macAddressString) {
        EditAndDelete editAndDelete = new EditAndDelete();
        Bundle bundle = new Bundle();
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
        editAndDelete.setArguments(bundle);
        return editAndDelete;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Getvalue Agrument
        getvalueAgrument();

//     Show view
        showView();

//        Cerate toolbar

        cerateToolbar();


    }   //main method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.itemDelete) {

            deleteItem();

            return true;
        }
        if (item.getItemId() == R.id.itemEdit) {

            editItim();

            return true;
        }


        return super.onOptionsItemSelected(item);


    }

    private void editItim() {

        nameString = nameEditAText.getText().toString().trim();
        ipAddressString = ipAddressEditText.getText().toString().trim();
        macAddressString = macAddressEditText.getText().toString().trim();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_name);
        builder.setTitle("Confirm Edit");
        builder.setMessage("Name : " + nameString + "\n" +
                "IP Address : " + ipAddressString + "\n" +
                "mac Address :" + macAddressString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SQLiteDatabase sqLiteDatabase = getActivity()
                        .openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING, Context.MODE_PRIVATE, null);
                ContentValues contentValues = new ContentValues();
                contentValues.put("Name", nameString);
                contentValues.put("IPAddress", ipAddressString);
                contentValues.put("MacAddress", macAddressString);

                sqLiteDatabase.update("airTABLE",contentValues,
                        "id" + "=" + idString,null);

                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(null,fragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment,new ListAirFragment())
                        .commit();

                dialog.dismiss();

            }   // Onclick
        });
        builder.show();


    }

    private void deleteItem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_name);
        builder.setTitle("Confirm Delete");
        builder.setMessage("Do you want to delete item ?");
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                myDelete();

                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager.getBackStackEntryCount() > 0) {
                    fragmentManager.popBackStack(null,fragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.contentMainFragment,
                                new ListAirFragment())
                        .commit();

                dialog.dismiss();
            }   // onclick
        });

        builder.show();

    }

    private void myDelete() {
        SQLiteDatabase sqLiteDatabase = getActivity()
                .openOrCreateDatabase(MyOpenHelper.nameDatabaseSTRING,
                        Context.MODE_PRIVATE, null);
        sqLiteDatabase.delete("airTABLE",
                "id" + "=" + idString, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_edit_and_delete, menu);

    }

    private void cerateToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarAddAir);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(nameString);
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Edit and Delete");
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

    private void showView() {
        nameEditAText = getView().findViewById(R.id.edtname);
        ipAddressEditText = getView().findViewById(R.id.edtIPAddress);
        macAddressEditText = getView().findViewById(R.id.edtMacAddress);

        nameEditAText.setText(nameString);
        ipAddressEditText.setText(ipAddressString);
        macAddressEditText.setText(macAddressString);
    }

    private void getvalueAgrument() {
        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");


        Log.d("24MayV1","Mac =" + macAddressString);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_air, container, false);
        return view;
    }
}
