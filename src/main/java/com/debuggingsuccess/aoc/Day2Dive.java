package com.debuggingsuccess.aoc;

import java.util.List;

public class Day2Dive
{
    /**
     * Maneuvers a submarine based on the provided list of commands.
     *
     * @param commands The commands for maneuvering the sub
     * @return The value of the final depth multiplied by the horizontal position.
     */
    public int dive(List<String> commands)
    {
        int depth = 0;
        int horPosition = 0;

        for (String command : commands)
        {
            Movement movement = convertCommandToMovement(command);
            switch (movement.direction)
            {
                case UP:
                    depth -= movement.value;
                    break;
                case DOWN:
                    depth += movement.value;
                    break;
                case FORWARD:
                    horPosition += movement.value;
                    break;
            }
        }

        return depth * horPosition;
    }
    /**
     * Maneuvers a submarine based on the provided list of commands.
     *
     * @param commands The commands for maneuvering the sub
     * @return The value of the final depth multiplied by the horizontal position.
     */
    public int diveWithAim(List<String> commands)
    {
        int aim = 0;
        int depth = 0;
        int horPosition = 0;

        for (String command : commands)
        {
            Movement movement = convertCommandToMovement(command);
            switch (movement.direction)
            {
                case UP:
                    aim -= movement.value;
                    break;
                case DOWN:
                    aim += movement.value;
                    break;
                case FORWARD:
                    horPosition += movement.value;
                    depth += movement.value * aim;
                    break;
            }
        }

        return depth * horPosition;
    }

    /**
     * Converts a command in the format "direction #" to a {@link Movement}.
     *
     * @param command The command to convert
     * @return The movement representing the command or a 0 valued movement if something went wrong.
     */
    private Movement convertCommandToMovement(String command)
    {
        String[] parts = command.split(" ");
        if (parts.length != 2) return new Movement(Direction.UP, 0);

        String directionString = parts[0];
        for (Direction direction : Direction.values())
        {
            if (direction.commandText.equals(directionString))
            {
                return new Movement(direction, Integer.parseInt(parts[1]));
            }
        }

        return new Movement(Direction.UP, 0);
    }

    private static class Movement
    {
        final Direction direction;
        final int value;

        private Movement(Direction direction, int value)
        {
            this.direction = direction;
            this.value = value;
        }
    }

    private enum Direction
    {
        UP("up"),
        DOWN("down"),
        FORWARD("forward");

        private String commandText;

        Direction(String commandText)
        {
            this.commandText = commandText;
        }

        public String getCommandText()
        {
            return commandText;
        }
    }
}
