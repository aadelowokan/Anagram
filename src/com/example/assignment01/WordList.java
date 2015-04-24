package com.example.assignment01;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class WordList
{
	@PrimaryKey
	@Persistent
	private Key id;
	
	@Persistent
	private List<String> list;
	
	public void setID(final Key id){ this.id = id; }
	public Key id(){ return id; }
	
	public int wordCount(){ return list.size(); }
	public String getWord(final int index){ return list.get(index); }
	public void addWord(final String word)
	{ 
		if(list == null)
			list = new ArrayList<String>();
		if(!list.contains(word))
			list.add(word);
	}
}