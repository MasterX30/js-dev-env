package test;

import static org.junit.Assert.*;



import org.junit.AfterClass;

import org.junit.BeforeClass;

import org.junit.Test;



import core.Connect4;

import core.Connect4ComputerPlayer;

/**
 * The connect 4 game logic.
 * @author Edward Miller
 * @version 2.0
 */

public class Connect4ComputerPlayerTest 
{
	public static Connect4ComputerPlayer computer;
	public static Connect4 game, collumnFullGame;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		game  = new Connect4();
		collumnFullGame = new Connect4();
		computer = new Connect4ComputerPlayer();

		for(int i = 0; i < 7; i++) 
		{
			collumnFullGame.setCursor(0);
			collumnFullGame.setCursor(1);
			collumnFullGame.setCursor(2);
			collumnFullGame.setCursor(3);
			collumnFullGame.setCursor(4);
			collumnFullGame.setCursor(5);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		computer = null;
		game = null;
		collumnFullGame = null;
	}

	@Test
	public void testComputerColumnChoiceInRange() 
	{

		

		//Generate a 1000 choices from the computer player

		//Assert that they are all between 1-7.

		for(int i = 0; i < 1000; i++) 
		{

			int move = computer.getCpuMove(game);

			assertTrue(move >= 1 && move <= 7);

		}

	}

	@Test
	public void testColumnFullBranch() 
	{	
		for(int i = 0; i < 100; i++) 
		{
			int move = computer.getCpuMove(collumnFullGame);
			assertTrue(move >= 1 && move <= 7);
		}
	}
}