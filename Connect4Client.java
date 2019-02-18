package core;

import java.net.*;

import user_interface.Connect4GUI;

import javafx.application.Platform;

import java.io.*;

/**
 * The connect 4 game GUI.
 * @author  Edward Miller
 * @version 3.0
 */

public class Connect4Client 
{
    // Setting server input/host.
    private DataInputStream server;
    private String host = "localhost";   
    private DataOutputStream toServer;
    private Connect4GUI gui;


    private boolean turn = false;
    private char cursor = ' ';
    private char oppCursor = ' ';
    

    private String promptPlayer = "Choose column.";
    private int option;
    private final int PLAYER_1 = 1;  
    private final int PLAYER_2 = 2; 
    private boolean playAgain = true;
    private boolean pause = true;
    private final int TIE = 3;
    private final int CONT = 4;
    private final int START = 5;
    private int choosePlayer;
    private final int PLAYER_1_WINNER = 1;
    private final int PLAYER_2_WINNER = 2;


    /**
     * @param Connect 4 GUI.
     */

    public Connect4Client (Connect4GUI gui)
    {
        this.gui = gui;
    }

    public void gameStart () throws IOException
    {
        connectServer();
    }

    @SuppressWarnings("restriction")
	private void connectServer() throws IOException 
    {
        
            Socket socket = new Socket(host, 8000);

            server = new DataInputStream(socket.getInputStream());

            toServer = new DataOutputStream(socket.getOutputStream());

        
      

            System.out.println("Cannot connect server.");
            System.exit(1);
        

        
        // New CPU thread for game control.
        new Thread(() -> 
        {
            try 
            {
                int playerOne = server.readInt();

                setPlayer(playerOne);

                System.out.println(playerOne);

                if (playerOne == PLAYER_1)
                {
                    gui.player(1);
                    cursor = 'X';
                    oppCursor = 'O';
                    
                    Platform.runLater(() -> 
                    {
                        gui.noButton();
                    });

                    Platform.runLater(() -> 
                    {
                        gui.inviText();
                    });

                    Platform.runLater(() -> 
                    {
                       gui.setLabel("Player 2 turn.");
                    });


                    Platform.runLater(() -> 
                    {
                        gui.newOpponent();
                    });

               
                    Platform.runLater(() -> 
                    {
                        gui.click();

                    });

                    Platform.runLater(() -> 
                    {
                        gui.text();
                    });


                    Platform.runLater(() -> 
                    {
                        gui.setTexT(promptPlayer);

                    });

                    Platform.runLater(() -> 
                    {
                        gui.setLabel("Choose column.");

                    });


                }
                
                else if(playerOne == PLAYER_2)
                {
                	gui.player(2);
                    cursor = 'O';
                    oppCursor = 'X';

                    inviInput();

                    Platform.runLater(() -> 
                    {
                        gui.setLabel("Player 1 turn.");
                    });
                }



                while (playAgain) 
                {
                    if (playerOne == PLAYER_1) 
                    {
                        serverInfo();
                        showInput();
                        playerMove();
                        option = gui.getChoice();


                        inviInput();
                        playerMove(option);
                        serverInfo();
                    }

                    else if (playerOne == PLAYER_2) 
                    {
                        serverInfo();
                        showInput();
                        playerMove();
                        option = gui.getChoice();
                        
                        inviInput();
                        playerMove(option);
                        serverInfo();
                    }
                }

            } catch (IOException exception) 
            
            {
                exception.printStackTrace ();
            }

            catch (InterruptedException exception) 
            {
                exception.printStackTrace();
            }
        }
        ).start();
        }

    private void playerMove () throws InterruptedException
    {
    	while (!gui.checkMove()) 
    	{
    		//TODO toggle sleep setting.
    			
    		Thread.sleep(50);
        }
    }

    @SuppressWarnings("restriction")
	public void playerMove (int column) throws IOException 
    {

        Platform.runLater(() -> gui.setMove(false));
        toServer.writeInt(column);
    }

    @SuppressWarnings("restriction")
	private void serverInfo() throws IOException 
    {
        int status = server.readInt();
        if (status == PLAYER_1_WINNER) 
        {
            playAgain = false;

            Platform.runLater(() -> gui.showWinner(1));
            receiveMove();

        } 
        
        else if (status == PLAYER_2_WINNER) 
        {

            Platform.runLater(() -> gui.showWinner(2));
            playAgain = false;

        } else if (status == TIE) 
        {
            gui.draw();

            if (cursor == 'O') 
            {
                receiveMove();
            }

        }
        else if (status == START)
        {
            return;
        }
        
        else 
        {
        	receiveMove();
            turn = true;
        }
    }


    @SuppressWarnings("restriction")
	private void receiveMove() throws IOException 
    {

        int rows = server.readInt();
        int columns = server.readInt();
        int playerNumber = server.readInt();

        Platform.runLater(() -> gui.update(rows, columns, playerNumber));
    }


    @SuppressWarnings("restriction")
	private void inviInput ()
    {

        Platform.runLater (() -> 
        {
            gui.inviText ();
        });

        Platform.runLater (() -> 
        {
            gui.noButton ();
        });

        if(getPlayer () == 1)
        {
            Platform.runLater(() -> 
            {
                gui.setLabel("Player 2");
            });
        }
        else
        {
            Platform.runLater(() -> 
            {
                gui.setLabel("Player 1");
            });
        }
    }

    @SuppressWarnings("restriction")
	private void showInput()
    {
        Platform.runLater(() -> 
        {
            gui.click();
        });

        Platform.runLater(() -> 
        {
            gui.text();
        });
        
        Platform.runLater(() -> 
        {
            gui.setTexT(promptPlayer);
        });


        if (getPlayer() == 1)
        {
            Platform.runLater(() -> {
                gui.setLabel("Player 1 choice.");
            });
        }
        else
        {
            Platform.runLater(() -> 
            {
                gui.setLabel("Player 2 choice.");
            });
        }
    }


    public int getPlayer () 
    {
        return choosePlayer;
    }


    public void setPlayer(int currentPlayer) 
    {
        this.choosePlayer = currentPlayer;
    }
}