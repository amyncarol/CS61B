

public class Part5 {

    public static void main(String[] args) {
        long n = Long.parseLong(args[0]);

    	Stopwatch timer = new Stopwatch();
    	
    	for (long i = 0; i < n; i++) {
    		long j = i * i;
    		while (j <= n) {
        	j += 1;
    		}
		}

    	 System.out.println(timer.elapsedTime() + " seconds elapsed");
    }
}