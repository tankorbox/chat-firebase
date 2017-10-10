package com.example.tan.finalproject.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
    ClickListener mClickListener;
    GestureDetector mGestureDetector;


    public RecyclerTouchListener(final Context context,final ClickListener clickListener) {
        this.mClickListener = clickListener;
        this.mGestureDetector = new GestureDetector(context,new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Toast.makeText(context,"short",Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Toast.makeText(context,"long",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface ClickListener {
        void onClick(View view, int position);
    }

}