package timing;

import edu.princeton.cs.algs4.Stopwatch;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class Experiments {

    private static void printTimingTable(TimingData data) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.println("------------------------------------------------------------");
        for (int i = 0; i < data.getNs().size(); i += 1) {
            int N = data.getNs().get(i);
            double time = data.getTimes().get(i);
            int opCount = data.getOpCounts().get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    /** Computes the nth Fibonacci number using a slow naive recursive strategy.*/
    private static int fib(int n) {
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return fib(n - 1) + fib(n - 2);
    }

    public static TimingData exampleFibonacciExperiment() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        // We're computing each fibonacci number 100 times to get a more stable number
        int ops = 100;

        for (int N = 10; N < 31; N++) {
            Ns.add(N);
            opCounts.add(ops);
            Stopwatch sw = new Stopwatch();
            for (int j = 0; j < ops; j++) {
                int fib = fib(N);
            }
            times.add(sw.elapsedTime());
        }

        return new TimingData(Ns, times, opCounts);
    }

    public static TimingData timeAListConstruction() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        // TODO: YOUR CODE HERE

        return null;
    }


    public static TimingData timeSLListGetLast() {
        List<Integer> Ns = new ArrayList<>();
        List<Double> times = new ArrayList<>();
        List<Integer> opCounts = new ArrayList<>();

        // TODO: YOUR CODE HERE

        return null;

    }

    public static void main(String[] args) {
        // TODO: Modify the following line to change the experiment you're running
        TimingData td = exampleFibonacciExperiment();
        // Modify this line to make the chart title make sense
        String title = "Naive Recursive Fibonacci";

        // Convert "times" (in seconds) and "opCounts" to nanoseconds / op
        List<Double> timesUsPerOp = new ArrayList<>();
        for (int i = 0; i < td.getTimes().size(); i++) {
            timesUsPerOp.add(td.getTimes().get(i) / td.getOpCounts().get(i) * 1e6);
        }

        printTimingTable(td);

        XYChart chart = QuickChart.getChart(title, "N", "time (us per op)", "Time", td.getNs(), timesUsPerOp);
        new SwingWrapper(chart).displayChart();
    }
}
