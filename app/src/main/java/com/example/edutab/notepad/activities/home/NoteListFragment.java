package com.example.edutab.notepad.activities.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.edutab.R;
import com.example.edutab.notepad.activities.note.NoteActivityIntentBuilder;
import com.example.edutab.notepad.models.Folder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.raizlabs.android.dbflow.config.FlowManager;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by MohMah on 8/21/2016.
 */
public class NoteListFragment extends Fragment{
	public static final String FOLDER = "FOLDER";

	@BindView(R.id.toolbar) Toolbar mToolbar;
	@BindView(R.id.recycler_view) RecyclerView mRecyclerView;
	@BindView(R.id.new_note) FloatingActionButton mNewNoteFAB;
	@BindView(R.id.zero_notes_view) View zeroNotesView;
	Adapter adapter;
	Folder folder;

	@Nullable @Override public View onCreateView(
	LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState
	){
		FlowManager.init(getContext());
		View view = inflater.inflate(R.layout.fragment_note_list, container, false);
		ButterKnife.bind(this, view);
		folder = getArguments() == null ? null : (Folder) getArguments().getParcelable(NoteListFragment.FOLDER);
		return view;
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
	@Override public void onActivityCreated(@Nullable Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		if (folder != null)
			mToolbar.setTitle("");
		mToolbar.setTitleTextAppearance(getContext(),R.style.TextViewStyleSubHeading);
		mToolbar.setBackgroundColor(Color.WHITE);
		mToolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				((HomeActivity) getActivity()).mDrawerLayout.openDrawer(Gravity.LEFT);
			}
		});
		StaggeredGridLayoutManager slm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		slm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
		mRecyclerView.setLayoutManager(slm);
		adapter = new Adapter(zeroNotesView, folder);
		mRecyclerView.setAdapter(adapter);
		adapter.loadFromDatabase();
	}

	@OnClick(R.id.new_note) void clickNewNoteButton(){
		Intent intent = new NoteActivityIntentBuilder().build(getContext());
		this.startActivity(intent);
	}

	@Override public void onStart(){
		super.onStart();
		adapter.registerEventBus();
	}

	@Override public void onStop(){
		super.onStop();
		adapter.unregisterEventBus();
	}
}
