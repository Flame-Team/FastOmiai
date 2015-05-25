package com.sogou.fastomiai.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class LogUtil {

    private static final boolean DBG = true;

    /**
     * Send a {@link #VERBOSE} log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (DBG) {
            Log.v(tag, msg);
        }
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (DBG) {
            Log.v(tag, msg, tr);
        }
    }

    /**
     * Send a {@link #DEBUG} log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (DBG) {
            Log.d(tag, msg);
        }
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (DBG) {
            Log.d(tag, msg, tr);
        }
    }

    /**
     * Send an {@link #INFO} log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (DBG) {
            Log.i(tag, msg);
        }
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (DBG) {
            Log.i(tag, msg, tr);
        }
    }

    /**
     * Send a {@link #WARN} log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (DBG) {
            Log.w(tag, msg);
        }
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (DBG) {
            Log.w(tag, msg, tr);
        }
    }

    /*
     * Send a {@link #WARN} log message and log the exception.
     * 
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     * 
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
        if (DBG) {
            Log.w(tag, tr);
        }
    }

    /**
     * Send an {@link #ERROR} log message.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (DBG) {
            Log.e(tag, msg);
        }
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     * @param tr
     *            An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (DBG) {
            Log.e(tag, msg, tr);
        }
    }

    /**
     * Write a log message to trace file under the sdcard root directory.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public synchronized static void f(String tag, String msg) {
        if (DBG) {
            toFile(tag, msg);
        }
    }

    /**
     * Write a log message to trace file under the sdcard root directory, and
     * print a log by cmd line.
     * 
     * @param priority
     *            The priority/type of this log message
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public synchronized static void f(int priority, String tag, String msg) {
        if (DBG) {
            Log.println(priority, tag, msg);
            toFile(tag, msg);
        }
    }

    /**
     * Write a log message to trace file under the sdcard root directory.
     * 
     * @param tag
     *            Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg
     *            The message you would like logged.
     */
    public synchronized static void toFile(String tag, String msg,
            String filename) {
        if (DBG) {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                File file = new File(Environment.getExternalStorageDirectory(),
                        filename);

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file, true);
                    Date date = new Date();
                    String xtime = date.getHours() + ":" + date.getMinutes()
                            + ":" + date.getSeconds();
                    String msgdata = xtime + " " + tag + " " + msg + "\r\n";
                    fos.write(msgdata.getBytes("UTF-8"));
                    fos.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        fos = null;
                    }
                }
            }
        }
    }

    private synchronized static void toFile(String tag, String msg) {
        toFile(tag, msg, "trace.txt");
    }
}
