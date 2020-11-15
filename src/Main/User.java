package Main;

import Observer.Observer;
import Observer.Subject;
import Visitor.StatsElementVisitor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;


public class User extends Subject implements TreeEntry, Observer {
    private String id;
    private List<String> followers;
    private List<String> following;
    private List<String> myTweets;
    private List<String> feed;
    private boolean windowOpened;
    private Stage stage;
    private BorderPane borderPane;
    private Alert alert;
    private TextField userText;
    private TextArea messageText;
    private ListView<String> followingList;
    private ListView<String> feedList;

    public User(String id) {
        this.id = id;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        myTweets = new ArrayList<>();
        feed = new ArrayList<>();
        initializeStage();
    }

    public void follow(String id) { // user attaches to given user id and will update when followed user updates
        Admin admin = Admin.getInstance();
        User user = admin.findUser(id);
        if (user != null) {
            user.attach(this);
            following.add(id);
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR, "Cannot find user.");
            alert.show();
        }
    }

    public void post(String message) { // posts message and notifies all observers
        myTweets.add(message);
        feed.add(message);
        notifyObservers();
    }

    public String getId() {
        return id;
    }

    public List<String> getTweets() {
        return myTweets;
    }

    public List<String> getFeed() {
        return feed;
    }

    public void render() { // populates new window with user information
        if (windowOpened) {
            return;
        }
        windowOpened = true;  // only one instance at a time

        userText = new TextField();
        messageText = new TextArea();
        Button followButton = new Button("Follow");
        Button postButton = new Button("Post");
        followingList= new ListView<>();
        feedList = new ListView<>();

        ObservableList<String> initializeFeedList = FXCollections.observableArrayList(feed); // repopulates news feed and following list after closing
        feedList.setItems(initializeFeedList);
        ObservableList<String> initializeFollowerList = FXCollections.observableArrayList(following);
        followingList.setItems(initializeFollowerList);

        followButton.setOnAction(event -> {
            if (id.equals(userText.getText())) {
                alert = new Alert(Alert.AlertType.ERROR, "You cannot follow yourself.");
                alert.show();
            }
            else if(following.contains(userText.getText())) {
                alert = new Alert(Alert.AlertType.ERROR, "You are already following this user.");
                alert.show();
            }
            else {
                follow(userText.getText());
                ObservableList<String> followList = FXCollections.observableArrayList(following); // updates following list after user is followed
                followingList.setItems(followList);
            }
            userText.clear();
        });

        postButton.setOnAction(event -> {
            if (messageText.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter text.");
                alert.show();
            }
            post(id + ": " + messageText.getText());
            ObservableList<String> tweetList = FXCollections.observableArrayList(feed);
            feedList.setItems(tweetList);
            messageText.clear();
        });

        /**************************************************************************************************************
         * UI Implementation
         **************************************************************************************************************/
        VBox rootBox = new VBox(10);
        HBox followBox = new HBox(10);
        HBox postBox = new HBox(10);

        Label followLabel = new Label("Follow User: ");
        Label followingLabel = new Label("Following");
        Label feedLabel = new Label("News Feed");

        rootBox.setPadding(new Insets(10));
        followBox.setPadding(new Insets(10));
        postBox.setPadding(new Insets(10));

        rootBox.setAlignment(Pos.CENTER);
        followBox.setAlignment(Pos.CENTER);
        postBox.setAlignment(Pos.CENTER);

        rootBox.getChildren().addAll(followBox, followingLabel, followingList, feedLabel, feedList, postBox);
        followBox.getChildren().addAll(followLabel, userText, followButton);
        postBox.getChildren().addAll(messageText, postButton);
        borderPane.setCenter(rootBox);
        stage.show();
        /**************************************************************************************************************
         * UI Implementation
         **************************************************************************************************************/
    }

    public int accept(StatsElementVisitor visitor) {
        return visitor.visit(this);
    } // accepts visitor into User class

    public void update(Subject subject) { // updates observers when tweet is posted
        User user = (User) subject;
        feed.add(user.getTweets().get(user.getTweets().size() - 1));
        ObservableList<String> tweetList = FXCollections.observableArrayList(feed);
        feedList.setItems(tweetList);
    }

    private void initializeStage() { // UI initialization
        windowOpened = false;
        borderPane = new BorderPane();
        stage = new Stage();
        stage.setTitle(id);
        stage.setScene(new Scene(borderPane, 750, 500));
        stage.setOnCloseRequest(event -> {
            windowOpened = false;
        });
    }
}
