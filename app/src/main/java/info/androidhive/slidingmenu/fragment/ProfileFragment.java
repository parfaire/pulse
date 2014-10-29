package info.androidhive.slidingmenu.fragment;

/**
 * Created by Gembloth on 9/2/2014.
 */

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.santoso.pramudita.pulse.R;
import com.santoso.pramudita.pulse.WebService.Connection;
import com.santoso.pramudita.pulse.WebService.DownloadImageTask;

public class ProfileFragment extends Fragment {
    SharedPreferences prefs;
    EditText edFirstname, edSurname, edDOB, edGender, edMobile, edEmail, edAddress, edEmergencyContact, edEmergencyNumber;
    Button btnEditProfile, btnSaveProfile, btnCancelProfile;
    Context context;
    ImageView imageView;
    public ProfileFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        prefs = getActivity().getSharedPreferences("PULSE", Context.MODE_PRIVATE);
        btnEditProfile = (Button)rootView.findViewById(R.id.btnEditProfile);
        btnSaveProfile = (Button)rootView.findViewById(R.id.btnSaveProfile);
        btnCancelProfile = (Button)rootView.findViewById(R.id.btnCancelProfile);
        edFirstname = (EditText)rootView.findViewById(R.id.edFirstname);
        edSurname = (EditText)rootView.findViewById(R.id.edSurname);
        edDOB = (EditText)rootView.findViewById(R.id.edDob);
        edGender = (EditText)rootView.findViewById(R.id.edGender);
        edMobile = (EditText)rootView.findViewById(R.id.edMobileNumber);
        edEmail = (EditText)rootView.findViewById(R.id.edEmail);
        edAddress = (EditText)rootView.findViewById(R.id.edAddress);
        edEmergencyContact = (EditText)rootView.findViewById(R.id.edEmergencyContact);
        edEmergencyNumber = (EditText)rootView.findViewById(R.id.edEmergencyNumber);
        imageView = (ImageView)rootView.findViewById(R.id.ivPhoto);
        //fetch data from prefs
        String fn,sn,dob,gender,mob,em,addr,econ,eno,pic;
        fn=prefs.getString("fname","");
        sn=prefs.getString("lname","");
        dob=prefs.getString("dob","");
        gender=prefs.getString("gender","");
        mob=prefs.getString("mobile","");
        addr=prefs.getString("address","");
        econ=prefs.getString("econtact","");
        eno=prefs.getString("enumb","");
        em=prefs.getString("email","");
        pic=prefs.getString("picture","");
        //download image
        new DownloadImageTask(imageView).execute(Connection.url + "images/" + pic);
        Log.e("A",Connection.url + "/images/" + pic);
        edFirstname.setText(fn);
        edSurname.setText(sn);
        edDOB.setText(dob);
        edGender.setText(gender);
        edMobile.setText(mob);
        edEmail.setText(em);
        edAddress.setText(addr);
        edEmergencyContact.setText(econ);
        edEmergencyNumber.setText(eno);
        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        btnCancelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edFirstname.setEnabled(false);
                edSurname.setEnabled(false);
                edDOB.setEnabled(false);
                edGender.setEnabled(false);
                edMobile.setEnabled(false);
                edEmail.setEnabled(false);
                edAddress.setEnabled(false);
                edEmergencyContact.setEnabled(false);
                edEmergencyNumber.setEnabled(false);
                btnSaveProfile.setVisibility(View.INVISIBLE);
                btnCancelProfile.setVisibility(View.INVISIBLE);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                edFirstname.setEnabled(true);
                edSurname.setEnabled(true);
                edDOB.setEnabled(true);
                edGender.setEnabled(true);
                edMobile.setEnabled(true);
                edEmail.setEnabled(true);
                edAddress.setEnabled(true);
                edEmergencyContact.setEnabled(true);
                edEmergencyNumber.setEnabled(true);
                btnSaveProfile.setVisibility(View.VISIBLE);
                btnCancelProfile.setVisibility(View.VISIBLE);
            }
        });
        btnCancelProfile.performClick();
        return rootView;
    }

    private SharedPreferences getSharedPreferences(String pulse, int modePrivate) {
        return null;
    }

    private void saveProfile(){
        try {
            String fname, sname, dob, mobile, email, passcode, address, emergencyContact, emergencyNumber;
            fname = edFirstname.getText().toString();
            sname = edSurname.getText().toString();
            dob = edDOB.getText().toString();
            mobile = edMobile.getText().toString();
            email = edEmail.getText().toString();
            address = edAddress.getText().toString();
            emergencyContact = edEmergencyContact.getText().toString();
            emergencyNumber = edEmergencyNumber.getText().toString();
            //new UpdateProfile(context).execute(fname, sname, dob, mobile, email, passcode, address, emergencyContact, emergencyNumber);
            btnCancelProfile.performClick();
        }
        catch(Exception e){

        }

    }
}
