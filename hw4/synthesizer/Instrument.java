package synthesizer;

/** the abstract superclass for all the instruments
 */
public class Instrument{

	protected static final int SR = 44100;      // Sampling Rate
    protected static double DECAY;        // energy decay factor
    
    /* Buffer for storing sound data. */
    protected BoundedQueue buffer;
    
    /* Create a guitar string of the given frequency.  */
    public Instrument(double frequency) {
        // TODO: Create a buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this divsion operation into an int. For better
        //       accuracy, use the Math.round() function before casting.
        //       Your buffer should be initially filled with zeros.
        int capacity = (int) Math.round(SR/frequency);
        buffer = new ArrayRingBuffer(capacity);
        for (int i=0; i<capacity; i++){
            buffer.enqueue(0);
        }

    }
    
    
    /* Pluck the string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in the buffer, and replace it with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each other.
        while(!buffer.isEmpty()){
            buffer.dequeue();
        }
        while(!buffer.isFull()){
            double r = Math.random()-0.5;
            buffer.enqueue(r);
        }
    }
    
    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic(){}
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        // double front1 = buffer.dequeue();
        // double front2 = buffer.peek();
        // double last = DECAY*(front1+front2)/2;
        // buffer.enqueue(last);
    
    
    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
    
}





