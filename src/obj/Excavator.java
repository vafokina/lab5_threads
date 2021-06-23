package obj;

import app.Logger;
import app.Main;
import app.TimeEvaluator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;

public class Excavator extends Figure {
    private int moveTo;
    private TaskGenerator task;
    private Heap heap;
    private Tipper tipper1;
    private Tipper tipper2;

    public Excavator(String name,
            TaskGenerator task, Heap heap, Tipper tip1, Tipper tip2,
                     ImageView view, int x1, int y1, int x2, int y2, int x3, int y3){
        super(name, new Point[] { new Point(x1,  y1), new Point(x2,  y2), new Point(x3,  y3)});
        moveTo = 1;
        images = new ArrayList<>();
        images.add(new Image("file:src\\res\\excav_empty_to_left.png"));
        images.add(new Image("file:src\\res\\excav_to_left.png"));
        images.add(new Image("file:src\\res\\excav_to_right.png"));
        images.add(new Image("file:src\\res\\excav_up_to_right.png"));
        images.add(new Image("file:src\\res\\excav_empty_to_right.png"));
        imageView = view;
        nextImage();
        this.task = task;
        this.heap = heap;
        tipper1 = tip1;
        tipper2 = tip2;
    }

    @Override
    public void move(int time) throws InterruptedException {

        if (pathPoint == 0) {
            pathPoint = moveTo;
            moveByIterations(time,
                    path[0].x-path[moveTo].x, path[0].y-path[moveTo].y, 100);
        } else {
            pathPoint = 0;
            moveByIterations(time,
                    path[moveTo].x-path[0].x, path[moveTo].y-path[0].y, 100);
        }
    }

    @Override
    public void run() {
        try {
            Logger.writeLog(name + " run");
            while(Main.running)
            {
                if (task.hasWaitingCountForExc())
                {
                    int restTime = TimeEvaluator.getExcavatorRestTime();
                    Logger.writeLog(name + " отдыхает " + restTime/1000);
                    thread.sleep(restTime / Game.gameSpeed);
                    work();
                }
            }
            Logger.writeLog(name + " close");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void work() throws InterruptedException {
        Logger.writeLog(name + " начал работу");
        int chosenTipper = 0;
        while (chosenTipper == 0)
        {
            if (!tipper1.isWorking())
            { chosenTipper = 1; continue;}
            if (!tipper2.isWorking())
            { chosenTipper = 2; continue;}
        }
        int allTime = 0;
        for (int i = 0; i < 2; i++)
        {
                heap.semaphore.acquire();
                heap.dec();
                nextImage();
                thread.sleep(500 / Game.gameSpeed);
            nextImage();
            thread.sleep(500 / Game.gameSpeed);
                moveTo = chosenTipper;

                int time = TimeEvaluator.getExcavatorTime();
                allTime += time;
                move(time);
                thread.sleep(500 / Game.gameSpeed);
                nextImage();
            if (chosenTipper == 1)
            {
                tipper1.incSize();
            }
            else if (chosenTipper == 2)
            {
                tipper2.incSize();
            } else  Logger.writeLog("Ошибочка вышла!");
                thread.sleep(500 / Game.gameSpeed);

                nextImage();
                thread.sleep(500 / Game.gameSpeed);
                nextImage();
                thread.yield();
            allTime += time;
                move(time);
        }
        Logger.writeLog(name + " закончил работу. Время: " + allTime/1000);
    }
}