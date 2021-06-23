package obj;

import app.Logger;
import app.Main;
import app.TimeEvaluator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;

public class Bulldozer extends Figure {
private TaskGenerator task;
private Heap heap;

    public Bulldozer(String name,
                     TaskGenerator task, Heap heap,
                     ImageView view, int x1, int y1, int x2, int y2){
        super(name, new Point[] { new Point(x1,  y1), new Point(x2,  y2)});
        images = new ArrayList<>();
        images.add(new Image("file:src\\res\\bull_to_left.png"));
        images.add(new Image("file:src\\res\\bull_to_right.png"));
        imageView = view;
        nextImage();
        this.task = task;
        this.heap = heap;
    }

    @Override
    public void run() {
        try {
            Logger.writeLog(name + " run");
            while(Main.running)
            {
                if (heap.getSize() < 4) {
                    if (task.hasWaitingCountForBull()) {
                        int allTime = 0;
                        Logger.writeLog(name + " начал работу");
                        for (int i = 0; i < 4; i++) {
                            int time = TimeEvaluator.getBulldozerTime();
                            allTime += time;
                            move(time);
                            nextImage();
                            if (i % 2 == 1) heap.inc();
                        }
                        Logger.writeLog(name + " закончил работу. Время: " + allTime / 1000);
                    }
                }
            }
            Logger.writeLog(name + " close");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
