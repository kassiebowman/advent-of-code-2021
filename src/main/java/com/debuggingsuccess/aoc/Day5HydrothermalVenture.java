package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @see <a href="https://adventofcode.com/2021/day/5">https://adventofcode.com/2021/day/5</>
 */
public class Day5HydrothermalVenture
{
    private final Map<Coord, Integer> grid = new HashMap<>();

    public Day5HydrothermalVenture(List<String> lines, boolean includeDiagonals)
    {
        for (String line : lines)
        {
            List<Coord> coordLine = parseLine(line);

            if (includeDiagonals || !isDiagonal(coordLine))
            {
                coordLine.forEach(coord -> grid.merge(coord, 1, Integer::sum));
            }
        }
    }

    /**
     * Parses a line in the format X1,Y1 -> X2,Y2 into a list of XY coordinates representing the line.
     *
     * @param line The string representation of the line
     * @return List of coordinates representing the line.
     */
    private List<Coord> parseLine(String line)
    {
        String[] xyStrings = line.split(" -> ");
        Coord coord1 = createCoordinate(xyStrings[0]);
        Coord coord2 = createCoordinate(xyStrings[1]);

        List<Coord> coordLine = new ArrayList<>();
        int xIncrement = Integer.compare(coord2.x, coord1.x);
        int yIncrement = Integer.compare(coord2.y, coord1.y);

        for (int x = coord1.x, y = coord1.y; x != coord2.x || y != coord2.y; x += xIncrement, y += yIncrement)
        {
            coordLine.add(new Coord(x, y));
        }

        coordLine.add(coord2);

        return coordLine;
    }

    private Coord createCoordinate(String coordString)
    {
        String[] values = coordString.split(",");
        return new Coord(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
    }

    private boolean isDiagonal(List<Coord> coordLine)
    {
        Coord coord1 = coordLine.get(0);
        Coord coord2 = coordLine.get(coordLine.size() - 1);
        return coord1.x != coord2.x && coord1.y != coord2.y;
    }

    /**
     * @return The number of points in the grid with values higher than 1.
     */
    public long getOverlappingPoints()
    {
        return grid.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .count();
    }

    private static class Coord
    {
        public final int x;
        public final int y;

        private Coord(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString()
        {
            return x + "," + y;
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coord coord = (Coord) o;
            return x == coord.x && y == coord.y;
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(x, y);
        }
    }
}
