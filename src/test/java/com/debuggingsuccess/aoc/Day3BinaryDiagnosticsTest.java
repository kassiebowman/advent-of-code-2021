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

class Day3BinaryDiagnosticsTest
{
    @ParameterizedTest
    @CsvSource({
            "day-3-control.txt, 198",
            "day-3-input.txt, 3374136",
    })
    void testCalculatePowerConsumption(String resourceName, int multipliedValues) throws URISyntaxException, IOException
    {
        int powerConsumption = new Day3BinaryDiagnostics().calculatePowerConsumption(getReport(resourceName));
        assertThat(powerConsumption).isEqualTo(multipliedValues);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getReport(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}