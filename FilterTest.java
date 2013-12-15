//****************************************************************************
// Sean Liu
// sl3497
//
// Objects of this class test the filter through the methods of Mailman.
//****************************************************************************

import java.util.Scanner;
import java.io.FileNotFoundException;

public class FilterTest {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Spam Filter Tester! \n");
		boolean again = true; // Give user multiple tries to enter files.
		while (again){
			try{
				Mailman m = new Mailman(args[0], args[1], args[2], "spam.txt");			
				m.initialize(); // Create inbox, blacklist, keywords, filter.
				System.out.println("Inbox, black list, keywords list, and " +
								   "filter initialized.");
				System.out.print("Would you like to add emails to the black" +
								 " list? y/n ");
				String go = input.next();
				String email;
				while (go.equals("y")){
					System.out.print("Email to add to black list: ");
					email = input.next();
					m.addToBlacklist(email); // Add email to blacklist.
					System.out.print("Want to add more? y/n ");
					go = input.next();
				}
				System.out.print("Would you like to add keywords to the " +
						 		 "keyword list? y/n ");
				go = input.next();
				String word;
				while (go.equals("y")){
					System.out.print("Keyword to add to keyowrd list: ");
					word = input.next();
					m.addKeyword(word); // Add email to blacklist.
					System.out.print("Want to add more? y/n ");
					go = input.next();
				}
				m.sortSpam(); // Filter emails.
				System.out.println("Inbox filtered.");
				m.blackListFiler(); // Write new text files.
				m.keywordFiler();
				m.spamFiler();
				System.out.println("Files updated. 'spam.txt' created");
				again = false;
			}
			catch (FileNotFoundException e){ // Ask user to reset file names.
				System.out.println("Please try again with valid " +
								   "input file names.");
				System.out.print("Email file name: ");
				args[0] = input.next();
				System.out.print("Blacklist file name: ");
				args[1] = input.next();
				System.out.print("Keywords file name: ");
				args[2] = input.next();
			}
			catch(ArrayIndexOutOfBoundsException e) {
	            System.out.println("Please specify command-line arguments:");
	            System.out.println("email file, blacklist file, " +
	            				   "keywords file");
	            again=false;
			}
		}
	}
}
