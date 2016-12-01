package br.usp.fester.fester.partygoer;

import model.Song;

/**
 * Created by adria on 9/21/2016.
 */
public interface TouchHelperAdapter
{
	boolean onItemMove(int fromPosition, int toPosition);
	Song onItemDismiss(int position);
}
