package com.birthday.nanit.nanitbirthday;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This fragment will show the view of the bday details from the Edit fragment.
 * Views are not yet connected to real data from Bundle.. just need to get the Bundle and set
 * to the appropriate views.
 *
 * Dynamically UI works and close button.
 * TODO : calcaulate the age and get from array.
 *        UI finish.
 *
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

    @BindView (R.id.close)
    ImageView closeButton;

    @BindView (R.id.photo_chooser)
    ImageView photoChooser;


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
        View view = inflater.inflate (R.layout.fragment_show_birthday_details, container, false);

        ButterKnife.bind (this,view);

        //do the same to match the array of the camera icon
        final TypedArray myImages = getResources ().obtainTypedArray(R.array.bday_details_bg_image);
        final Random random = new Random();

        //Genrate a random index in the range
        int randomInt = random.nextInt(myImages.length());

        // Generate the drawableID from the randomInt
        int drawableID = myImages.getResourceId(randomInt, -1);
        view.setBackgroundResource(drawableID);

        closeButton.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                finish ();
            }
        });

        //step 4. get the result like we did in the previous fragment and set to the ImageView.
        photoChooser.setOnClickListener (new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                startActivity (new Intent (getContext (),ChooseImageActivity.class));
            }
        });

        return view;
    }

    /***
     * Removes this fragment
     */
    private void finish(){
        getActivity().getSupportFragmentManager().popBackStack ();
    }

    @Override
    public void onResume() {
        super.onResume ( );
    }

}
