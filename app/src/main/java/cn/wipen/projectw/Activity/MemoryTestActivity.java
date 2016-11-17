package cn.wipen.projectw.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import cn.wipen.projectw.R;

public class MemoryTestActivity extends AppCompatActivity {
    private static ImageView mIv;//由于mIV持有this对象，使this无法被GC回收

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_test);
        mIv = new ImageView(this);//这里应该使用getContext()
        Drawable mDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
        mIv.setImageDrawable(mDrawable);
    }
}
