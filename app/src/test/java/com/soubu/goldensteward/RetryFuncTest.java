package com.soubu.goldensteward;

import android.text.TextUtils;

import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.ui.splash.RetryFunc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.shadows.ShadowLog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.plugins.RxJavaPlugins;
import rx.plugins.RxJavaSchedulersHook;
import rx.schedulers.Schedulers;

/**
 * RxJava重试机制
 * <p>
 * 作者：余天然 on 2016/12/13 下午2:19
 */
@RunWith(RobolectricTestRunner.class)
public class RetryFuncTest {

    //实际请求的次数
    int index;

    //同步锁
    CountDownLatch latch = new CountDownLatch(1);

    //接收器
    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            LogUtil.print("");
            latch.countDown();
        }

        @Override
        public void onError(Throwable e) {
            LogUtil.print("无法获取数据");
            latch.countDown();
        }

        @Override
        public void onNext(String s) {
            LogUtil.print(s);
        }
    };

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
    }

    /**
     * 直接使用Create创建发射器
     */
    @Test
    public void create() throws InterruptedException {
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String data = getData();
                if (!TextUtils.isEmpty(data)) {
                    subscriber.onNext(data);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("数据为空"));
                }
            }
        });

        observable
                .retryWhen(new RetryFunc(3))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(subscriber);
        latch.await();
    }

    /**
     * 使用just+map创建发射器
     */
    @Test
    public void just() throws InterruptedException {
        Thread thread = Thread.currentThread();
        ExecutorService customThreadExecutor = new ThreadPoolExecutor(2, 20, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

        Observable.just(1)
                .map(var -> getData())
                .retryWhen(new RetryFunc(3))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.immediate())
                .subscribe(subscriber);

        latch.await();
    }

    /**
     * 使用just+map+flatMap创建发射器
     */
    @Test
    public void flatmap() throws InterruptedException {
        // 让Schedulers.io()返回当前线程
        RxJavaPlugins.getInstance().registerSchedulersHook(new RxJavaSchedulersHook() {
            @Override
            public Scheduler getNewThreadScheduler() {
                return Schedulers.immediate();
            }
        });

        Observable.just(1)
                .map(var -> getData())
                .flatMap(data -> checkData(data))
                .retryWhen(new RetryFunc(3))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(subscriber);
        latch.await();
    }

    /**
     * 检查数据
     */
    private Observable<String> checkData(String data) {
        if (!TextUtils.isEmpty(data)) {
            return Observable.just(data);
        }
        return Observable.error(new Throwable("数据为空"));
    }

    /**
     * 获取数据
     */
    private String getData() {
        index++;
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LogUtil.print("第" + index + "次获取数据");
        //假设只有在第三次请求时才能获取数据
        if (index == 3) {
            return "我是数据";
        }
        return null;
    }

    @Test
    public void test() {
        Observable.just("你好")
                .map(String::length)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(var -> {
                    LogUtil.print("var=" + var);
                    LogUtil.printStack();
                });
    }
}
