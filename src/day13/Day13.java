package day13;

import utils.ReadFiles;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day13 {

    public static void main(String[] args) {

        System.out.println("Day 13");
        List<String> lines = ReadFiles.getInputData("src/day13/input1.txt");
        List<List<LongPoint>> points = getCoefficientsList(lines);

        Long result = 0L;
        Long result2 = 0L;

        for (List<LongPoint> point : points) {

            long minTokens = solve(point.get(0), point.get(1), point.get(2));
            result += minTokens;

            point.get(2).setX(point.get(2).getX() + 10000000000000L);
            point.get(2).setY(point.get(2).getY() + 10000000000000L);
            minTokens = solve(point.get(0), point.get(1), point.get(2));
            result2 += minTokens;
        }

        System.out.println("Part 1: " + result);
        System.out.println("Part 2: " + result2);

    }

    public static long solve(LongPoint a, LongPoint b, LongPoint target) {

        Long result = 0L;

        BigDecimal ax = new BigDecimal(a.getX());
        BigDecimal ay = new BigDecimal(a.getY());
        BigDecimal bx = new BigDecimal(b.getX());
        BigDecimal by = new BigDecimal(b.getY());
        BigDecimal px = new BigDecimal(target.getX());
        BigDecimal py = new BigDecimal(target.getY());

        BigDecimal br = ((py.divide(ay, MathContext.DECIMAL32)).subtract(px.divide(ax, MathContext.DECIMAL32))).divide((by.divide(ay, MathContext.DECIMAL32)).subtract(bx.divide(ax, MathContext.DECIMAL32)), MathContext.DECIMAL32);
        BigDecimal ar = (py.subtract(by.multiply(br))).divide(ay, MathContext.DECIMAL32);

        ar = isValidSolution(ar);
        br = isValidSolution(br);

        if (ar.compareTo(BigDecimal.ZERO) >= 0 && br.compareTo(BigDecimal.ZERO) >= 0) {
            result = ar.multiply(new BigDecimal(3)).add(br).round(MathContext.DECIMAL32).longValue();
        }

        return result;

    }

    private static BigDecimal isValidSolution(BigDecimal a) {

        BigDecimal decimalParts = a.subtract(BigDecimal.valueOf(a.longValue()));

        BigDecimal returned = BigDecimal.valueOf(-1);

        if (a.compareTo(BigDecimal.ZERO) >= 0) {
            if (decimalParts.compareTo(BigDecimal.valueOf(0.001)) <= 0) {
                returned = new BigDecimal(a.longValue());
            } else if (decimalParts.compareTo(BigDecimal.valueOf(0.999)) >= 0) {
                returned = new BigDecimal(a.longValue()).add(BigDecimal.ONE);
            }
        }
        return returned;
    }

    private static List<List<LongPoint>> getCoefficientsList(List<String> lines) {

        int i = 0;

        List<List<LongPoint>> coefficientsList = new ArrayList<>();
        while (i < lines.size()) {
            List<LongPoint> points = new ArrayList<>();

            String line = lines.get(i);
            while (i < lines.size() && !line.equals("")) {
                points.add(getCoefficients(line));
                i++;
                if (i < lines.size())
                    line = lines.get(i);
            }
            i++;
            coefficientsList.add(points);
        }
        return coefficientsList;
    }

    private static LongPoint getCoefficients(String line) {

        Pattern pattern = Pattern.compile("(\\d+)");
        LongPoint point = new LongPoint(0, 0);
        Matcher matcher = pattern.matcher(line);
        int i = 0;
        while (matcher.find()) {
            if (i == 0)
                point.setX(Long.parseLong(matcher.group(0)));
            else
                point.setY(Long.parseLong(matcher.group(0)));
            i++;
        }
        return point;
    }

}

class LongPoint {

    long x;

    long y;

    public LongPoint(long x, long y) {
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public void setX(long x) {
        this.x = x;
    }

    public void setY(long y) {
        this.y = y;
    }
}

