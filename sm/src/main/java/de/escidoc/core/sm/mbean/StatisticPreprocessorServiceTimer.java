package de.escidoc.core.sm.mbean;


/**
 * @author mih
 * 
 *         Singleton for timing Scheduled Job.
 *         We need this because Spring Scheduler has Bug and 
 *         always executes scheduled job twice.
 * 
 */
public final class StatisticPreprocessorServiceTimer {

    private static final StatisticPreprocessorServiceTimer instance = new StatisticPreprocessorServiceTimer();;
    
    private long lastExecutionTime = 0;

    /**
     * private Constructor for Singleton.
     * 
     */
    private StatisticPreprocessorServiceTimer() {
    }

    /**
     * Only initialize Object once. Check for old objects in cache.
     * 
     * @return StatisticPreprocessorServiceTimer StatisticPreprocessorServiceTimer
     * 
     */
    public static StatisticPreprocessorServiceTimer getInstance() {
        return instance;
    }
    
    /**
     * Get lastExecutionTime.
     * 
     */
    public synchronized long getLastExecutionTime() {
        final long savedLastExecutionTime = lastExecutionTime;
        lastExecutionTime = System.currentTimeMillis();
        return savedLastExecutionTime;
    }

}

