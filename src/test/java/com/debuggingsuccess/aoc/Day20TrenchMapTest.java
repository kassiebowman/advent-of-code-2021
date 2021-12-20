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

class Day20TrenchMapTest
{
    @ParameterizedTest
    @CsvSource({
            "day-20-control.txt, 2, 35",
            "day-20-input.txt, 2, 5483",
            "day-20-control.txt, 50, 3351",
            "day-20-input.txt, 50, 20330",
    })
    void testEnhance(String resourceName, int times, int pixels) throws URISyntaxException, IOException
    {
        int lightPixels = new Day20TrenchMap(getInput(resourceName)).enhance(times);
        assertThat(lightPixels).isEqualTo(pixels);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getInput(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}