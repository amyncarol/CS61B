import java.util.Map;
import java.util.HashMap;

public class UsernameBank {

    // Instance variables (remember, they should be private!)
    // YOUR CODE HERE
    private static Map<String, Integer> badEmails;
    private static Map<String, Integer> badUsernames;
    private static Map<Username, String> nameToEmail;
    private static Map<String, String> emailToName;

    public UsernameBank() {
        badEmails = new HashMap<String, Integer>();
    	badUsernames = new HashMap<String, Integer>();
    	nameToEmail = new HashMap<Username, String>();
    	emailToName = new HashMap<String, String>();
    }

    public void generateUsername(String username, String email) {
        Username usr = new Username(username);
        if (email == null) {
        	throw new NullPointerException("email is null!");
        }
        if (isEmail(email) && !nameToEmail.containsKey(usr) && !emailToName.containsKey(email)) {
        	nameToEmail.put(usr, email);
        	emailToName.put(email, username);
        } else if (nameToEmail.containsKey(usr)) {
        	throw new IllegalArgumentException("username already existed");
        }
    }

    public String getEmail(String username) {
        Username usr;
        try {
        	usr = new Username(username);
        } catch (IllegalArgumentException e) {
        	recordBadUsername(username);
        	return null;
        }
        if (nameToEmail.containsKey(usr)) {
        	return nameToEmail.get(usr);
        } else {
        	recordBadUsername(username);
        	return null;
        }   
    }

    public String getUsername(String userEmail)  {
        if (userEmail == null) {
        	throw new NullPointerException("userEmail is null!");
        } else if (emailToName.containsKey(userEmail)) {
        	return emailToName.get(userEmail);
        } else {
        	recordBadEmail(userEmail);
        	return null;
        }
    }

    public Map<String, Integer> getBadEmails() {
        // YOUR CODE HERE
        return badEmails;
    }

    public Map<String, Integer> getBadUsernames() {
        // YOUR CODE HERE
        return badUsernames;
    }

    public String suggestUsername() {
        for (int i = 0; i < 50; i++) {
        	Username usr = new Username();
        	if (!nameToEmail.containsKey(usr)) {
        		return usr.getUsername();
        	}
        }  
        return null;
    }

    // The answer is somewhere in between 3 and 1000.
    public static final int followUp() {
        // YOUR CODE HERE
        return 6;
    }

    // Optional, suggested method. Use or delete as you prefer.
    private void recordBadUsername(String username) {
        if (badUsernames.containsKey(username)) {
        	badUsernames.put(username, badUsernames.get(username) + 1);
        } else {
        	badUsernames.put(username, 1);
        }
    }

    // Optional, suggested method. Use or delete as you prefer.
    private void recordBadEmail(String email) {
        if (badEmails.containsKey(email)) {
        	badEmails.put(email, badEmails.get(email) + 1);
        } else {
        	badEmails.put(email, 1);
        }
    }

    private boolean isEmail(String email) {
    	return email.contains("@");
    }
}