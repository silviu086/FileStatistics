package com.example.silviu.filesstat;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Silviu on 11.01.2017.
 */

public class FileAdapter extends BaseAdapter {
    private Context mContext;
    private File mFile;
    private ArrayList<Long> mListSize;
    private ArrayList<Integer> mListDirectoryFiles;
    private LayoutInflater inflater;

    public FileAdapter(Context context, File file, ArrayList<Long> listSize, ArrayList<Integer> listDirectoryFiles) {
        this.mContext = context;
        this.mFile = file;
        mListSize = listSize;
        mListDirectoryFiles = listDirectoryFiles;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class FileHolder{
        TextView textViewNume;
        TextView textViewSize;
        TextView textViewFiles;
        LinearLayout linearLayoutSizeGr;
        LinearLayout linearLayoutSize;
        LinearLayout layout;
        ImageView imageView;

        public FileHolder(View v){
            imageView = (ImageView) v.findViewById(R.id.imageView);
            textViewNume = (TextView) v.findViewById(R.id.textViewNume);
            textViewFiles = (TextView) v.findViewById(R.id.textViewFiles);
            linearLayoutSize = (LinearLayout) v.findViewById(R.id.linearLayoutSize);
            linearLayoutSizeGr = (LinearLayout) v.findViewById(R.id.linearLayoutSizeGr);

            layout = new LinearLayout(mContext);
            layout.setOrientation(LinearLayout.HORIZONTAL);
            //layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
            textViewSize = new TextView(mContext);
            textViewSize.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
            textViewSize.setTextSize(15);
            linearLayoutSizeGr.addView(layout);
            linearLayoutSize.addView(textViewSize);
        }
    }

    @Override
    public int getCount() {
        return mFile.listFiles().length;
    }

    @Override
    public Object getItem(int position) {
        if(mFile.isDirectory()){
            return mFile.listFiles()[position];
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        FileHolder hFile = null;
        final File file = mFile.listFiles()[position];

        if (convertView == null) {
            v = inflater.inflate(R.layout.file_list, null);
            hFile = new FileHolder(v);
            v.setTag(hFile);
        } else {
            hFile = (FileHolder) v.getTag();
        }

        if(file.isDirectory()){
            hFile.imageView.setBackground(mContext.getResources().getDrawable(R.drawable.directory));
        }else{
            hFile.imageView.setBackground(mContext.getResources().getDrawable(R.drawable.file));
        }
        hFile.textViewNume.setText(file.getName());
        int height = 60;
        int width = Functions.getLengthDip(mContext, mListSize.get(position), mFile.getTotalSpace());
        hFile.layout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        if(width<5){
            hFile.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreenLight));
        }else if(width<15){
            hFile.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorGreen));
        }else if(width<30){
            hFile.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrangeLight));
        }else if(width<45){
            hFile.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrange));
        }else if(width<60){
            hFile.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorRedLight));
        }else if(width>60){
            hFile.layout.setBackgroundColor(mContext.getResources().getColor(R.color.colorRed));
        }
        hFile.textViewSize.setText(Functions.getReadableSize(mListSize.get(position)));
        if(file.isDirectory()){
            hFile.textViewFiles.setText(String.valueOf(mListDirectoryFiles.get(position)) + " files");
        }else{
            hFile.textViewFiles.setText("Tip fisier");
        }
        return v;
    }
}
