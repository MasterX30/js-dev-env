package user_interface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import core.Connect4;
import core.Connect4Client;
import core.Connect4ComputerPlayer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import java.io.IOException;
import java.util.Optional;

/**
 * The connect 4 game logic.
 * @author Edward Miller
 * @version 3.0
 */

@SuppressWarnings({ "unused" })
public class Connect4GUI extends Application
{
    public static void main(String[] args) 
    {
        launch(args);
    }
    
    private Connect4ComputerPlayer computer;          

    private int rows = 0;                        
    private int cpu = 0;     
	private int playerOne = 0;			
    private int place = 0;    
     
    private Connect4 game;     

    private TextField columnText;

    public Label label;			
    public Button button;               
   
    private Alert alert;           
    private Alert cpuAlert;        
    private Alert opponent;  
    private Alert network;    
    private Alert columnFull;           
    private Alert won;                    
    private Alert tie;                 
    private Alert invalid;       

    private boolean online;  
    private boolean multiPlayer;  
    private boolean turnDone;     

    Connect4Client client;                    
    						
    GridPane grid;					

	// Default constructor			
    public Connect4GUI()
    {
        game = new Connect4();
    }

    @Override
    public void start(Stage stage)
    {     
        button = new Button();							
        columnText = new TextField();

        label = new Label("Player join.");
        ButtonType yes = new ButtonType("Yes.");
        ButtonType no = new ButtonType("No.");

        	online = false;      
        
        if(!online) 
     {	

            ButtonType computerButtonType = new ButtonType("CPU.");
            ButtonType playerButtonType = new ButtonType("Human.");
            
                multiPlayer = true;
                computer = new Connect4ComputerPlayer();
            {
            	multiPlayer = false;
            }
        }

		stage.setTitle("Connect 4");
		BorderPane pane = new BorderPane();
		grid = new GridPane();
		Scene scene = new Scene(pane);

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(10, 10, 0, 10));
		
		for (int i = 0; i < 7; ++i) 
		{
			for (int j = 0; j < 6; ++j) 
			{
				grid.add (new Circle(45), j, i);
			}
		}
		
        grid.setRotate(180);
        grid.setScaleX(-1);

        FlowPane bFlow = new FlowPane(10,0);
        bFlow.setMaxSize(1024,84);
        bFlow.setMinSize(1024,84);
        bFlow.setPadding(new Insets(30, 200, 30, 0));
        button.setText("Enter");

        Pane top = new Pane();
        top.setMinSize(1000, 100);
        top.setMaxSize(1000, 100);

        Pane right = new Pane();
        right.setMinSize(105, 580);
        right.setMaxSize(105, 580);

        Pane center = new Pane();
        center.setMinSize(800, 600);
        center.setMaxSize(800, 600);
        center.getChildren().add(grid);

        Pane left = new Pane();
        left.setMinSize(105, 580);
        left.setMaxSize(105, 580);

        Pane bottom = new Pane();
        bottom.setMinSize(1000, 100);
        bottom.setMaxSize(1000, 100);
        bottom.getChildren().add(bFlow);

        pane.setCenter(center);
        pane.setTop(top);
        pane.setLeft(left);
        pane.setRight(right);
        pane.setBottom(bottom);

        stage.setScene(scene);
        stage.show();

        if (online)
        {
        	bFlow.getChildren().addAll(label,columnText,button);
            bFlow.setAlignment(Pos.CENTER);
            button.setOnAction(event -> Platform.runLater(() -> setMove()));
        }
        
        else
        {
            label = new Label("Player 1 choose.");
            columnText.setPromptText("Choose Column.");
            bFlow.getChildren().addAll(label,columnText,button);
            bFlow.setAlignment(Pos.CENTER);

            button.setOnAction(new EventHandler<ActionEvent>() 
            {
                @Override
                public void handle(ActionEvent event)
                {
                    try 
                    {
                    	place = Integer.parseInt(columnText.getText());
                    	
                    }
                    catch (Exception exception) 
                    {
                    	invalid.showAndWait();
                        columnText.setText("");
                        return;
 }
                    if (place > 7 || place < 1)
                    {
                    	invalid.showAndWait();	
                        return;
                    }

                    if (game.getTurn() == 'X') 
                    {
						if (!turn(place)) 
						{
							columnFull.showAndWait();
							columnText.setText("");
							return;
						}

						rows = game.getCurrentRow();
						Circle circle = (Circle) (grid.getChildren().get((place - 1) * 6 + rows));
						circle.setFill(Color.BLUE);

						if (game.gameWon()) 
						{
							won.setHeaderText("");
							won.showAndWait();
							System.exit(0);

						}
						else if (game.tie()) 
						{
							tie.setTitle("Draw");
							tie.setHeaderText("Tie");
							tie.showAndWait();
							Platform.exit();
					}

						if (multiPlayer) 
						{		
							columnText.setVisible(false);
							button.setVisible(false);
							label.setText("CPU turn.");
							game.setTurn('O');

							cpu = computer.getCpuMove(game);
							game.setCursor ((cpu - 1));
							rows = game.getCurrentRow();

							cpuAlert.setHeaderText(null);
							cpuAlert.setContentText("");
							cpuAlert.showAndWait();

							Circle cpuCircle = (Circle) (grid.getChildren().get((cpu - 1) * 6 + rows));
							cpuCircle.setFill(Color.GREEN);

							if (game.gameWon()) 
							{
								won.setHeaderText("You lost.");
								won.showAndWait();
								System.exit(0);
							}

							else if (game.tie()) 
							{
								tie.setTitle("Tie.");
								tie.setHeaderText("Tie.");
								tie.showAndWait();
								Platform.exit();
							}
						}
                    } 
                    
                    else
                    { 
                        if (!turn(place))
                        {
                            columnFull.showAndWait();
                            columnText.setText("");
                            return;
                        }
                        
                       rows = game.getCurrentRow();

                        Circle fill = (Circle) (grid.getChildren().get((place - 1) * 6 + rows));
                        fill.setFill(Color.YELLOW);

                        if (game.gameWon()) 
                        {
                            won.setHeaderText("Player won wins.");
                            won.showAndWait();
                            System.exit(0);
                        }   
                        else if (game.tie()) 
                        {
                            tie.setTitle("Tie.");
                            tie.setHeaderText("Tie.");
                            tie.showAndWait();
                            Platform.exit();
                        }
                    }
                    if (game.getTurn() == 'X' && !multiPlayer) 
                    {			               	
                       game.setTurn('O');
                       label.setText("Player 2 choice.");
                    } 
                    else 
                    {																
                        game.setTurn('X');
                        button.setVisible(true);
                        columnText.setVisible(true);
                        label.setText("Player 1 choice.");
                    }
                    columnText.setText("");
                }
            });
        }
    }
    public void update(int row, int col, int player)
    {
        Circle color = (Circle) (grid.getChildren().get((col - 1) * 6 + row));

        if (player == 1)
        {
            color.setFill(Color.BLUE);
        }
        else
        {
            color.setFill(Color.BLACK);
        }
    }

    private boolean turn(int choice) 
    {
            if (!game.setCursor((choice-1)))
            {
             return false;
            }
            else
            {
                return true;
            }
    }

    public void setTexT(String text)
    {
        columnText.setPromptText(text);
    }

    public void setLabel(String text)
    {

        label.setText(text);
    }
    public void noLabel()
    {
        label.setVisible(false);
    }

    public void label()
    {
        label.setVisible(true);
    }

    public void noButton()
    {
        button.setVisible(false);
    }

    public void click()
    {
        button.setVisible(true);
    }

    public void inviText()
    {
        columnText.setVisible(false);
    }
    public void text()
    {
        columnText.setVisible(true);
    }

    public void newOpponent()
    {
        opponent.showAndWait();
    }

    public void setMove()
    {
        try 
        {
            place = Integer.parseInt (columnText.getText());
        } catch (Exception exception) 
        {
            invalid.showAndWait();
            columnText.setText("");
            return;
        }
        if (place > 7 || place < 1) 
        {
            invalid.showAndWait();
            return;
        }
        else
        {
            turnDone = true;
        }
    }

    public boolean checkMove()
    {

        return turnDone;
    }

    public void setMove (boolean move) 
    {
    	turnDone = move;
    }

    public int getChoice() 
    {
        return place;
    }

    public void player (int player)
    {
        playerOne = player;
    }

    public void draw()
    {
        tie.showAndWait();
        System.exit(0);
    }

    public void showWinner(int player)
    {
        if (player == 1)
        {
            won.setHeaderText("You win.");
        }else
        {
            won.setHeaderText("Player 2 wins.");
        }
        won.showAndWait();
        System.exit(0);
    }
}