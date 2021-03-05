package com.example.edutab.notepad.activities.home;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.example.edutab.R;
import com.example.edutab.notepad.activities.editfolders.EditFoldersActivityIntentBuilder;
import com.example.edutab.notepad.database.AppDatabase;
import com.example.edutab.notepad.database.FoldersDAO;
import com.example.edutab.notepad.models.Folder;
import com.google.android.material.navigation.NavigationView;
import com.raizlabs.android.dbflow.config.FlowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.ViewTreeObserver;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MohMah on 8/17/2016.
 */
public class HomeActivity extends AppCompatActivity{
	private static final String TAG = "HomeActivity";
	private static final int ALL_NOTES_MENU_ID = -1;

	@BindView(R.id.navigation_view) NavigationView mNavigationView;
	@BindView(R.id.drawer_layout) DrawerLayout mDrawerLayout;
	List<Folder> latestFolders;
	BackupRestoreDelegate backupRestoreDelegate;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		ButterKnife.bind(this);
		mDrawerLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){
			@Override public void onGlobalLayout(){
				mDrawerLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				setFragment(null);
			}
		});
		backupRestoreDelegate = new BackupRestoreDelegate(this);
		if (getIntent().getData() != null) backupRestoreDelegate.handleFilePickedWithIntentFilter(getIntent().getData());
			mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
				@Override public boolean onNavigationItemSelected(MenuItem item){
					Log.e(TAG, "onNavigationItemSelected() called with: " + "item id = [" + item.getItemId() + "]");
					int menuId = item.getItemId();
					if (menuId == ALL_NOTES_MENU_ID){
						setFragment(null);
					}else{
						setFragment(FoldersDAO.getFolder(menuId));
					}
					mDrawerLayout.closeDrawer(Gravity.LEFT);
					inflateNavigationMenus(menuId);
					return true;
				}
			});
	}

	@Override protected void onStart(){
		super.onStart();
		AppDatabase appDatabase = new AppDatabase();
		FlowManager.init(getApplicationContext());
		inflateNavigationMenus(ALL_NOTES_MENU_ID);

	}

	public void inflateNavigationMenus(int checkedItemId){
		Menu menu = mNavigationView.getMenu();
		menu.clear();
		menu
				.add(Menu.NONE, ALL_NOTES_MENU_ID, Menu.NONE, "Notes")
				.setIcon(R.drawable.ic_note_white_24dp)
				.setChecked(checkedItemId == ALL_NOTES_MENU_ID);
		final SubMenu subMenu = menu.addSubMenu("Folders");
		latestFolders = FoldersDAO.getLatestFolders();
		for (Folder folder : latestFolders){
			subMenu
					.add(Menu.NONE, folder.getId(), Menu.NONE, folder.getName())
					.setIcon(R.drawable.ic_folder_black_24dp)
					.setChecked(folder.getId() == checkedItemId);
		}
	}

	@Override public void onBackPressed(){
		if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
			mDrawerLayout.closeDrawer(Gravity.LEFT);
		}else{
			super.onBackPressed();
		}
	}

	public void setFragment(Folder folder){
		// Create a new fragment and specify the fragment to show based on nav item clicked
		Fragment fragment = new NoteListFragment();
		if (folder != null){
			Bundle bundle = new Bundle();
			bundle.putParcelable(NoteListFragment.FOLDER, folder);
			fragment.setArguments(bundle);
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == BackupRestoreDelegate.PICK_RESTORE_FILE_REQUEST_CODE){
			backupRestoreDelegate.handleFilePickedWithFilePicker(resultCode, data);
		}
	}
}
