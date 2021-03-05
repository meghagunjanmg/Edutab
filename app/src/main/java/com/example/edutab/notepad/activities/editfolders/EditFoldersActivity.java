package com.example.edutab.notepad.activities.editfolders;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.edutab.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import se.emilsjolander.intentbuilder.IntentBuilder;

/**
 * Created by MohMah on 8/19/2016.
 */
@IntentBuilder
public class EditFoldersActivity extends AppCompatActivity{
	private static final String TAG = "EditFoldersActivity";

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	Adapter adapter;

	static Context context;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_folders);
		EditFoldersActivityIntentBuilder.inject(getIntent(), this);
		context = getApplicationContext();
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				onBackPressed();
			}
		});
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(RecyclerView.VERTICAL);
		mRecyclerView.setLayoutManager(llm);
		adapter = new Adapter(getApplicationContext());
		mRecyclerView.setAdapter(adapter);
		adapter.loadFromDatabase();
	}

	@Override protected void onStart(){
		super.onStart();
		adapter.registerEventBus();
	}

	@Override protected void onStop(){
		super.onStop();
		adapter.unregisterEventBus();
	}

	@Override
	public void onBackPressed() {
		finish();
	}
}
