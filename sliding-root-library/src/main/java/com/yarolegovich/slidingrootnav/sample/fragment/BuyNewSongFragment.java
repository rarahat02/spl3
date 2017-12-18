package com.yarolegovich.slidingrootnav.sample.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.preference.Preference;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rarahat02.angads25.filepicker.model.DialogConfigs;
import com.rarahat02.angads25.filepicker.model.DialogProperties;
import com.rarahat02.angads25.filepicker.view.FilePickerDialog;
import com.yarolegovich.slidingrootnav.sample.R;

import java.io.File;


public class BuyNewSongFragment extends Fragment implements Preference.OnPreferenceChangeListener
{


    private static final String EXTRA_TEXT = "text";



    Button buySongButton;
    EditText editText;
    TextView textView;
    public static final int FILE_CODE = 0;

    private static final String TAG = "Encryption";

    private FilePickerDialog dialog;
    DialogProperties properties;

    //private TextView mTextView;


    public static Fragment createFor(String text) {
        BuyNewSongFragment fragment = new BuyNewSongFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        final LinearLayout frameLayout = (LinearLayout) inflater.inflate(R.layout.fragment_buy_new_song, container, false);


        buySongButton = (Button) frameLayout.findViewById(R.id.buysong_button_id);
/*        decryptButton = (Button) frameLayout.findViewById(R.id.decrypt_button_id);
        editText = (EditText) frameLayout.findViewById(R.id.edit_text_id);
        textView = (TextView) frameLayout.findViewById(R.id.file_path);

        mTextView = (TextView) frameLayout.findViewById(R.id.log_textView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());*/



        properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File("/");
        properties.error_dir = new File("/storage");
        properties.offset = new File("/storage");
        properties.extensions = new String[]{".mp3"};

        dialog = new FilePickerDialog(getActivity(),properties);
        dialog.setTitle("Select a File");

/*        dialog.setDialogSelectionListener(new DialogSelectionListener() {
            @Override
            public String onSelectedFilePaths(String[] files)
            {
                for (String path : files)
                {
                    //Toast.makeText(getActivity(), path, Toast.LENGTH_SHORT).show();

                   *//* if(flag == ENCRYPTION_MODE)
                    {
                        log(EncryptionClass.encrypt(getActivity(), path, editText.getText().toString()));

                    }
                    else if(flag == DECRYPTION_MODE)
                    {
                        log(EncryptionClass.decrypt(getActivity(), path));

                    }
                    else log("Unknown");*//*



                }
            }
        });*/


        buySongButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent i = new Intent(getActivity(),  com.yarolegovich.slidingrootnav.sample.file_picker_pack.FilteredFilePickerActivity.class);
                //Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                //flag = ENCRYPTION_MODE;
                dialog.show();


                //getActivity().startActivityForResult(i, FILE_CODE);



            }
        });
/*

        decryptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                flag = DECRYPTION_MODE;
                dialog.show();
            }
        });
*/


        return frameLayout;
    }









    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue)
    {
        if(preference.getKey().equals("directories"))
        {   String value=(String)newValue;
            String arr[]=value.split(":");
            return true;

            /*for (String item: arr)
            {
                if(item.equals())
                {

                }
            }*/

        }
        return false;
    }






























    /* // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String EXTRA_TEXT = "text";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PlusOneButton mPlusOneButton;

    private OnFragmentInteractionListener mListener;

    public static Fragment createFor(String text) {
        BuyNewSongFragment fragment = new BuyNewSongFragment();
        Bundle args = new Bundle();
        args.putString(EXTRA_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    *//**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BuyNewSongFragment.
     *//*
    // TODO: Rename and change types and number of parameters
    public static BuyNewSongFragment newInstance(String param1, String param2) {
        BuyNewSongFragment fragment = new BuyNewSongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy_new_song, container, false);

        //Find the +1 button
        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
*/
}
