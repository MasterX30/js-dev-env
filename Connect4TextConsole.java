package user_interface;

import core.*;
import java.util.Scanner;
import javafx.application.Application;
import java.util.InputMismatchException;

/**
 * The connect 4 text logic.
 * @author  Edward Miller
 * @version 3.0
 */

@SuppressWarnings("restriction")
public class Connect4TextConsole  
{
	public static void main(String[] args) 
    {
    	Scanner gameOption = new Scanner(System.in);
        boolean isValid = false;
        char option = 'X';
        System.out.println("Enter 't' for text game or 'g' for graphics");

        while (!isValid)
        {
            try 
            {
                option = gameOption.next().charAt(0);
                if(option == 't' || option == 'g')
                {
                    isValid = true;
                }
                
                else
                {
                    System.out.println("Invalid.  Try again.");
                }
            }
            catch (InputMismatchException exception)
            {
                System.out.println("Invalid.  Try again.");
                gameOption.nextLine();
                isValid = false;
            }
        }
        
        if (option == 'g')
        {
             Application.launch(Connect4GUI.class);
        }
        else
        {
            Connect4TextConsole game = new Connect4TextConsole();
            game.start();
        }
        gameOption.close();
    }


    private Scanner scanner;   
    private Scanner cScanner;
    private Connect4 game;
    private Connect4ComputerPlayer cpu;
    

    private boolean multi;
    private boolean playCpu;   
    private boolean won = false;
    private boolean tieGame = false;
   
    private int option;

    // Default constructor
    public Connect4TextConsole()
    {
        game = new Connect4();       
        cScanner = new Scanner(System.in);       
        scanner = new Scanner(System.in);
    }

    public void start()
    {
        printBoard();
        System.out.println("Enter p for player 2, or c for CPU.");

        while(!playCpu)
        {
        	try
        	{
        		this.playCpu();
            }
        	catch (InputMismatchException exception)
        	{
        		System.out.println("Invalid, try again.");
                cScanner.nextLine();
            }
        }

        if(multi)
        {
            cpu = new Connect4ComputerPlayer();
        }

        while(true)
        {
            game.setTurn('X');
            playerTurn();
            printBoard();
            if (game.gameWon())
            {
                won = true;
                break;
            }

            if (game.tie())
            {
                tieGame = true;
                break;
            }
            game.setTurn('O');

            if(multi)
            {
            	cpuTurn();
            }
            
            else
            {
                playerTurn();
            }
            
            printBoard();

            if (game.gameWon())
            {
            	won = true;
                break;
            }

            if (game.tie())
            {
            	tieGame = true;
                break;
            }
        }

        if (won)
        {
        	if (game.getTurn() == 'X')
        	{
                System.out.println ("Player one wins.");
            }
        	else if (multi)
            {
                System.out.println("You lose.");
            }
        	else
        	{
                System.out.println("Player 2 wins.");
            }
        }

        if (tieGame)
        {
        	System.out.println("Tie.");
        }
    }


    private void playerTurn() 
    {
    	option = getChoice ();
        while (game.setCursor ((option - 1)) == false)
        {
            System.out.println("Invalid choice.");
            option = scanner.nextInt();
        }
    }

    private void cpuTurn() 
    {
        int cpuChoice = 0;
        cpuChoice = cpu.getCpuMove(game);
        game.setCursor (cpuChoice - 1);
    }

    private int getChoice() 
    {
    	if (game.getTurn() == 'X')
    	{
    		System.out.println("PlayerX turn.");
        }
    	else 
        {
            System.out.println("Player O.");
        }
        try 
        {
        	option = scanner.nextInt();
        }
        catch (InputMismatchException exception)
        {
            System.out.println("Invalid response.");
            scanner.nextLine();
            getChoice();
        }

        if(option > 7 || option < 1)
        {
        	System.out.println("Invalid response.");
        }
        return option;
    }

    public void printBoard()
    {

        for (int row = 6; row > 0 ; --row)
        {
            for (int col = 0; col < 7; ++col)
            {
                System.out.print ('|');
                System.out.print (game.gameBoard [row-1][col]);
            }
            System.out.print('|');
            System.out.println();
        }
    }

    public void printBoard (Connect4 game)
    {
        for (int row = 6; row > 0 ; --row)
        {
           for (int col = 0; col < 7; ++col)
           {
                System.out.print ('|');
                System.out.print (game.gameBoard[row-1][col]);
            }
            System.out.print('|');
            System.out.println();
        }
    }

   private void playCpu() throws InputMismatchException 
   {
        char choose = cScanner.next().charAt(0);
        if(choose != 'C' && choose != 'P')
        {
            throw new InputMismatchException();
        }
        else
        {
            playCpu = true;
            if(choose == 'C')
            {
                this.multi = true;
            }
            else
            {
                this.multi = false;
            }
        }
    }
}