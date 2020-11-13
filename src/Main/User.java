package Main;

import Observer.Observer;
import Observer.Subject;
import Visitor.StatsElementVisitor;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
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

    public User(String id) {
        this.id = id;
        followers = new ArrayList<>();
        following = new ArrayList<>();
        myTweets = new ArrayList<>();
        feed = new ArrayList<>();
        initializeStage();
    }

    public void follow(String id) {
        Admin admin = Admin.getInstance();
        User user = admin.findUser(id);
        if (user != null) {
            user.attach(this);
            following.add(id);
            System.out.println("You are now following " + id);
        }
        else {
            alert = new Alert(Alert.AlertType.ERROR, "Cannot find user.");
            alert.show();
        }
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

        followButton.setOnAction(event -> {
            if (id.equals(userText.getText())) {
                alert = new Alert(Alert.AlertType.ERROR, "You cannot follow yourself.");
                alert.show();
            }
            else {
                follow(userText.getText());
                ObservableList<String> items = FXCollections.observableArrayList(following);
                followingList.setItems(items);
            }
        });

        postButton.setOnAction(event -> {
            post(messageText.getText());
        });

        following.add("Test");
        ObservableList<String> items = FXCollections.observableArrayList(following);
        followingList.setItems(items);

        //followingList.getItems().addListener((ListChangeListener<String>) c -> followingList.setItems(items));

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
