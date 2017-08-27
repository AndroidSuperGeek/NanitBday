package com.birthday.nanit.nanitbirthday;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Step 1
 */
public class BirthdayDetailsFragment extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_BDAY = "birthday";
    private static final String ARG_IMG = "image";

    // The request code
    private String mName;
    private String mBirthday;

    private String mImageUrl;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.birrthday)
    EditText bdayView;

    @BindView (R.id.picture)
    ImageView pictureView;

    @BindView (R.id.showBirthday)
    Button showBdayScreen;

    private SharedPreferences pref = null;
    private SharedPreferences.Editor editor = null;

    public BirthdayDetailsFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param name Parameter 1.
     * @param birthday Parameter 2.
     * @param imageUrl
     * @return A new instance of fragment BirthdayDetailsFragment.
     */
    public static BirthdayDetailsFragment newinstance(String name, String birthday, String imageUrl) {
        BirthdayDetailsFragment fragment = new BirthdayDetailsFragment ( );
        Bundle args = new Bundle ( );
        args.putString (ARG_NAME, name);
        args.putString (ARG_BDAY, birthday);
        args.putString (ARG_IMG,imageUrl);
        fragment.setArguments (args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        Bundle fragmentArgs= getArguments ();
        if ( fragmentArgs != null) {
            mName = fragmentArgs.getString (ARG_NAME);
            mBirthday = fragmentArgs.getString (ARG_BDAY);
            mImageUrl = fragmentArgs.getString (ARG_IMG);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate (R.layout.fragment_birthday_details, container, false);

        pref = getContext ().getApplicationContext ().getSharedPreferences("NanitSharedPref", 0); // 0 - for private mode
        editor = pref.edit();


        ButterKnife.bind (this,view);
        showBdayScreen.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                saveDetails();
                FragmentManager manager = getFragmentManager ();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.contentFrame, new ShowBirthdayDetailsFragment ()).commit();
            }
        });

        name.addTextChangedListener (mTextWatcher);
        bdayView.addTextChangedListener (mTextWatcher);

        bdayView.setOnClickListener(new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext (), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        pictureView.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                startActivity (new Intent (getContext (),ChooseImageActivity.class));
            }
        });
        return view;
    }

    private void saveDetails() {
        editor.putString (NanitSharedPref.NAME,name.getText ().toString ());
        editor.putString (NanitSharedPref.BDAY,bdayView.getText ().toString ());
        editor.putString (NanitSharedPref.PIC_URL,mImageUrl);
    }

    @Override
    public void onStart() {
        LocalBroadcastManager.getInstance(getContext ()).registerReceiver(
                mMessageReceiver, new IntentFilter (NanitConstants.PHOTO_CHOOSE));
        super.onStart ();
    }

    public void onDetach() {
//        LocalBroadcastManager.getInstance(getContext ()).unregisterReceiver(mMessageReceiver);
        super.onDetach ();
    }


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    void checkFieldsForEmptyValues(){
        String s1 = name.getText().toString();
        String s2 = bdayView.getText().toString();

        if(s1.equals("")|| s2.equals("")){
            showBdayScreen.setEnabled(false);
        } else {
            showBdayScreen.setEnabled(true);
        }
    }

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateView ();
        }

    };

    private void updateView() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        bdayView.setText(sdf.format(myCalendar.getTime()));
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            Bitmap photo = (Bitmap) intent.getParcelableExtra (NanitConstants.PhotoKey);
            pictureView.setImageBitmap (photo);
        }
    };


}
