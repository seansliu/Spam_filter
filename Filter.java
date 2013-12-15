//****************************************************************************
// Sean Liu
// sl3497
//
// Objects of this class contain methods for filtering Messages. 
//****************************************************************************

import java.util.ArrayList;

public class Filter {
	// Store emails, blacklist, keywords, and spam in arraylists.
	private ArrayList<Message> inbox;
	private ArrayList<String> blackList, keywords, spam;
	
	public Filter(ArrayList<Message> ib, ArrayList<String> bl, 
				  ArrayList<String> kw, ArrayList<String> s){
		blackList = bl; // Save arraylists into object.
		keywords = kw;
		inbox = ib;
		spam = s;
	}
	
	// Return whether input Message is spam.
	private boolean isSpam(Message m){
		if (blackList.contains(m.getSender())) // Check with sender.
			return true;
		String[] subWords = toArray(m.getSubject()); // Check subject.
		if (inArrayList(subWords, keywords))
			return true;
		String[] mesWords = toArray(m.getMessage()); // Check message.
		if (inArrayList(mesWords, keywords))
			return true;
		return false;
	}
	
	// Return whether input arraylist contains any element in input array.
	private boolean inArrayList(String[] array, ArrayList<String> aL){
		for (String s:array){
			if (aL.contains(s))
				return true;
		}
		return false;
	}
	
	// Given a Message, add its subject keywords to keywords arraylist. 
	private void updateKeywords(Message m){
		String[] subWords = toArray(m.getSubject());
		for (String w:subWords){
			if (w.length() >= 6 && !keywords.contains(w))
				keywords.add(w);
		}
	}
	
	// Return an array of word tokens from a string input.
	private String[] toArray(String s){
		return s.toLowerCase().split("\\W+");
	}
	
	// Add sender of input Message to the blacklist.
	private void updateBlackList(Message m){
		String sender = m.getSender();
		if (!blackList.contains(sender))
			blackList.add(sender);
	}
	
	// Implements filtering.
	public void filter(){
		for (Message m : inbox){
			if (isSpam(m)){ // Check is Message is spam.
				spam.add(m.getMIN()); // Add email to spam list.
				updateBlackList(m); // Add sender to blacklist.
				updateKeywords(m); // Add keywords to keywords list.
			}
		}
	}
}
