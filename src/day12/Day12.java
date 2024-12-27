package day12;

import utils.Point;
import utils.ReadFiles;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day12 {

    private static char[][] grid;

    private static Set<Point> visitedPoints = new HashSet<>();

    public static void main(String[] args) {

        System.out.println("Day 12");

        List<Set<Point>> plantsRegions = new ArrayList<>();
        grid = ReadFiles.getInputDataAsCharMatrix("src/day12/test.txt");
        Integer result = 0;
        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {
                Point point = new Point(i, j);
                if(!visitedPoints.contains(point))
                    result += getPlantRegion(point);
            }
        }
        System.out.println(result);
    }

    private static Integer getPlantRegion(Point startPosition) {

        char plantType = grid[startPosition.getX()][startPosition.getY()];

        Set<Point> discoveredPlants = new HashSet<>();
        List<Point> edges = new ArrayList<>();
        discoveredPlants.add(startPosition);
        Set<Point> neighbours = getNeighbours(startPosition, plantType, discoveredPlants, edges);
        while (!neighbours.isEmpty()) {
            Set<Point> newNeighbours = new HashSet<>();
            for (Point neighbour : neighbours) {
                newNeighbours.addAll(getNeighbours(neighbour, plantType, discoveredPlants, edges));
            }
            discoveredPlants.addAll(neighbours);
            neighbours = newNeighbours;
        }
        visitedPoints.addAll(discoveredPlants);
        sortEdges(new ArrayList<>(edges));
        return discoveredPlants.size() * edges.size();
    }

    private static Set<Point> getNeighbours(Point position, char plantType, Set<Point> visited, List<Point> edges) {

        Set<Point> neighbours = new HashSet<>();
        Point upperNeighbour = getNeighbour(position, plantType, visited, new Point(-1, 0), edges);
        Point downNeighbour = getNeighbour(position, plantType, visited, new Point(+1, 0), edges);
        Point leftNeighbour = getNeighbour(position, plantType, visited, new Point(0, -1), edges);
        Point rightNeighbour = getNeighbour(position, plantType, visited, new Point(0, +1), edges);

        if (upperNeighbour != null)
            neighbours.add(upperNeighbour);
        if (downNeighbour != null)
            neighbours.add(downNeighbour);
        if (leftNeighbour != null)
            neighbours.add(leftNeighbour);
        if (rightNeighbour != null)
            neighbours.add(rightNeighbour);

        return neighbours;
    }

    private static Point getNeighbour(Point position, char plantType, Set<Point> visited, Point deplacement, List<Point> edges) {

        Point possibleNeighbour = position.add(deplacement);

        if(possibleNeighbour.getX() >= 0
        && possibleNeighbour.getY() >= 0
        && possibleNeighbour.getX() < grid.length
        && possibleNeighbour.getY() < grid[0].length
        && !visited.contains(possibleNeighbour)
        && grid[possibleNeighbour.getX()][possibleNeighbour.getY()] == plantType) {

            visited.add(possibleNeighbour);
            return possibleNeighbour;

        } else if(possibleNeighbour.getX() < 0
                || possibleNeighbour.getY() < 0
                || possibleNeighbour.getX() >= grid.length
                || possibleNeighbour.getY() >= grid[0].length
                || grid[possibleNeighbour.getX()][possibleNeighbour.getY()] != plantType){
            edges.add(possibleNeighbour);
            return null;
        }
        return null;
    }

    private static List<Point> sortEdges(List<Point> edges) {

        Set<Point> vertex = new HashSet<>();

        var edgesMap = edges.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        List<Point> edgesList = new ArrayList<>(edges);
        edgesList.sort(Comparator.comparingInt((Point p) -> p.getX())
                .thenComparingDouble(p -> p.getY()));

        Point direction = new Point(0, 1);
        Point startPoint = edgesList.get(0).clone();
        if(edgesMap.get(startPoint) > 1) {
            vertex.add(startPoint);
        }
        return edgesList;
    }

}
