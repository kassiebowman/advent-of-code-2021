package com.debuggingsuccess.aoc;

import java.util.List;

public class Day1SonarSweep
{
    /**
     * Determines the number of times the depth increases in the measurements using a sliding window to reduce noise.
     *
     * @param measurements The depth measurements
     * @param windowSize   The size of the sliding window to use for comparison
     * @return The number of times the depth measurement increases from the previous window of
     * measurements.
     */
    public int getDepthIncreaseCount(List<Integer> measurements, int windowSize)
    {
        int size = measurements.size();
        if (size <= windowSize) return 0;

        int increaseCount = 0;
        int previousMeasurement = getSumForWindow(measurements, 0, windowSize);
        for (int i = 1; i < size; i++)
        {
            int currentMeasurement = getSumForWindow(measurements, i, windowSize);
            if (currentMeasurement > previousMeasurement) increaseCount++;
            previousMeasurement = currentMeasurement;
        }

        return increaseCount;
    }

    /**
     * Determines the sum for the measurements in the sliding window, starting at the specified index.
     *
     * @param measurements  The depth measurements
     * @param startingIndex The starting index for the window
     * @param windowSize    The number of measurements in the window
     * @return The sum of measurements in the sliding window
     */
    private int getSumForWindow(List<Integer> measurements, int startingIndex, int windowSize)
    {
        if (measurements.size() < startingIndex + windowSize) return 0;

        int sum = 0;
        for (int i = startingIndex; i < startingIndex + windowSize; i++)
        {
            sum += measurements.get(i);
        }

        return sum;
    }
}
