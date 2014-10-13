package info.androidhive.slidingmenu.fragment;

/**
 * Created by Gembloth on 9/2/2014.
 */

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.santoso.pramudita.pulse.R;
import com.santoso.pramudita.pulse.WebService.UpdateProfile;

public class ProfileFragment extends Fragment {

    EditText edFirstname, edSurname, edDOB, edGender, edMobile, edEmail, edPasscode, edAddress, edEmergencyContact, edEmergencyNumber;
    Button btnEditProfile, btnSaveProfile, btnCancelProfile;
    Context context;
    public ProfileFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        btnEditProfile = (Button)rootView.findViewById(R.id.btnEditProfile);
        btnSaveProfile = (Button)rootView.findViewById(R.id.btnSaveProfile);
        btnCancelProfile = (Button)rootView.findViewById(R.id.btnCancelProfile);
        edFirstname = (EditText)rootView.findViewById(R.id.edFirstname);
        edSurname = (EditText)rootView.findViewById(R.id.edSurname);
        edDOB = (EditText)rootView.findViewById(R.id.edFirstname);
        edGender = (EditText)rootView.findViewById(R.id.edGender);
        edMobile = (EditText)rootView.findViewById(R.id.edMobileNumber);
        edEmail = (EditText)rootView.findViewById(R.id.edEmail);
        edPasscode = (EditText)rootView.findViewById(R.id.edPasscode);
        edAddress = (EditText)rootView.findViewById(R.id.edAddress);
        edEmergencyContact = (EditText)rootView.findViewById(R.id.edEmergencyContact);
        edEmergencyNumber = (EditText)rootView.findViewById(R.id.edEmergencyNumber);

        btnSaveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveProfile();
            }
        });

        btnCancelProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edFirstname.setFocusable(false);
                edSurname.setFocusable(false);
                edDOB.setFocusable(false);
                edGender.setFocusable(false);
                edMobile.setFocusable(false);
                edEmail.setFocusable(false);
                edPasscode.setFocusable(false);
                edAddress.setFocusable(false);
                edEmergencyContact.setFocusable(false);
                edEmergencyNumber.setFocusable(false);
                btnSaveProfile.setVisibility(View.INVISIBLE);
                btnCancelProfile.setVisibility(View.INVISIBLE);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                edFirstname.setFocusable(true);
                edSurname.setFocusable(true);
                edDOB.setFocusable(true);
                edGender.setFocusable(true);
                edMobile.setFocusable(true);
                edEmail.setFocusable(true);
                edPasscode.setFocusable(true);
                edAddress.setFocusable(true);
                edEmergencyContact.setFocusable(true);
                edEmergencyNumber.setFocusable(true);
                btnSaveProfile.setVisibility(View.VISIBLE);
                btnSaveProfile.setEnabled(true);
                btnCancelProfile.setVisibility(View.VISIBLE);
                btnCancelProfile.setEnabled(true);
            }
        });

        return rootView;
    }

    private void saveProfile(){
        try {
            String fname, sname, dob, mobile, email, passcode, address, emergencyContact, emergencyNumber;
            fname = edFirstname.getText().toString();
            sname = edSurname.getText().toString();
            dob = edDOB.getText().toString();
            mobile = edMobile.getText().toString();
            email = edEmail.getText().toString();
            passcode = edPasscode.getText().toString();
            address = edAddress.getText().toString();
            emergencyContact = edEmergencyContact.getText().toString();
            emergencyNumber = edEmergencyNumber.getText().toString();
            new UpdateProfile(context).execute(fname, sname, dob, mobile, email, passcode, address, emergencyContact, emergencyNumber);
        }
        catch(Exception e){

        }

    }
}
