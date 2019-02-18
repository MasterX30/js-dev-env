package core;

import java.util.Random;

/**
 * The connect 4 game GUI.
 * @author  Edward Miller
 * @version 3.0
 */

public class Connect4ComputerPlayer 
{
	// Random num generator.
    private Random randomNum;

    /**
     * @param The Connect 4 game.
     * @return CPU mo.
     */
    public int getCpuMove (Connect4 game)
    {
    	int column = 0;
    	
    	randomNum = new Random();
        column  = 1 + randomNum.nextInt((7 - 1) + 1);
        
        while(game.fullColumn(column-1))
        {
            column  = 1 + randomNum.nextInt((7 - 1) + 1);
        }
     return column;
    }
}