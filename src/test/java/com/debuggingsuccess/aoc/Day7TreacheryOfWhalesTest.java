package com.debuggingsuccess.aoc;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class Day7TreacheryOfWhalesTest
{
    @ParameterizedTest
    @CsvSource({
            "day-7-control.txt, true, 37",
            "day-7-input.txt, true, 337488",
            "day-7-control.txt, false, 168",
            "day-7-input.txt, false, 89647695",
    })
    void testCalculateMinFuelCost(String resourceName, boolean constantFuelUse, int cost) throws URISyntaxException, IOException
    {
        long fuelCost = new Day7TreacheryOfWhales(getInput(resourceName)).calculateMinFuelCost(constantFuelUse);
        assertThat(fuelCost).isEqualTo(cost);
    }

    @SuppressWarnings("ConstantConditions")
    private String getInput(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return Files.readAllLines(Paths.get(url.toURI())).get(0);
    }
}