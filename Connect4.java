package core;

/**
 * The connect 4 game logic.
 * @author  Edward Miller
 * @version 3.0
 */

public class Connect4 
{
    
    // Rows on game board.
    private final int ROWS = 6;
   

    // Columns on game board.
    private final int COLUMNS = 7;
   

    // Array to create game board.
    public char [][] gameBoard;


    // Winning score.
    private final int WINNING_SCORE = 4;

    
    // Current turns.
    private int turnCount = 0;


    // Current Turn for player.
    private char currentPlayerTurn = 'X';


    // Player one symbol.
    private final char PLAYER1_SYMBOL = 'X';
    

    // Player two symbol.
    private final char PLAYER2_SYMBOL = 'O';


    // Board cursor.
    private final char BOARD_CURSOR = ' ';


    // Current row of cursor.
    private int currentPositionRow;

    
    // Current column of cursor.
    private int currentPositionCol;


    // Default constructor
    public Connect4 ()
    {
        gameBoard = new char[ROWS][COLUMNS];
        
        for (int row = 0; row < ROWS; ++row)
        {
            for (int col = 0; col < COLUMNS; ++col)
            {
                gameBoard[row][col] = BOARD_CURSOR;
            }
        }
    }
    
    
    /**
     * @return Current turn.
     */
    public char getTurn() 
    {
        return currentPlayerTurn;
    }

    /**
     * @param Set the current turn
     */
    public void setTurn (char curentPlayerTurn) 
    {
        this.currentPlayerTurn = curentPlayerTurn;
    }


    /**
     * @return Get row of cursor.
     */
    public int getCurrentRow () 
    {
        return currentPositionRow;
    }


    /**
     * @param Get the current row of cursor.
     */
    private void setRow (int setRow) 
    {
        this.currentPositionRow = setRow;
    }

    /**
     * @return int representing the column that the disc was placed.
     */
    public int getCol () 
    {
        return currentPositionCol;
    }


    /**
     * @param Current column of cursor.
     */
    private void setCurrentCol (int col)
    {
        this.currentPositionCol = col;
    }


    /**
     * @param Placement of Cursor.
     * @return Cursor. placement is valid.
     */
    public boolean setCursor (int col)
    {
        if(fullColumn(col))
        {
            return false;
        }

        else
        {
            for (int row = 0; row < ROWS; ++row)
            {
                if (gameBoard[row][col] == BOARD_CURSOR)
                {
                    if (getTurn() == PLAYER1_SYMBOL)
                    {
                        gameBoard[row][col] = PLAYER1_SYMBOL;
                    }
                    
                    else  	
                    {
                    	gameBoard[row][col] = PLAYER2_SYMBOL;
                    }
                    
                    turnCount++;
                    setRow (row);
                    setCurrentCol (col);
                    break;               
                }
            }
            return true;  
        }
    }

    /**
     * @param Which column to check.
     * @return Is column available.
     */
    public boolean fullColumn (int col)
    {
        if (this.gameBoard[(ROWS-1)][col] != BOARD_CURSOR )
        {
            return true;
        }
        return false;
    }

    /**
     * @return Is the result a tie.
     */
    public boolean tie()
    {
        if (turnCount >= (ROWS * COLUMNS))
        {
            return true;
        }
        return false;
    }

    /**
     * @return Return if game won.
     */
    public boolean gameWon()
    {
        if (turnCount < 7)
        {
      return false;
        }
        if (horizontalWin())
        {
            return true;
        } 
        else if (rDiagonalWin())
        {
            return true;
        }else if (lDiagonalWin())
        {
            return true;
        } else if (downWinner())
        {
        	return true;
        }  
        else
        {
            return false;
        }
    }
  
    /**
     * @return Return if game won.
     */
    private boolean horizontalWin() 
    {
        char cursorCHK = getTurn();

        int numCursor = 0;

        for (int col = (this.currentPositionCol); col >= 0; --col)
        {
            if (gameBoard[currentPositionRow][col] == cursorCHK)
            {
                ++numCursor;
            }
            else 
            {
                break;
            }
        }

        for (int col = (this.currentPositionCol+1); col < COLUMNS; ++col)
        {
        	if (gameBoard[currentPositionRow][col] == cursorCHK)
        	{
        		++numCursor;
            }
        	else 
            {
        		break;
            }
        }

        if (numCursor >= WINNING_SCORE)
        {
        	return true;
        }
        else
        {
            return false;
        }
    }


    /**
     * @return true if a winner and false otherwise
     */
    private boolean downWinner() 
    {
        char cursorCHK = getTurn();

        int numCursor = 0;

        for (int row = (this.currentPositionRow); row >= 0; --row)
        {
        	if (gameBoard[row][currentPositionCol] == cursorCHK)
        	{
        		++numCursor;
        	}
        	else 
        	{
        		break;
            }
        }

        if (numCursor >= WINNING_SCORE)
        {
        	return true;
        }
        else
        {
        	return false;
        }
    }

    /**
     * @return Return if winner.
     */
    private boolean rDiagonalWin() 
    {
    	char cursorCHK = getTurn();

        int numCursor = 0;

        for (int column = (this.currentPositionCol), row = (this.currentPositionRow);
        		column >= 0 && row >= 0 ; --row, --column)
        {
        	if (gameBoard[row][column] == cursorCHK)
        	{
        		++numCursor;
            }
        	else 
            {
        		break;
            }
        }

        for (int column = (this.currentPositionCol + 1), 
        		row = (this.currentPositionRow + 1) ; column < COLUMNS && row < ROWS; ++column, ++row)
        {
            if (gameBoard [row][column] == cursorCHK)
            {
            	++numCursor;
            }
            else 
            {
                break;
            }
        }

        if (numCursor >= WINNING_SCORE)
        {
        	return true;
        }else
        {
        	return false;
        }
    }





    /**
     * @return Return if winner.
     */
    private boolean lDiagonalWin() 
    {
    	char cursorCHK = getTurn();

        int numCursor = 0;

        for (int column = (this.currentPositionCol), row = (this.currentPositionRow);
        		column >= 0 && row < ROWS ; ++row, --column)
        {
            if (gameBoard[row][column] == cursorCHK)
            {
                numCursor++;
            }
            else 
            {
            	break;
            }
        }

        for (int column = (this.currentPositionCol + 1), row = (this.currentPositionRow - 1);
        		column < COLUMNS && row >= 0; --row, ++column)
        {
            if (gameBoard[row][column] == cursorCHK)
            {
            	++numCursor;
            }
            else 
            {
            	break;
            }
        }

        if(numCursor >= WINNING_SCORE)
        {
        	return true;
        }else
        {
        	return false;
        }
    }
}