package synthesizer;

public class Drum extends Instrument{

	private static final double DECAY = 1.000; 

	public Drum(double frequency){
		super(frequency);
	}

	@Override
	public void tic(){
		// TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        //       Flipping the sign of a new value with probability 0.5 before
        //       enqueueing it in tic() will produce a drum sound. A decay factor 
        //       of 1.0 (no decay) will yield a better sound, and you will need
        //       to adjust the set of frequencies used.
        double front1 = buffer.dequeue();
        double front2 = buffer.peek();
        double last = DECAY*(front1+front2)/2;
        if (Math.random()>0.5){
        	last = 0-last;
        }
        buffer.enqueue(last);

	}




}