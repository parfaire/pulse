package info.androidhive.slidingmenu.fragment;

/**
 * Created by Gembloth on 9/2/2014.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.santoso.pramudita.pulse.Earphone;
import com.santoso.pramudita.pulse.R;
import com.santoso.pramudita.pulse.Touch;

public class HomeFragment extends Fragment {
    LinearLayout touch;
    LinearLayout earphone;
    Intent i;
    public HomeFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        touch = (LinearLayout) rootView.findViewById(R.id.linearLayoutTouch);
        earphone = (LinearLayout) rootView.findViewById(R.id.linearLayoutEarphone);
        touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getActivity().getApplicationContext(),Touch.class);
                startActivity(i);
            }
        });
        earphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getActivity().getApplicationContext(),Earphone.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}
