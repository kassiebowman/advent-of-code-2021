package com.debuggingsuccess.aoc;

import java.util.HashMap;
import java.util.Map;

/**
 * @see <a href="https://adventofcode.com/2021/day/7">https://adventofcode.com/2021/day/7</>
 */
public class Day7TreacheryOfWhales
{
    private final int minLocation;
    private final int maxLocation;
    private final Map<Integer, Integer> crabsAtLocation = new HashMap<>();

    public Day7TreacheryOfWhales(String crabLocations)
    {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        String[] locationStrings = crabLocations.split(",");
        for (String locationString : locationStrings)
        {
            int location = Integer.parseInt(locationString);
            if (location < min) min = location;
            if (location > max) max = location;

            crabsAtLocation.merge(location, 1, Integer::sum);
        }

        minLocation = min;
        maxLocation = max;
    }

    public int calculateMinFuelCost(boolean contactFuelUse)
    {
        int minFuelCost = Integer.MAX_VALUE;
        for (int i = minLocation; i <= maxLocation; i++)
        {
            int fuelCost = 0;
            for (Map.Entry<Integer, Integer> entry : crabsAtLocation.entrySet())
            {
                int distance = Math.abs(entry.getKey() - i);
                if (contactFuelUse)
                {
                    fuelCost += distance * entry.getValue();
                } else {
                    // Use triangle numbers formula to get the sum of the increasing costs
                    fuelCost += distance * (distance + 1) / 2 * entry.getValue();
                }
            }

            if (fuelCost < minFuelCost) minFuelCost = fuelCost;
        }

        return minFuelCost;
    }
}
