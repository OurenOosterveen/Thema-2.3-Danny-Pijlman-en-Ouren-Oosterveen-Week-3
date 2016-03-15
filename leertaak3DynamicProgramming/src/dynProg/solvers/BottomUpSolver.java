package dynProg.solvers;

import dynProg.Solver;

/**
 * Created by ouren on 11-3-2016.
 */
public class BottomUpSolver implements Solver {
    @Override
    public boolean solve(int[] numbers, int sum) {
        // get the amount of number to go through
        int n = numbers.length - 1;

        // create grid
        boolean[][] solution = new boolean[n + 1][sum + 1];

        // initialize first row
        for (int i = 0; i < sum + 1; i++){

            // set current number in the numbers array to true
            if (i == 0 || numbers[0] == i) {
                solution[0][i] = true;
            }
            else {
                solution[0][i] = false;
            }
        }

        // Go through all the rows
        for (int currentRow = 1; currentRow <= n; currentRow++) {
            // go through all the numbers in a row
            for (int currentNumber = 0; currentNumber < sum + 1; currentNumber++) {

                // check if current number is in the numbers list or if it is already true on the previous row
                if (currentNumber == numbers[currentRow] || solution[currentRow - 1][currentNumber]) {
                    solution[currentRow][currentNumber] = true;
                }
                else {
                    solution[currentRow][currentNumber] = false;
                }

                // check if current number - the number in the current row == true && current number in the row != already true
                if (currentNumber - numbers[currentRow] >= 0 && !solution[currentRow][currentNumber]) {
                    // check if in the previous row, at the spot of col - numbers[row] == true
                    // e.g. numbers {3,5} looking for 8
                    // if 8 - 5 = 3, is 3 true ? 8 is also true
                    if (solution[currentRow - 1][currentNumber - numbers[currentRow]]) {
                        solution[currentRow][currentNumber] = true;
                    }
                    else {
                        solution[currentRow][currentNumber] = false;
                    }
                }
            }
        }

        // return value in the last row and column
        return solution[n][sum];
    }
}
