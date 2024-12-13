package day11;

import utils.ReadFiles;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 {

    private static Map<Long, List<Long>> stoneMap = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Day 11");
        List<String> lines = ReadFiles.getInputData("src/day11/input1.txt");
        System.out.print("Part1: ");
        blink(lines.get(0), 25);
        System.out.print("Part2: ");
        blink(lines.get(0), 75);
    }

    private static void blink(String line, int times) {

        List<Long> stones = Arrays.stream(line.split(" ")).map(Long::valueOf).collect(Collectors.toList());
        Long result = 0L;
        Map<Long, Long> stonesMap = stones.stream().collect(Collectors.groupingBy(Long::valueOf, Collectors.counting()));

        for(int i = 0; i<times; i++) {
            Map<Long, Long> nextLevelMap = new HashMap<>();
            for (Map.Entry<Long, Long> entry : stonesMap.entrySet()) {

                List<Long> stoneValues = getValues(entry.getKey());
                result += stoneValues.size() * entry.getValue();
                for (Long stoneValue : stoneValues) {
                    Long stoneValueAmount = nextLevelMap.getOrDefault(stoneValue, 0L);
                    nextLevelMap.put(stoneValue, stoneValueAmount + entry.getValue());
                }
            }
            stonesMap = nextLevelMap;
            if(i == times-1)
                System.out.println(result);
            result = 0L;
        }

    }

    private static List<Long> getValues(Long stone) {

        List<Long> values = stoneMap.get(stone);
        if(values == null) {
            values = getNewStones(stone);
        }
        return values;
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
        stoneMap.put(stone, newStones);
        return newStones;
    }

    private static boolean isEven(Integer number) {
        return number % 2 == 0;
    }

}
