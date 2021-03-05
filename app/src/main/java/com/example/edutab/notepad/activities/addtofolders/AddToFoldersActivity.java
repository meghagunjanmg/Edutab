package com.example.edutab.notepad.activities.addtofolders;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.example.edutab.R;
import com.example.edutab.activity_subjects_lists;
import com.example.edutab.notepad.activities.home.HomeActivity;
import com.example.edutab.notepad.events.NoteFoldersUpdatedEvent;
import com.raizlabs.android.dbflow.config.FlowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import org.greenrobot.eventbus.EventBus;
import se.emilsjolander.intentbuilder.Extra;
import se.emilsjolander.intentbuilder.IntentBuilder;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.example.edutab.notepad.utils.ViewUtils.tintDrawable;

/**
 * Created by MohMah on 8/21/2016.
 */
@IntentBuilder
public class AddToFoldersActivity extends AppCompatActivity{
	private static final String TAG = "AddToFoldersActivity";

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	Adapter adapter;
	@Extra Integer noteId;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_of_folders);
		AddToFoldersActivityIntentBuilder.inject(getIntent(), this);
		ButterKnife.bind(this);
		setSupportActionBar(mToolbar);
		mToolbar.setTitle("Add to folders");
		mToolbar.setNavigationIcon(tintDrawable(R.drawable.ic_save_white_24dp, R.color.md_white_1000,getApplicationContext()));
		mToolbar.setTitle("Done");
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				finish();
			}
		});
		FlowManager.init(getApplicationContext());
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(RecyclerView.VERTICAL);
		mRecyclerView.setLayoutManager(llm);
		adapter = new Adapter(noteId,getApplicationContext());
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
		EventBus.getDefault().post(new NoteFoldersUpdatedEvent(noteId));
	}
}
