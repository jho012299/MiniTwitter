package cs3560;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Admin { // implements singleton design pattern
    private static Admin instance = null;

    private Admin() {}

    public static Admin getInstance() { // only creates instance if doesn't exist
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public BorderPane test() {
        BorderPane borderPane = new BorderPane();

        TreeItem<String> rootItem = new TreeItem<>("Root");
        TreeItem<String> item1 = new TreeItem<>("Hello");
        TreeItem<String> item2 = new TreeItem<>("World");
        TreeItem<String> group = new TreeItem<>("List");
        TreeItem<String> groupItem1 = new TreeItem<>("Milk");
        TreeItem<String> groupItem2 = new TreeItem<>("Eggs");

        rootItem.getChildren().add(item1);
        item1.getChildren().add(group);
        item1.getChildren().add(item2);
        group.getChildren().add(groupItem1);
        group.getChildren().add(groupItem2);

        TreeView<String> treeView = new TreeView<>(rootItem);

        TextArea textArea = new TextArea();
        textArea.setMaxHeight(15);

        Button button = new Button("Click me!");
        button.setOnAction(event -> {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
                Stage stage = new Stage();
                //stage.setTitle(treeView.getSelectionModel().getSelectedItem().getValue());
                stage.setTitle(textArea.getText());
                stage.setScene(new Scene(root, 450, 450));
                stage.show();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox();
        HBox hbox = new HBox();
        hbox.getChildren().add(textArea);
        hbox.getChildren().add(button);
        vbox.getChildren().add(hbox);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        borderPane.setLeft(treeView);
        borderPane.setCenter(vbox);

        return borderPane;
    }
}
