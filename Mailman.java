//****************************************************************************
// Sean Liu
// sl3497
//
// Objects of this class contain methods for reading text files and 
// maintaining the email program.
//****************************************************************************

import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Mailman {
	private String blName, kwName, spamName;
	private File mailFile, blackFile, keywordFile;
	private ArrayList<Message> inbox = new ArrayList<Message>();
	private ArrayList<String> blackList = new ArrayList<String>();
	private ArrayList<String> keywords = new ArrayList<String>();
	private ArrayList<String> spam = new ArrayList<String>();
	private Filter filter;
	
	// Instantiates file-related variables.
	public Mailman(String emails, String blacklist, String keywords, 
				   String spam) {
		mailFile = new File(emails);
		blackFile = new File(blacklist);
		keywordFile = new File (keywords);
		blName = blacklist;
		kwName = keywords;
		spamName = spam;
	}
	
	// Takes input email String, extracts formatted email information, and 
	// returns instantiated Message object using the extracted information.
	private Message makeEmail(String email){
		Scanner reader = new Scanner(email);
		reader.nextLine(); // Skip <BEGIN>
		String send = reader.nextLine();
		// Extract sender.
		String[] sendLine = send.split(" ");
		String sender = sendLine[sendLine.length-1].replace("<", "");
		sender = sender.replace(">", "");
		// Extract recipient.
		String receive = reader.nextLine();
		String[] recipLine = receive.split(" ");
		String recipient = recipLine[recipLine.length-1].replace("<", "");
		recipient = recipient.replace(">", ""); 
		// Extract date.
		String date = reader.nextLine().split("Date: ")[1];
		// Extract Subject.
		String subject = reader.nextLine().split("Subject: ")[1];
		// Extract MIN.
		String id = reader.nextLine().split("MIN: ")[1].replace("<","");
		id = id.replace(">","");
		// Extract Message.
		String message = email.split("Message Body:\n")[1];
		return new Message(id, sender, recipient, date, subject, message);	
	}
	
	// Reads through emails file and adds each email to the inbox arraylist.
	public void fillInbox() throws FileNotFoundException {
		Scanner reader = new Scanner(mailFile);
		String temp, line = "";
		while (reader.hasNextLine()){
			temp = reader.nextLine() + "\n";
			if (temp.contains("<END>")){ // Marks end of an email.
				inbox.add(makeEmail(line));
				line = temp = ""; // Reset variables.
			}
			line += temp;
		}
	}
	
	// Reads blacklist file and adds each email to blacklist.
	public void makeBlacklist() throws FileNotFoundException {
		Scanner reader = new Scanner(blackFile);
		String email;
		while (reader.hasNextLine()){
			email = reader.nextLine();
			addToBlacklist(email);
		}
	}
	
	// Reads keywords file and adds each email to keywords.
	public void makeKeywords() throws FileNotFoundException {
		Scanner reader = new Scanner(keywordFile);
		String word;
		while (reader.hasNextLine()){
			word = reader.nextLine().trim();
			if (word.length() > 0) // Verify word.
				addKeyword(word);
		}
	}
	
	// Adds input keyword to the keywords list in Filter.
	public void addKeyword(String kw){
		if (!keywords.contains(kw)) // Check for repeats.
			keywords.add(kw.toLowerCase());
	}
	
	// Adds input email to blacklist in Filter.
	public void addToBlacklist(String bl){
		if (bl.contains("@") && !blackList.contains(bl)) // Verfy email.
			blackList.add(bl);
	}
	
	// Creates the Filter, given inbox, blacklist, and keywords.
	public void createFilter(){
		filter = new Filter(inbox, blackList, keywords, spam);
	}
	
	// Filters inbox for spam using Filter.
	public void sortSpam(){
		filter.filter();
	}
	
	// Writes blacklist to file.
	public void blackListFiler() throws FileNotFoundException {
		PrintWriter output = new PrintWriter(blName);
		output.print(blackListToString());
		output.close();
	}
	
	// Writes keywords to file.
	public void keywordFiler() throws FileNotFoundException {
		PrintWriter output = new PrintWriter(kwName);
		String string = keywordsToString();
		output.print(string);
		output.close();
	}
	
	// Writes spam MINs to file.
	public void spamFiler() throws FileNotFoundException {
		PrintWriter output = new PrintWriter(spamName);
		output.print(spamToString());
		output.close();
	}
	
	// Writes inbox into a text file with given filename.
	public void inboxFiler(String mailName) throws FileNotFoundException {
		PrintWriter output = new PrintWriter(mailName);
		output.print(inboxToString());
		output.close();
	}	
	
	// Creates initial conditions for maintaining email.
	public void initialize() throws FileNotFoundException {
		fillInbox();
		makeBlacklist();
		makeKeywords();
		createFilter();
	}
	
	// Return blacklist as string for printing/writing.
	public String blackListToString(){
		String bList = "";
		for (String email:blackList)
			bList += email + "\n";
		return bList;
	}
	
	// Return keywords list as string for printing/writing.
	public String keywordsToString(){
		String kWords = "";
		for (String word:keywords)
			kWords += word + "\n";
		return kWords;
	}
	
	// Return spam MINs as string for printing/writing.
	public String spamToString(){
		String spammers = "";
		for (String min:spam)
			spammers += min + "\n";
		return spammers;
	}
	
	// Return inbox of Messages as string for printing/writing.
	public String inboxToString(){
		String inboxString = "";
		for (Message m:inbox)
			inboxString += m.toString() + "\n";
		return inboxString;
	}
	
	// Return arraylist of spam MIN Strings.
	public ArrayList<String> getSpam(){
		return spam;
	}
	
	// Return arraylist of blacklisted email address Strings.
	public ArrayList<String> getBlackList(){
		return blackList;
	}
	
	// Return arraylist of keyword Strings. 
	public ArrayList<String> getKeywords(){
		return keywords;
	}
}
