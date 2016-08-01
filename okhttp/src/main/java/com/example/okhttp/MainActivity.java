package com.example.okhttp;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.textView)
    TextView textView;

    @Bind(R.id.button2)
    Button button2;

    @Bind(R.id.button3)
    Button button3;
    public static final String SERVERURL = "http://172.28.21.68:8080/WebInfos/app/homelist0";
    public static final String imageUrl = "http://avatar.csdn.net/8/B/B/1_sinyu890807.jpg";


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Bundle data = msg.getData();
                String result = data.getString("result");
                textView.setText(result);
            }
        }
    };
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.button4)
    Button button4;
    @Bind(R.id.button5)
    Button button5;
    @Bind(R.id.iv_netWorkImage)
    NetworkImageView ivNetWorkImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useNetworkImageView();
            }
        });


        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImage();
            }
        });


        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uesVolley();
            }
        });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                useAsyncTask();
            }


        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Request request = new Request.Builder().url(SERVERURL).build();
                        try {
                            Response response = okHttpClient.newCall(request).execute();
                            String result = response.body().string();
                            Message msg = new Message();
                            msg.what = 0;
                            Bundle bundle = new Bundle();
                            bundle.putString("result", result);
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }

    private void useNetworkImageView() {

        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        /*，ImageLoader的构造函数接收两个参数，第一个参数就是RequestQueue对象，第二个参数是一个ImageCache对象*/
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        ivNetWorkImage.setDefaultImageResId(R.mipmap.ic_download);
        ivNetWorkImage.setErrorImageResId(R.mipmap.ic_launcher);
        ivNetWorkImage.setImageUrl(imageUrl, imageLoader);

    }

    private void loadImage() {

        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                new com.android.volley.Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        image.setImageBitmap(response);
                    }
                },
                0, 0, Bitmap.Config.RGB_565,
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

        );
//        mQueue.add(imageRequest);

/*，ImageLoader的构造函数接收两个参数，第一个参数就是RequestQueue对象，第二个参数是一个ImageCache对象*/
        ImageLoader imageLoader = new ImageLoader(mQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
        /*我们通过调用ImageLoader的getImageListener()方法能够获取到一个ImageListener对象，
        getImageListener()方法接收三个参数，
        第一个参数指定用于显示图片的ImageView控件，
        第二个参数指定加载图片的过程中显示的图片，
        第三个参数指定加载图片失败的情况下显示的图片*/
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(image,
                R.mipmap.ic_launcher,
                R.mipmap.ic_download);
/*get()方法接收两个参数，第一个参数就是图片的URL地址，第二个参数则是刚刚获取到的ImageListener对象。
当然，如果你想对图片的大小进行限制，也可以使用get()方法的重载，指定图片允许的最大宽度和高度*/
        imageLoader.get(imageUrl, imageListener, 200, 200);
    }

    private void uesVolley() {
    /*1. 创建一个RequestQueue对象。
    2. 创建一个StringRequest对象。
    3. 将StringRequest对象添加到RequestQueue里面。
    */
        /**
         * 注意这里拿到的RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，
         * 然后按照一定的算法并发地发出这些请求。RequestQueue内部的设计就是非常合适高并发的，
         * 因此我们不必为每一次HTTP请求都创建一个RequestQueue对象，这是非常浪费资源的，
         * 基本上在每一个需要和网络交互的Activity中创建一个RequestQueue对象就足够了。
         */
        RequestQueue mQueue = Volley.newRequestQueue(MainActivity.this);

/**
 * 这里new出了一个StringRequest对象，StringRequest的构造函数需要传入三个参数，
 * 第一个参数就是目标服务器的URL地址
 * 第二个参数是服务器响应成功的回调，
 * 第三个参数是服务器响应失败的回调
 */
        StringRequest stringRequest = new StringRequest("http://www.baidu.com", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                textView.setText(response);
                Log.d("TAG", response);

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //最后，将这个StringRequest对象添加到RequestQueue里面就可以了
//        mQueue.add(stringRequest);

        /*  JsonRequest的用法*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(SERVERURL, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textView.setText(response.toString());
                        Log.e("JsonObject", response.toString());

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mQueue.add(jsonObjectRequest);


    }

    //在主线程调用该方法
    private void useAsyncTask() {
        /*主线程调用AsynTask子类实例的execute()方法后，首先会调用onPreExecute()方法。
        onPreExecute()在主线程中运行，可以用来写一些开始提示代码。*/
        DownLoader downLoader = new DownLoader(textView);
        /*execute（）向doInBackground（）传递。*/
        downLoader.execute(SERVERURL);
    }

    class DownLoader extends AsyncTask<String, Integer, String> {
        TextView text;

        public DownLoader(TextView text) {
            this.text = text;
        }

        /*处理完毕之后异步线程结束，在主线程中调用onPostExecute()方法。onPostExecute()可以进行一些结束提示处理*/
        @Override
        protected String doInBackground(String... params) {

            String url = params[0];
            String result = null;
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = null;
            try {
                response = okHttpClient.newCall(request).execute();
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*//publishProgress()为AsyncTask类中的方法
            //常在doInBackground()中调用此方法
            //用于通知主线程,后台任务的执行情况.
            //此时会触发AsyncTask中的onProgressUpdate()方法*/

/*　doInBackground()的返回值会传递给onPostExecute()。*/
            return result;
        }

        @Override
        protected void onCancelled(String s) {
        }

        /*onProgressUpdate()方法用于更新异步执行中,在主线程中处理异步任务的执行信息  参数为publishProgress() 方法的参数*/
        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        /*处理完毕之后异步线程结束，在主线程中调用onPostExecute()方法。onPostExecute()可以进行一些结束提示处理*/
        @Override
        protected void onPostExecute(String s) {
            text.setText(s);


        }

        /*之后启动新线程，调用doInBackground()方法，进行异步数据处理。*/
        @Override
        protected void onPreExecute() {
            System.out.println("开始执行异步任务.....");
        }

        /*onCancelled()方法用于异步任务被取消时,在主线程中执行相关的操作*/
        @Override
        protected void onCancelled() {
        }
    }


}
