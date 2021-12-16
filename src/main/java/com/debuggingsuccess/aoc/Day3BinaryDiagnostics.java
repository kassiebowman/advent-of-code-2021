package com.debuggingsuccess.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://adventofcode.com/2021/day/3">https://adventofcode.com/2021/day/3</>
 */
public class Day3BinaryDiagnostics
{
    public int calculatePowerConsumption(List<String> report)
    {
        Map<Integer, Integer> bitCounts = new HashMap<>();

        for (String reportValue : report)
        {
            String[] bitStrings = reportValue.split("");

            int length = bitStrings.length;
            for (int i = 0; i < length; i++)
            {
                int bitValue = bitStrings[i].equals("1") ? 1 : 0;

                // When putting the bits in the map, use the bit position of the binary number, where the rightmost bit
                // has a position of 0. This will simplify the logic for constructing the binary number later.
                bitCounts.merge(length - i - 1, bitValue, (vOld, vNew) -> vOld += vNew);
            }
        }

        int gamma = 0;
        int epsilon = 0;
        int threshold = report.size() / 2;
        for (Map.Entry<Integer, Integer> bitCountEntry : bitCounts.entrySet())
        {
            Integer bitPosition = bitCountEntry.getKey();
            Integer bitCount = bitCountEntry.getValue();

            if (bitCount > threshold)
            {
                gamma += 1 << bitPosition;
            } else
            {
                epsilon += 1 << bitPosition;
            }
        }

        return gamma * epsilon;
    }

//    public long getLifeSupportRating(List<String> report)
//    {
//
//    }

    /**
     * Gets the number of set bits at the specified position for all values in the report.
     *
     * @param report   The report of diagnostic values as binary string
     * @param position The bit position, where the left-most bit is 0
     * @return Number of set bits at the specified position
     */
    private long getBitCountForPosition(List<String> report, int position)
    {
        return report.stream()
                .map(reportValue -> reportValue.charAt(position)) // Get the right character
                .map(Integer::valueOf)                            // Convert to int
                .filter(value -> value == 1)                      // Keep only the values equal to 1
                .count();                                         // Count the remaining values (effectively adding them)
    }
}
