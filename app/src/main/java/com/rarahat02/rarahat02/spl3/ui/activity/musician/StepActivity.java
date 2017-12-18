package com.rarahat02.rarahat02.spl3.ui.activity.musician;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.rarahat02.instamaterial.R;
import com.rarahat02.rarahat02.spl3.ui.activity.musician.fragment.VerticalStepperDemoFragment;


public class StepActivity extends AppCompatActivity
{

	private String artistId, password;

	private Fragment mVerticalStepperDemoFragment = new VerticalStepperDemoFragment();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_step);

		Bundle extras = getIntent().getExtras();

		if (extras != null)
		{
			this.artistId = extras.getString("artistId");
			this.password = extras.getString("password");

		}

		Toolbar toolbar = findViewById(R.id.toolbar);
		toolbar.setTitle("Publish File");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		assert actionBar != null;
		actionBar.setDisplayHomeAsUpEnabled(true);


		Bundle bundle = new Bundle();
		bundle.putString("artistId", artistId);
		bundle.putString("password", password);
		mVerticalStepperDemoFragment.setArguments(bundle);

		getSupportFragmentManager().beginTransaction()
				.replace(R.id.container, mVerticalStepperDemoFragment).commit();


	}

}
