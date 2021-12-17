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

class Day5HydrothermalVentureTest
{
    @ParameterizedTest
    @CsvSource({
            "day-5-control.txt, false, 5",
            "day-5-input.txt, false, 7473",
            "day-5-control.txt, true, 12",
            "day-5-input.txt, true, 24164",
    })
    void testGetOverlappingPoints(String resourceName, boolean includeDiagonals, int count) throws URISyntaxException, IOException
    {
        long points = new Day5HydrothermalVenture(getLines(resourceName), includeDiagonals).getOverlappingPoints();
        assertThat(points).isEqualTo(count);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getLines(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}