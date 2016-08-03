package applecation;

import android.app.Application;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * 作者: 小光哥 on 2016/8/3.
 * 邮箱: 1445037803@qq.com
 * 修订历史:
 * 描述:
 * 　　　　　　　　┏┓　　　┏┓
 * 　　　　　　　┏┛┻━━━┛┻┓━━━━┻┓
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃
 * 　　　　　　　┃　＞　　　＜　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃...　⌒　...　┃
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　史　┃   神兽保佑,代码无bug
 * 　　　　　　　　　┃　诗　┃
 * 　　　　　　　　　┃　之　┃
 * 　　　　　　　　　┃　宠　┃
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┗━━━┓
 * 　　　　　　　　　┃BUG天敌　　　┣┓┣┓┣┓┣┓┣┓
 * 　　　　　　　　　┃　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛
 */

/**
 * 全局配置 ImageLoaderConfiguration
 * 先要配置ImageLoaderConfiguration这个类实现全局ImageLoader的实现情况。
 */
public class MyApplecation extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config;
        config = new ImageLoaderConfiguration
                .Builder(MyApplecation.this)
                .memoryCacheExtraOptions(480,800)// max width, max height，即保存的每个缓存文件的最大长宽
//                .diskCacheExtraOptions()
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY-2)
                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))//你可以通过自己的内存缓存实现
                .memoryCacheSize(2*1024*1024)//内存缓存大小
                .diskCacheSize(50*1024*1024)//磁盘缓存大小
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.FIFO)//设置线程池队列任务,为FIFO(先进先出)
                .diskCacheFileCount(100)//设置磁盘缓存的文件最大数量
//                .diskCache(new UnlimitedDiskCache(cacheDir))// 自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())//显示的图片的各种格式DisplayImageOptions 的设置
                .imageDownloader(new BaseImageDownloader(MyApplecation.this,5*1000,30*1000))
                .writeDebugLogs()// Remove for release app
                .build();//构建完成

        //配置好ImageLoaderConfiguration后，调用以下方法来实现初始化：
        ImageLoader.getInstance().init(config);
        class BaseImageDowner implements ImageDownloader {

            @Override
            public InputStream getStream(String imageUri, Object extra) throws IOException {
                return null;
            }
        }
    }
}
