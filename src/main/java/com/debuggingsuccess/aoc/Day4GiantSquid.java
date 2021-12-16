package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @see <a href="https://adventofcode.com/2021/day/4">https://adventofcode.com/2021/day/4</>
 */
public class Day4GiantSquid
{
    private final List<BingoBoard> boards;
    private final List<Integer> callSequence;
    private final boolean keepPlaying;

    /**
     * Constructor.
     *
     * @param bingoInput The input for the game as a list of strings, where the first string is the numbers to be called
     *                   and the remaining strings are board grids, separated by a blank line
     */
    public Day4GiantSquid(List<String> bingoInput, boolean keepPlaying)
    {
        callSequence = createCallSequence(bingoInput.remove(0));
        boards = createBoards(bingoInput);
        this.keepPlaying = keepPlaying;
    }

    private List<Integer> createCallSequence(String numberString)
    {
        return Arrays.stream(numberString.split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * Parses the input to create bingo boards.
     *
     * @param bingoInput The input lines
     * @return The bingo boards represented by the input.
     */
    private List<BingoBoard> createBoards(List<String> bingoInput)
    {
        List<String> boardRows = new ArrayList<>();
        List<BingoBoard> boards = new ArrayList<>();

        for (String line : bingoInput)
        {
            if (line.isBlank())
            {
                if (!boardRows.isEmpty()) boards.add(new BingoBoard(boardRows));
                boardRows.clear();
            } else
            {
                boardRows.add(line);
            }
        }

        if (!boardRows.isEmpty()) boards.add(new BingoBoard(boardRows));

        return boards;
    }

    /**
     * Calls the numbers in order until a board gets a bingo, at which point the score of the winning board is reported.
     *
     * @return The score on the winning board
     */
    public int playBingo()
    {
        int finalScore = -1;

        for (Integer number : callSequence)
        {
            for (BingoBoard board : boards)
            {
                int score = board.callNumber(number);
                if (score != -1)
                {
                    if (!keepPlaying) return score;
                    finalScore = score;
                }
            }

            boards.removeIf(BingoBoard::hasBingo);
            if (boards.isEmpty()) break;
        }

        return finalScore;
    }

    /**
     * Represents a bingo board using a list of lists and {@link BingoNumber}s for the individual
     * squares in the grid.
     */
    private static class BingoBoard
    {
        private final int size;
        private final List<List<BingoNumber>> rows = new ArrayList<>();

        BingoBoard(List<String> boardRows)
        {
            size = boardRows.size();
            boardRows.forEach(row -> rows.add(parseRow(row)));
        }

        private List<BingoNumber> parseRow(String rowString)
        {
            List<BingoNumber> row = new ArrayList<>();
            String[] numberStrings = rowString.split("\\s+");
            for (String numberString : numberStrings)
            {
                if (numberString.isBlank()) continue;
                row.add(new BingoNumber(Integer.parseInt(numberString)));
            }

            return row;
        }

        /**
         * Marks the number on the board (if present) and checks for a bingo.
         *
         * @param number The number that was called
         * @return Resulting score if the number produced a bingo, or -1 if no bingo.
         */
        private int callNumber(int number)
        {
            markBoard(number);
            return hasBingo() ? scoreBingo(number) : -1;
        }

        /**
         * Searches the board for the number and marks it if present.
         *
         * @param number The number to mark
         */
        private void markBoard(int number)
        {
            rows.stream()
                    .flatMap(Collection::stream)
                    .filter(bn -> bn.value == number)
                    .findFirst()
                    .ifPresent(BingoNumber::mark);
        }

        public boolean hasBingo()
        {
            return isHorizontalBingo() || isVerticalBingo();
        }

        /**
         * Checks for a horizontal bingo.
         *
         * @return True if all numbers in any row are marked.
         */
        private boolean isHorizontalBingo()
        {
            for (List<BingoNumber> row : rows)
            {
                int markedInRow = 0;
                for (BingoNumber bingoNumber : row)
                {
                    if (!bingoNumber.marked) break;
                    markedInRow++;
                }

                if (markedInRow == size) return true;
            }

            return false;
        }

        /**
         * Checks for a vertical bingo.
         *
         * @return True if all numbers in any column are marked.
         */
        private boolean isVerticalBingo()
        {
            for (int i = 0; i < size; i++)
            {
                int markedInColumn = 0;
                for (List<BingoNumber> row : rows)
                {
                    if (!row.get(i).marked) break;
                    markedInColumn++;
                }

                if (markedInColumn == size) return true;
            }

            return false;
        }

        /**
         * Scores the board by summing all the unmarked numbers and multiplying by the called number.
         *
         * @param number The called number
         * @return The score for the board.
         */
        public int scoreBingo(int number)
        {
            return rows.stream()
                    .flatMap(Collection::stream)
                    .filter(bn -> !bn.marked)
                    .mapToInt(bn -> bn.value)
                    .sum() * number;
        }

        @Override
        public String toString()
        {
            return "BingoBoard{" +
                    "rows=" + rows +
                    '}';
        }
    }

    /**
     * Represents a number on the bingo board, including whether it has been marked.
     */
    private static class BingoNumber
    {
        public final int value;
        private boolean marked = false;

        private BingoNumber(int value)
        {
            this.value = value;
        }

        public void mark()
        {
            this.marked = true;
        }

        @Override
        public String toString()
        {
            return marked ? "*" + value + "*" : String.valueOf(value);
        }
    }
}
