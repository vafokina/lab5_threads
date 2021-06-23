package obj;

import app.Logger;
import app.Main;
import app.TimeEvaluator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;

public class Tipper extends Figure {
    private boolean working;
    private int size;
    private TaskGenerator task;

    public Tipper(String name,
                  TaskGenerator task,
            ImageView view, int x1, int y1, int x2, int y2){
        super(name, new Point[] { new Point(x1,  y1), new Point(x2,  y2)});
        images = new ArrayList<>();
        images.add(new javafx.scene.image.Image("file:src\\res\\tip_empty_to_right.png"));
        images.add(new Image("file:src\\res\\tip_to_right.png"));
        images.add(new Image("file:src\\res\\tip_empty_to_left.png"));
        imageView = view;
        nextImage();
        size = 0;
        this.task = task;
    }

    public synchronized boolean isWorking() { return  working; }

    public int getSize() {
        return size;
    }
    public synchronized void setSize(int size) {
        this.size = size;
        if (size == 2) { nextImage(); working = true; }
    }
    public synchronized void incSize() {
        size++;
        if (size == 2) {
            nextImage();
            working = true; }
    }

    @Override
    public void run() {
        try {
            System.out.println(name + " run");
            while(Main.running)
            {
                if (isWorking())
                {
                    int allTime;
                    Logger.writeLog(name + " отправился с грузом");
                    thread.sleep(3500 / Game.gameSpeed);
                    allTime = TimeEvaluator.getTipperFullTime();
                    move(allTime);
                    thread.sleep(1500 / Game.gameSpeed);
                    nextImage();
                    thread.sleep(1500 / Game.gameSpeed);

                    Logger.writeLog(name + " прибыл в пункт назначения. Время: " + allTime/1000);
                    task.Done();

                    Logger.writeLog(name + " возвращается на базу");
                    allTime = TimeEvaluator.getTipperEmptyTime();
                    move(allTime);
                    thread.sleep(500 / Game.gameSpeed);
                    nextImage();
                    thread.sleep(500 / Game.gameSpeed);

                    Logger.writeLog(name + " вернулся на базу. Время: " + allTime/1000);
                    working = false;
                    size = 0;
                }
            }
            System.out.println(name + " close");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
