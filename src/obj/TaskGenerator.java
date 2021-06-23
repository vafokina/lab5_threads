package obj;

import app.Logger;
import app.Main;
import app.TimeEvaluator;

public class TaskGenerator extends Thread {
    private int count;
    private int doneCount;

    private int toDoCountForBull;
    private int toDoCountForExc;

    public TaskGenerator()
    {
        count = 0;
        doneCount = 0;

         toDoCountForBull = 0;
         toDoCountForExc = 0;
    }

    public synchronized int getToDoCount()
    { return count - doneCount;}
    public synchronized int getDoneCount()
    { return doneCount;}

    public synchronized boolean hasWaitingCountForBull() {
        if (toDoCountForBull > 0)
        {
            toDoCountForBull--;
            toDoCountForExc++;
            return true;
        }
        return false;
    }
    public synchronized boolean hasWaitingCountForExc() {
        if (toDoCountForExc  > 0) {
            toDoCountForExc--;
            return true;
        }
        return false;
    }

    public synchronized void incCount()
    {
        count++;
        toDoCountForBull++;
        Logger.writeLog("Поступил заказ № " + count);
    }
    public synchronized void Done()
    { doneCount++;
        Logger.writeLog("Заказ выполнен. Всего выполнено " + doneCount);
    }

    @Override
    public void run() {
        try {
            Logger.writeLog("Генератор заданий" + " run");
            this.sleep(2000 / Game.gameSpeed);
            while(Main.running)
            {
                incCount();
                this.sleep(TimeEvaluator.getTaskGeneratorTime()/ Game.gameSpeed);
            }
            Logger.writeLog("Генератор заданий" + " close");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
