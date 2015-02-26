package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.Map;
import java.util.List;

/** An implementation of a natural hunter who feasts on Plips.
 *  @author Josh Hug
 */
public class Clorus extends Creature {
    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;
    /** probability of taking a move when ample space available. */
    private double moveProbability = 0.5;
 

    /** creates clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }

    /** creates a clorus with energy equal to 1. */
    public Clorus() {
        this(1);
    }

    /**The color method for Cloruses should 
    * always return the color R = 34, G = 0, B = 231.
    */
    public Color color() {
        r = 34;
        b = 231;
        g = 0;
        return color(r, g, b);
    }

    /** If a Clorus attacks another Creature, it should gain
     * that Creature's energy. This should happen in the attack()
     * method, not in chooseAction(). You do not need to worry 
     * about making sure the attacked() creature dies — the simulator
     * does that for you. */
    public void attack(Creature c) {
        energy = energy + c.energy();
    }

    /** Cloruses should lose 0.03 units of energy on a move action
     */
    public void move() {
        energy = energy - 0.03;
    }


    /** Cloruses lose 0.01 units of energy on a stay action
     */
    public void stay() {
        energy = energy - 0.01;
    }

    /** Cloruses and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Clorus.
     */
    public Clorus replicate() {
        energy = energy * 0.5;
        return new Clorus(energy);
    }

    /** Clorus take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if any Plips are seen, the Clorus will ATTACK 
     *  one of them randomly.
     *  3. Otherwise, if the Clorus has energy greater than or equal
     *  to one, it will REPLICATE to a random empty square.
     *  4. Otherwise, the Clorus will MOVE.
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }
        if (plips.size() > 0) {
            Direction moveDir = HugLifeUtils.randomEntry(plips);
            return new Action(Action.ActionType.ATTACK, moveDir);
        }
        if (energy >= 1.0) {
            Direction moveDir = HugLifeUtils.randomEntry(empties);
            return new Action(Action.ActionType.REPLICATE, moveDir);
        }
        Direction moveDir = HugLifeUtils.randomEntry(empties);
        return new Action(Action.ActionType.MOVE, moveDir);
    }
}
