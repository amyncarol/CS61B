import java.lang.Math;

public class Username {

    // Potentially useless note: (int) '0' == 48, (int) 'a' == 97

    // Instance Variables (remember, they should be private!)
    // YOUR CODE HERE

    private String userName = "";

    String getUsername() {
    	return userName;
    }

    public Username() {
        double i = Math.random();
        int nameLength;
        if (i > 0.5) {
        	nameLength = 2;
        } else {
        	nameLength = 3;
        }

        for (int j = 0; j < nameLength; j++) {
        	int c = (int) Math.floor((Math.random()*62));
        	char ch;
        	if (c < 10) {
        		ch = (char) (c+48);
        	} else if (c < 36) {
        		ch = (char) (c+65-10);
        	} else {
        		ch = (char) (c+97-36);
        	}
        	this.userName += ch;
        }
    }

    public Username(String reqName) {
        if (reqName == null) {
        	throw new NullPointerException("Requested username is null!");
        } else if ((reqName.length() < 2) || (reqName.length() > 3)) {
        	throw new IllegalArgumentException("Requested username length should be 2 or 3");
        } else if (reqName.matches("[a-zA-Z0-9]{2}") || reqName.matches("[a-zA-Z0-9]{3}")) {
        	this.userName = reqName;
        } else {
        	throw new IllegalArgumentException("please only use a-z, A-Z and 0-9");
        }
    }

    @Override
    public boolean equals(Object o) {
        Username other = (Username) o;
        return this.userName.toLowerCase().equals(other.userName.toLowerCase());
    }

    @Override
    public int hashCode() { 
        String lowerUserName = this.userName.toLowerCase();
        int code = 0;
        for (int i = 0; i < lowerUserName.length(); i++) {
        	int asc2 = lowerUserName.charAt(i);
        	code += asc2 * Math.pow(128, i);
        }
        return code;
    }

    public static void main(String[] args) {
    	Username un = new Username();
    	System.out.println(un.userName);
    	un = new Username();
    	System.out.println(un.userName);
    	un = new Username();
    	System.out.println(un.userName);

    	Username un1 = new Username("11B");
    	Username un2 = new Username("11b");
    	System.out.println(un1.equals(un2));

    	System.out.println(un1.hashCode());
    }
}