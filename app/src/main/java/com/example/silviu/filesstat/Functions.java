package com.example.silviu.filesstat;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by Silviu on 11.01.2017.
 */

public class Functions {
    public static long getDirectorySize(File file) {
        long size;
        if (file.isDirectory()) {
            size = 0;
            for (File child : file.listFiles()) {
                size += getDirectorySize(child);
            }
        } else {
            size = file.length();
        }
        return size;
    }

    public static int getDirectoryFileCount(File directory) {
        int count = 0;
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                count++;
            }
            if (file.isDirectory()) {
                count += getDirectoryFileCount(file);
            }
        }
        return count;
    }

    public static String getReadableSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups))
                + " " + units[digitGroups];
    }

    public static int getLengthDip(Context context,long fileSize, long totalSize) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        long percent = 100 * fileSize/totalSize;
        float dp;
        if(percent<1){
            dp = 1;
        }else{
            dp = 2*percent;
        }
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}
