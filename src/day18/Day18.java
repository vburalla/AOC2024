package day18;

import utils.Point;
import utils.ReadFiles;

import java.util.*;
import java.util.stream.Collectors;

public class Day18 {

    private static Point UP = new Point(0, -1);
    private static Point DOWN = new Point(0, 1);
    private static Point LEFT = new Point(-1, 0);
    private static Point RIGHT = new Point(1, 0);
    private static Point[] DIRECTIONS = new Point[]{UP, DOWN, LEFT, RIGHT};
    private static int LIMIT = 1024;
    private static int MAX_SIZE = 70;

    public static void main(String[] args) {

        System.out.println("Day 18");
        Set<Point> blockingPoints = new HashSet<>();
        List<String> lines = ReadFiles.getInputData("src/day18/input1.txt");
        System.out.println(findShortestPath(lines.stream().limit(LIMIT).map(Point::new).collect(Collectors.toSet()), MAX_SIZE, MAX_SIZE));
        int result = 1;
        int i = 1;
        while(result > 0 && (LIMIT + i) < lines.size()) {
            blockingPoints = lines.stream().limit(LIMIT + i).map(Point::new).collect(Collectors.toSet());
            result = findShortestPath(blockingPoints, MAX_SIZE, MAX_SIZE);
            i++;
        }
        if(result == -1) {
            System.out.println("Blocking byte = " + lines.get(LIMIT + i - 2) );
        }


    }

    public static int findShortestPath(Set<Point> corrupted, int maxX, int maxY) {
        Point start = new Point(0, 0);
        Point goal = new Point(maxX, maxY);

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Set<Point> closedSet = new HashSet<>();
        Map<Point, Integer> gScores = new HashMap<>();

        Node startNode = new Node(start, 0, heuristic(start, goal), null);
        openSet.add(startNode);
        gScores.put(start, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.point.equals(goal)) {
                return current.gScore;
            }

            closedSet.add(current.point);

            for (Point dir : DIRECTIONS) {
                Point neighbor = current.point.add(dir);

                if (neighbor.getX() < 0 || neighbor.getX() > maxX || neighbor.getY() < 0 || neighbor.getY() > maxY ||
                        corrupted.contains(neighbor) || closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeGScore = current.gScore + 1;

                if (!(gScores.containsKey(neighbor) && tentativeGScore >= gScores.get(neighbor))) {
                    gScores.put(neighbor, tentativeGScore);
                    int fScore = tentativeGScore + heuristic(neighbor, goal);
                    Node neighborNode = new Node(neighbor, tentativeGScore, fScore, current);
                    openSet.add(neighborNode);
                }
            }
        }

        return -1;
    }

    private static int heuristic(Point a, Point b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}

class Node implements Comparable<Node> {

    Point point;
    int gScore;
    int fScore;
    Node parent;

    Node(Point point, int gScore, int fScore, Node parent) {
        this.point = point;
        this.gScore = gScore;
        this.fScore = fScore;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.fScore, other.fScore);
    }
}
