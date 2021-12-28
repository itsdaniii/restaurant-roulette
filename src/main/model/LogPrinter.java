package model;

/**
 * This class references AlarmSystem
 * Defines behaviours that event log printers must support.
 */
public interface LogPrinter {
    /**
     * Prints the log
     * @param el  the event log to be printed
     */
    void printLog(EventLog el);
}
