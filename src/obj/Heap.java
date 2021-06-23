package obj;

import app.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Heap extends Figure {
    private int size;
    public Semaphore semaphore;

    public Heap(ImageView view, int x1, int y1) throws InterruptedException {
        super("Куча", new Point[] { new Point(x1,  y1) });
        images = new ArrayList<>();
        images.add(new javafx.scene.image.Image("file:src\\res\\heap_0.png"));
        images.add(new Image("file:src\\res\\heap_1.png"));
        images.add(new Image("file:src\\res\\heap_2.png"));
        images.add(new Image("file:src\\res\\heap_3.png"));
        imageView = view;
        nextImage();
        size = 0;
        semaphore = new Semaphore(4, false);
        semaphore.acquire(4);
    }

    public synchronized int getSize()
    { return size; }
    public synchronized void inc()
    {
        size++;
        if (size >= images.size())
            currentImage = images.size() - 1;
        else currentImage = size;
        semaphore.release();
    }
    public synchronized void dec() throws InterruptedException {
        size--;
        if (size >= images.size())
            currentImage = images.size() - 1;
        else currentImage = size;
    }

}