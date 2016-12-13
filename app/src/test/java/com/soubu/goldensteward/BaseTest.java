package com.soubu.goldensteward;

import android.content.Context;

import com.soubu.goldensteward.support.constant.BaseConfig;
import com.soubu.goldensteward.support.utils.ShowWidgetUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowLog;

/**
 * 作者：余天然 on 2016/12/13 上午11:33
 */
@RunWith(RobolectricTestRunner.class)
public class BaseTest {

    Context context;

    @Before
    public void setUp() {
        ShadowLog.stream = System.out;
        BaseConfig.IS_TEST = true;
        context = RuntimeEnvironment.application;
        ShowWidgetUtil.register(context);
    }

    @Test
    public void test() {

    }
}
