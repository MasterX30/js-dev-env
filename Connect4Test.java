package test;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import core.Connect4;
import user_interface.Connect4TextConsole;

/**
 * The connect 4 game logic.
 * @author Edward Miller
 * @version 2.0
 */

public class Connect4Test
{
	private static Connect4 diagDownRightWin,diagUpRightWin, diagUpLeftWin, testThreeDownNotaWin, placeDiscChecks,
	verticalWinner, horizontalLeftWinner, horizontalRightWinner, noColumnsFull, allColumnsFull, playerX, playerO;

	private static Connect4TextConsole console;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		//console = new Connect4TextConsole(); 	 //For debugging
		//For testing Player 'X' methods

		playerX = new Connect4();
		playerX.setTurn('X');
		
		//For testing Player 'O' methods

		playerO = new Connect4();
		playerO.setTurn('O');
	
		//For testing place disc methods
		placeDiscChecks = new Connect4();
	
		//Connect4 Object to test when no columns are full
		noColumnsFull = new Connect4();


		horizontalLeftWinner = new Connect4();
		horizontalLeftWinner.setCursor(0);
		horizontalLeftWinner.setCursor(1);
		horizontalLeftWinner.setCursor(2);
		horizontalLeftWinner.setTurn('O');
		horizontalLeftWinner.setCursor(0);
		horizontalLeftWinner.setCursor(1);
		horizontalLeftWinner.setCursor(2);
		horizontalLeftWinner.setCursor(4);
		horizontalLeftWinner.setTurn('X');
		horizontalLeftWinner.setCursor(3);

		

		horizontalRightWinner = new Connect4();
		horizontalRightWinner.setCursor(4);
		horizontalRightWinner.setCursor(3);
		horizontalRightWinner.setCursor(2);
		horizontalRightWinner.setTurn('O');
		horizontalRightWinner.setCursor(4);
		horizontalRightWinner.setCursor(3);
		horizontalRightWinner.setCursor(2);
		horizontalRightWinner.setCursor(4);
		horizontalRightWinner.setTurn('X');
		horizontalRightWinner.setCursor(1);

		

		verticalWinner = new Connect4();
		verticalWinner.setCursor(0);
		verticalWinner.setCursor(0);
		verticalWinner.setCursor(0);
		verticalWinner.setTurn('O');
		verticalWinner.setCursor(1);
		verticalWinner.setCursor(1);
		verticalWinner.setCursor(1);
		verticalWinner.setCursor(2);
		verticalWinner.setTurn('X');
		verticalWinner.setCursor(0);



		testThreeDownNotaWin = new Connect4();
		testThreeDownNotaWin = new Connect4();
		testThreeDownNotaWin.setCursor(0);
		testThreeDownNotaWin.setCursor(0);
		testThreeDownNotaWin.setTurn('O');
		testThreeDownNotaWin.setCursor(1);
		testThreeDownNotaWin.setCursor(1);
		testThreeDownNotaWin.setCursor(1);
		testThreeDownNotaWin.setCursor(2);
		testThreeDownNotaWin.setTurn('X');
		testThreeDownNotaWin.setCursor(0);


		diagUpLeftWin = new Connect4();
		diagUpLeftWin.setCursor(0);
		diagUpLeftWin.setCursor(0);
		diagUpLeftWin.setCursor(0);
		diagUpLeftWin.setCursor(0);
		diagUpLeftWin.setCursor(1);
		diagUpLeftWin.setCursor(1);
		diagUpLeftWin.setCursor(1);
		diagUpLeftWin.setTurn('O');
		diagUpLeftWin.setCursor(2);
		diagUpLeftWin.setTurn('X');
		diagUpLeftWin.setCursor(2);
		diagUpLeftWin.setCursor(3);


		diagDownRightWin = new Connect4();
		diagDownRightWin.setCursor(0);
		diagDownRightWin.setCursor(0);
		diagDownRightWin.setCursor(0);
		diagDownRightWin.setCursor(1);
		diagDownRightWin.setCursor(1);
		diagDownRightWin.setCursor(1);
		diagDownRightWin.setTurn('O');
		diagDownRightWin.setCursor(2);
		diagDownRightWin.setTurn('X');
		diagDownRightWin.setCursor(2);
		diagDownRightWin.setCursor(3);
		diagDownRightWin.setCursor(0);


		diagUpRightWin = new Connect4();
		diagUpRightWin.setCursor(3);
		diagUpRightWin.setCursor(3);
		diagUpRightWin.setCursor(3);
		diagUpRightWin.setCursor(3);
		diagUpRightWin.setCursor(2);
		diagUpRightWin.setCursor(2);
		diagUpRightWin.setCursor(2);
		diagUpRightWin.setTurn('O');
		diagUpRightWin.setCursor(1);
		diagUpRightWin.setTurn('X');
		diagUpRightWin.setCursor(1);
		diagUpRightWin.setCursor(0);


		allColumnsFull = new Connect4();

		for(int i = 1; i < 7; i++) 
		{
			for(int j = 0; j < 7; j++) 
			{
				allColumnsFull.setCursor(j);
			}	
		}		
		//console.printGameBoard(allColumnsFull);
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		allColumnsFull = null;
		noColumnsFull = null;
		playerO = null;
		playerX = null;
		horizontalLeftWinner = null;
		verticalWinner = null;
		horizontalRightWinner = null;
		testThreeDownNotaWin = null;
		placeDiscChecks = null;
		diagUpLeftWin = null;
		diagUpRightWin = null;
	}

	@Test
	public void testGetCurentPlayerTurn() 
	{
		assertEquals('X',playerX.getTurn());
		assertEquals('O',playerO.getTurn());
	}

	@Test
	public void testSetCurentPlayerTurn() 
	{
		assertEquals('X', playerX.getTurn());
		assertNotEquals('X', playerO.getTurn());
		assertEquals('O', playerO.getTurn());
		assertNotEquals('O', playerX.getTurn());
	}
	
	@Test
	public void testGetPlacedRow()
	{
		assertEquals(3, verticalWinner.getCurrentRow());
		assertEquals(0, horizontalLeftWinner.getCurrentRow());
	}

	@Test
	public void testGetPlacedCol() 
	{
		assertEquals(0, verticalWinner.getCol());
		assertEquals(3, horizontalLeftWinner.getCol());	
	}

	@Test
	public void testPlaceDisc() 
	{
		assertFalse(allColumnsFull.setCursor(0));
		assertTrue(placeDiscChecks.setCursor(0));
	}

	@Test
	public void testIsColumnFull()
	{
		assertTrue(allColumnsFull.fullColumn(0));
		assertTrue(allColumnsFull.fullColumn(1));
		assertTrue(allColumnsFull.fullColumn(2));
		assertTrue(allColumnsFull.fullColumn(3));
		assertTrue(allColumnsFull.fullColumn(4));
		assertTrue(allColumnsFull.fullColumn(5));
		assertTrue(allColumnsFull.fullColumn(6));

		assertFalse(noColumnsFull.fullColumn(0));		
	}


	@Test
	public void testIsDraw() 
	{
		assertTrue(allColumnsFull.tie());
		assertFalse(noColumnsFull.tie());
	}

	@Test
	public void testIsHorizontalLeftWinner()
	{
		assertTrue(horizontalLeftWinner.gameWon());
	}
	
	@Test
	public void testIsHorizontalRightWinner() 
	{
		assertTrue(horizontalRightWinner.gameWon());
	}

	@Test
	public void isNotEmptyWinner() 
	{
		assertFalse(noColumnsFull.gameWon());	
	}

	@Test
	public void testIsVerticleWinner() 
	{
		assertTrue(verticalWinner.gameWon());
		assertFalse(noColumnsFull.gameWon());
	}

	@Test
	public void testLessThan4NotAWinner()
	{
		assertFalse(testThreeDownNotaWin.gameWon());
	}

	@Test
	public void testDiagonalUpLeftisAWinner() 
	{
		assertTrue(diagUpLeftWin.gameWon());
	}

	@Test
	public void testDiagonalUpRightIsAWinner()
	{
		assertTrue(diagUpRightWin.gameWon());
	}

	@Test
	public void testDiagonalDownRightIsAWinner() 
	{
		assertTrue(diagDownRightWin.gameWon());
	}
}