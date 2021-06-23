package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static boolean running;
    public static ArrayList<Thread> threads;
    public static Thread gameThread;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Строительная компания");
        primaryStage.setScene(new Scene(root, 840, 480));
        primaryStage.show();
        running = true;

//        threads = new ArrayList<>();
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent e) {
//                running = false;
//                try {
//                    for (Thread thread : threads) {
//                        thread.join();
//                }
//                    gameThread.join();
//                } catch (InterruptedException interruptedException) {
//                    interruptedException.printStackTrace();
//                }
//
//                Platform.exit();
//                System.exit(0);
//            }
//        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void stop() {
        running = false;
        Platform.setImplicitExit(true);
    }
}
