package com.example.imooc_appupdater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.example.imooc_appupdater.updater.AppUpdater;
import com.example.imooc_appupdater.updater.net.INetCallBack;
import com.tencent.bugly.crashreport.BuglyLog;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    volatile int A = 0;

    volatile int B = 0;

    //@lombok.SneakyThrows
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppUpdater.getInstance().getINetManager().get("http://www.yyj2857.cn", new INetCallBack() {
            @Override
            public void success(String response) {
                //1.解析json数据
                //2.做版本匹配（如需更新）
                //3.弹窗

                //4.点击下载
                Log.v("!", response);
            }

            @Override
            public void failed(Throwable throwable) {
                if (throwable instanceof SocketTimeoutException) {
                    //判断超时异常
                    Log.e("超时异常", String.valueOf(throwable));

                }
                if (throwable instanceof ConnectException) {
                    //判断连接异常
                    Log.e("连接异常", "1");
                }
                //  Log.e("1",throwable.getMessage());
            }
        });


        //获取APP ID并将以下代码复制到项目Application类onCreate()中，Bugly会为自动检测环境并完成配置：
        /**
         * 第三个参数为SDK调试模式开关，调试模式的行为特性如下：
         *
         * 输出详细的Bugly SDK的Log；
         * 每一条Crash都会被立即上报；
         * 自定义日志将会在Logcat中输出。
         * 建议在测试阶段建议设置成true，发布时设置为false。
         */
        // CrashReport.initCrashReport(getApplicationContext(), "f057b14c56", true);

//        //增加进程上报策略
//        Context context = getApplicationContext();
//        // 获取当前包名
//        String packageName = context.getPackageName();
//        // 获取当前进程名
//        String processName = getProcessName(android.os.Process.myPid());
//        // 设置是否为上报进程
//        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
//        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        // 初始化Bugly
//        CrashReport.initCrashReport(context, "f057b14c56", true, strategy);
//        // 如果通过“AndroidManifest.xml”来配置APP信息，初始化方法如下
//        // CrashReport.initCrashReport(context, strategy);
//
//        CrashReport.testJavaCrash();
//
//        CrashReport.setUserSceneTag(this, 9527); // 上报后的Crash会显示该标签
//        CrashReport.putUserData(this, "userkey", "uservalue");
//        int[] names={1,2};
//        try {
//            int io = names[4];
//        } catch (Exception e) {
//            e.printStackTrace();
//            CrashReport.postCatchedException(e);  // bugly会将这个throwable上报
//        }

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //线程池
//                ExecutorService executorService = Executors.newFixedThreadPool(10);
//                while (true) {
//
//                    executorService.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            synchronized (this) {
//                                ++B;
//                                CrashReport.setUserSceneTag(MainActivity.this, 9527);
//                                CrashReport.putUserData(MainActivity.this, "userkey", "uservalue");
//                                BuglyLog.e(String.valueOf(B),String.valueOf(A));
//                                //Log.e(String.valueOf(B), String.valueOf(A));
//                                --B;
//                            }
//                            try {
//                                Thread.sleep(10000);
//                            } catch (Exception ignored) {
//                            }
//                        }
//                    });
//                }
//            }
//        }).start();


    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    /**
     *  到这里，架构就搭的差不多了，可以看到，里面的实现功能我一个都还没写呢！
     *  但是，是不是有一种整个的流程都已经写的差不多的感觉了！！！！！！！！
     *
     *  当返回成功时，获取字符串，就算没有功能，业务逻辑也可以开始写了！！！
     *  这就是架构啊！！！
     *
     *  这样做，就可以让实现功能与写业务逻辑的两个进行并行操作
     *
     *  所以我们通过接口去屏蔽具体的实现的具体的好处
     *  1.方便我们未来替换时，具体的实现
     *  2.提供开发效率，并行开发
     *  3.屏蔽了接口，让用户无法去调用我们定义的接口
     *
     */
    //get请求

}
