//****************************************************************************
// Sean Liu
// sl3497
//
// Objects of this class manages information related to an email.
//****************************************************************************

public class Message {
	
	// Information for each message.
	private String identification, sender, recipient, date, subject, message;
	
	// Constructor saves the input information into the object.
	public Message(String id, String s,String r, String d, String sub, 
				   String m){
		identification = id;
		sender = s.toLowerCase();
		recipient = r.toLowerCase();
		date = d;
		subject = sub;
		message = m;
	}
	
	// Return MIN.
	public String getMIN(){
		return identification;
	}
	
	// Return sender.
	public String getSender(){
		return sender;
	}
	
	// Return recipient.
	public String getRecipient(){
		return recipient;
	}
	
	// Return date string.
	public String getDate(){
		return date;
	}
	
	// Return subject line.
	public String getSubject(){
		return subject;
	}
	
	// Return message string.
	public String getMessage(){
		return message;
	}
	
	// Return entire email, formatted for printing.
	public String toString(){
		String mail = "<START>\n";
		mail += "MIN: " + identification + "\n";
		mail += "Sender: " + sender + "\n";
		mail += "Recipient: " + recipient + "\n";
		mail += "Date: " + date + "\n";
		mail += "Subject: " + subject + "\n";
		mail += "Message:\n" + message + "\n<END>\n";
		return mail;
	}
}
