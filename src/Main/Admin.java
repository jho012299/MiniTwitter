package Main;

import Visitor.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

public class Admin { // implements singleton design pattern
    private static Admin instance = null;
    private Image groupIcon = new Image("file:./src/icon.png");
    private ImageView twitterBanner = new ImageView(new Image("file:./src/twitterBanner.jpg"));
    private Alert alert;
    private UserGroup rootGroup;

    private Admin() {
        rootGroup = new UserGroup("Root");
    }

    public static Admin getInstance() { // only creates instance if doesn't exist
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public BorderPane showUI() {
        BorderPane borderPane = new BorderPane(); // method returns borderpane that holds the UI

        TreeItem<String> rootItem = new TreeItem<>("Root", new ImageView(groupIcon)); // initial tree includes root
        TreeView<String> treeView = new TreeView<>(rootItem);

        TextField userText = new TextField();
        userText.setPrefWidth(350);
        TextField groupText = new TextField();
        groupText.setPrefWidth(350);

        Button userButton = new Button("Add User"); // add user event
        userButton.setOnAction(event -> {
            if (treeView.getSelectionModel().getSelectedItem() == null) { // alerts when invalid input
                alert = new Alert(Alert.AlertType.ERROR, "Please select a group.");
                alert.show();
            }
            else if (userText.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter an id.");
                alert.show();
            }
            else if (!checkUnique(rootGroup, userText.getText()) || userText.getText().equals("Root")) { // id of user and groups cannot be same
                alert = new Alert(Alert.AlertType.ERROR, "Please enter a unique id.");
                alert.show();
            }
            else {
                if (treeView.getSelectionModel().getSelectedItem().getValue().equals("Root")) {
                    TreeItem<String> treeItem = new TreeItem<>(userText.getText());
                    treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                    treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                    rootGroup.addUser(userText.getText());
                }
                else {
                    findForUser(treeView, rootGroup, userText); // checks if selected item is group; if so, add it under the group
                }
                userText.clear();
            }
        });

        Button groupButton = new Button("Add Group"); // similar execution to user button
        groupButton.setOnAction(event -> {
            if (treeView.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR, "Please select a group.");
                alert.show();
            }
            else if (groupText.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter an id.");
                alert.show();
            }
            else if (!checkUnique(rootGroup, groupText.getText()) || groupText.getText().equals("Root")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter a unique id.");
                alert.show();
            }
            else {
                if (treeView.getSelectionModel().getSelectedItem().getValue().equals("Root")) {
                    TreeItem<String> treeItem = new TreeItem<>(groupText.getText(), new ImageView(groupIcon));
                    treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                    treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                    rootGroup.addUserGroup(groupText.getText());
                }
                else {
                    findForGroup(treeView, rootGroup, groupText);
                }
                groupText.clear();
            }
        });

        Button userViewButton = new Button("Open User View"); // opens a new window with corresponding user information
        userViewButton.setOnAction(event -> {
            if (treeView.getSelectionModel().getSelectedItem() != null) {
                User user = checkUser(rootGroup, treeView.getSelectionModel().getSelectedItem().getValue());
                if(user != null) {
                    user.render();
                }
                else {
                    alert = new Alert(Alert.AlertType.ERROR, "Please select a user.");
                    alert.show();
                }
            }
            else {
                alert = new Alert(Alert.AlertType.ERROR, "Please select a user.");
                alert.show();
            }

        });

        Button showUserTotal = new Button("Show User Total"); // shows an alert with number of users
        showUserTotal.setOnAction(event -> {
            UserTotal visitor = new UserTotal();
            rootGroup.accept(visitor);
            int count = visitor.getCounter();
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " registered users.");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Users");
            alert.show();
        });

        Button showGroupTotal = new Button("Show Group Total"); // shows an alert with number of groups
        showGroupTotal.setOnAction(event -> {
            GroupTotal visitor = new GroupTotal();
            rootGroup.accept(visitor);
            int count = visitor.getCounter() - 1;
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " user group(s).");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Groups");
            alert.show();
        });

        Button showMessageTotal = new Button("Show Messages Total"); // shows an alert with number of messages
        showMessageTotal.setOnAction(event -> {
            MessageTotal visitor = new MessageTotal();
            rootGroup.accept(visitor);
            int count = visitor.getCounter();
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " message(s).");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Messages");
            alert.show();
        });

        Button showPositivePercentage = new Button("Show Positive Percentage"); // shows an alert with percentage of positive messages
        showPositivePercentage.setOnAction(event -> {
            PositiveTotal positiveVisitor = new PositiveTotal();
            MessageTotal messageVisitor = new MessageTotal();
            rootGroup.accept(positiveVisitor);
            rootGroup.accept(messageVisitor);
            int positiveCount = positiveVisitor.getCounter();
            int messageCount = messageVisitor.getCounter();

            if (messageCount == 0) {
                alert = new Alert(Alert.AlertType.ERROR, "There are currently no messages.");
            }
            else {
                double percentage = (double) positiveCount / messageCount;
                DecimalFormat df = new DecimalFormat("##.##");
                alert = new Alert(Alert.AlertType.INFORMATION, df.format(percentage * 100) + "% of messages are positive.");
            }
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Positive Messages");
            alert.show();
        });

        Button showRecentlyUpdated = new Button("Show Most Recently Updated"); // Shows most recently updated user (including account creation)
        showRecentlyUpdated.setOnAction(event -> {
            LastUpdated visitor = new LastUpdated();
            rootGroup.accept(visitor);
            User user = visitor.getUser();

            if (user == null) {
                alert = new Alert(Alert.AlertType.INFORMATION, "No currently registered users.");
            }
            else {
                alert = new Alert(Alert.AlertType.INFORMATION, "The last updated user is: " + user.getId());
            }
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Last Updated User");
            alert.show();
        });

        /**************************************************************************************************************
         * UI Implementation
         **************************************************************************************************************/
        VBox vbox = new VBox(10);
        HBox userBox = new HBox(10);
        HBox groupBox = new HBox(10);
        HBox viewBox = new HBox(10);
        HBox bannerBox = new HBox(10);
        HBox totalBox = new HBox(10);
        HBox messageBox = new HBox(10);
        HBox updateBox = new HBox(10);

        userBox.getChildren().addAll(userText, userButton);
        groupBox.getChildren().addAll(groupText, groupButton);
        viewBox.getChildren().add(userViewButton);
        bannerBox.getChildren().add(twitterBanner);
        totalBox.getChildren().addAll(showUserTotal, showGroupTotal);
        messageBox.getChildren().addAll(showMessageTotal, showPositivePercentage);
        updateBox.getChildren().add(showRecentlyUpdated);
        vbox.getChildren().addAll(userBox, groupBox, viewBox, bannerBox, totalBox, messageBox, updateBox);

        vbox.setPadding(new Insets(5, 10, 5, 10));
        userBox.setPadding(new Insets(5, 10, 5, 10));
        userBox.setAlignment(Pos.CENTER);
        groupBox.setPadding(new Insets(5, 10, 5, 10));
        groupBox.setAlignment(Pos.CENTER);
        viewBox.setPadding(new Insets(5, 10, 5, 10));
        viewBox.setAlignment(Pos.CENTER);
        bannerBox.setPadding(new Insets(5, 10, 5, 10));
        bannerBox.setAlignment(Pos.CENTER);
        totalBox.setPadding(new Insets(10));
        totalBox.setAlignment(Pos.CENTER);
        messageBox.setPadding(new Insets(10));
        messageBox.setAlignment(Pos.CENTER);
        updateBox.setPadding(new Insets(10));
        updateBox.setAlignment(Pos.CENTER);

        userButton.setPrefWidth(150);
        groupButton.setPrefWidth(150);
        userViewButton.setPrefWidth(500);
        showUserTotal.setPrefWidth(250);
        showGroupTotal.setPrefWidth(250);
        showMessageTotal.setPrefWidth(250);
        showPositivePercentage.setPrefWidth(250);
        showRecentlyUpdated.setPrefWidth(250);

        borderPane.setLeft(treeView);
        borderPane.setCenter(vbox);

        /**************************************************************************************************************
         * UI Implementation
         **************************************************************************************************************/

        return borderPane;
    }

    public User findUser(String id) { // returns corresponding user with id (full search)
        return checkUser(rootGroup, id);
    }

    private boolean checkUnique(TreeEntry entry, String id) { // checks if id is unique
        for  (TreeEntry t : ((UserGroup) entry).getList()) {
            if (t.getId().equals(id)) {
                return false;
            }
            if (t instanceof UserGroup) {
                if (!checkUnique(t, id)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void findForUser(TreeView<String> treeView, TreeEntry entry, TextField text) { // checks if selected item is UserGroup and adds user as child
        for (TreeEntry t : ((UserGroup) entry).getList()) {
            if (t instanceof UserGroup) {
                if (treeView.getSelectionModel().getSelectedItem().getValue().equals(t.getId())) {
                    TreeItem<String> treeItem = new TreeItem<>(text.getText());
                    treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                    treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                    ((UserGroup) t).addUser(text.getText());
                    break;
                }
                else {
                    findForUser(treeView, t, text);
                }
            }
        }
    }

    private void findForGroup(TreeView<String> treeView, TreeEntry entry, TextField text) { // checks if selected item is UserGroup and adds group as child
            for (TreeEntry t : ((UserGroup) entry).getList()) {
                if (t instanceof UserGroup) {
                    if (treeView.getSelectionModel().getSelectedItem().getValue().equals(t.getId())) {
                        TreeItem<String> treeItem = new TreeItem<>(text.getText(), new ImageView(groupIcon));
                        treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                        treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                        ((UserGroup) t).addUserGroup(text.getText());
                        break;
                    }
                    else {
                        findForGroup(treeView, t, text);
                    }
                }
            }
    }

    private User checkUser(TreeEntry entry, String id) { // returns user with corresponding id
       if (entry instanceof User && entry.getId().equals(id)) {
           return (User) entry;
       }

       if (entry instanceof UserGroup) {
           for (TreeEntry t : ((UserGroup) entry).getList()) {
               User user = checkUser(t, id);
               if (user != null) {
                   return user;
               }
           }
       }
       return null;
    }
}