import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

public class Gitlet {

	public static void main(String[] args){
		Git git = new Git();
		String command = args[0];
                String[] tokens = new String[args.length - 1];
                System.arraycopy(args, 1, tokens, 0, args.length - 1);
                switch (command) {
                	case "init":
                		git.init();
                		break;
                	case "add":
                		git.add(tokens[0]);
                		break;
                	case "commit":
                                if (tokens.length != 1) {
                                        git.commit(null);
                                } else {
                		      git.commit(tokens[0]);
                                }
                		break;
                	case "remove":
                		git.remove(tokens[0]);
                		break;
                	case "log":
                		git.log();
                		break;
                	case "global-log":
                		git.globalLog();
                		break;
                	case "checkout":
                		git.checkout(tokens[0]);
                		break;
                }
	}
}
