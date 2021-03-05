package com.example.edutab.notepad.events;


import com.example.edutab.notepad.models.Folder;

/**
 * Created by MohMah on 8/19/2016.
 */
public class FolderCreatedEvent{
	private Folder folder;

	public FolderCreatedEvent(Folder folder){
		this.folder = folder;
	}

	public Folder getFolder(){
		return folder;
	}
}
