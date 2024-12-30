package day17;

import utils.ReadFiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.floor;

public class Day17 {

    private static Integer registerA;

    private static Integer registerB;

    private static Integer registerC;

    private static Integer position = 0;

    private static List<Integer> results = new ArrayList<>();

    private static List<Integer> operators;

    public static void main(String[] args) {

        System.out.println("Day 17");
        List<String> lines = ReadFiles.getInputData("src/day17/input1.txt");

        registerA = Integer.parseInt(lines.get(0).replace("Register A: ",""));
        registerB = Integer.parseInt(lines.get(1).replace("Register B: ",""));
        registerC = Integer.parseInt(lines.get(2).replace("Register C: ",""));
        operators = Arrays.stream(lines.get(4).replace("Program: ", "").split(",")).map(Integer::valueOf).toList();

        while(position < operators.size()) {
            executeOperations(operators.get(position), operators.get(position + 1));
        }
        System.out.println("Part 1: " + results.stream().map(String::valueOf).collect(Collectors.joining (",")));
    }

    private static void executeOperations(Integer instr, Integer op) {

        if(op > 3 && instr != 1) {
            op = getOperand(op);
        }
        operate(instr, op);

    }

    private static Integer getOperand(Integer op) {

        return switch (op) {
            case 4 -> registerA;
            case 5 -> registerB;
            case 6 -> registerC;
            default -> null;
        };
    }

    private static void operate(Integer instr, Integer operator) {

        switch (instr) {
            case 0:
                adv(operator);
                break;
            case 1:
                bxl(operator);
                break;
            case 2:
                bst(operator);
                break;
            case 3:
                pointer(operator);
                break;
            case 4:
                bxc();
                break;
            case 5:
                out(operator);
                break;
            case 6:
                bvb(operator);
                break;
            case 7:
                cdv(operator);
                break;
            default:
                throw new RuntimeException("Invalid operation");
        }
    }

    private static void adv(Integer operator) {

        registerA = (int) floor(registerA / (Math.pow(2,operator)));
        position+=2;
    }

    private static void bxl(Integer operator) {

        registerB = registerB ^ operator;
        position+=2;
    }

    private static void bst(Integer operator) {

        registerB = operator % 8;
        position+=2;
    }

    private static void out(Integer operator) {

        results.add(operator % 8);
        position+=2;
    }

    private static void pointer(Integer operator) {

        if(!registerA.equals(0)) {
            position = operator;
        } else {
            position+=2;
        }
    }

    private static void bxc() {

        registerB = registerB ^ registerC;
        position+=2;
    }

    private static void bvb(Integer operator) {

        registerB = (int) floor(registerA / (Math.pow(2,operator)));
        position+=2;
    }

    private static void cdv(Integer operator) {

        registerC = (int) floor(registerA / (Math.pow(2,operator)));
        position+=2;
    }

}
