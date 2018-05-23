package kantason.daikin.co.th.daikinairlesson1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kantason.daikin.co.th.daikinairlesson1.fragment.ControlFragment;
import kantason.daikin.co.th.daikinairlesson1.fragment.ListAirFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Add Fragment
            if (savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.contentMainFragment, new ListAirFragment()).commit();
            }
    }   //main method
}   // main class
