package kantason.daikin.co.th.daikinairlesson1.fragment;

import android.net.Uri;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import kantason.daikin.co.th.daikinairlesson1.MainActivity;
import kantason.daikin.co.th.daikinairlesson1.R;
import kantason.daikin.co.th.daikinairlesson1.utility.MyConstant;
import kantason.daikin.co.th.daikinairlesson1.utility.PostData;
import me.tankery.lib.circularseekbar.CircularSeekBar;

public class ControlFragment extends Fragment {


    //    Expplicit
    private int powerAnInt = 0;

    private String idString, nameString, ipAddressString, macAddressString;
    private String contentroomtemp,contentIOTString ,powString, stempString, f_rateString,
            f_dirString ,modeString,roomtempString,frateS,fdirS;
    private  int indexInt ;

    private SwitchCompat aSwitch;
    private TextView textView;

    private String[] modeStrings = new String[]{"FAN","Cool","DRY","No Connection"};


    public static ControlFragment controlInstance(String idString,
                                                  String nameString,
                                                  String ipAddressString,
                                                  String macAddressString) {
        ControlFragment controlFragment = new ControlFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", idString);
        bundle.putString("Name", nameString);
        bundle.putString("IpAddress", ipAddressString);
        bundle.putString("MacAddress", macAddressString);
        controlFragment.setArguments(bundle);
        return controlFragment;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Getvalue Argument
        getvalueArgument();

//        GetData IOT
        getDataIOT();

//        getroomtemp
        getroomtemp();
//        Create toolbar

        createToolbar();


//        OnOff Controller
        onOffController();

//        settempup
        settempup();

//        settempdown
        settempdown();


//        FAN Crontroller
        FANCrontroller();


//        Cool Controoler
        coolControoler();

//        dryControler
        dryController();

//        spinRate
        spinRate();

//        Spindir
        spindir();

//        Scduld Controller
        scduldController();

//        Setback
        setback();




    }   // main method

    private void setback() {
        TextView textView = getView().findViewById(R.id.txtSetBack);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment,SetBackFragment.setbackInstance(idString,nameString,ipAddressString,macAddressString))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void scduldController() {
        TextView textView = getView().findViewById(R.id.txtscdule);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, SceduleFragment.sceduleInstance(idString,nameString,ipAddressString,macAddressString))
                        .addToBackStack(null)
                        .commit();


            }
        });
    }

    private void spindir() {
        Spinner spinner = getView().findViewById(R.id.spinnerdir);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fdir = parent.getItemAtPosition(position).toString();
                Log.d("0507V1","selectitem =" + fdir);


                if (fdir.equals("OFF")) {
                    fdirS = "0";
                } else if (fdir.equals("Vertical")) {
                    fdirS = "1";
                } else if (fdir.equals("Horizontal")) {
                    fdirS = "2";
                }else  {
                    fdirS = "3";
                }

                if (fdirS.equals(f_dirString)) {

                }else {
                    MyConstant myConstant = new MyConstant();
                    String urlString = "http://" + ipAddressString +
                            myConstant.getUrlSetfdirString() + fdirS;
                    Log.d("0507V2", "urlsent =" + urlString);


                    myThreadIOT(urlString);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void settempdown() {



            ImageView imageView = getView().findViewById(R.id.imvminus);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int stempInt = Integer.parseInt(stempString.trim());
                    int indexInttemp = stempInt - 1 ;
                    String tempdownString = Integer.toString(indexInttemp);

                    MyConstant myConstant = new MyConstant();
                    String urlString = "http://"+ ipAddressString +
                            myConstant.getUrlSettempString() + tempdownString;
                    Log.d("0407","urlstring = " + urlString);

                    myThreadIOT(urlString);
                    // sent and getdata tempshow
                    getDataIOT();
                }
            });




    }

    private void settempup() {


            ImageView imageView = getView().findViewById(R.id.imvplus);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int stempInt = Integer.parseInt(stempString.trim());
                    int indexInttemp = stempInt + 1 ;
                    String tempupString = Integer.toString(indexInttemp);

                    MyConstant myConstant = new MyConstant();
                    String urlString = "http://"+ ipAddressString +
                            myConstant.getUrlSettempString() + tempupString;
                    Log.d("0407","urlstring = " + urlString);

                    myThreadIOT(urlString);
                // sent and getdata tempshow
                    getDataIOT();

                }
            });



    }

    private void getroomtemp() {
        try {

            PostData postData = new PostData(getActivity());
            postData.execute(contentroomtemp);

            String jsontempString = postData.get();

//            if (jsontempString.equals(null)){
//
//                jsontempString = "[{"+"ret"+":"+"OK"+","+"param"+":"+"{"+"htemp"+":"+"00"+","+"otemp"+":"+"31"+","+"err"+":"+"00"+"}}]";
//
//            }

            jsontempString = "[" + jsontempString + "]";
            Log.d("24MayV2","json = " + jsontempString);

            JSONArray jsontempArray = new JSONArray(jsontempString);
            JSONObject jsontempObject = jsontempArray.getJSONObject(0);

            String paramStringtemp = jsontempObject.getString("param");
            paramStringtemp = "[" + paramStringtemp + "]";

            Log.d("24MayV2","paramStringtemp = " + paramStringtemp);

            JSONArray jsonArray1 = new JSONArray(paramStringtemp);
            JSONObject jsonObjecttemp = jsonArray1.getJSONObject(0);

            roomtempString = jsonObjecttemp.getString("htemp");


            Log.d("24MayV3","Htemp =" + roomtempString);


//            showroomtemp

            showroomtemp();

        }
        catch (Exception a) {
            //e.printStackTrace();
            Log.d("24MayV4","a =" + a.toString());

        roomtempString = "00";


        Log.d("24MayV3","Htemp =" + roomtempString);


//            showroomtemp

        showroomtemp();

        }
    }

    private void showroomtemp() {

        TextView textViewtemp = getView().findViewById(R.id.txtroomTemp);
        textViewtemp.setText(roomtempString + "  " + getString(R.string.unit_rtemp));

    }

    private void spinRate() {
        //        spinRate
        Spinner spinner = getView().findViewById(R.id.spinnerRate);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String frate = parent.getItemAtPosition(position).toString();
                Log.d("0507V1","selectitem =" + frate);


                if (frate.equals("Auto")) {
                    frateS = "A";
                } else if (frate.equals("Silent")) {
                    frateS = "B";
                } else if (frate.equals("Level 1")) {
                    frateS = "3";
                } else if (frate.equals("Level 2")) {
                    frateS = "4";
                } else if (frate.equals("Level 3")) {
                    frateS = "5";
                } else if (frate.equals("Level 4")) {
                    frateS = "6";
                }else  {
                    frateS = "7";
                }

                if (frateS.equals(f_rateString)) {

                }else {
                    MyConstant myConstant = new MyConstant();
                    String urlString = "http://" + ipAddressString +
                            myConstant.getUrlSetfrateString() + frateS;
                    Log.d("0507V2", "urlsent =" + urlString);


                    myThreadIOT(urlString);

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void dryController() {

        ImageView imageView = getView().findViewById(R.id.imvDry);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyConstant myConstant = new MyConstant();
                String urlString = "http://"+ ipAddressString +
                        myConstant.getUrlSetModeString() + "2";
                textView.setText(modeStrings[2]);

                myThreadIOT(urlString);


            }
        });

    }

    private void coolControoler() {
        ImageView imageView = getView().findViewById(R.id.imvCool);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyConstant myConstant = new MyConstant();
                String urlString = "http://"+ ipAddressString +
                        myConstant.getUrlSetModeString() + "1";
                textView.setText(modeStrings[1]);

                myThreadIOT(urlString);


            }
        });
    }

    private void FANCrontroller() {
        ImageView imageView = getView().findViewById(R.id.imvFan);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyConstant myConstant = new MyConstant();
                String urlString = "http://"+ ipAddressString +
                        myConstant.getUrlSetModeString() + "0";
                textView.setText(modeStrings[0]);

                myThreadIOT(urlString);


            }
        });
    }

    private void fanRateController() {

           indexInt = 6;
        //int f_rateInt = Integer.parseInt(f_rateString.trim());
        if (f_rateString.equals("A")) {
            indexInt = 0;
        } else if (f_rateString.equals("B")) {
            indexInt = 1;
        }else if (f_rateString.equals("3")) {
            indexInt = 2;
        }else if (f_rateString.equals("4")) {
            indexInt = 3;
        }else if (f_rateString.equals("5")) {
            indexInt = 4;
        }else if (f_rateString.equals("6")) {
            indexInt = 5;
        }else  {
            indexInt = 6;
        }


        Spinner spinner = getView().findViewById(R.id.spinnerRate);

        MyConstant myConstant = new MyConstant();
        String[] strings = myConstant.getFrateStrings();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,strings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setSelection(indexInt);

    }

    private void getDataIOT() {
        try {

            PostData postData = new PostData(getActivity());
            postData.execute(contentIOTString);

            String jsonString = postData.get();


//            if (jsonString.equals(null)){
//
//                jsonString = "["+"ret"+":"+"OK"+","+"param"+":"+"{"+"pow"+":"+"0"+","+"mode"+":"+"3"+","+"stemp"+":"+"25"+","+"f_rate"+":"+"A"+","+"f_dir"+":"+"0"+","+"alert"+":"+"0"+"}]";
//
//            }

            jsonString = "[" + jsonString + "]";

            Log.d("24MayV2","json = " + jsonString);



            JSONArray jsonArray = new JSONArray(jsonString);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String paramString = jsonObject.getString("param");
            paramString = "[" + paramString + "]";

            Log.d("24MayV2","paramString = " + paramString);

            JSONArray jsonArray1 = new JSONArray(paramString);
            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);

            powString = jsonObject1.getString("pow");
            f_rateString = jsonObject1.getString("f_rate");
            f_dirString = jsonObject1.getString("f_dir");
            stempString = jsonObject1.getString("stemp");
            modeString = jsonObject1.getString("mode");

            Log.d("24MayV3","POW =" + powString);
            Log.d("24MayV3","f_rate =" + f_rateString);
            Log.d("24MayV3","f_dir =" + f_dirString);
            Log.d("24MayV3","stemp =" + stempString);
            Log.d("24MayV3","mode =" + modeString);

            // Show Power
            aSwitch = getView().findViewById(R.id.switchOnOff);
            if (Integer.parseInt(powString) == 1) {
                Log.d("24MayV4", "ON");
                aSwitch.setChecked(true);
            } else {
                Log.d("24MayV4", "OFF");
                aSwitch.setChecked(false);
            }

            //Show mode

            textView = getView().findViewById(R.id.txtMode);
            textView.setText(modeStrings[Integer.parseInt(modeString.trim())]);

//            Show temp
            showTemp();

//            fanDirControoler
            fanDirControoler();

//             FanRate Controller
            fanRateController();






        }
        catch (Exception e) {
            //e.printStackTrace();
            Log.d("24MayV4","e =" + e.toString());

            powString = "0";
            f_rateString = "A";
            f_dirString = "0";
            stempString = "00";
            modeString = "3";

            Log.d("24MayV3","POW =" + powString);
            Log.d("24MayV3","f_rate =" + f_rateString);
            Log.d("24MayV3","f_dir =" + f_dirString);
            Log.d("24MayV3","stemp =" + stempString);
            Log.d("24MayV3","mode =" + modeString);

            // Show Power
            aSwitch = getView().findViewById(R.id.switchOnOff);
            if (Integer.parseInt(powString) == 1) {
                Log.d("24MayV4", "ON");
                aSwitch.setChecked(true);
            } else {
                Log.d("24MayV4", "OFF");
                aSwitch.setChecked(false);
            }

            //Show mode

            textView = getView().findViewById(R.id.txtMode);
            textView.setText(modeStrings[Integer.parseInt(modeString.trim())]);

//            Show temp
            showTemp();

//            fanDirControoler
            fanDirControoler();

//             FanRate Controller
            fanRateController();

        }
    }

    private void showTemp() {
        TextView textView1 = getView().findViewById(R.id.txtTemp);
        textView1.setText(stempString + "  " + getString(R.string.unit_temp));

        CircularSeekBar circularSeekBar = getView().findViewById(R.id.circularSeekbar);

        float currentFloat = (float)((Float.parseFloat(stempString.trim())-18) * 7.1428572);

        circularSeekBar.setProgress(currentFloat);

        circularSeekBar.setOnSeekBarChangeListener(new CircularSeekBar.OnCircularSeekBarChangeListener() {
            @Override
            public void onProgressChanged(CircularSeekBar circularSeekBar, float progress, boolean fromUser) {
                Log.d("24MayV5","Progress = " + progress);
            }

            @Override
            public void onStopTrackingTouch(CircularSeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(CircularSeekBar seekBar) {

            }
        });
    }

    private void fanDirControoler() {

        Spinner spinner = getView().findViewById(R.id.spinnerdir);
        MyConstant myConstant = new MyConstant();
                String[] strings = myConstant.getfDirStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1,strings);
        spinner.setAdapter(stringArrayAdapter);
        spinner.setSelection(Integer.parseInt(f_dirString.trim()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.itemSetup) {
//            To Do
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contentMainFragment,EditAndDelete.editAndDeleteInstance(idString,nameString,ipAddressString,macAddressString))
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);


        inflater.inflate(R.menu.menu_control,menu);
    }

    private void createToolbar() {
        Toolbar toolbar = getView().findViewById(R.id.toolbarControl);

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

    private void getvalueArgument() {
        idString = getArguments().getString("id");
        nameString = getArguments().getString("Name");
        ipAddressString = getArguments().getString("IpAddress");
        macAddressString = getArguments().getString("MacAddress");

        MyConstant myConstantroomtemp = new MyConstant();
        contentroomtemp  = "http://" + ipAddressString +myConstantroomtemp.geturlgetroomtemp();
        MyConstant myConstant = new MyConstant();
        contentIOTString = "http://" + ipAddressString +myConstant.getUrlInfoString();


        Log.d("24MayV2","ContentIOT = " + contentIOTString);

        Log.d("23MayV1","id ==>" + idString);
        Log.d("23MayV1","name ==>" + nameString);
        Log.d("23MayV1","ip ==>" + ipAddressString);
        Log.d("23MayV1","mac ==>" + macAddressString);
    }

    private void onOffController() {



        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyConstant myConstant = new MyConstant();

                if (aSwitch.isChecked()) {
                    powerAnInt = 1;
                } else {
                    powerAnInt = 0;
                }

                Log.d("21MayV1", "powerAnint ==>" + powerAnInt);
                String urlOnOffString = "http://" + ipAddressString + myConstant.getUrlSetPowerString() + Integer.toString(powerAnInt);
                Log.d("24MV1","urlOnOffString = " +urlOnOffString);

//                MyThread IOT
                myThreadIOT(urlOnOffString);

            }   //Onclick

        });


    }   // onoff controler

    private void myThreadIOT(String urlString) {

        try {

            PostData postData = new PostData(getActivity());
            postData.execute(urlString);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        return view;
    }
}
