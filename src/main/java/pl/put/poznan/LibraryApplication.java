package pl.put.poznan;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.put.poznan.DAO.DbConnection;
import pl.put.poznan.utils.FxmlUtils;

public class LibraryApplication extends Application{
    static final public DbConnection dbConnection = new DbConnection();
    public static final String MAINMENU_FXML = "/fxml/MainMenu.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        dbConnection.initDbOperations();
        Parent root = FxmlUtils.fxmlLoader(MAINMENU_FXML);
        primaryStage.setTitle("Biblioteka");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        dbConnection.closeDbOperations();

    }

    public static void main(String[] args){
        launch(args);
    }
}
