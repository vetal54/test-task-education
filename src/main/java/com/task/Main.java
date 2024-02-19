package com.task;

import java.io.IOException;
import java.util.Arrays;
import org.apache.commons.math3.stat.StatUtils;

public class Main {

  public static void main(String[] args) throws IOException {
    final var file = "10m.txt";
    var stream = ClassLoader.getSystemResourceAsStream(file);
    if (stream == null) {
      throw new RuntimeException("Cannot get resource: " + file);
    }
    var bytes = stream.readAllBytes();

    var lines = new String(bytes).split("\n");
    double[] numbers = Arrays.stream(lines)
        .mapToDouble(Double::parseDouble)
        .toArray();

    double[] sortedNumbers = Arrays.copyOf(numbers, numbers.length);

    Arrays.sort(sortedNumbers);

    var max = sortedNumbers[sortedNumbers.length - 1];
    var min = sortedNumbers[0];

    var median = calculateMedian(sortedNumbers);
    var avg = StatUtils.mean(numbers);

    var maxAscSequence = findMaxSequence(numbers, true);
    var maxDscSequence = findMaxSequence(numbers, false);

    printResults(max, min, median, avg, maxAscSequence, maxDscSequence);
  }

  private static double calculateMedian(double[] sortedNumbers) {
    var halfSize = (sortedNumbers.length - 1) / 2;
    if (sortedNumbers.length % 2 == 0) {
      var firstElement = sortedNumbers[halfSize];
      var secondElement = sortedNumbers[halfSize + 1];
      return (firstElement + secondElement) * 0.5;
    } else {
      return sortedNumbers[halfSize];
    }
  }

  private static int findMaxSequence(double[] numbers, boolean ascending) {
    int maxSequence = 0;
    int currentSequence = 1;

    for (int i = 1; i < numbers.length - 1; i += 2) {
      var isPrevSequence = (ascending ? numbers[i - 1] < numbers[i] : numbers[i - 1] > numbers[i]);
      var isNextSequence = (ascending ? numbers[i] < numbers[i + 1] : numbers[i] > numbers[i + 1]);

      if (isPrevSequence || isNextSequence) {
        currentSequence++;
        maxSequence = Math.max(currentSequence, maxSequence);
      } else {
        currentSequence = 1;
      }
    }

    return maxSequence;
  }

  private static void printResults(double max, double min, double median, double avg,
      int maxAscSequence, int maxDscSequence) {
    System.out.println("Max number is: " + (int) max);
    System.out.println("Min number is: " + (int) min);
    System.out.println("Median is: " + median);
    System.out.println("Arithmetic mean is: " + avg);
    System.out.println("Max sequence of ascending numbers is: " + maxAscSequence);
    System.out.println("Max sequence of descending numbers is: " + maxDscSequence);
  }
}
