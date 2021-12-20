package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @see <a href="https://adventofcode.com/2021/day/8">https://adventofcode.com/2021/day/8</>
 */
public class Day8SevenSegmentSearch
{
    /**
     * Determines the number of times 1, 4, 7, or 8 occurs in the output (as these are digits with unique segment counts
     * that can be easily identified).
     *
     * @param entries The list of entries corresponding to the different displays
     * @return The number of times 1, 4, 7, and 8 occur in the output displays.
     */
    public int getUniqueDigitCount(List<String> entries)
    {
        int uniqueCount = 0;
        for (String entry : entries)
        {
            List<String> outputValues = getOutputStrings(entry);
            for (String outputValue : outputValues)
            {
                List<Integer> potentialDigits = getPotentialDigits(outputValue);
                if (potentialDigits.size() == 1) uniqueCount++;
            }
        }

        return uniqueCount;
    }

    /**
     * @param entry The entry in the notes describing each digit and the displayed output.
     * @return The ten strings corresponding to the 10 digits, 0-9.
     */
    private List<String> getDigitStrings(String entry)
    {
        String[] parts = entry.split(" \\| ");
        return new ArrayList<>(Arrays.asList(parts[0].split("\\s+")));
    }

    /**
     * @param entry The entry in the notes describing each digit and the displayed output.
     * @return The four strings corresponding to the 4-digit display.
     */
    private List<String> getOutputStrings(String entry)
    {
        String[] parts = entry.split(" \\| ");
        return new ArrayList<>(Arrays.asList(parts[1].split("\\s+")));
    }

    /**
     * Deciphers the digits shown on each output display and sums their values.
     *
     * @param entries The list of entries corresponding to the different displays
     * @return Sum of the output displays.
     */
    public int getOutputSum(List<String> entries)
    {
        int sum = 0;
        for (String entry : entries)
        {
            List<String> digitStrings = getDigitStrings(entry);
            Map<Integer, String> digitStringMap = createDigitMap(digitStrings);
            sum += decodeOutput(digitStringMap, getOutputStrings(entry));
        }

        return sum;
    }

    /**
     * Creates a map of the digits 0-9 to the representative string.
     *
     * @param digitStrings The strings representing the digits
     * @return The map of digits to the string of segments.
     */
    private Map<Integer, String> createDigitMap(List<String> digitStrings)
    {
        Map<Integer, String> digitStringMap = new HashMap<>();

        // First do the easy ones: 1, 4, 7, and 8
        assignUniqueDigits(digitStringMap, digitStrings);

        // Then 9 is the 6-digit string that contains all the segments in 4
        findDigitThatContainsKnownDigit(digitStrings, digitStringMap, 6, 4, 9);

        // And 0 is the 6-digit string that contains all the segments in 7
        findDigitThatContainsKnownDigit(digitStrings, digitStringMap, 6, 7, 0);

        // And 3 is the 5-digit string that contains all the segments in 7
        findDigitThatContainsKnownDigit(digitStrings, digitStringMap, 5, 7, 3);

        // Then 5 is the 5-digit string whose segments are all contained in 9
        findDigitContainedByKnownDigit(digitStrings, digitStringMap, 5, 9, 5);

        // Finally, 6 is the last remaining 6-digit string, and 2 is the last remaining 5-digit string
        for (String digitString : digitStrings)
        {
            if (digitString.length() == 6) digitStringMap.put(6, digitString);
            if (digitString.length() == 5) digitStringMap.put(2, digitString);
        }

        return digitStringMap;
    }

    /**
     * Identifies a digit based on the requirement of the digit string of a given length to contain some other known
     * digit string.
     *
     * @param digitStrings    The list of digit strings
     * @param digitStringMap  The map of known digits to the representative strings
     * @param segmentCount    The number of segments in the digit to find
     * @param containedDigit  The digit that should be contained in the digit to find
     * @param digitToIdentify The digit to find
     */
    private void findDigitThatContainsKnownDigit(List<String> digitStrings, Map<Integer, String> digitStringMap,
                                                 int segmentCount, int containedDigit, int digitToIdentify)
    {
        findDigitByContainment(digitStrings, digitStringMap, segmentCount, containedDigit, digitToIdentify, true);
    }

    /**
     * Identifies a digit based on the requirement of the digit string of a given length to be contained in some other
     * known digit string.
     *
     * @param digitStrings    The list of digit strings
     * @param digitStringMap  The map of known digits to the representative strings
     * @param segmentCount    The number of segments in the digit to find
     * @param containingDigit The digit that should contain the digit to find
     * @param digitToIdentify The digit to find
     */
    private void findDigitContainedByKnownDigit(List<String> digitStrings, Map<Integer, String> digitStringMap,
                                                int segmentCount, int containingDigit, int digitToIdentify)
    {
        findDigitByContainment(digitStrings, digitStringMap, segmentCount, containingDigit, digitToIdentify, false);
    }

    /**
     * Identifies a digit based on the requirement of the digit string of a given length to contain some other known
     * digit string.
     *
     * @param digitStrings        The list of digit strings
     * @param digitStringMap      The map of known digits to the representative strings
     * @param segmentCount        The number of segments in the digit to find
     * @param knownDigit          The digit that should be contained in the digit to find
     * @param digitToIdentify     The digit to find
     * @param knownDigitContained Whether the known digit in the one that should be contained
     */
    private void findDigitByContainment(List<String> digitStrings, Map<Integer, String> digitStringMap, int segmentCount,
                                        int knownDigit, int digitToIdentify, boolean knownDigitContained)
    {
        String knownDigitString = digitStringMap.get(knownDigit);
        for (String digitString : digitStrings)
        {
            if (digitString.length() == segmentCount)
            {
                boolean contained = knownDigitContained ?
                        containsSegments(digitString, knownDigitString) :
                        containsSegments(knownDigitString, digitString);
                if (contained)
                {
                    digitStringMap.put(digitToIdentify, digitString);
                    break;
                }
            }
        }

        digitStrings.remove(digitStringMap.get(digitToIdentify));
    }

    /**
     * @param digitString1 The string to check for containment of the other string's segments
     * @param digitString2 The string of the contained digit
     * @return True if the first string contains all the segments in the second string.
     */
    private boolean containsSegments(String digitString1, String digitString2)
    {
        for (int i = 0; i < digitString2.length(); i++)
        {
            if (!digitString1.contains(digitString2.subSequence(i, i + 1))) return false;
        }

        return true;
    }

    /**
     * Decodes the digits with unique segment counts, populating the map and removing them from the list of digit strings.
     *
     * @param digitStringMap The map of digits to the representative string
     * @param digitStrings   The list of digit strings
     */
    private void assignUniqueDigits(Map<Integer, String> digitStringMap, List<String> digitStrings)
    {
        List<String> decodedDigits = new ArrayList<>();
        for (String digitString : digitStrings)
        {
            List<Integer> potentialDigits = getPotentialDigits(digitString);
            if (potentialDigits.size() == 1)
            {
                decodedDigits.add(digitString);
                digitStringMap.put(potentialDigits.get(0), digitString);
            }
        }

        digitStrings.removeAll(decodedDigits);
    }

    private int decodeOutput(Map<Integer, String> digitStringMap, List<String> outputStrings)
    {
        int positionMultiplier = 1000;
        int outputValue = 0;
        for (String outputString : outputStrings)
        {
            outputValue += findMatchingDigit(digitStringMap, outputString) * positionMultiplier;
            positionMultiplier /= 10;
        }

        return outputValue;
    }

    private int findMatchingDigit(Map<Integer, String> digitStringMap, String outputString)
    {
        int numSegments = outputString.length();
        for (Map.Entry<Integer, String> entry : digitStringMap.entrySet())
        {
            String knownDigitString = entry.getValue();
            if (numSegments == knownDigitString.length())
            {
                if (containsSegments(outputString, knownDigitString)) return entry.getKey();
            }
        }

        return -1;
    }

    private List<Integer> getPotentialDigits(String segments)
    {
        switch (segments.length())
        {
            case 2:
                return List.of(1);
            case 3:
                return List.of(7);
            case 4:
                return List.of(4);
            case 5:
                return List.of(2, 3, 5);
            case 6:
                return List.of(0, 6, 9);
            case 7:
                return List.of(8);
            default:
                return Collections.emptyList();
        }
    }
}
