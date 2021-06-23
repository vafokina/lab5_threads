package obj;

import app.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;

abstract class Figure implements Runnable {
    public String name;
    public ArrayList<Image> images;
    public ImageView imageView;
    protected Integer currentImage;

    protected Point[] path;
    protected int pathPoint;
    private double X;
    private double Y;

    protected Thread thread;

    public Figure(String name, Point[] path)
    {
        this.name = name;
        pathPoint = 0;
        currentImage = -1;
        this.path = path;
        X = path[0].x;
        Y = path[0].y;
    }

    public void nextImage(){
        currentImage++;
        if (currentImage == images.size()) currentImage = 0;
    }

    public void move(int time) throws InterruptedException {
        if (pathPoint == 0) {
            pathPoint = 1;
            moveByIterations(time, path[0].x-path[1].x, path[0].y-path[1].y, 100);
        } else {
            pathPoint = 0;
            moveByIterations(time, path[1].x-path[0].x, path[1].y-path[0].y, 100);
        }
    }

    public void moveByIterations(int time, double dx, double dy, int n) throws InterruptedException {
        int sleepTime = time/ n;
        for (int i = 0; i < n; i++)
        {
            if (!Main.running) break;
            X = X - dx / n;
            Y = Y - dy / n;
//System.out.printf(Thread.currentThread().getName() + " " + i + "\n");

            thread.sleep(sleepTime/ Game.gameSpeed);
        }
    }

    public void paint() {
        imageView.setX(X);
        imageView.setY(Y);
        imageView.setImage(images.get(currentImage));
    }

    public void start() {
        thread = new Thread( this );
        thread.start();
    }

    @Override
    public void run() {
//        try {
//            while(Main.running)
//            {
//                //System.out.printf(Thread.currentThread().getName());
//                move();
//            }
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
