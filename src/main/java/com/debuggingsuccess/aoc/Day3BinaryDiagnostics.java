package com.debuggingsuccess.aoc;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="https://adventofcode.com/2021/day/3">https://adventofcode.com/2021/day/3</>
 */
public class Day3BinaryDiagnostics
{
    public int calculatePowerConsumption(List<String> report)
    {
        int gamma = 0;
        int epsilon = 0;
        int threshold = report.size() / 2;
        int bits = report.get(0).length();
        for (int i = 0; i < bits; i++)
        {
            int bitPosition = bits - i - 1;
            if (getBitCountForPosition(report, i) >= threshold)
            {
                gamma += 1 << bitPosition;
            } else
            {
                epsilon += 1 << bitPosition;
            }
        }

        return gamma * epsilon;
    }

    public int getLifeSupportRating(List<String> report)
    {
        int oxygenRatingString = getValueByBitCriteria(report, true);
        int co2RatingString = getValueByBitCriteria(report, false);

        return oxygenRatingString * co2RatingString;
    }

    /**
     * Starting at the left-most bit, selects only values in the report that match the specified bit criteria for that
     * bit position.
     *
     * @param report     The report of diagnostic values as binary strings
     * @param mostCommon Whether the values matching the most common bit should be kept
     * @return The remaining value after reducing by each bit position until a single value remains.
     */
    private int getValueByBitCriteria(List<String> report, boolean mostCommon)
    {
        int i = 0;
        double threshold = report.size() / 2.0;
        while (report.size() > 1)
        {
            String mostCommonBit = (getBitCountForPosition(report, i) >= threshold) ? "1" : "0";
            String keepValue = mostCommon ? mostCommonBit : (mostCommonBit.equals("1") ? "0" : "1");
            int bitPosition = i;
            report = report.stream()
                    .filter(value -> value.substring(bitPosition, bitPosition + 1).equals(keepValue))
                    .collect(Collectors.toList());
            threshold = report.size() / 2.0;
            i++;
        }

        return Integer.valueOf(report.get(0), 2);
    }

    /**
     * Gets the number of set bits at the specified position for all values in the report.
     *
     * @param report   The report of diagnostic values as binary strings
     * @param position The bit position, where the left-most bit is 0
     * @return Number of set bits at the specified position
     */
    private long getBitCountForPosition(List<String> report, int position)
    {
        return report.stream()
                .map(reportValue -> reportValue.substring(position, position + 1)) // Get the right character
                .map(Integer::valueOf)                            // Convert to int
                .filter(value -> value == 1)                      // Keep only the values equal to 1
                .count();                                         // Count the remaining values (effectively adding them)
    }
}
