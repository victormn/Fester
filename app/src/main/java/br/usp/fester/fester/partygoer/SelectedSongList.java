package br.usp.fester.fester.partygoer;

import java.util.ArrayList;
import java.util.List;

import model.Song;

/**
 * Created by adria on 9/21/2016.
 */
public class SelectedSongList
{
	private static SelectedSongList ourInstance = new SelectedSongList();
	private List<Song> mSongList;

	public static SelectedSongList getInstance()
	{
		return ourInstance;
	}
	public List<Song> getSongs() { return mSongList; }

	public void addSong(String title, String artist)
	{
		Song song = new Song(title, artist);
		mSongList.add(song);
	}

	public int addSong(Song song)
	{
		mSongList.add(song);
		return mSongList.indexOf(song);
	}

	public Song removeSong(int position)
	{
		return mSongList.remove(position);
	}

	private SelectedSongList()
	{
		mSongList = new ArrayList<>();
	}
}
