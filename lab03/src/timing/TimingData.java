package timing;

import java.util.List;

public class TimingData {
    private List<Integer> Ns;
    private List<Double> times;
    private List<Integer> opCounts;

    public TimingData(List<Integer> Ns, List<Double> times, List<Integer> opCounts) {
        this.Ns = Ns;
        this.times = times;
        this.opCounts = opCounts;
    }

    public List<Integer> getNs() {
        return this.Ns;
    }

    public List<Double> getTimes() {
        return this.times;
    }

    public List<Integer> getOpCounts() {
        return this.opCounts;
    }
}
