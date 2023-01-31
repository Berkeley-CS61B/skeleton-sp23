package timing;

import jh61b.utils.RuntimeInstrumentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertWithMessage;

public class TimingExperimentTest {

    public static List<Double> averageUsPerOp(TimingData td) {
        // Convert "times" (in seconds) and "opCounts" to nanoseconds / op
        List<Double> timesUsPerOp = new ArrayList<>();
        for (int i = 0; i < td.getTimes().size(); i++) {
            timesUsPerOp.add(td.getTimes().get(i) / td.getOpCounts().get(i) * 1e6);
        }
        return timesUsPerOp;
    }

    @Test
    public void testTimeAListConstruction() {
        TimingData td = Experiments.timeAListConstruction();

        assertWithMessage("First trial should be at least 1000 iterations")
                .that(td.getNs().get(0))
                .isAtLeast(1000);
        assertWithMessage("Last trial should be at least 64000 iterations")
                .that(td.getNs().get(td.getNs().size() - 1))
                .isAtLeast(64000);

        assertWithMessage("Resize strategy should be fixed")
                .that(Helpers.estComplexity(td.getNs(), averageUsPerOp(td)))
                .isEqualTo(RuntimeInstrumentation.ComplexityType.CONSTANT);
    }

    @Test
    public void testTimeSLListGetLast() {
        TimingData td = Experiments.timeSLListGetLast();

        assertWithMessage("First trial should be at least 1000 iterations")
                .that(td.getNs().get(0))
                .isAtLeast(1000);
        assertWithMessage("Last trial should be at least 64000 iterations")
                .that(td.getNs().get(td.getNs().size() - 1))
                .isAtLeast(64000);

        assertWithMessage("SLList getLast is linear")
                .that(Helpers.estComplexity(td.getNs(), averageUsPerOp(td)))
                .isEqualTo(RuntimeInstrumentation.ComplexityType.LINEAR);
    }
}
