package cs3560;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Admin { // implements singleton design pattern
    private static Admin instance = null;
    private final Node rootIcon = new ImageView(new Image("file:./src/icon.png"));

    private Admin() {
    }

    public static Admin getInstance() { // only creates instance if doesn't exist
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public BorderPane showUI() {
        BorderPane borderPane = new BorderPane();

        TreeItem<String> rootItem = new TreeItem<>("Root", rootIcon);
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
            Alert alert;
            if (treeView.getSelectionModel().getSelectedItem() == null) {
                alert = new Alert(Alert.AlertType.ERROR, "Please select a group.");
                alert.show();
            }
            else if (userText.getText().equals("")) {
                alert = new Alert(Alert.AlertType.ERROR, "Please enter an id.");
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
                    TreeEntry entry = findGroup(treeView, rootGroup);
                    if (entry != null) {
                        TreeItem<String> treeItem = new TreeItem<>(userText.getText());
                        treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                        treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                        ((UserGroup) entry).addUser(userText.getText());
                    }
                    else {
                        alert = new Alert(Alert.AlertType.ERROR, "Please select a group.");
                        alert.show();
                    }
                }
            }
            userText.clear();
        });

        Button groupButton = new Button("Add Group");
        groupButton.setOnAction(event -> {
            Alert alert;
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
                    TreeItem<String> treeItem = new TreeItem<>(groupText.getText(), rootIcon);
                    treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                    treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                    rootGroup.addUserGroup(groupText.getText());
                }
                else {
                    TreeEntry entry = findGroup(treeView, rootGroup);
                    if (entry != null) {
                        TreeItem<String> treeItem = new TreeItem<>(groupText.getText(), rootIcon);
                        treeView.getSelectionModel().getSelectedItem().getChildren().add(treeItem);
                        treeView.getSelectionModel().getSelectedItem().setExpanded(true);
                        ((UserGroup) entry).addUserGroup(groupText.getText());
                    }
                    else {
                        alert = new Alert(Alert.AlertType.ERROR, "Please select a group.");
                        alert.show();
                    }
                }
                groupText.clear();
            }
        });

        VBox vbox = new VBox();
        HBox userBox = new HBox();
        HBox groupBox = new HBox();
        userBox.getChildren().add(userText);
        userBox.getChildren().add(userButton);
        groupBox.getChildren().add(groupText);
        groupBox.getChildren().add(groupButton);
        vbox.getChildren().add(userBox);
        vbox.getChildren().add(groupBox);

        vbox.setPadding(new Insets(10, 10, 10, 10));
        userBox.setPadding(new Insets(10, 10, 10, 10));
        groupBox.setPadding(new Insets(10, 10, 10, 10));

        borderPane.setLeft(treeView);
        borderPane.setCenter(vbox);

        return borderPane;
    }

    private TreeEntry findGroup(TreeView<String> treeView, TreeEntry entry) {
        for (TreeEntry t : ((UserGroup) entry).getList()) {
            if (t instanceof UserGroup) {
                if (treeView.getSelectionModel().getSelectedItem().getValue().equals(t.getId())) {
                    return t;
                }
                else {
                    findGroup(treeView, t);
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