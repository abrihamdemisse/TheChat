package com.AbrishPhoenix.TheChat;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static String dataa = "";
    public static void log(Context context, String message) {
        log(context, null, message);
    }

    public static void log(Context context, String tag, String message) {
        try {
            dataa = FileUtil.readFile(FileUtil.getPackageDataDir(context).concat("/log_file/log.txt"));
            
            if (tag != null) {
                dataa = dataa + "#" + tag + " => ";
            }
            dataa = dataa + "At " + Helper.getTime() + " : " + message + "\n";
            FileUtil.writeFile(FileUtil.getPackageDataDir(context).concat("/log_file/log.txt"), dataa);
            
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }
}