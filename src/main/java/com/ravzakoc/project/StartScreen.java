package com.ravzakoc.project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartScreen extends Application {
    private FileUserService userService = new FileUserService();
    private Label timerLabel = new Label("Time: 0 sec");//süre
    private Timeline timeline;//time
    private int seconds = 0;//time
    private VBox root1;
    private String player1Name;
    private String player2Name;
    private Label player1ScoreLabel; // score label
    private Label player2ScoreLabel; //score label
    private int player1Score;    // score
    private int player2Score;
    private boolean isPlayer1Turn = true;// score
    @Override


    public void start(Stage primaryStage){

        VBox rootRegister = new VBox(10);
        Scene scene1= new Scene(rootRegister ,300,200);
        scene1.setRoot(Register(scene1));

        Label label1= new Label("Starting Memory Game with Timer" );
        Button button1 =new Button("Continue");
        root1 = new VBox(10);
        root1.getChildren().addAll(label1,button1);
        root1.setAlignment(Pos.CENTER);// prints in the middle of the screen

        //button used for level selection
        button1.setOnAction(event->{
            VBox root2 = new VBox(10);
            Label label2 = new Label("Choose number of players");
            Button button2 = new Button("1 Player");
            Button button3 =new Button("2 Player");
            Button button10 = new Button("Back");
            root2.getChildren().addAll(label2,button2,button3,button10);// Used to print on the screen one below thw other
            root2.setAlignment(Pos.CENTER);
            scene1.setRoot(root2);

            //Back button
            button10.setOnAction(a->{
                scene1.setRoot(root1);
            });


            button2.setOnAction(k->{
                VBox root5= new VBox(10);
                Label labelisim = new Label("Write your name ");
                TextField textisim1= new TextField();// The field where the user writes her name.
                Button button11 = new Button("Send");
                Button button13 = new Button("Back");
                root5.getChildren().addAll(labelisim,textisim1,button11,button13);
                scene1.setRoot(root5);

                // Back button
                button13.setOnAction(a->{
                    scene1.setRoot(root2);
                });

                button11.setOnAction(event1->{
                    String playerName = textisim1.getText().trim();

                    if (playerName.isEmpty()) {
                        labelisim.setText("Please enter your name!");
                        return;
                    }
                    this.player1Name = playerName;
                    this.player1Score = 0;
                    this.player2Name = null;
                    this.player2Score = 0;

                    VBox root3= new VBox(10);
                    Button button4=new Button("Basic");
                    Button button5 =new Button("Middle");
                    Button button6 =new Button ("Hard");
                    Label label3 = new Label("Choose level.");
                    Button button15 = new Button("Back");
                    root3.getChildren().addAll(label3,button4,button5,button6, button15);
                    root3.setAlignment(Pos.CENTER);
                    scene1.setRoot(root3);

                    //Back button
                    button15.setOnAction(a->{
                        scene1.setRoot(root5);
                    });

                    //The card game method is called
                    // Basic 4x4
                    button4.setOnAction(event2 -> {
                        scene1.setRoot(CardGame(3,2,"Basic Level - Memory Game "));

                    });
                    button5.setOnAction(p->{
                        scene1.setRoot(CardGame(4,3,"Middle Level - Memory Game "));
                    });
                    button6.setOnAction(betul->{
                        scene1.setRoot(CardGame(5,4,"Hard Level - Memory Game" ));
                    });
                });
            });

            button3.setOnAction(l->{
                VBox root6=new VBox(10);
                Label labelisim1= new Label("Write the name of Player 1");
                TextField textisim2= new TextField();
                Label labelisim2= new Label("Write the name of Player 2");
                TextField textisim3= new TextField();
                Button button12= new Button("Send");
                Button button14 = new Button("Back");
                root6.getChildren().addAll(labelisim1,textisim2,labelisim2,textisim3,button12,button14);
                scene1.setRoot(root6);

                //Back button
                button14.setOnAction(a->{
                    scene1.setRoot(root2);
                });

                button12.setOnAction(n->{
                    String player1 = textisim2.getText().trim();
                    String player2 = textisim3.getText().trim();

                    if (player1.isEmpty() || player2.isEmpty()) { // If player1 and player2 names are not entered, the if loop will not work.
                        labelisim1.setText("Both player names must be filled!");
                        return;
                    }
                    this.player1Name = player1;
                    this.player2Name = player2;
                    this.player1Score = 0;
                    this.player2Score = 0;
                    VBox root4= new VBox(10);
                    Button button7=new Button("Basic");
                    Button button8 =new Button("Middle");
                    Button button9 =new Button ("Hard");
                    Label label4 = new Label("Choose level.");
                    Button button16 = new Button("Back");
                    root4.getChildren().addAll(label4,button7,button8,button9, button16);
                    root4.setAlignment(Pos.CENTER);
                    scene1.setRoot(root4);

                    //Back button
                    button16.setOnAction(a->{
                        scene1.setRoot(root6);
                    });

                    //The card game method is called
                    button7.setOnAction(e->{
                        scene1.setRoot(CardGame(3,4," Basic Level - Memory Game "));
                    });
                    button8.setOnAction(e->{
                        scene1.setRoot(CardGame(4,4," Middle Level - Memory Game "));
                    });
                    button9.setOnAction(e->{
                        scene1.setRoot(CardGame(5,4," Hard Level - Memory Game "));
                    });
                }); });



        });
        //The Stage is called
        primaryStage.setTitle("Memory Game with Timer");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    private VBox CardGame(int rows, int cols, String title){//satır , sütunu ve başlığı belirler

        //It shows that the seconds increase by 1 until the game ends.
        seconds = 0;//time
        timerLabel.setText("Time: 0 sec");
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    seconds++;
                    timerLabel.setText("Time: " + seconds + " sec");
                })
        );
        // Makes the counter run in an infinite loop until the game ends.
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        VBox root7 = new VBox(10);
        Label Label = new Label (title);
        VBox scoreBoard = new VBox(5); // Creates a separate VBox for the scoreboard
        scoreBoard.setAlignment(Pos.TOP_CENTER);

        //Player2 is definite as an empty character by placing it in an empty string, so only single player mode will work
        if (player2Name == null || player2Name.isEmpty()) { // Single player mode
            player1ScoreLabel = new Label(player1Name + ": " + player1Score);
            player1ScoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); //We added style
            scoreBoard.getChildren().add(player1ScoreLabel);
        } else { // Two player mode
            HBox dualPlayerScore = new HBox(20);
            dualPlayerScore.setAlignment(Pos.TOP_CENTER);

            player1ScoreLabel = new Label(player1Name + ": " + player1Score);
            player1ScoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;"); // Adds style to the label

            player2ScoreLabel = new Label(player2Name + ": " + player2Score);
            player2ScoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label divider = new Label("|"); //Used as a separator
            divider.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

            dualPlayerScore.getChildren().addAll(player1ScoreLabel, divider, player2ScoreLabel);
            scoreBoard.getChildren().add(dualPlayerScore);
        }

        //For card order
        GridPane Grid1 = new GridPane();
        Grid1.setAlignment(Pos.CENTER);
        Grid1.setHgap(10);
        Grid1.setVgap(10);

        //Creates a list of card values
        int Cards1 = rows * cols;
        List<Integer> numbers = new ArrayList<>();

        //Creates escort pairs by adding each card number twice
        for (int n = 1; n <= Cards1 / 2; n++){
            numbers.add(n);
            numbers.add(n);
        }
        //It allows the numbers of the cards to be mixed up.
        Collections.shuffle(numbers);
        List<Button> selectedCards = new ArrayList<>();
        int index = 0;
        //Star card buttons are created in the specified sizes and the cards are placed in rows and columns.
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                Button card = new Button("*");
                card.setPrefSize(60,60);

                int CardValue = numbers.get(index);
                card.setUserData(CardValue);
                //On card click show the value, prevent further clicks, and track it in the selected cards list
                card.setOnAction(e -> {
                    card.setText(String.valueOf(CardValue));
                    card.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
                    card.setDisable(true);
                    selectedCards.add(card);

                    // Check if two cards are selected, then extract their stored values to compare for a match
                    if (selectedCards.size() == 2) {
                        Button first = selectedCards.get(0);
                        Button second = selectedCards.get(1);

                        int val1 = (int) first.getUserData();
                        int val2 = (int) second.getUserData();

                        // Matching logic: If the cards match, add a point to the current player and update the score label
                        if (val1 == val2) {

                            if (player2Name == null || player2Name.isEmpty()) {
                                player1Score++;
                                player1ScoreLabel.setText(player1Name + ": " + player1Score);
                            } else { // Turn does not change after a match, as the same player continues
                                if (isPlayer1Turn) {
                                    player1Score++;
                                    player1ScoreLabel.setText(player1Name + ": " + player1Score);
                                } else {
                                    player2Score++;
                                    player2ScoreLabel.setText(player2Name + ": " + player2Score);
                                }

                            }
                            selectedCards.clear();

                            // Check if all cards are opened.
                            boolean allCardsDisabled = true;

                            // Check all buttons in the grid; if any is enabled, set flag and exit the loop
                            ObservableList<Node> children = Grid1.getChildren();
                            int k;
                            for (k = 0; k < children.size(); k++) {
                                Node node = children.get(k);
                                if (node instanceof Button) {
                                    Button b = (Button) node;
                                    if (!b.isDisabled()) {
                                        allCardsDisabled = false;
                                        break;
                                    }
                                }
                            }
                            if (allCardsDisabled) {
                                timeline.stop();
                                timerLabel.setText("Game Over! Time: " + seconds + " sec");
                            }
                        } else {
                            // If there is no match, close the cards and give the turn to the other user
                            new Thread(() -> {
                                try {
                                    Thread.sleep(800);//Shows how many milliseconds the card was open
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                                // Close the card nd clear the selection list
                                Platform.runLater(() -> {
                                    first.setText("*");
                                    second.setText("*");
                                    first.setStyle("");   // Style is resed
                                    second.setStyle("");
                                    first.setDisable(false);
                                    second.setDisable(false);
                                    selectedCards.clear();

                                    //Order change only happens on unsuccessful match
                                    if (player2Name != null && !player2Name.isEmpty()) {
                                        isPlayer1Turn = !isPlayer1Turn;
                                    }
                                });
                            }).start();
                        }
                    }
                });

                Grid1.add(card , j, i);
                index++;



            }
        }
        root7.setAlignment(Pos.CENTER);
        root7.getChildren().addAll(Label, scoreBoard, timerLabel, Grid1);
        return root7;
    }
    public VBox Register(Scene scene){
        Label label1= new Label("REGISTER");
        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(8);
        grid1.setVgap(8);
        Label label2=new Label("User Name");
        Label label3= new Label("Password");
        TextField text1=new TextField();
        TextField text2= new TextField();

        // Add elements to the GridPane
        grid1.add(label2, 0, 0);
        grid1.add(text1, 1, 0);
        grid1.add(label3, 0, 1);
        grid1.add(text2, 1, 1);


        VBox rootregister= new VBox(10);
        Button button1= new Button("Register");
        Button button2= new Button("Login Screen");
        rootregister.setAlignment(Pos.CENTER);
        rootregister.getChildren().addAll(button1,button2);
        VBox rootregister2= new VBox(20);
        rootregister2.setAlignment(Pos.CENTER);
        rootregister2.getChildren().addAll(label1 , grid1 , rootregister);

        button1.setOnAction(e -> {
            String username = text1.getText().trim();
            String password = text2.getText().trim();

            // Attempt user registration; show success message if successful, error message if failed
            if (userService.register(username, password)) {
                label1.setText("Registration successful!");
            } else {
                label1.setText("Registration failed. Username might be taken or fields are empty.");
            }
        });


        button2.setOnAction(e->{
            // Switch from the Register screen to the Login screen
            Scene scene1 = button2.getScene();
            scene.setRoot(Login(scene));
        });

        return rootregister2;

    }
    public VBox Login(Scene scene){
        Label label1= new Label("LOGİN");
        GridPane grid1 = new GridPane();
        grid1.setAlignment(Pos.CENTER);
        grid1.setHgap(8);
        grid1.setVgap(8);
        Label label2=new Label("User Name");
        Label label3= new Label("Password");
        TextField text1=new TextField();
        TextField text2= new TextField();

        grid1.add(label2, 0, 0);  // 1. row, 1. column
        grid1.add(text1, 1, 0);    // 2.row, 1. column
        grid1.add(label3, 0, 1);   // 1.row, 2. column
        grid1.add(text2, 1, 1);    // 2.row, 2. column

        VBox rootlogin= new VBox(10);
        Button button1= new Button("Login");
        Button button2= new Button("Back to register ");
        rootlogin.setAlignment(Pos.CENTER);
        rootlogin.getChildren().addAll(button1,button2);
        VBox rootlogin2 = new VBox(20);
        rootlogin2.setAlignment(Pos.CENTER);
        rootlogin2.getChildren().addAll(label1 , grid1 , rootlogin);

        button2.setOnAction(e->{
            scene.setRoot(Register(scene));

        });

        button1.setOnAction(e -> {
            String username = text1.getText().trim();
            String password = text2.getText().trim();

            if (userService.login(username, password)) {
                scene.setRoot(root1); // Redirect to the start screen

            } else {
                label1.setText("Login failed. Check your credentials.");
            }
        });

        return rootlogin2;

    }
    public static void main(String[] args) {
        launch(args);
    }
}