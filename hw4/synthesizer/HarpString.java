package synthesizer;

public class HarpString extends Instrument{

	private static final double DECAY = 0.996; 

	public HarpString(double frequency){
		super(frequency*2);
	}

	@Override
	public void tic(){
		// TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       Do not call StdAudio.play().
        //       Flipping the sign of the new value before enqueueing
        //       it in tic() will change the sound from guitar-like to harp-like.
        double front1 = buffer.dequeue();
        double front2 = buffer.peek();
        double last = 0-DECAY*(front1+front2)/2;
        buffer.enqueue(last);

	}




}