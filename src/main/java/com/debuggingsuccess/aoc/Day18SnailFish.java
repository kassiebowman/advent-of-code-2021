package com.debuggingsuccess.aoc;

import java.util.ArrayList;
import java.util.List;

public class Day18SnailFish
{
    /**
     * Adds a list of snail numbers by combining the trees and reducing them successively.
     *
     * @param snailNumStrings The strings representing the snail numbers
     * @return The magnitude of the resulting tree
     */
    public int addAll(List<String> snailNumStrings)
    {
        Node rootNode = parse(snailNumStrings.remove(0));
        for (String snailNumString : snailNumStrings)
        {
            Node newNode = parse(snailNumString);
            rootNode = add(rootNode, newNode);
            reduce(rootNode);
        }

        return calculateMagnitude(rootNode);
    }

    /**
     * Finds the maximum value achieved from adding any two values in the provided list.
     *
     * @param snailNumStrings The strings representing the snail numbers
     * @return The magnitude of the maximum addition.
     */
    public int findMaxAdd(List<String> snailNumStrings)
    {
        List<Node> numberTrees = new ArrayList<>();
        for (String snailNumString : snailNumStrings)
        {
            numberTrees.add(parse(snailNumString));
        }

        int maxSum = -1;
        int size = numberTrees.size();
        for (int i = 0; i < size; i++)
        {
            for (int j = 0; j < size; j++)
            {
                if (i == j) continue;
                
                // Make copies to avoid modifying the original parsed values.
                Node rootNode = add(new Node(numberTrees.get(i)), new Node(numberTrees.get(j)));
                reduce(rootNode);
                int sum = calculateMagnitude(rootNode);
                if (sum > maxSum) maxSum = sum;
            }
        }

        return maxSum;
    }

    private Node parse(String numString)
    {
        if (!numString.contains("["))
        {
            return new Node(Integer.parseInt(numString));
        }

        String nakedPair = numString.substring(1, numString.length() - 1);
        int commaIndex = findCommaForNakedPair(nakedPair);
        return new Node(
                parse(nakedPair.substring(0, commaIndex)),
                parse(nakedPair.substring(commaIndex + 1)));
    }

    /**
     * Finds the position of the comma that splits the values in the pair.
     *
     * @param pair Pair with the outer brackets stripped, e.g. 1,[2,3]
     * @return The position of the comma that splits the values of the pair.
     */
    private int findCommaForNakedPair(String pair)
    {
        int numOpenBrackets = 0;
        int numCloseBrackets = 0;
        for (int i = 0; i < pair.length(); i++)
        {
            switch (pair.charAt(i))
            {
                case '[':
                    numOpenBrackets++;
                    break;
                case ']':
                    numCloseBrackets++;
                    break;
                case ',':
                    if (numOpenBrackets == numCloseBrackets) return i;
                    break;
            }
        }

        return -1;
    }

    /**
     * Combines the trees represented by the two roots by creating a new root with the first node on the left and the
     * second on the right.
     *
     * @param leftNode  The node to put on the left
     * @param rightNode The node to put on the right
     * @return The root node for th combined tree.
     */
    private Node add(Node leftNode, Node rightNode)
    {
        return new Node(leftNode, rightNode);
    }

    private void reduce(Node rootNode)
    {
        boolean reducible = true;
        do
        {
            Node explodableNode = findExplodableNode(rootNode, 0);
            if (explodableNode != null)
            {
                explode(explodableNode);
            } else
            {
                Node splittableNode = findSplittableNode(rootNode);
                if (splittableNode != null)
                {
                    splittableNode.split();
                } else
                {
                    reducible = false;
                }
            }
        } while (reducible);
    }

    private Node findExplodableNode(Node node, int depth)
    {
        if (node == null) return null;
        if (depth == 4 && node.value == -1) return node;

        Node leftNode = findExplodableNode(node.left, depth + 1);
        if (leftNode != null) return leftNode;

        return findExplodableNode(node.right, depth + 1);
    }

    private void explode(Node node)
    {
        Node nextLeafToLeft = findNextLeafToLeft(node);
        if (nextLeafToLeft != null) nextLeafToLeft.value += node.left.value;

        Node nextLeafToRight = findNextLeafToRight(node);
        if (nextLeafToRight != null) nextLeafToRight.value += node.right.value;

        node.left = null;
        node.right = null;
        node.value = 0;
    }

    private Node findNextLeafToLeft(Node node)
    {
        if (node == null) return null;

        Node parent = node.parent;
        if (parent == null) return null;

        if (parent.left == node) return findNextLeafToLeft(parent);

        Node currentNode = parent.left;
        while (currentNode.value == -1)
        {
            currentNode = currentNode.right;
        }

        return currentNode;
    }

    private Node findNextLeafToRight(Node node)
    {
        if (node == null) return null;

        Node parent = node.parent;
        if (parent == null) return null;

        if (parent.right == node) return findNextLeafToRight(parent);

        Node currentNode = parent.right;
        while (currentNode.value == -1)
        {
            currentNode = currentNode.left;
        }

        return currentNode;
    }

    private Node findSplittableNode(Node node)
    {
        if (node == null) return null;
        if (node.value >= 10) return node;

        Node leftNode = findSplittableNode(node.left);
        if (leftNode != null) return leftNode;

        return findSplittableNode(node.right);
    }

    private int calculateMagnitude(Node rootNode)
    {
        if (rootNode.left == null || rootNode.right == null) return rootNode.value;
        return 3 * calculateMagnitude(rootNode.left) + 2 * calculateMagnitude(rootNode.right);
    }

    /**
     * Node to represent a pair in the tree.
     */
    private static class Node
    {
        Node parent = null;
        Node left = null;
        Node right = null;
        int value = -1;

        /**
         * Copy constructor
         * @param source The node to copy
         */
        public Node(Node source)
        {
            value = source.value;
            if (source.left != null)
            {
                left = new Node(source.left);
                left.parent = this;
            }

            if (source.right != null)
            {
                right = new Node(source.right);
                right.parent = this;
            }
        }

        public Node(Node left, Node right)
        {
            this.left = left;
            left.parent = this;
            this.right = right;
            right.parent = this;
        }

        public Node(int value)
        {
            this.value = value;
        }

        public void split()
        {
            double halfValue = value / 2.0;
            int leftValue = (int) Math.floor(halfValue);
            int rightValue = (int) Math.ceil(halfValue);

            left = new Node(leftValue);
            left.parent = this;
            right = new Node(rightValue);
            right.parent = this;
            value = -1;
        }

        @Override
        public String toString()
        {
            if (value != -1) return String.valueOf(value);

            return "[" + left.toString() + "," + right.toString() + "]";
        }
    }
}
