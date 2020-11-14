package Main;

import Visitor.GroupTotal;
import Visitor.MessageTotal;
import Visitor.PositiveTotal;
import Visitor.UserTotal;
import javafx.geometry.Insets;
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
        BorderPane borderPane = new BorderPane();

        TreeItem<String> rootItem = new TreeItem<>("Root", new ImageView(groupIcon));
        TreeView<String> treeView = new TreeView<>(rootItem);

        TextField userText = new TextField();
        TextField groupText = new TextField();

        Button userButton = new Button("Add User");
        userButton.setOnAction(event -> {
            if (treeView.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR, "Please select a group.");
                alert.show();
            }
            else if (userText.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter an id.");
                alert.show();
            }
            else if (!checkUnique(rootGroup, userText.getText()) || userText.getText().equals("Root")) {
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
                    findForUser(treeView, rootGroup, userText);
                }
                userText.clear();
            }
        });

        Button groupButton = new Button("Add Group");
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

        Button userViewButton = new Button("Open User View");
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

        Button showUserTotal = new Button("Show User Total");
        showUserTotal.setOnAction(event -> {
            UserTotal visitor = new UserTotal();
            int count = rootGroup.accept(visitor);
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " registered users.");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Users");
            alert.show();
        });

        Button showGroupTotal = new Button("Show Group Total");
        showGroupTotal.setOnAction(event -> {
            GroupTotal visitor = new GroupTotal();
            int count = rootGroup.accept(visitor) - 1;
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " user group(s).");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Groups");
            alert.show();
        });

        Button showMessageTotal = new Button("Show Messages Total");
        showMessageTotal.setOnAction(event -> {
            MessageTotal visitor = new MessageTotal();
            int count = rootGroup.accept(visitor);
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " message(s).");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Messages");
            alert.show();
        });

        Button showPositivePercentage = new Button("Show Positive Percentage");
        showPositivePercentage.setOnAction(event -> {
            PositiveTotal positiveVisitor = new PositiveTotal();
            MessageTotal messageVisitor = new MessageTotal();
            int positiveCount = rootGroup.accept(positiveVisitor);
            int messageCount = rootGroup.accept(messageVisitor);
            double percentage = (double) positiveCount / messageCount;
            DecimalFormat df = new DecimalFormat("##.##");
            alert = new Alert(Alert.AlertType.INFORMATION, df.format(percentage * 100) + "% of messages are positive.");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Positive Messages");
            alert.show();
        });

        VBox vbox = new VBox();
        HBox userBox = new HBox();
        HBox groupBox = new HBox();
        HBox viewBox = new HBox();
        HBox totalBox = new HBox();
        HBox messageBox = new HBox();

        userBox.getChildren().addAll(userText, userButton);
        groupBox.getChildren().addAll(groupText, groupButton);
        viewBox.getChildren().add(userViewButton);
        totalBox.getChildren().addAll(showUserTotal, showGroupTotal);
        messageBox.getChildren().addAll(showMessageTotal, showPositivePercentage);
        vbox.getChildren().addAll(userBox, groupBox, viewBox, totalBox, messageBox);

        vbox.setPadding(new Insets(10, 10, 10, 10));
        userBox.setPadding(new Insets(10, 10, 10, 10));
        groupBox.setPadding(new Insets(10, 10, 10, 10));

        borderPane.setLeft(treeView);
        borderPane.setCenter(vbox);

        return borderPane;
    }

    public User findUser(String id) {
        return checkUser(rootGroup, id);
    }

    private boolean checkUnique(TreeEntry entry, String id) {
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

    private void findForUser(TreeView<String> treeView, TreeEntry entry, TextField text) {
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

    private void findForGroup(TreeView<String> treeView, TreeEntry entry, TextField text) {
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

    private User checkUser(TreeEntry entry, String id) {
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

/*try {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Stage stage = new Stage();
        //stage.setTitle(treeView.getSelectionModel().getSelectedItem().getValue());
        //stage.setTitle(textArea.getText());
        TreeEntry user = new User(userText.getText());
        TreeEntry userGroup = new UserGroup("hello");

        //treeView.getSelectionModel().getSelectedItem();
        // TODO: maybe use isLeaf()?

        stage.setScene(new Scene(root, 450, 450));
        stage.show();
        }
        catch (IOException e) {
        e.printStackTrace();

        For x in all users in root :
      If x is GROUP_TO_ADD_TO:
          Add as a tree item
       else If x is a usergroup :
              for y in all users in x:
                    If y is GROUP_TO_ADD_TO:
                        Add as a tree item
                   else If y is a user group:
                         for z in all users in y:
                               ....

 */