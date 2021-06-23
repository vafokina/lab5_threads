package app;

public class Logger {
    public synchronized static void writeLog(String str)
    {
        System.out.println(str);
    }
    public synchronized static void writeLog(String name, int time)
    {
        writeLog(name + " проработал " + time);
    }
}
