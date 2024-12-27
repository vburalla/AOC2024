package day14;

import utils.Point;
import utils.ReadFiles;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class Day14 {

    private static Character[][] grid;
    private static final int X_LIMIT = 101;
    private static final int Y_LIMIT = 103;
    private static List<Robot> robots = new ArrayList<>();
    static int counter = 0;

    public static void main(String[] args) {
        System.out.println("Day 14");

        grid = new Character[103][101];
        placeRobots("src/day14/input1.txt");
        moveRobots(100, false);
        getCoefficients();
        moveRobots(1000000, true);
    }

    private static void getCoefficients() {

        int[] coefficients = new int[4];

        for (Robot robot : robots) {
            int quadrant = getQuadrant(robot);
            if (quadrant >= 0)
                coefficients[quadrant]++;
        }

        System.out.println("Coefficients: " + Arrays.stream(coefficients).reduce(1, (a, b) -> a * b));
    }

    private static void placeRobots(String path) {

        List<String> lines = ReadFiles.getInputData(path);
        for (String line : lines) {

            String[] robotData = line.split(" ");
            Point initialPosition = new Point(robotData[0].replace("p=", ""));
            Point movement = new Point(robotData[1].replace("v=", ""));

            robots.add(new Robot(initialPosition, movement));

        }
    }

    private static void moveRobots(int times,
                                   boolean draw) {

        for (int i = 0; i < times; i++) {
            for (Robot robot : robots) {
                moveRobot(robot);
            }

            counter++;
            Set<Point> robotUbications = robots.stream().map(r -> r.position).collect(Collectors.toSet());
            if(robotUbications.size() == robots.size()) {
                drawGrid();
                System.out.println(counter);
                break;
            }
        }
    }

    private static void moveRobot(Robot robot) {

        robot.position = robot.position.add(robot.movement);
        if (robot.position.getX() >= X_LIMIT) {
            robot.position.setX(robot.position.getX() - X_LIMIT);
        } else if (robot.position.getX() < 0) {
            robot.position.setX(robot.position.getX() + X_LIMIT);
        }
        if (robot.position.getY() >= Y_LIMIT) {
            robot.position.setY(robot.position.getY() - Y_LIMIT);
        } else if (robot.position.getY() < 0) {
            robot.position.setY(robot.position.getY() + Y_LIMIT);
        }
    }

    private static int getQuadrant(Robot robot) {

        int quadrant = -1;
        if (robot.position.getX() < X_LIMIT / 2) {
            if (robot.position.getY() < Y_LIMIT / 2)
                quadrant = 0;
            else if (robot.position.getY() > Y_LIMIT / 2)
                quadrant = 2;
        } else if (robot.position.getX() > X_LIMIT / 2) {
            if (robot.position.getY() < Y_LIMIT / 2)
                quadrant = 1;
            else if (robot.position.getY() > Y_LIMIT / 2)
                quadrant = 3;
        }

        return quadrant;
    }


    private static void drawGrid() {

        System.out.println();
        System.out.println();
        char[][] grid = new char[Y_LIMIT][X_LIMIT];
        for (int i = 0; i < Y_LIMIT; i++) {
            for (int j = 0; j < X_LIMIT; j++) {
                grid[i][j] = ' ';
            }
        }
        for (Robot robot : robots) {
            grid[robot.position.getY()][robot.position.getX()] = '#';
        }
        for (int i = 0; i < Y_LIMIT; i++) {
            for (int j = 0; j < X_LIMIT; j++) {
                System.out.print(grid[i][j]);
            }
            System.out.println();
        }
    }

}

class Robot {

    Point position;

    Point movement;

    public Robot(Point position, Point movement) {
        this.position = position;
        this.movement = movement;
    }
}
