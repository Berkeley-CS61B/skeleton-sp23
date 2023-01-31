package timing;

import jh61b.utils.RuntimeInstrumentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Helpers {
    static Set<Integer> findMaxes(List<Double> times) {
        int maxIdx = 0;
        int sndIdx = -1;

        for(int i = 0; i < times.size(); ++i) {
            if (times.get(i) > times.get(maxIdx)) {
                sndIdx = maxIdx;
                maxIdx = i;
            }
        }

        return Set.of(maxIdx, sndIdx);
    }

    static boolean isApproximately(RuntimeInstrumentation.ComplexityType type, List<Integer> inputs, List<Double> times) {
        List<Double> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();
        Set<Integer> skips = findMaxes(times);

        double sumY;
        for(int i = 0; i < times.size(); ++i) {
            if (!skips.contains(i)) {
                double numElements = inputs.get(i);
                x.add((double)numElements);
                sumY = switch (type) {
                    case CONSTANT -> times.get(i);
                    case LOGARITHMIC -> times.get(i) / (Math.log10(numElements) / Math.log10(2.0));
                    case LINEAR -> times.get(i) / (double) numElements;
                    case QUADRATIC -> times.get(i) / (double) (numElements * numElements);
                    default -> throw new RuntimeException("unimplemented isApproximately for " + type);
                };

                y.add(sumY);
            }
        }

        double sumX = 0.0;
        sumY = 0.0;

        for(int i = 0; i < x.size(); ++i) {
            sumX += x.get(i);
            sumY += y.get(i);
        }

        double std = edu.princeton.cs.algs4.StdStats.stddev(x.stream().mapToDouble(d -> d).toArray());
        double inter;
        double mse;
        int i;
        double slope;
        if (type == RuntimeInstrumentation.ComplexityType.CONSTANT) {
            slope = 0.0;
            inter = sumY / (double)y.size();
        } else {
            mse = 0.0;

            for(i = 0; i < y.size(); ++i) {
                mse += (x.get(i) - sumX / (double) y.size()) * (y.get(i) - sumY / (double) y.size());
            }

            mse /= y.size() - 1;
            slope = mse / std;
            inter = sumY / (double)y.size() - slope * sumX / (double)y.size();
        }

        mse = 0.0;

        for(i = 0; i < y.size(); ++i) {
            mse += Math.pow(y.get(i) - inter + slope * x.get(i), 2.0);
        }

        double rSq = 1.0 - mse / std;
        return rSq >= 0.92;
    }

    // TODO: This method should be made public in jh61b and generalized to `double`.
    public static RuntimeInstrumentation.ComplexityType estComplexity(List<Integer> inputs, List<Double> times) {
        if (isApproximately(RuntimeInstrumentation.ComplexityType.CONSTANT, inputs, times)) {
            return RuntimeInstrumentation.ComplexityType.CONSTANT;
        } else if (isApproximately(RuntimeInstrumentation.ComplexityType.LOGARITHMIC, inputs, times)) {
            return RuntimeInstrumentation.ComplexityType.LOGARITHMIC;
        } else if (isApproximately(RuntimeInstrumentation.ComplexityType.LINEAR, inputs, times)) {
            return RuntimeInstrumentation.ComplexityType.LINEAR;
        } else if (isApproximately(RuntimeInstrumentation.ComplexityType.QUADRATIC, inputs, times)) {
            return RuntimeInstrumentation.ComplexityType.QUADRATIC;
        } else {
            return RuntimeInstrumentation.ComplexityType.WORSE;
        }
    }
}
