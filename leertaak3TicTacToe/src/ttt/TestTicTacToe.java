package ttt;

import junit.framework.TestCase;

/**
 * Created by ouren on 8-3-2016.
 */
public class TestTicTacToe extends TestCase
{
    TicTacToe tac = new TicTacToe();

    public void testIsAWin()
    {
        assertEquals(false, tac.isAWin(tac.HUMAN));
        tac.board[0][0] = tac.HUMAN;
        tac.board[0][1] = tac.HUMAN;
        tac.board[0][2] = tac.HUMAN;
        assertEquals(true, tac.isAWin(tac.HUMAN));
        tac.clearBoard();
    }

    public void testPositionValue()
    {
        tac.board[0][0] = tac.HUMAN;
        tac.board[0][1] = tac.HUMAN;
        tac.board[0][2] = tac.HUMAN;
        assertEquals(tac.HUMAN_WIN,tac.positionValue());
        tac.clearBoard();
        tac.board[0][0] = tac.COMPUTER;
        tac.board[0][1] = tac.COMPUTER;
        tac.board[0][2] = tac.COMPUTER;
        assertEquals(tac.COMPUTER_WIN,tac.positionValue());
        tac.clearBoard();
    }

    public void testChooseMove()
    {
        assertEquals(2, tac.chooseMove());
    }
}
