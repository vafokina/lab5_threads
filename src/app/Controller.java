package app;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import obj.Game;

public class Controller {

    @FXML
    private ImageView bulldozerImage;

    @FXML
    private ImageView excavatorImage1;

    @FXML
    private ImageView excavatorImage2;

    @FXML
    private ImageView tipperImage1;

    @FXML
    private ImageView tipperImage2;

    @FXML
    private ImageView tipperImage3;

    @FXML
    private ImageView tipperImage4;

    @FXML
    private ImageView heapImage;

    @FXML
    private Button startButton;

    @FXML
    private Slider timeline;

    @FXML
    private Label taskCount;

    @FXML
    private Label taskDoneCount;

    @FXML
    void initialize() throws InterruptedException {
        Game game = new Game(bulldozerImage, excavatorImage1, excavatorImage2,
                tipperImage1, tipperImage2, tipperImage3, tipperImage4,
                heapImage,
                this);

        timeline.setValue(game.gameSpeed);

        startButton.setOnAction(e -> {
            game.start();
            startButton.setDisable(true);
        });

        timeline.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<?extends Number> observable, Number oldValue, Number newValue)
            { Game.gameSpeed = (int)timeline.getValue(); }
        });
    }

    public void setLabelText(int toDoCount, int doneCount) {
        taskCount.setText("Заказов в обработке: " + toDoCount);
        taskDoneCount.setText("Выполнено заказов: " + doneCount);
    }

}
