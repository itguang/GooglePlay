package com.example.xutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

public class MainActivity extends AppCompatActivity {
    String imageUrl = "http://pic.baike.soso.com/p/20090711/20090711101754-314944703.jpg";

    @ViewInject(value = R.id.button)
    Button button;

    @ViewInject(value = R.id.button2)
    Button button2;

    @ViewInject(value = R.id.button3)
    Button button3;

    @ViewInject(R.id.textView)
    TextView text;
    @ViewInject(R.id.imageView)
    ImageView imageView;

   public boolean hasError = false;
    public String result = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        x.view().inject(this);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams("http://blog.csdn.net/mobile/experts.html");
                // 默认缓存存活时间, 单位:毫秒.(如果服务没有返回有效的max-age或Expires)
                params.setCacheMaxAge(1000 * 60 * 10);
                // 接下来使用CacheCallback, xUtils将为该请求缓存数据.
                x.http().get(params, new Callback.CacheCallback<String>() {
                    @Override
                    public boolean onCache(String result) {
                        // 得到缓存数据, 缓存过期后不会进入这个方法.
                        // 如果服务端没有返回过期时间, 参考params.setCacheMaxAge(maxAge)方法.
                        //
                        // * 客户端会根据服务端返回的 header 中 max-age 或 expires 来确定本地缓存是否给 onCache 方法.
                        //   如果服务端没有返回 max-age 或 expires, 那么缓存将一直保存, 除非这里自己定义了返回false的
                        //   逻辑, 那么xUtils将请求新数据, 来覆盖它.
                        //
                        // * 如果信任该缓存返回 true, 将不再请求网络;----这里可以和刷新配合使用
                        //   返回 false 继续请求网络, 但会在请求头中加上ETag, Last-Modified等信息,
                        //   如果服务端返回304, 则表示数据没有更新, 不继续加载数据.
                        //
                        text.setText(result);
                        return false; // true: 信任缓存数据, 不在发起网络请求; false不信任缓存数据.
                    }

                    @Override
                    public void onSuccess(String result) {
                        // 注意: 如果服务返回304 或 onCache 选择了信任缓存, 这时result为null.
                        if (result != null) {
                            text.setText(result);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                            // 成功获取数据,在此方法中对返回的数据进行操作

                    }
                });
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestParams params = new RequestParams("http://blog.csdn.net/mobile/experts.html");
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("网络请求onSuccess()...");

                        text.setText(result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        System.out.println("网络请求onFinished()...");
                    }
                });


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageOptions options = new ImageOptions.Builder()

                        .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))//图片大小
                        .setRadius(DensityUtil.dip2px(5))//ImageView圆角半径
                        .setCrop(true)// 如果ImageView的大小不是定义为wrap_content, 不要crop.
                        .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                        .setLoadingDrawableId(R.mipmap.ic_launcher)//加载中默认显示图片
                        .setFailureDrawableId(R.mipmap.ic_launcher)//加载失败后默认显示图片


                        .build();

                x.image().bind(imageView, imageUrl);//使用xUtils加载图片,默认自带缓存
                x.image().bind(imageView, imageUrl, options, new MyCallBack());
            }
        });

    }

    /**
     * 加载图片时的回调函数
     */
    class MyCallBack implements Callback.CommonCallback {

        @Override
        public void onSuccess(Object result) {
            System.out.println("加载图片" + "onSuccess()");

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {
            System.out.println("加载图片" + "onFinished()");

        }
    }


}
