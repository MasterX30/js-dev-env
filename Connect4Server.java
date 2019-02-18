package core;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane;


/**
 * The connect 4 game GUI.
 * @author  Edward Miller
 * @version 3.0
 */



public class Connect4Server extends Application 
{
	
    public static void main(String[] args) 
    {
        launch(args);
    }
	
    private int numGame = 1;
    public static final int PLAYER_1_WIN = 1;
    public static final int PLAYER_2_WIN = 2;
    public static final int TIE = 3;
    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int CONT = 4;


    @Override
    public void start (Stage stage) 
    {
        TextArea log = new TextArea();
        Scene scene = new Scene (new ScrollPane (log), 400, 250);

        stage.setScene(scene);
        stage.show();

        new Thread( () -> 
        {
            try 
            {
                ServerSocket socket = new ServerSocket(8000);
               
                while (true) 
                {
                   
                    Socket socket_1 = socket.accept();

                    new DataOutputStream (socket_1.getOutputStream()).writeInt(PLAYER_1);

                    Socket player_2 = socket.accept();

                    new DataOutputStream (player_2.getOutputStream()).writeInt(PLAYER_2);

                    new Thread (new Session(socket_1, player_2, log)).start();
                }
            } 
            catch (IOException exception) 
            {
                exception.printStackTrace();
            }
            }).start();
    }

    class Session implements Runnable
    {
        private int numGame = 1;
        public static final int PLAYER_1_WIN = 1;
        public static final int PLAYER_2_WIN = 2;
        public static final int TIE = 3;
        public static final int PLAYER_1 = 1;
        public static final int PLAYER_2 = 2;
        public static final int CONT = 4;

        private final int PLAYER_2_READY = 99;
        private final int START = 5;

        private Connect4 newGame;
        private Socket playerOne;
        private Socket playerTwo;
        private TextArea text;
        private boolean play = true;
        
        private Session (Socket player1, Socket player2, TextArea text)
        {
            this.playerOne = player1;
            this.playerTwo = player2;
            newGame = new Connect4();
            this.text = text;    
         }

        @Override
        public void run () 
        {
            try 
            {
                DataInputStream fromPlayer1 = new DataInputStream (playerOne.getInputStream());

                DataOutputStream toPlayer1 = new DataOutputStream (playerOne.getOutputStream());

                DataInputStream fromPlayer2 = new DataInputStream (playerTwo.getInputStream());

                DataOutputStream toPlayer2 = new DataOutputStream (playerTwo.getOutputStream());


                toPlayer1.writeInt(PLAYER_2_READY);

                toPlayer1.writeInt(START);

                int column;

                while(true) 
                {
                    newGame.setTurn('X');

                    column = fromPlayer1.readInt();

                    newGame.setCursor(column-1);

                    if(newGame.gameWon())
                    {

                        toPlayer1.writeInt(PLAYER_1_WIN);

                        toPlayer2.writeInt(PLAYER_2_WIN);

                        text.appendText("Player 1 is a winner " + column);

                        break;

                    }

                    else if(newGame.tie())
                    {
                        toPlayer1.writeInt(TIE);
                        break;
                    }
                    else
                    {
                        toPlayer1.writeInt(CONT);
                        playerMove(toPlayer1, newGame.getCurrentRow(), column, 1);
                        toPlayer2.writeInt(CONT);
                        playerMove(toPlayer2, newGame.getCurrentRow(), column, 1);
                    }


                    newGame.setTurn('O');

                    column = fromPlayer2.readInt();

                    newGame.setCursor (column - 1);

                    //sendMove(toPlayer1, column);

                    if (newGame.gameWon())
                    {
                        toPlayer2.writeInt(PLAYER_2_WIN);
                        toPlayer1.writeInt(PLAYER_2_WIN);
                        break;
                    }

                    else if(newGame.tie())
                    {
                        toPlayer2.writeInt(TIE);
                        break;
                        
                    }else
                    {

                        toPlayer2.writeInt(CONT);
                        playerMove(toPlayer2, newGame.getCurrentRow(), column, 2);

                        toPlayer1.writeInt(CONT);
                        playerMove(toPlayer1, newGame.getCurrentRow(), column, 2);
                    }
                }
            }
            
            catch (IOException exception) 
            {
                exception.printStackTrace();
            }
        }

        private void playerMove (DataOutputStream out, int row, int col, int num)
                throws IOException 
        {
        	System.out.println("Send row + " + row + "to player");
            out.writeInt(row); // Send column

            System.out.println("Send column + " + col + "to player");
            out.writeInt(col); // Send column

            out.writeInt(num); // Send player info
        }
    }

    public void endGame () 
    {
    	Platform.exit();
    }
}