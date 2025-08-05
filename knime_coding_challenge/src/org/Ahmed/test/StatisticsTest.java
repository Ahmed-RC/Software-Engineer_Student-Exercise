package org.Ahmed.test;

import static org.junit.Assert.assertEquals;

import org.Ahmed.Statistics;
import org.junit.Test;

public class StatisticsTest {

    @Test
    public void testLineCountIncrements() {
        Statistics stats = Statistics.getInstance();
        int start = stats.getNoOfLinesRead();
        stats.updateStatisticsWithLine("line1");
        stats.updateStatisticsWithLine("line2");
        assertEquals(start + 2, stats.getNoOfLinesRead());
    }

    @Test
    public void testUniqueLines() {
        Statistics stats = Statistics.getInstance();
        int start = stats.getNoOfUniqueLines();
        stats.updateStatisticsWithLine("same");
        stats.updateStatisticsWithLine("same");
        assertEquals(start + 1, stats.getNoOfUniqueLines());
    }
}
