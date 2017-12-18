package com.rarahat02.rarahat02.spl3.ui.activity.musician.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.afollestad.materialdialogssample.MyPlayground;
import com.yarolegovich.slidingrootnav.sample.EncryptionClass;

import com.github.rarahat02.instamaterial.R;
import moe.feng.common.stepperview.VerticalStepperItemView;
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes;

public class VerticalStepperDemoFragment extends Fragment
{

	private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[3];
	private Button mNextBtn0, SelectFileButton, mPrevBtn1, mNextBtn2, mPrevBtn2, cancelButton1;


    SharedPreferences sharedpreferences;
    //private String artistCode = "Rahat001";
    private String artistCode, password;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        artistCode = getArguments().getString("artistId");
        password = getArguments().getString("password");
        return inflater.inflate(R.layout.fragment_vertical_stepper, parent, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
    {
		mSteppers[0] = view.findViewById(R.id.stepper_0);
		mSteppers[1] = view.findViewById(R.id.stepper_1);
		mSteppers[2] = view.findViewById(R.id.stepper_2);

		VerticalStepperItemView.bindSteppers(mSteppers);



        final TextFieldBoxes songFullName = view.findViewById(R.id.song_full_name);
        final TextFieldBoxes songVersion = view.findViewById(R.id.song_version);


        songFullName.getEditText().addTextChangedListener(new TextWatcher()
            {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.toString().length() < 10)
                    songFullName.setError("Song title must have atleast 10 characters");
            }
        });



        songVersion.getEditText().addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable.toString().length() > 2)
                    songVersion.setError("Invalid version");
            }
        });





		mNextBtn0 = view.findViewById(R.id.button_file_id_retriever);
		mNextBtn0.setOnClickListener(new View.OnClickListener()
        {
			@Override
			public void onClick(View view)
            {



                if(songFullName.getText().length() < 10 ||
                        //artistFullName.getText().length() < 4 ||
                        songVersion.getText().length() != 2)
                {
                    mSteppers[0].setErrorText("Entry error!");
                }
                else
                {
                    if (mSteppers[0].getErrorText() != null)
                    {
                        mSteppers[0].setErrorText(null);
                    }


                    mSteppers[0].nextStep();
                    mSteppers[0].setTitle("Step 1 is completed. Go on !!!");
                    mSteppers[0].setSummary(                                    //original file name
                            songFullName.getText() + "_" +
                                    //artistFullName.getText().substring(0, 4) + "_" +
                                    songVersion.getText().substring(0, 2));

                    mSteppers[0].setSummaryFinished(                            //truncated file name
                            songFullName.getText().substring(0, 10) +
                            //artistFullName.getText().substring(0, 4) + "_" +
                            songVersion.getText().substring(0, 2)
                    );
                }

			}
		});

        cancelButton1 = view.findViewById(R.id.step1_cancel);
        cancelButton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if (mSteppers[0].getErrorText() != null)
                {
                    mSteppers[0].setErrorText(null);
                }
            }
        });


		mPrevBtn1 = view.findViewById(R.id.button_prev_1);
		mPrevBtn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[1].prevStep();
			}
		});

		SelectFileButton = view.findViewById(R.id.button_next_1);
        
		SelectFileButton.setOnClickListener(new View.OnClickListener() 
        {
			@Override
			public void onClick(View view) 
            {

                MyPlayground.showFinisherDialog(getActivity(), mSteppers[0].getSummary(), mSteppers[0].getSummaryFinished(), artistCode, password);
                mSteppers[1].nextStep();

			}
		});
		
		
		

		mPrevBtn2 = view.findViewById(R.id.button_prev_2);
		mPrevBtn2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[2].prevStep();
			}
		});

		mNextBtn2 = view.findViewById(R.id.button_next_2);
		mNextBtn2.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
;
                SharedPreferences receiver = getActivity().getSharedPreferences(EncryptionClass.MyPREFERENCES, Context.MODE_PRIVATE);

                Snackbar.make(view, receiver.getString(EncryptionClass.ENCRYPTED_FILE_PATH, null), Snackbar.LENGTH_LONG).show();
			}
		});
	}


}
