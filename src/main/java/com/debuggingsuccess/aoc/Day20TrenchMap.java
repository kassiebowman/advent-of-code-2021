package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://adventofcode.com/2021/day/20">https://adventofcode.com/2021/day/20</>
 */
public class Day20TrenchMap
{
    private final List<Integer> pixels = new ArrayList<>();
    private final List<List<Integer>> imageRows = new ArrayList<>();

    public Day20TrenchMap(List<String> input)
    {
        parsePixels(input.remove(0));

        input.remove(0); // Skip blank line

        parseImage(input);
    }

    private void parsePixels(String pixelString)
    {
        for (int i = 0; i < pixelString.length(); i++)
        {
            pixels.add(pixelString.charAt(i) == '#' ? 1 : 0);
        }
    }

    private void parseImage(List<String> input)
    {
        for (String rowValues : input)
        {
            List<Integer> row = new ArrayList<>();

            for (int i = 0; i < rowValues.length(); i++)
            {
                row.add(rowValues.charAt(i) == '#' ? 1 : 0);
            }

            imageRows.add(row);
        }
    }

    /**
     * Enhances the image the specified number of times.
     *
     * @param times The number of times to enhance the image
     * @return The number of light pixels in the enhanced image.
     */
    public int enhance(int times)
    {
        List<List<Integer>> outputImage = imageRows;
        for (int i = 0; i < times; i++)
        {
            outputImage = enhance(outputImage);
        }

        return countLightPixels(outputImage);
    }

    private List<List<Integer>> enhance(List<List<Integer>> rows)
    {
        List<List<Integer>> outputImage = new ArrayList<>();
        int colCount = rows.get(0).size();

        // The output image is 1 pixel wider and taller on each side
        for (int i = -1; i < rows.size() + 1; i++)
        {
            List<Integer> outRow = new ArrayList<>();
            for (int j = -1; j < colCount + 1; j++)
            {
                int squareValue = getSquareValue(rows, i, j);
                int pixelValue = pixels.get(squareValue);
                outRow.add(pixelValue);
            }

            outputImage.add(outRow);
        }

        return outputImage;
    }

    private int getSquareValue(List<List<Integer>> rows, int rowIndex, int colIndex)
    {
        int value = 0;
        int iLimit = rows.size() - 1;
        int jLimit = rows.get(0).size() - 1;
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++)
        {
            List<Integer> row = i >= 0 && i <= iLimit ? rows.get(i) : null;

            for (int j = colIndex - 1; j <= colIndex + 1; j++)
            {
                if (row == null)
                {
                    value = value << 1;
                } else if (j >= 0 && j <= jLimit)
                {
                    value = (value << 1) + row.get(j);
                } else
                {
                    value = value << 1;
                }
            }
        }

        return value;
    }

    private int countLightPixels(List<List<Integer>> rows)
    {
        int lightPixels = 0;
        int colCount = rows.get(0).size();

        for (List<Integer> row : rows)
        {
            for (int j = 0; j < colCount; j++)
            {
                lightPixels += row.get(j);
            }
        }

        return lightPixels;
    }
}
