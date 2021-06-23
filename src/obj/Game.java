package obj;

import app.Controller;
import app.Main;
import javafx.application.Platform;
import javafx.scene.image.ImageView;

public class Game implements Runnable {

    public static Integer gameSpeed;
    public Thread gameThread;

    public Bulldozer bulldozer;
    public Excavator excavator1;
    public Excavator excavator2;
    public Tipper tipper1;
    public Tipper tipper2;
    public Tipper tipper3;
    public Tipper tipper4;
    public Heap heap;
    public Figure[] figures;
Controller controller;
    public TaskGenerator taskGenerator;

    public Game(ImageView bull, ImageView excav1, ImageView excav2,
                ImageView tip1, ImageView tip2, ImageView tip3, ImageView tip4,
                ImageView heap,
                Controller controller) throws InterruptedException {
        Game.gameSpeed = 2;
        taskGenerator = new TaskGenerator();
        this.heap = new Heap(heap, 140, 140);

        bulldozer = new Bulldozer("Бульдозер", taskGenerator, this.heap,
                bull, 50, 140, 10, 140);
        tipper1 = new Tipper("Самосвал 1", taskGenerator,
                tip1, 330, 20,  740, 20);
        tipper2 = new Tipper("Самосвал 2", taskGenerator,
                tip2, 330, 100, 740, 100);
        tipper3 = new Tipper("Самосвал 3", taskGenerator,
                tip3, 330, 180, 740, 180);
        tipper4 = new Tipper("Самосвал 4", taskGenerator,
                tip4, 330, 260, 740, 260);
        excavator1 = new Excavator("Погрузчик 1", taskGenerator, this.heap, tipper1, tipper2,
                excav1, 220, 100, 240,  20, 240, 100);
        excavator2 = new Excavator("Погрузчик 2", taskGenerator, this.heap, tipper3, tipper4,
                excav2, 220, 180, 240, 180, 240, 260);
        figures = new Figure[]{bulldozer, excavator1, excavator2,
                tipper1, tipper2, tipper3, tipper4, this.heap};

        this.controller = controller;
    }

    public void start()
    {
        gameThread = new Thread(this);
        gameThread.start();
        bulldozer.start();
        excavator1.start(); excavator2.start();
        tipper1.start(); tipper2.start(); tipper3.start(); tipper4.start();
        taskGenerator.start();

//        Main.gameThread = gameThread;
//        //Main.threads.add(gameThread);
//        Main.threads.add(bulldozer.thread);
//        Main.threads.add(excavator1.thread);
//        Main.threads.add(excavator2.thread);
//        Main.threads.add(tipper1.thread);
//        Main.threads.add(tipper2.thread);
//        Main.threads.add(tipper3.thread);
//        Main.threads.add(tipper4.thread);
//        Main.threads.add(taskGenerator);
    }

    @Override
    public void run() {
        while(Main.running) {
           Platform.runLater(() -> {
                for (Figure figure : figures) {
                figure.paint();
                }
                controller.setLabelText(
                        taskGenerator.getToDoCount(),
                        taskGenerator.getDoneCount());
            });

            try {
                gameThread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
