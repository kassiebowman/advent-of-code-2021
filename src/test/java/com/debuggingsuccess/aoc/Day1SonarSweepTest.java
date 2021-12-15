package com.debuggingsuccess.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class Day1SonarSweepTest
{
    @ParameterizedTest
    @CsvSource({
            "day-1-control.txt, 7, 1",
            "day-1-input.txt, 1791, 1",
            "day-1-control.txt, 5, 3",
            "day-1-input.txt, 1822, 3",
    })
    void testGetDepthIncreaseCount(String resourceName, int count, int windowSize) throws URISyntaxException, IOException
    {
        int depthIncreaseCount = new Day1SonarSweep().getDepthIncreaseCount(getMeasurements(resourceName), windowSize);
        assertThat(depthIncreaseCount).isEqualTo(count);
    }

    @SuppressWarnings("ConstantConditions")
    private List<Integer> getMeasurements(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return Files.readAllLines(Paths.get(url.toURI())).stream()
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }
}