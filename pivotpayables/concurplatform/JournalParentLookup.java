/**
 * 
 */
package com.pivotpayables.concurplatform;

import java.util.ArrayList;

/**
 * @author John
 *
 */
public class JournalParentLookup {
	private static ArrayList<Itemization> items = new ArrayList<Itemization>();
	private static Itemization item = new Itemization();
	private static ArrayList<Allocation> allocations = new ArrayList<Allocation>();
	private static Allocation allocation = new Allocation();
	private static ArrayList<JournalEntry> journals = new ArrayList<JournalEntry>();
	private static JournalEntry journal = new JournalEntry();

	public static JournalEntry getJournal (Expense expense, String journalID){
		// This method returns the JournalEntry object for the specified journal ID
		
		items = expense.getItems();// get the itemizations for this expense
		for (Itemization ititem:items){// iterate for each Itemization
			item = ititem;
			allocations = item.getAllocations();// get the allocations for this itemization
			for (Allocation itallocation:allocations){// iterate for each allocation
				allocation = itallocation;
				journals = allocation.getJournals();// get the journal entries for this allocation
				for (JournalEntry itjournal:journals){// iterate for each Journal Entry
				
					journal = itjournal;// get the Journal Entry for this iteration
					if (journal.getID() == journalID){// then this is the desired journal entry
						return journal;
					}
				}
			}
		}
		return journal;// return empty allocation
	}
	public static Allocation getAllocation (Expense expense, String journalID){
		// This method returns the parent Allocation object for the specified journal ID
		
		items = expense.getItems();// get the itemizations for this expense
		for (Itemization ititem:items){// iterate for each Itemization
			item = ititem;
			allocations = item.getAllocations();// get the allocations for this itemization
			for (Allocation itallocation:allocations){// iterate for each allocation
				allocation = itallocation;
				journals = allocation.getJournals();// get the journal entries for this allocation
				for (JournalEntry itjournal:journals){// iterate for each Journal Entry
				
					journal = itjournal;// get the Journal Entry for this iteration
					if (journal.getID() == journalID){// then this is the desired journal entry
						return allocation;
					}
				}
			}
		}
		return allocation;// return empty allocation
	}
	public static Itemization getItemization (Expense expense, String journalID){
		// This method returns the parent Itemization object for the specified journal ID
		
		items = expense.getItems();// get the itemizations for this expense
		for (Itemization ititem:items){// iterate for each Itemization
			item = ititem;
			allocations = item.getAllocations();// get the allocations for this itemization
			for (Allocation itallocation:allocations){// iterate for each allocation
				allocation = itallocation;
				journals = allocation.getJournals();// get the journal entries for this allocation
				for (JournalEntry itjournal:journals){// iterate for each Journal Entry
				
					journal = itjournal;// get the Journal Entry for this iteration
					if (journal.getID() == journalID){// then this is the desired journal entry
						return item;
					}
				}
			}
		}
		return item;// return empty itemization
	}
}
