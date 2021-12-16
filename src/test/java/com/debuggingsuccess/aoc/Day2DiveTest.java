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

class Day2DiveTest
{
    @ParameterizedTest
    @CsvSource({
            "day-2-control.txt, 150",
            "day-2-input.txt, 2039912",
    })
    void testDive(String resourceName, int multipliedValues) throws URISyntaxException, IOException
    {
        int result = new Day2Dive().dive(getCommands(resourceName));
        assertThat(result).isEqualTo(multipliedValues);
    }
    @ParameterizedTest
    @CsvSource({
            "day-2-control.txt, 900",
            "day-2-input.txt, 1942068080",
    })
    void testDiveWithAim(String resourceName, int multipliedValues) throws URISyntaxException, IOException
    {
        int result = new Day2Dive().diveWithAim(getCommands(resourceName));
        assertThat(result).isEqualTo(multipliedValues);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getCommands(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}