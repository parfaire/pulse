package info.androidhive.slidingmenu.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santoso.pramudita.pulse.R;

/**
 * Created by Gembloth on 9/2/2014.
 */
public class AboutFragment extends Fragment {
    public AboutFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        return rootView;
    }
}
