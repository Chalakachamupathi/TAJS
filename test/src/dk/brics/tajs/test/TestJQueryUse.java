package dk.brics.tajs.test;

import dk.brics.tajs.Main;
import dk.brics.tajs.monitoring.AnalysisTimeLimiter;
import dk.brics.tajs.monitoring.CompositeMonitoring;
import dk.brics.tajs.monitoring.IAnalysisMonitoring;
import dk.brics.tajs.monitoring.Monitoring;
import dk.brics.tajs.options.Options;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for different usages of jQuery.
 * Tests are from the OOPSLA 2014 benchmarks.
 * <p>
 * NB: these tests should all succeed, see {@link TestJQueryUse_unanalyzable} for tests that we fail to analyze successfully.
 */
public class TestJQueryUse {

    @BeforeClass
    public static void beforeClass() {
        Main.initLogging();
    }

    @Test
    public void auto_ajax_abort() {
        test("ajax_abort");
    }

    @Test
    public void auto_ajax_abort2() {
        test("ajax_abort2");
    }

    @Test
    public void auto_ajax_progress() {
        test("ajax_progress");
    }

    @Test
    public void auto_ajax_progress2() {
        test("ajax_progress2");
    }

    @Test
    public void auto_ajax_progressErrorCallback() {
        test("ajax_progressErrorCallback");
    }

    @Test
    public void auto_animate_stop() {
        test("animate_stop");
    }

    @Test
    public void auto_animate_stopQueue() {
        test("animate_stopQueue");
    }

    @Test
    public void auto_animate_stopQueue2() {
        test("animate_stopQueue2");
    }

    @Test
    public void auto_dimensions_get() {
        test("dimensions_get");
    }

    @Test
    public void auto_dimensions_getWindow() {
        test("dimensions_getWindow");
    }

    @Test
    public void auto_dimensions_set() {
        test("dimensions_set");
    }

    @Test
    public void auto_divTest2() {
        test("divTest2");
    }

    @Test
    public void auto_dom_appendPrepend() {
        test("dom_appendPrepend");
    }

    @Test
    public void auto_dom_appendPrepend2() {
        test("dom_appendPrepend2");
    }

    @Test
    public void auto_dom_appendPrepend3() {
        test("dom_appendPrepend3");
    }

    @Test
    public void auto_dom_beforeAfter() {
        test("dom_beforeAfter");
    }

    @Test
    public void auto_dom_beforeAfter2() {
        test("dom_beforeAfter2");
    }

    @Test
    public void auto_dom_beforeAfter3() {
        test("dom_beforeAfter3");
    }

    @Test
    public void auto_dom_removeEmpty() {
        test("dom_removeEmpty");
    }

    @Test
    public void auto_dom_removeEmpty2() {
        test("dom_removeEmpty2");
    }

    @Test
    public void auto_dom_setCssAttribute() {
        test("dom_setCssAttribute");
    }

    @Test
    public void auto_fading_fadeIn() {
        test("fading_fadeIn");
    }

    @Test
    public void auto_fading_fadeInOut() {
        test("fading_fadeInOut");
    }

    @Test
    public void auto_fading_fadeToggleSpeed() {
        test("fading_fadeToggleSpeed");
    }

    @Test
    public void auto_sliding_down() {
        test("sliding_down");
    }

    @Test
    public void auto_sliding_downSpeeds() {
        test("sliding_downSpeeds");
    }

    @Test
    public void auto_sliding_toggle() {
        test("sliding_toggle");
    }

    private void test(String testCase) {
        Main.reset();
        Options.get().enableDeterminacy();
        Options.get().enableIncludeDom();
        Options.get().enableUnevalizer();
        Options.get().enableTest();

        // Successfull tests runs in less than 15 seconds on `Intel(R) Core(TM) i7-3520M CPU @ 2.90GHz`
        AnalysisTimeLimiter analysisLimiter = new AnalysisTimeLimiter(90);

        IAnalysisMonitoring monitor = null;
        boolean showLineAnalysis = false; // for debugging
        if (showLineAnalysis) {
            // NB: requires TAJS-meta to be on the classpath
            // monitor = new CompositeMonitoring(analysisLimiter, MonitorFactory.createLineAnalysisMonitor());
        } else {
            monitor = CompositeMonitoring.buildFromList(new Monitoring(), analysisLimiter);
        }

        Misc.run(new String[]{"test/jquery-use-autogenerated/" + testCase + ".snippet_in_plain.htm.dir/index.htm"}, monitor);

        if (analysisLimiter.analysisExceededTimeLimit()) {
            Assert.fail("Analysis exceeded time limit");
        }
    }
}
