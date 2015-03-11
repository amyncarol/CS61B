package ngordnet;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.In;

/** Provides a simple user interface for exploring WordNet and NGram data.
 *  @author [Yao Cai]
 */
public class NgordnetUI {
    public static void main(String[] args) {
        In in = new In("./ngordnet/ngordnetui.config");
        System.out.println("Reading ngordnetui.config...");

        String wordFile = in.readString();
        String countFile = in.readString();
        String synsetFile = in.readString();
        String hyponymFile = in.readString();
        NGramMap ngm = new NGramMap(wordFile, countFile);
        WordNet wn = new WordNet(synsetFile, hyponymFile);
        System.out.println("\nBased on ngordnetui.config, using the following: "
            + wordFile + ", " + countFile + ", " + synsetFile 
            + ", and " + hyponymFile + ".");

        //set initail startDate and endDate
        TimeSeries<Long> ts = ngm.totalCountHistory();
        int startDate = ts.firstKey();
        int endDate = ts.lastKey();

        while (true) {
            System.out.print("> ");
            String line = StdIn.readLine();
            String[] rawTokens = line.split(" ");
            String command = rawTokens[0];
            String[] tokens = new String[rawTokens.length - 1];
            System.arraycopy(rawTokens, 1, tokens, 0, rawTokens.length - 1);
            switch (command) {
                case "quit": 
                    return;
                case "help":
                    In inHelp = new In("./ngordnet/help.txt");
                    String helpStr = inHelp.readAll();
                    System.out.println(helpStr);
                    break;  
                case "range": 
                    startDate = Integer.parseInt(tokens[0]); 
                    endDate = Integer.parseInt(tokens[1]);
                    System.out.println("Start date: " + startDate);
                    System.out.println("End date: " + endDate);
                    break;
                case "count":
                    String word = tokens[0];
                    int year = Integer.parseInt(tokens[1]);
                    int count = ngm.countInYear(word, year);
                    System.out.println(count);
                    break;
                case "hyponyms":
                    String word2 = tokens[0];
                    System.out.println(wn.hyponyms(word2));
                    break;
                case "history":
                    Plotter.plotAllWords(ngm, tokens, startDate, endDate);
                    break;
                case "hypohist":
                    Plotter.plotCategoryWeights(ngm, wn, tokens, startDate, endDate);
                    break;
                case "wordlength":
                    YearlyRecordProcessor yrp = new WordLengthProcessor();
                    Plotter.plotProcessedHistory(ngm, startDate, endDate, yrp);
                    break;
                case "zipf":
                    int year2 = Integer.parseInt(tokens[0]);
                    Plotter.plotZipfsLaw(ngm, year2);
                    break;
                default:
                    System.out.println("Invalid command. use help command for more info");  
                    break;
            }
        }
    }
} 
