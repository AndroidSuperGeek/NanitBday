package com.birthday.nanit.nanitbirthday;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Not finished yet..
 * This fragment will show the view of the bday details from the Edit fragment.
 */
public class ShowBirthdayDetailsFragment extends Fragment {
    private static final String ARG_NAME = "name";
    private static final String ARG_BDAY = "birthday";
    private static final String ARG_IMG = "image";

    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private String mName;
    private String mBirthday;

    private String mImageUrl;

    @BindView(R.id.name)
    TextView nameView;
    @BindView(R.id.birrthday)
    EditText bdayView;

    @BindView (R.id.picture)
    ImageView pictureView;

    @BindView (R.id.showBirthday)
    Button showBdayScreen;

    public ShowBirthdayDetailsFragment() {
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
    public static ShowBirthdayDetailsFragment newinstance(String name, String birthday, String imageUrl) {
        ShowBirthdayDetailsFragment fragment = new ShowBirthdayDetailsFragment ( );
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

        ButterKnife.bind (this,view);

        if ( showBdayScreen != null) {
            showBdayScreen.setOnClickListener (new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    //move to next fragment
                }
            });
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume ( );
    }

}
