package app;

import java.util.Random;

public class TimeEvaluator {
    public static Random rnd;
    protected static int tipperFullTime;
    protected static int tipperEmptyTime;
    protected static int excavatorTime;
    protected static int excavatorRestTime;
    protected static int bulldozerTime;
    protected static int taskGeneratorTime;
    protected static double deviationPercentage;
    protected static int toMillis;

    static{
        rnd = new Random();
        tipperFullTime = 25_000;
        tipperEmptyTime = 20_000;
        excavatorTime = 13_000/4;
        excavatorRestTime = 5_000;
        bulldozerTime = 4_000/2;
        taskGeneratorTime = 20_000;

        deviationPercentage = 0.5;
        toMillis = 1000;
    }

    protected static int getDeviation(int val, double deviationPercentage)
    {
        return (int)(val * deviationPercentage);
    }

    protected static int getRndVal(int val)
    {
        return getRndVal(val, deviationPercentage);
    }
    protected static int getRndVal(int val, double deviationPercentage)
    {
        int deviation = getDeviation(val, deviationPercentage);
        int curDeviation = rnd.nextInt(deviation*2+1);
        return val - deviation + curDeviation;
    }

    public static int getTipperFullTime()
    {
        return getRndVal(tipperFullTime);
    }
    public static int getTipperEmptyTime()
    {
        return getRndVal(tipperEmptyTime);
    }
    public static int getExcavatorTime()
    {
        return getRndVal(excavatorTime);
    }
    public static int getExcavatorRestTime()
    {
        return excavatorRestTime;
    }
    public static int getBulldozerTime()
    {
        return getRndVal(bulldozerTime);
    }
    public static int getTaskGeneratorTime()
    {
        return getRndVal(taskGeneratorTime, 0.8);
    }
}
