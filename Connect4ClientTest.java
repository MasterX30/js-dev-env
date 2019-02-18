package test;

import java.net.Socket;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import user_interface.Connect4GUI;
import core.Connect4Client;
import core.Connect4Server;
import static org.junit.Assert.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * The connect 4 game logic.
 * @author Edward Miller
 * @version 2.0
 */

public class Connect4ClientTest 
{
	public static Connect4Client player1client, player2client;
	public static Connect4GUI player1gui, player2gui;
	public static ServerSocket serverSocket;
	public static Socket player1Socket , player2Socket;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		player1gui = new Connect4GUI();
		player1client = new Connect4Client(player1gui);
		player2gui = new Connect4GUI();
		player2client = new Connect4Client(player2gui);
		
		 new Thread( () -> 
		 { 
			  try 
			  { 
	                serverSocket = new ServerSocket(8000);
	                player1Socket = serverSocket.accept();

	                DataOutputStream toPlayer1 = new DataOutputStream(player1Socket.getOutputStream());
	                toPlayer1.writeInt(1);
	                toPlayer1.writeInt(99);
	                toPlayer1.writeInt(5);

	                player2Socket = serverSocket.accept();
	                new DataOutputStream(player2Socket.getOutputStream()).writeInt(2);	             	             

	            }
			  catch (IOException exception) 
			  {
	                exception.printStackTrace();
	          }			 
		 }).start();

		 player1client.gameStart();
		 player2client.gameStart();     
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception 
	{
		player1gui = null;
		player1client = null;
		player1Socket = null;

		player2gui = null;
		player2client = null;
		player2Socket = null;
	}

	@Test
	public void testConnect4Client() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testRun() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSendMove() 
	{
		fail("Not yet implemented");
	}

	@Test
	public void testPlayer1WhichPlayerAmI() 
	{
		assertEquals(1,player1client.getPlayer());
		assertNotEquals(2,player1client.getPlayer());
	}
	
	@Test
	public void testPlayer2WhichPlayerAmI() 
	{
		assertEquals(2,player2client.getPlayer());
		assertNotEquals(1,player2client.getPlayer());
	}

	@Test
	public void testIfPlayer2Connected()
	{	
		assertEquals(2,player2client.getPlayer());
		assertNotEquals(1,player2client.getPlayer());
	}
}