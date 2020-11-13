package Main;

import Observer.Observer;
import Observer.Subject;
import Visitor.StatsElementVisitor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class User extends Subject implements TreeEntry, Observer {
    private String id;
    private List<String> followers;
    private List<String> following;
    private List<String> myTweets;
    private List<String> feed;
    private boolean windowOpened;
    private Stage stage;
    private BorderPane borderPane;

    public User(String id) {
        this.id = id;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        myTweets = new ArrayList<>();
        feed = new ArrayList<>();
        initializeStage();
    }

    public void follow(String id) {
        // attach
        following.add(id);
    }

    public void post(String message) {
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

    public void render() {
        if (windowOpened) {
            return;
        }
        windowOpened = true;

        TextField userText = new TextField();
        TextArea messageText = new TextArea();
        Button followButton = new Button("Follow");
        Button postButton = new Button("Post");
        ListView<String> followingList= new ListView<>();
        ListView<String> feedList = new ListView<>();
        following.add("Test");
        ObservableList<String> items = FXCollections.observableArrayList(following);
        followingList.setItems(items);
        VBox rootBox = new VBox();
        HBox followBox = new HBox();
        HBox postBox = new HBox();

        rootBox.getChildren().addAll(followBox, followingList, postBox, feedList);
        followBox.getChildren().addAll(userText, followButton);
        postBox.getChildren().addAll(messageText, postButton);
        borderPane.setCenter(rootBox);
        stage.show();
    }

    public int accept(StatsElementVisitor visitor) {
        return visitor.visit(this);
    }

    public void update(Subject subject) {
        User user = (User) subject;
        feed.add(user.getTweets().get(user.getTweets().size() - 1));
    }

    private void initializeStage() {
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
