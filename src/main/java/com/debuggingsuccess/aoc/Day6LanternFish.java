package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @see <a href="https://adventofcode.com/2021/day/6">https://adventofcode.com/2021/day/6</>
 */
public class Day6LanternFish
{
    /**
     * Simulates fish population after the specified number of days
     *
     * @param fishTimerCsv A comma-separated list of fish timer values
     * @param days         The number of days to simulate
     * @return The number of fish after the specified number of days
     */
    public long simulate(String fishTimerCsv, int days)
    {
        List<Long> fishAtAge = initializeFish(fishTimerCsv);

        for (int d = 0; d < days; d++)
        {
            // Get the number of fish that are going to spawn today
            long spawningFish = fishAtAge.get(0);

            // Decrement the age of all fish
            for (int i = 1; i <= 8; i++)
            {
                fishAtAge.set(i - 1, fishAtAge.get(i));
            }

            // Put the spawning fish back in the cycle
            fishAtAge.set(6, fishAtAge.get(6) + spawningFish);

            // And spawn the new fish
            fishAtAge.set(8, spawningFish);
        }

        return fishAtAge.stream().mapToLong(count -> count).sum();
    }

    private List<Long> initializeFish(String fishTimerCsv)
    {
        List<Long> fishAtAge = new ArrayList<>();
        LongStream.rangeClosed(0, 8).forEach(v -> fishAtAge.add(0L));

        String[] timers = fishTimerCsv.split(",");
        for (String timer : timers)
        {
            int age = Integer.parseInt(timer);
            Long count = fishAtAge.get(age);
            fishAtAge.set(age, ++count);
        }

        return fishAtAge;
    }
}
