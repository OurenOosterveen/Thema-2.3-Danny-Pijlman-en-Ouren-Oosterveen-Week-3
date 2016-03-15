import java.util.Stack;

public class Solution extends Stack<Candidate> {
	private static final int[] rowArray = new int[]{0, 1, 1, 1, 2, 2, 2, 3};
	private static final int[] columnArray = new int[]{2, 0, 1, 2, 1, 2, 3, 2};
	private Candidate[][] board = new Candidate[4][4];
	private int[] row;
	private int[] column;
	int[][] check = new int[][]{new int[0], new int[0], {1}, {0}, {2}, {3, 4}, {5, 6}, {7}};

	public Solution() {
		this.row = rowArray;
		this.column = columnArray;
	}

	private boolean bordersCard(int row, int column, char cardChar) {
		boolean cardFound = false;
		if(row > 0 && this.board[row - 1][column] != null && this.board[row - 1][column].getCardChar() == cardChar) {
			cardFound = true;
		}

		if(row + 1 < 4 && this.board[row + 1][column] != null && this.board[row + 1][column].getCardChar() == cardChar) {
			cardFound = true;
		}

		if(column > 0 && this.board[row][column - 1] != null && this.board[row][column - 1].getCardChar() == cardChar) {
			cardFound = true;
		}

		if(column + 1 < 4 && this.board[row][column + 1] != null && this.board[row][column + 1].getCardChar() == cardChar) {
			cardFound = true;
		}

		return cardFound;
	}

	public boolean fits(Candidate candidate) {
		int nextLocationIndex = this.size();
		char currentChar = candidate.getCardChar();
		int nextRowCoordinate = this.row[nextLocationIndex];
		int nextColCoordinate = this.column[nextLocationIndex];
		return !this.bordersCard(nextRowCoordinate, nextColCoordinate, currentChar);
	}

	public void record(Candidate candidate) {
		int i = this.size();
		this.board[this.row[i]][this.column[i]] = candidate;
		this.push(candidate);
	}

	public boolean complete() {
		return this.size() == 8 && this.isCorrect();
	}

	public void show() {
		System.out.println(this);
	}

	public Candidate eraseRecording() {
		int i = this.size() - 1;
		this.board[this.row[i]][this.column[i]] = null;
		return (Candidate)this.pop();
	}

	private char mustBeAdjacentTo(char card) {
		return (char)(card == 65?'K':(card == 75?'Q':(card == 81?'J':'?')));
	}

	private boolean isCorrect() {
		boolean isCorrect = true;

		for(int index = 0; index < rowArray.length; ++index) {
			int row = rowArray[index];
			int column = columnArray[index];
			Candidate candidate = this.board[row][column];
			if(candidate != null) {
				char mustBeAdjacentTo = this.mustBeAdjacentTo(candidate.getCardChar());
				if(mustBeAdjacentTo != 63 && !this.bordersCard(row, column, mustBeAdjacentTo)) {
					isCorrect = false;
				}
			}
		}

		return isCorrect;
	}

	public String toString() {
		String indentation = " ";
		StringBuilder buffer = new StringBuilder();
		buffer.append(indentation).append(indentation);
		buffer.append("\n");

		for(int j = 0; j < 4; ++j) {
			for(int x = 0; x < 4; ++x) {
				buffer.append(indentation);
				if(this.board[x][j] != null) {
					buffer.append(this.board[x][j].getCardChar());
				} else {
					buffer.append('-');
				}
			}

			buffer.append("\n");
		}

		return buffer.toString();
	}
}