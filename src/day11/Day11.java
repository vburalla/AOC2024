package day11;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day11 {

    public static void main(String[] args) {
        System.out.println("Day 11");
        List<String> lines = ReadFiles.getInputData("src/day11/input1.txt");
        //blink(75, lines.get(0));
        blink(lines.get(0), 75);
    }

    private static void blink(int times, String line) {

        List<Long> stones = Arrays.stream(line.split(" ")).map(Long::valueOf).collect(Collectors.toList());

        for(int i=0; i<times; i++) {

            List<Long> newStones = new ArrayList<>();
            for(Long stone: stones) {
                newStones.addAll(getNewStones(stone));
            }
            stones = newStones;
            System.out.println(stones.size());
        }
        System.out.println(stones.size());
    }

    private static void blink(String line, int times) {

        List<Long> stones = Arrays.stream(line.split(" ")).map(Long::valueOf).collect(Collectors.toList());
        Long result = 0L;
        for(int i = 0; i<stones.size(); i++) {

            result += elements(stones.get(i), times);
        }
        System.out.println(result);
    }

    private static List<Long> getNewStones(Long stone) {

        List<Long> newStones = new ArrayList<>();
        String valueAsString = String.valueOf(stone);
        if(stone.equals(0L)) {
            newStones.add(1L);
        } else if(isEven(valueAsString.length())){
            newStones.add(Long.valueOf(valueAsString.substring(0, valueAsString.length()/2)));
            newStones.add(Long.valueOf(valueAsString.substring(valueAsString.length()/2)));
        } else {
            newStones.add(stone * 2024);
        }
        return newStones;
    }

    private static boolean isEven(Integer number) {
        return number % 2 == 0;
    }

    private static Long elements(Long value, int times) {

        Long result = 0L;
        if(times == 1) {
            result += getNewStones(value).size();
        } else {
            List<Long> newElements = getNewStones(value);
            result += elements(newElements.get(0), times - 1);
            if(newElements.size() > 1)
                result+= elements(newElements.get(1), times - 1);
        }
        return result;
    }
}
