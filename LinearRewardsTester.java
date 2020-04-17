package mathcomp.oletsky.reinforcementlearning;

import mathcomp.oletsky.mathhelper.VectMatr;
import mathcomp.oletsky.randomchooser.RandomChooser;

/**
 * @author Oleksiy Oletsky
 * Illustration of reinforcement learning by linear rewards algorithm
 */

public class LinearRewardsTester {
    public static void main(String[] args) {
        double[] props = {
                0.1, 0.1, 0.1, 0.1, 0.1,
                0.1, 0.1, 0.1, 0.1, 0.1};
        double[] successProbs = {
                0.1, 0.7, 0.2, 0.6, 0.1,
                0.3, 0.5, 0.4, 0.2, 0.3
        };

        final int KOL = 1000;
        final double alpha=0.1;
        final double EPS=1.-1.E-10;

        int n = props.length;
        int optIndex=VectMatr.getMaxIndex(successProbs);
        System.out.println("Optimal action is "+optIndex);
        System.out.println("----------------------------");

        int kolOfSuccesses=0;
        int probsUntilLearned=KOL;
        boolean learned = false;
        for (int i=0; i<KOL; i++) {
            //VectMatr.defaultOutputVector(props);

            int choice = RandomChooser.chooseByProps(props);
            int success = RandomChooser.bernoulliProbe(successProbs[choice]);
            //System.out.println(choice+" "+success);
            if (success==1) {
                kolOfSuccesses++;
                for (int j=0; j<n; j++) {
                    if (j==choice) {
                        props[j]=props[j]+alpha*(1.-props[j]);
                    }
                    else {
                        props[j]=props[j]-alpha*props[j];
                    }
                }
            }

            if (!learned) {

                double foundProb = VectMatr.getMaxValue(props);
                if (foundProb>EPS) {
                    learned=true;
                    probsUntilLearned=i;
                }
            }

        }
        System.out.println("Eventually:");
        VectMatr.defaultOutputVector(props);
        int foundAction = VectMatr.getMaxIndex(props);
        double foundProb = VectMatr.getMaxValue(props);
        System.out.println("Found action: "+foundAction);
        System.out.println("Max prob: "+foundProb);
        System.out.println("Success ratio: "+kolOfSuccesses/(KOL+0.));
        System.out.println("Ratio until learned: "+probsUntilLearned/(KOL+0.));
    }
}
