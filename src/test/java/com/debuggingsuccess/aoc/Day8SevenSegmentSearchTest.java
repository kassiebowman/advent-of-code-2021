package com.debuggingsuccess.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Day8SevenSegmentSearchTest
{
    @ParameterizedTest
    @CsvSource({
            "day-8-control.txt, 26",
            "day-8-input.txt, 532",
    })
    void testGetUniqueDigitCount(String resourceName, int count) throws URISyntaxException, IOException
    {
        long uniqueDigitCount = new Day8SevenSegmentSearch().getUniqueDigitCount(getEntries(resourceName));
        assertThat(uniqueDigitCount).isEqualTo(count);
    }

    @ParameterizedTest
    @CsvSource({
            "day-8-control.txt, 61229",
            "day-8-input.txt, 1011284",
    })
    void testGetOutputSum(String resourceName, int sum) throws URISyntaxException, IOException
    {
        long outputSum = new Day8SevenSegmentSearch().getOutputSum(getEntries(resourceName));
        assertThat(outputSum).isEqualTo(sum);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getEntries(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}