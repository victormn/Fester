package br.usp.fester.fester.partygoer;

import model.Song;

/**
 * Created by adria on 9/22/2016.
 */
public interface SongManager
{
	boolean onAddSong(Song song);
	Song onRemoveSong(int position);
	boolean onMoveSong(int fromPosition, int toPosition);
}
