package day7;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

    private static final Pattern numberPattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        System.out.println("Day 7");
        List<String> lines = ReadFiles.getInputData("src/day7/input1.txt.txt");

        Long total = 0L;
        Long total2 = 0L;
        for (String line : lines) {
            total += getResult(line, false);
            Long result2 = getResult(line, true);
            total2 += result2;
            if(result2 > 0) {
                System.out.println(result2);
            }
        }

        System.out.println("Total: " + total);
        System.out.println("Total: " + total2);

    }

    private static Long getResult(String line, boolean thirdOperator) {

        List<Long> operators = getNumbers(line);
        Long expectedResult = operators.remove(0);
        List<Long> results = List.of(operators.remove(0));

        int i=0;

        while(i < operators.size()) {

            Long operator = operators.get(i);

            List<Long> tempResults = new ArrayList<>();

            for(Long value : results) {

                Long tempResult = value + operator;
                if (tempResult.equals(expectedResult) && i == operators.size() - 1) {
                    return expectedResult;
                } else if (tempResult <= expectedResult) {
                    tempResults.add(tempResult);
                }
                tempResult = value * operator;
                if (tempResult.equals(expectedResult) && i == operators.size() - 1) {
                    return expectedResult;
                } else if (tempResult <= expectedResult ) {
                    tempResults.add(tempResult);
                }
                if(thirdOperator) {
                    tempResult = Long.parseLong(value.toString() + operator.toString());
                    if (tempResult.equals(expectedResult) && i == operators.size() - 1) {
                        return expectedResult;
                    } else if (tempResult <= expectedResult) {
                        tempResults.add(tempResult);
                    }
                }
            }
            results = tempResults;
            i++;
        }
        return 0L;
    }


    private static List<Long> getNumbers(String line) {

        List<Long> numbers = new ArrayList<>();
        Matcher matcher = numberPattern.matcher(line);

        while (matcher.find()) {
            numbers.add(Long.parseLong(matcher.group()));
        }
        return numbers;
    }

}
