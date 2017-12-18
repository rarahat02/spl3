package com.yarolegovich.slidingrootnav.sample.fragment;


import android.content.pm.PackageManager;

import android.os.Bundle;

import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rarahat02.angads25.filepicker.model.DialogConfigs;
import com.rarahat02.angads25.filepicker.model.DialogProperties;
import com.rarahat02.angads25.filepicker.view.FilePickerDialog;
import com.yarolegovich.slidingrootnav.sample.R;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class EncryptFragment extends Fragment implements Preference.OnPreferenceChangeListener  {

    private static final String EXTRA_TEXT = "text";

    Button encryptButton, decryptButton;
    EditText editText;
    TextView textView;
    public static final int FILE_CODE = 0;

    private static final String TAG = "Encryption";

    private FilePickerDialog dialog;
    DialogProperties properties;

    private TextView mTextView;

    public static final int ENCRYPTION_MODE = 0;
    public static final int DECRYPTION_MODE = 1;
    public static final int INITIAL_MODE = 2;
    int flag;


    String localEncrypted;


    public static Fragment createFor(String text) {
        EncryptFragment fragment = new EncryptFragment();
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

        final LinearLayout frameLayout = (LinearLayout) inflater.inflate(R.layout.fragment_encrypt, container, false);


        encryptButton = (Button) frameLayout.findViewById(R.id.encrypt_button_id);
        decryptButton = (Button) frameLayout.findViewById(R.id.decrypt_button_id);
        editText = (EditText) frameLayout.findViewById(R.id.edit_text_id);
        textView = (TextView) frameLayout.findViewById(R.id.file_path);

        mTextView = (TextView) frameLayout.findViewById(R.id.log_textView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());



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

                    if(flag == ENCRYPTION_MODE)
                    {
                        log(EncryptionClass.encrypt(getActivity(), path, editText.getText().toString()));

                    }
                    else if(flag == DECRYPTION_MODE)
                    {
                        log(EncryptionClass.decrypt(getActivity(), path));

                    }
                    else log("Unknown");



                }
            }
        });*/


        encryptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent i = new Intent(getActivity(),  com.yarolegovich.slidingrootnav.sample.file_picker_pack.FilteredFilePickerActivity.class);
                //Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                flag = ENCRYPTION_MODE;
                dialog.show();


                //getActivity().startActivityForResult(i, FILE_CODE);



            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                flag = DECRYPTION_MODE;
                dialog.show();
            }
        });


        return frameLayout;
    }

    public void log(final String message) {
        Log.d(TAG, message);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.append(message + "\n");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(dialog!=null)
                    {   //Show dialog if the read permission has been granted.
                        dialog.show();
                    }
                }
                else {
                    //Permission has not been granted. Notify the user.
                    Toast.makeText(getActivity(),"Permission is Required for getting list of files",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String text = getArguments().getString(EXTRA_TEXT);

        /*TextView textView = (TextView) view.findViewById(R.id.text);
        textView.setText(text);*/
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
}
