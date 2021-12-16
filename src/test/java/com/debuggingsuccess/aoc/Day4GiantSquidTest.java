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

class Day4GiantSquidTest
{
    @ParameterizedTest
    @CsvSource({
            "day-4-control.txt, false, 4512",
            "day-4-input.txt, false, 63552",
            "day-4-control.txt, true, 1924",
            "day-4-input.txt, true, 9020",
    })
    void testPlayBingo(String resourceName, boolean keepPlaying, int score) throws URISyntaxException, IOException
    {
        int powerConsumption = new Day4GiantSquid(getBingoInput(resourceName), keepPlaying).playBingo();
        assertThat(powerConsumption).isEqualTo(score);
    }

    @SuppressWarnings("ConstantConditions")
    private List<String> getBingoInput(String resourceName) throws URISyntaxException, IOException
    {
        URL url = getClass().getClassLoader().getResource(resourceName);
        return new ArrayList<>(Files.readAllLines(Paths.get(url.toURI())));
    }
}