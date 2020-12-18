package mathcomp.oletsky.reinforcementlearning;

import mathcomp.oletsky.mathhelper.VectMatr;
import mathcomp.oletsky.randomchooser.RandomChooser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Simple reinforcement learning
 * After each attempt a reward is added to the accumulated reward
 */
public class SimpleRewardTesting {
    public static void main(String[] args) {

        //Accumulated rewards for actions
        double[] accumulatedRewards={50., 50., 50.};

        final int KOLACTIONS=accumulatedRewards.length;

        int[] values={-5, 0, 5}; //Average results of actions

        //Parameters of random rewards

        final int RANGE=10;
        final int MEDIANA=5;
        final int KOLEXPERIMENTS=500;
        final int THRESHOLD=1000;
        Random rand = new Random();

        ArrayList<Integer> history = new ArrayList<>();
        for (int i = 0; i < KOLEXPERIMENTS; i++) {
            //Calculating exponential probabilities
            double[] probs = RandomChooser.getProbsByValues(accumulatedRewards);
            //Random choice
            int action=RandomChooser.chooseByProps(probs);
            history.add(action);
            //Reaction of environment
            int rawReaction = rand.nextInt(RANGE+1)-MEDIANA;
            int reaction = rawReaction+values[action];

            //Modifying rewards
            accumulatedRewards[action]+=reaction;
            if (accumulatedRewards[action]<1) accumulatedRewards[action]=1;
            if (accumulatedRewards[action]>THRESHOLD)
                accumulatedRewards[action]=THRESHOLD;

        }
        //Final output

        for (int i = 0; i <history.size() ; i++) {
            System.out.print(history.get(i)+" ");
            if (i%30==0) System.out.println();
        }
        System.out.println();
        System.out.println("Final values:");
        VectMatr.defaultOutputVector(accumulatedRewards);
        //Counting frequences
        HashMap<Integer, Integer> counts=new HashMap<>();
        for (int h:history) {
            if (counts.containsKey(h)) counts.put(h,counts.get(h)+1);
            else counts.put(h,1);
        }
        System.out.println("Frequences:");
        for (var entry:counts.entrySet()) {
            System.out.println(entry.getKey()+" - "+entry.getValue());
        }
    }
}
