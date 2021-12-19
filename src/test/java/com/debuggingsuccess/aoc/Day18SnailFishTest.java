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

class Day18SnailFishTest
{
    @ParameterizedTest
    @CsvSource({
            "day-18-magnitude1.txt, 143",
            "day-18-magnitude2.txt, 1384",
            "day-18-magnitude3.txt, 3488",
            "day-18-add1.txt, 445",
            "day-18-add2.txt, 791",
            "day-18-add3.txt, 3488",
            "day-18-explode1.txt, 1384",
            "day-18-control.txt, 4140",
            "day-18-input.txt, 3734",
    })
    void testAdd(String resourceName, int magnitude) throws URISyntaxException, IOException
    {
        long result = new Day18SnailFish().add(getNumberStrings(resourceName));
        assertThat(result).isEqualTo(magnitude);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getNumberStrings(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}