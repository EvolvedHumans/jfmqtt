package com.example.graphicsloading;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class MainActivity extends Activity {

    private final static String TAG = "com.example.graphicsloading";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView = findViewById(R.id.image);
        String url = "https://ftp.bmp.ovh/imgs/2021/02/8fdbbfe606d22575.png";
//        ImageLoader.getInstance().loadImage(url, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                Log.d(imageUri,"开始加载");
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                Log.d(imageUri,"开始失败");
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                Log.d(imageUri,"开始完毕");
//                imageView.setImageBitmap(loadedImage);
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//                Log.d(imageUri,"取消加载");
//            }
//        });
//        如果觉得传入ImageLoaderListener太复杂了，我们可以使用SimpleImageLoadingListener类
//        该类提供了ImageLoaderListener接口方法的空实现，使用的是缺省适配器模式
//        异步实现方法
//        ImageLoader.getInstance().loadImage(url,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                imageView.setImageBitmap(loadedImage);
//            }
//        });

        //如果我们要指定图片的大小该怎么办？
        //初始化一个ImageSize对象，指定图片的宽和高。
//        ImageSize imageSize = new ImageSize(100000,100000);
//        ImageLoader.getInstance().loadImage(url,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                imageView.setImageBitmap(loadedImage);
//            }
//        });

        //上面只是很简单的使用ImageLoader来加载网络图片，在实际的开发中，我们并不会这么使用，
        //那么我们平常会怎么使用呢？我们会用到DisplayImageOptions，他可以配置一些图片显示的选项，
        //比如图片在加载中ImageView显示的图片，是否需要使用内存缓存。是否需要使用文件缓存等等

        //修改上面的代码设置如下：
        //显示图片的配置
//        DisplayImageOptions options = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)  //缓存内存
//                .cacheOnDisk(true)   //缓存磁盘
//                .bitmapConfig(Bitmap.Config.RGB_565) //图片格式RGB_565
//                .build();

        //我们使用DisplayImageOptions来配置显示图片的一些选项，这里我添加了将图片缓存到内存已经缓存图片到文件系统中，
        //这样就不用担心每次都从网络中去加载图片了，这样就方便了很多，但是DisplayImageOptions选项中有些选项对于loadImage()
        //方法是无效的
//        ImageLoader.getInstance().loadImage(url,options,new SimpleImageLoadingListener(){
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                super.onLoadingComplete(imageUri, view, loadedImage);
//                imageView.setImageBitmap(loadedImage);
//            }
//        });

        //displayImage()加载图片
        //接下来我们就来看看网路图片加载的另一个方法displayImage()
        //显示图片的配置
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        //这个方法使用起来比较方便，而且使用displayImage()方法，他会根据控件的大小和ImageScaleType来自动剪裁
        //图片，
        ImageLoader.getInstance().displayImage(url,imageView,options);

        //我们在加载网路图片的时候，经常有需要显示图片下载进度，Universal-Image-Loader提供这样的功能，
        //只需要在displayIm()方法中传入ImageLoadingProgressListener接口就可以了。
        //由于displayImage()方法中待ImageLoadingProgressListener参数的方法都有待ImageLoadingListener参数，
        //所以我这里直接new一个SimpleImageLoadingListener，然后我们就可以在回调方法onPregressUpdate()得到图片的加载进度。
//        ImageLoader.getInstance().displayImage(url, imageView, options, new SimpleImageLoadingListener(), new ImageLoadingProgressListener() {
//            @Override
//            public void onProgressUpdate(String imageUri, View view, int current, int total) {
//                Log.d(String.valueOf(total), String.valueOf(current));
//            }
//        });


        //Universal-Image-Loader框架不仅可以加载网络图片，还可以加载sd卡中的图片，Content provider等，
        // 使用也很简单，只是将图片的url稍加的改变下就行了，下面就加载文件系统的图片
        //显示图片的配置
        //加载文件系统中的图片
//        final ImageView mImageView = (ImageView) findViewById(R.id.image);
//        String imagePath = "/mnt/sdcard/image.png";
//        String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);
//        ImageLoader.getInstance().displayImage(imageUrl, mImageView, options);

        //当然还有来源于Content provider，drawable，assets中，使用的时候也很简单，我们只需要给每个图片来源的
        //地方加上Scheme包裹起来，然后当做图片的url传递到ImageLoader中能够，Universal-Image-Loader框架会根据不同的Scheme获取到输入流

//        //图片来源于Content provider
//        String contentprividerUrl = "content://media/external/audio/albumart/13";
//
//        //图片来源于assets
//        String assetsUrl = ImageDownloader.Scheme.ASSETS.wrap("image.png");
//
//        //图片来源于
//        String drawableUrl = ImageDownloader.Scheme.DRAWABLE.wrap("R.drawable.image");

        //GridView,ListView加载图片
        //相信大部分人都使用GridView，ListView来显示大量的图片，而当我们快速滑动GridView，ListView，我们系统
        //能停止图片的加载，而在GridView，ListView停止滑动的时候加载当前界面的图片，
        //这个框架当然也提供这个功能，使用起来也很简单，它提供了PauseOnScroll这个类来控制ListView，GridView
        //滑动过程中停止去加载图片，该类使用的是代理模式
        //第一个参数就是我们的图片加载对象ImageLoader, 第二个是控制是否在滑动过程中暂停加载图片，
        // 如果需要暂停传true就行了，第三个参数控制猛的滑动界面的时候图片是否加载
        //listView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
        //gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));
    }
}
