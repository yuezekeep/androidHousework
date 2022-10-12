package com.example.myapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class CourseListAdapter
        extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    private List<RecordsBean> mCourseListData;
    private Context mContext;
    private int resourceId;
    private static final String TAG = "CourseListAdapter";

    public CourseListAdapter(Context context, int resourceId, List<RecordsBean> data) {
        this.mContext = context;
        this.mCourseListData = data;
        this.resourceId = resourceId;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.activity_course, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //为布局item绑定数据，mCourseListData是一个List容器
    @Override
    public void onBindViewHolder(ViewHolder holder,int position) {
        RecordsBean recordsBean= mCourseListData.get(position);
        holder.courseName.setText(recordsBean.getCourseName());
        holder.courseId.setText(recordsBean.getCourseId());
        holder.collegeName.setText(recordsBean.getCollegeName());

        Uri uri=Uri.parse(recordsBean.getCoursePhoto());
        Log.d("路径：   ",recordsBean.getCoursePhoto());
        holder.image.setImageURI(uri);
//        String hhh=recordsBean.getCoursePhoto();
//        Bitmap bitmap=getHttpBitmap(hhh);
//        holder.image.setImageBitmap(bitmap);
//        holder.image.setB
        Log.d(TAG, "onBindViewHolder: "+recordsBean.getCollegeName());

    }

//    public static Bitmap getHttpBitmap(String url) {
//        URL myFileUrl = null;
//        Bitmap bitmap = null;
//        try {
//            Log.d(TAG, url);
//            myFileUrl = new URL(url);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//        try {
//            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
//            conn.setConnectTimeout(0);
//            conn.setDoInput(true);
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            bitmap = BitmapFactory.decodeStream(is);
//            is.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return bitmap;
//    }
    //返回要添加的item個數
    @Override
    public int getItemCount() {
        return mCourseListData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView courseId;
        TextView courseName;
        TextView collegeName;

        ImageView image;
        public ViewHolder(View view) {
            super(view);

            courseId = view.findViewById(R.id.courseId);
            collegeName = view.findViewById(R.id.collegeName);
            courseName= view.findViewById(R.id.courseName);
            image=view.findViewById(R.id.image_item);
        }
    }
}
