package com.dimedriller.log;

import java.util.Objects;

public class Log {
    private static final String TAG = "Advanced";
    private static final boolean DEBUG = true;
    private static final int FIRST_STACK_TRACE_ENTRY_INDEX = 4;
    private static final int STACK_TRACE_MESSAGE_MAX_SYMBOLS_COUNT = 1000;

    enum Output {
        ERROR {
            @Override
            void print(String message) {
                android.util.Log.e(TAG, message);
            }
        },
        WARNING {
            @Override
            void print(String message) {
                android.util.Log.w(TAG, message);
            }
        },
        INFO {
            @Override
            void print(String message) {
                android.util.Log.i(TAG, message);
            }
        },
        DEBUG {
            @Override
            void print(String message) {
                android.util.Log.d(TAG, message);
            }
        };

        abstract void print(String message);
    }

    private static void printLogMessage(Output output, Object obj, Object[] logItems) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuilder logMessage = new StringBuilder();
        if (obj != null)
            logMessage.append(Integer.toHexString(obj.hashCode())).append(" - ");
        logMessage.append(stackTrace[FIRST_STACK_TRACE_ENTRY_INDEX].getMethodName());

        int countItems = logItems.length;
        if (countItems != 0) {
            logMessage.append(": ").append(logItems[0]);
            for (int counterItem = 1; counterItem < countItems; counterItem++)
                logMessage.append(", ").append(logItems[counterItem]);
        }
        output.print(logMessage.toString());
    }

    public static void e(Object... logItems) {
        printLogMessage(Output.ERROR, null, logItems);
    }

    public static void w(Object... logItems) {
        printLogMessage(Output.WARNING, null, logItems);
    }

    public static void i(Object... logItems) {
        printLogMessage(Output.INFO, null, logItems);
    }

    public static void d(Object... logItems) {
        if (!DEBUG)
            return;

        printLogMessage(Output.DEBUG, null, logItems);
    }

    public static void dh(Object obj, Object... logItems) {
        if (!DEBUG)
            return;

        printLogMessage(Output.DEBUG, obj, logItems);
    }

    private static void addToLog(StringBuffer logString, StackTraceElement stackTraceItem) {
        logString.append(stackTraceItem.getClassName())
                .append(".")
                .append(stackTraceItem.getMethodName())
                .append(":")
                .append(stackTraceItem.getLineNumber());
    }

    private static void sth(int firstStackTraceEntryIndex, Object obj) {
        //noinspection ConstantConditions,PointlessBooleanExpression
        if (!DEBUG)
            return;

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer logString = new StringBuffer();
        if (obj != null)
            logString.append(Integer.toHexString(obj.hashCode())).append("\n");
        addToLog(logString, stackTrace[firstStackTraceEntryIndex]);
        for(int counterItem = firstStackTraceEntryIndex + 1; counterItem < stackTrace.length; counterItem++) {
            if (logString.length() > STACK_TRACE_MESSAGE_MAX_SYMBOLS_COUNT) {
                android.util.Log.d(TAG, logString.toString());
                logString = new StringBuffer();
            } else
                logString.append("\n");
            addToLog(logString, stackTrace[counterItem]);
        }
        android.util.Log.d(TAG, logString.toString());
    }

    public static void sth(Object obj) {
        sth(FIRST_STACK_TRACE_ENTRY_INDEX, obj);
    }

    public static void st() {
        sth(FIRST_STACK_TRACE_ENTRY_INDEX, null);
    }
}
