package info.androidhive.slidingmenu.fragment;

/**
 * Created by Gembloth on 9/2/2014.
 */

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.santoso.pramudita.pulse.Earphone;
import com.santoso.pramudita.pulse.R;
import com.santoso.pramudita.pulse.Touch;

public class HomeFragment extends Fragment {
    LinearLayout touch,earphone,call;
    Button btnStart;
    Intent i;
    String trigger;
    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        btnStart = (Button) rootView.findViewById(R.id.btnStart);
        touch = (LinearLayout) rootView.findViewById(R.id.linearLayoutTouch);
        earphone = (LinearLayout) rootView.findViewById(R.id.linearLayoutEarphone);
        call = (LinearLayout) rootView.findViewById(R.id.linearLayoutCall);
        btnStart.setEnabled(false);
        btnStart.setTextColor(Color.WHITE);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger = "CALL";
                earphone.setBackgroundColor(Color.TRANSPARENT);
                touch.setBackgroundColor(Color.TRANSPARENT);
                call.setBackgroundColor(Color.rgb(66, 140, 255));
                btnStart.setBackgroundColor(Color.rgb(66, 140, 255));
                btnStart.setEnabled(true);

            }
        });
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger = "TOUCH";
                call.setBackgroundColor(Color.TRANSPARENT);
                earphone.setBackgroundColor(Color.TRANSPARENT);
                touch.setBackgroundColor(Color.rgb(66, 140, 255));
                btnStart.setBackgroundColor(Color.rgb(66, 140, 255));
                btnStart.setEnabled(true);

            }
        });
        earphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trigger = "EARPHONE";
                call.setBackgroundColor(Color.TRANSPARENT);
                touch.setBackgroundColor(Color.TRANSPARENT);
                earphone.setBackgroundColor(Color.rgb(66,140,255));
                btnStart.setBackgroundColor(Color.rgb(66, 140, 255));
                btnStart.setEnabled(true);

            }
        });
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trigger.equals("TOUCH")){
                    i = new Intent(getActivity().getApplicationContext(),Touch.class);
                    startActivity(i);
                }else if(trigger.equals("EARPHONE")){
                    i = new Intent(getActivity().getApplicationContext(),Earphone.class);
                    startActivity(i);
                }else{
                    Intent callPolice = new Intent(Intent.ACTION_CALL, Uri.parse("tel:000"));
                    startActivity(callPolice);
                }

            }
        });
        return rootView;
    }
}
