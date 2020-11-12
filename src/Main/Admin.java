package Main;

import Visitor.GroupTotal;
import Visitor.MessageTotal;
import Visitor.UserTotal;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Admin { // implements singleton design pattern
    private static Admin instance = null;
    private Image groupIcon = new Image("file:./src/icon.png");
    private Alert alert;

    private Admin() {}

    public static Admin getInstance() { // only creates instance if doesn't exist
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public BorderPane showUI() {
        BorderPane borderPane = new BorderPane();

        TreeItem<String> rootItem = new TreeItem<>("Root", new ImageView(groupIcon));
        UserGroup rootGroup = new UserGroup("Root");

        TreeView<String> treeView = new TreeView<>(rootItem);

        TextArea userText = new TextArea();
        userText.setMaxHeight(15);
        userText.setMaxWidth(250);

        TextArea groupText = new TextArea();
        groupText.setMaxHeight(15);
        groupText.setMaxWidth(250);

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
            else { //TODO:prevent users from creating same id
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
            else { //TODO:prevent users from creating same id
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

        Button userViewButton = new Button("Open User View"); //TODO: checkUnique()
        userViewButton.setOnAction(event -> {
            String selected = treeView.getSelectionModel().getSelectedItem().getValue();
            if (treeView.getSelectionModel().getSelectedItem() == null || !checkUser(rootGroup, selected)) {
                alert = new Alert(Alert.AlertType.ERROR, "Please select a user.");
                alert.show();
            }
        });

        Button showUserTotal = new Button("Show User Total");
        showUserTotal.setOnAction(event -> {
            int count = getUserCount(rootGroup);
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " registered users.");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Users");
            alert.show();
        });

        Button showGroupTotal = new Button("Show Group Total");
        showGroupTotal.setOnAction(event -> {
            int count = getGroupCount(rootGroup);
            alert = new Alert(Alert.AlertType.INFORMATION, "There are currently " + count + " user group(s).");
            alert.setTitle("Mini Twitter");
            alert.setHeaderText("Number of Groups");
            alert.show();
        });

        Button showMessageTotal = new Button("Show Messages Total");
        showMessageTotal.setOnAction(event -> {
            MessageTotal visitor = new MessageTotal();
            int count = 0;
            for (TreeEntry t : rootGroup.getList()) {
                count +=  t.accept(visitor);
            }
        });

        Button showPositivePercentage = new Button("Show Positive Percentage");
        showPositivePercentage.setOnAction(event -> {
            // make MessageTotalVisitor and PositiveTotalVisitor and divide the counts
        });

        VBox vbox = new VBox();
        HBox userBox = new HBox();
        HBox groupBox = new HBox();
        HBox viewBox = new HBox();
        HBox totalBox = new HBox();
        userBox.getChildren().add(userText);
        userBox.getChildren().add(userButton);
        groupBox.getChildren().add(groupText);
        groupBox.getChildren().add(groupButton);
        viewBox.getChildren().add(userViewButton);
        totalBox.getChildren().add(showUserTotal);
        totalBox.getChildren().add(showGroupTotal);
        vbox.getChildren().add(userBox);
        vbox.getChildren().add(groupBox);
        vbox.getChildren().add(viewBox);
        vbox.getChildren().add(totalBox);

        vbox.setPadding(new Insets(10, 10, 10, 10));
        userBox.setPadding(new Insets(10, 10, 10, 10));
        groupBox.setPadding(new Insets(10, 10, 10, 10));

        borderPane.setLeft(treeView);
        borderPane.setCenter(vbox);

        return borderPane;
    }

    private void findForUser(TreeView<String> treeView, TreeEntry entry, TextArea text) {
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

    private void findForGroup(TreeView<String> treeView, TreeEntry entry, TextArea text) {
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

    private boolean checkUser(TreeEntry entry, String id) {
       if (entry.getId().equals(id)) {
           return entry instanceof User;
       }

       if (entry instanceof UserGroup) {
           for (TreeEntry t : ((UserGroup) entry).getList()) {
               if (checkUser(t, id)) {
                   return true;
               }
           }
       }
       return false;
    }

    private int getUserCount(TreeEntry entry) {
        int count = 0;
        UserTotal visitor = new UserTotal();
        for (TreeEntry t : ((UserGroup)entry).getList()) {
            if (t instanceof UserGroup) {
                count += getUserCount(t);
            }
            count += t.accept(visitor);
        }
        return count;
    }

    private int getGroupCount(TreeEntry entry) {
        int count = 0;
        GroupTotal visitor = new GroupTotal();
        for (TreeEntry t : ((UserGroup)entry).getList()) {
            if (t instanceof UserGroup) {
                count += getGroupCount(t);
            }
            count += t.accept(visitor);
        }
        return count;
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