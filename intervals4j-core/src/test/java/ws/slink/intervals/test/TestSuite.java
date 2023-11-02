package ws.slink.intervals.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CalendarToolsTest.class,
    IntervalBasicTests.class,
    IntervalBuilderTest.class,
    IntervalCustomTests.class,
})
public class TestSuite {
}
