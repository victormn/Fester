package br.usp.fester.fester.partygoer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import model.Song;

/**
 * Created by adria on 9/20/2016.
 */
public class AllSongsList
{
	private static AllSongsList ourInstance = new AllSongsList();
	private TreeSet<Song> mSongSet;
	private List<Song> mSongList;

	public static AllSongsList getInstance()
	{
		return ourInstance;
	}
	public List<Song> getSongs() { return mSongList; }

	public int addSong(String title, String artist)
	{
		Song song = new Song(title, artist);
		return addAndConvert(song);
	}

	public int addSong(Song song)
	{
		return addAndConvert(song);
	}

	private int addAndConvert(Song song)
	{
		mSongSet.add(song);
		mSongList = new ArrayList<>(mSongSet);
		return mSongList.indexOf(song);
	}

	public Song removeSong(int position)
	{
		Song removed = mSongList.get(position);
		mSongSet.remove(removed);
		return mSongList.remove(position);
	}

	private AllSongsList()
	{
		mSongSet = new TreeSet<>();

		addSong("Hello", "Adele");
		addSong("Set Fire to the Rain", "Adele");
		addSong("Jesus of Suburbia", "Green Day");
		addSong("Cake by the Ocean", "DNCE");
		addSong("Grace Kelly", "Mika");
		addSong("Do I Wanna Know?", "Arctic Monkeys");
		addSong("Blaze Up The Fire", "Major Lazer");
		addSong("Sorry", "Justin Bieber");
		addSong("Maneater", "Nelly Furtado");
		addSong("Make Me", "Britney Spears");
		addSong("Chandelier", "Sia");
		addSong("Alive", "Sia");
		addSong("The Greatest", "Sia");
		addSong("Supermassive Black Hole", "Muse");
		addSong("Take Ü There", "Jack Ü");
		addSong("Into You", "Ariana Grande");
		addSong("Problem", "Ariana Grande");
		addSong("Final Song", "MØ");
		addSong("Stronger", "Clean Bandit");
		addSong("Heathens", "Twenty One Pilots");
		addSong("Perfect Illusion", "Lady Gaga");
		addSong("In Common", "Alicia Keys");
		addSong("Work", "Rihanna");
		addSong("Hymn For The Weekend", "Coldplay");
		addSong("Cold Water (feat. Justin Bieber & MØ)", "Major Lazer");
		addSong("Can't Stop The Feeling", "Justin Timberlake");
		addSong("You & Me", "Marc E. Bassy");
		addSong("Pompeii", "Bastille");
		addSong("Lethargy", "Bastille");
		addSong("Make Love", "Inês Brasil");
		addSong("Kingdom Come", "Demi Lovato");
		addSong("Geronimo", "Sheppard");

		mSongList = new ArrayList<>(mSongSet);
		//mSongSet = null;
	}
}
