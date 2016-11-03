package com.soubu.goldensteward.base.greendao;

import android.content.Context;

import org.greenrobot.greendao.async.AsyncSession;

/**
 * Created by dingsigang on 16-8-2.
 */
public class DBHelper {

    private static DBHelper INSTANCE = null;

    /**
     * not thread-safe
     */
    public static DBHelper getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new DBHelper(context);
        return INSTANCE;
    }

    private static final String DB_NAME = "golden.db";
    private DaoSession daoSession;
    private AsyncSession asyncSession;

    private DBHelper(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        daoSession = daoMaster.newSession();
        asyncSession = daoSession.startAsyncSession();
    }

    public AddressDao getAddressDao() {
        return daoSession.getAddressDao();
    }

    public AsyncSession getAsyncSession() {
        return asyncSession;
    }
}
