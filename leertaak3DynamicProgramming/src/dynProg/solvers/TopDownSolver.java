package dynProg.solvers;

import dynProg.Solver;

/**
 * Created by ouren on 11-3-2016.
 */
public class TopDownSolver implements Solver {
    private Boolean[][] solution;

    public boolean solve(int[] subset, int sum) {
        // Create a variable for the solution
        solution = new Boolean[subset.length][sum + 1];
        boolean answer = findSolution(subset, sum);
        if (answer) {
            System.out.print("Solution found with a subset of: ");
            for(int k=0;k<subset.length;k++){
                System.out.print(subset[k] + ", ");
            }
            System.out.println("and the searched number of: " + sum);
        }
        else{
            System.out.print("Solution not found with a subset of: ");
            for(int k=0;k<subset.length;k++){
                System.out.print(subset[k] + ", ");
            }
            System.out.println("and the searched number of: " + sum);
        }
        return answer;
    }

    private boolean findSolution(int[] subset, int sum){

        // show all items in subset, and the searched number
        System.out.print("Items in subset: ");
        for(int k=0;k<subset.length;k++){
            System.out.print(subset[k] + ", ");
        }
        System.out.println("Search: " + sum);

        // return true if solution is equal to 0
        if (sum == 0 ) {
            return true;
        }

        // return false is subset is empty or sum is smaller then 0 (your not gonna find a lower than 0)
        if (subset.length == 0 || sum < 0){
            return false;
        }

        // return value of doSolve
        if (solution[subset.length - 1][sum] == null){
            // the square is empty, and is able to be filled with a value
            // call doSolve to fill square
            solution[subset.length - 1][sum] = doSolve(subset, sum);
        }
        return solution[subset.length - 1][sum];
    }

    private boolean doSolve(int[] subset, int sum){

        // show all items in subset, and the searched number
        System.out.println("Items in subset: ");
        for(int l=0;l<subset.length;l++){
            System.out.print(subset[l] + ", ");
        }
        System.out.println("Search: " + sum);

        // create new subset
        int n = subset.length - 1;
        int[] newSubet = new int[subset.length - 1];

        for(int index = 0; index < n; index++){
            newSubet[index] = subset[index];
        }
        return findSolution(newSubet, sum) || findSolution(newSubet, sum - subset[n]);
    }
}
