package com.debuggingsuccess.aoc;

import java.util.List;

public class Day1SonarSweep
{
    public int getDepthIncreaseCount(List<Integer> measurements)
    {
        int size = measurements.size();
        if (size <= 1) return 0;

        int increaseCount = 0;
        int previousMeasurement = measurements.get(0);
        for (int i = 1; i < size; i++)
        {
            Integer currentMeasurement = measurements.get(i);
            if (currentMeasurement > previousMeasurement) increaseCount++;
            previousMeasurement = currentMeasurement;
        }

        return increaseCount;
    }
}
