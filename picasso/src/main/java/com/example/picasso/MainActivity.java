package com.example.picasso;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.picasso.Picasso;
import it.sephiroth.android.library.picasso.Transformation;

public class MainActivity extends AppCompatActivity {

    String imageUrl = "http://www.jycoder.com/json/Image/1.jpg";


    @Bind(R.id.button)
    Button button;
    @Bind(R.id.imageView)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /**
         * 图片下载的步骤：
         * 1、使用异步任务或者 handler+thread 获取图片资源
         * 2、使用bitmapFactory 对图片进行解码
         * 3、显示图片
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso
                        .with(MainActivity.this)
                        .load(imageUrl)//有多个重载方法
                        .placeholder(R.mipmap.ic_launcher)//,默认显示图片
                        .error(R.mipmap.ic_launcher)//加载错误时显示的图片
                        .resize(80,80)//转换图片以适应布局大小并减少内存占用
                        .centerCrop()
//                        .transform(new CustomeCropAquareTransFormation()) //自定义转换
                        .into(imageView);//显示到指定控件上
            }
        });
    }

    public class CustomeCropAquareTransFormation implements Transformation{

        @Override
        public Bitmap transform(Bitmap bitmap) {
            int size = Math.min(bitmap.getWidth(),bitmap.getHeight());//得到 Width 和 Height 的最小值
            //判断以高度进行缩减还是以高度进行缩减
            int x= (bitmap.getWidth()-size)/2;
            int y = (bitmap.getHeight()-size)/2;
            Bitmap resultBitmap = Bitmap.createBitmap(bitmap, x, y, 80, 80);//从原始位图剪切图像
            if(resultBitmap!=bitmap){
                bitmap.recycle();//回收Bitmap资源
            }
            return resultBitmap;
        }

        @Override
        public String key() {
            return "square()";
        }
    }



}
