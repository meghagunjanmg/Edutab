package com.example.edutab.notepad.activities.addtofolders;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.edutab.R;
import com.example.edutab.activity_subjects_lists;
import com.example.edutab.notepad.activities.home.HomeActivity;
import com.example.edutab.notepad.database.FolderNoteDAO;
import com.example.edutab.notepad.models.Folder;
import com.example.edutab.notepad.models.Note;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by MohMah on 8/19/2016.
 */
class SelectFolderViewHolder extends RecyclerView.ViewHolder{
	private static final String TAG = "SelectFolderViewHolder";
	private final Adapter adapter;
	@BindView(R.id.checkbox) CheckBox checkBox;
	@BindView(R.id.folder_name_text) TextView folderName;
	private Folder folder;
	private Note note;

	public SelectFolderViewHolder(final View itemView, final Adapter adapter){
		super(itemView);
		ButterKnife.bind(this, itemView);
		this.adapter = adapter;
		itemView.setOnClickListener(new View.OnClickListener(){
			@Override public void onClick(View v){
				setChecked(!checkBox.isChecked());
			}
		});
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
			@Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked){
				if (isChecked){
					adapter.getCheckedFolders().add(folder);
					FolderNoteDAO.createFolderNoteRelation(folder, note);

				}else{
					adapter.getCheckedFolders().remove(folder);
					FolderNoteDAO.removeFolderNoteRelation(folder, note);

				}
				Log.e(TAG, "onClick: checkedFolders:" + adapter.getCheckedFolders());
			}
		});
	}

	public void setData(Folder folder, Note note){
		this.folder = folder;
		this.note = note;
		folderName.setText(folder.getName());
	}

	public void setChecked(boolean checked){
		checkBox.setChecked(checked);
	}
}
