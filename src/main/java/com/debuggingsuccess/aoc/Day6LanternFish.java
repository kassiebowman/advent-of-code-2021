package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public int simulate(String fishTimerCsv, int days)
    {
        List<Fish> fishList = initializeFish(fishTimerCsv);

        for (int i = 0; i < days; i++)
        {
            List<Fish> newFish = fishList.stream()
                    .map(Fish::updateTimer)
                    .filter(ready -> ready)
                    .map(ready -> new Fish())
                    .collect(Collectors.toList());

            fishList.addAll(newFish);
        }

        return fishList.size();
    }

    private List<Fish> initializeFish(String fishTimerCsv)
    {
        List<Fish> fishList = new ArrayList<>();
        String[] timers = fishTimerCsv.split(",");
        for (String timer : timers)
        {
            fishList.add(new Fish(Integer.parseInt(timer)));
        }

        return fishList;
    }

    private static class Fish
    {
        private int timer = 8;

        public Fish()
        {
        }

        public Fish(int daysLeft)
        {
            this.timer = daysLeft;
        }

        /**
         * Reduces the timer by 1 (or resets to 6 if 0).
         *
         * @return True if the Fish is ready to spawn.
         */
        public boolean updateTimer()
        {
            boolean readyToSpawn = timer == 0;
            timer = readyToSpawn ? 6 : timer - 1;

            return readyToSpawn;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Fish fish = (Fish) o;
            return timer == fish.timer;
        }

        @Override
        public int hashCode()
        {
            return 0;
        }
    }
}
