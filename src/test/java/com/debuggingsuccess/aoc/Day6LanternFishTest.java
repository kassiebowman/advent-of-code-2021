package com.debuggingsuccess.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class Day6LanternFishTest
{
    @ParameterizedTest
    @CsvSource({
            "day-6-control.txt, 18, 26",
            "day-6-control.txt, 80, 5934",
            "day-6-control.txt, 256, 26984457539",
            "day-6-input.txt, 80, 363101",
            "day-6-input.txt, 256, 1644286074024"
    })
    void testSimulate(String resourceName, int days, long count) throws URISyntaxException, IOException
    {
        long fishCount = new Day6LanternFish().simulate(getInput(resourceName), days);
        assertThat(fishCount).isEqualTo(count);
    }

    @SuppressWarnings("ConstantConditions")
    private String getInput(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return Files.readAllLines(Paths.get(url.toURI())).get(0);
    }
}