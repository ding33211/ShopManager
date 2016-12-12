package com.soubu.goldensteward.support.helper;

import android.content.Context;
import android.text.TextUtils;

import com.soubu.goldensteward.support.bean.server.UserServerParams;
import com.soubu.goldensteward.support.constant.SpKey;
import com.soubu.goldensteward.support.greendao.DBHelper;
import com.soubu.goldensteward.support.greendao.User;
import com.soubu.goldensteward.support.greendao.UserDao;
import com.soubu.goldensteward.support.utils.LogUtil;
import com.soubu.goldensteward.support.utils.SPUtil;

import java.util.List;

/**
 * 作者：余天然 on 2016/12/12 下午2:32
 */
public class UserManager {

    private static Context context;
    private static UserDao dao;
    private static User user;

    public static void init(Context _context) {
        context = _context;
    }

    public static boolean saveUserInfo(UserServerParams params) {
        LogUtil.print("");
        try {
            if (dao == null) {
                dao = DBHelper.getInstance(context).getUserDao();
            }
            List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(params.getPhone())).list();
            user = new User();
            if (list.size() > 0) {
                user = list.get(0);
            }
            user.setName(params.getName());
            user.setAddress(params.getAddress());
            user.setCity(params.getCity());
            user.setCity_id(params.getCity_id());
            user.setProvince(params.getProvince());
            user.setProvince_id(params.getProvince_id());
            user.setCompany(params.getCompany());
            user.setCompany_profile(params.getCompany_profile());
            user.setCompany_size(params.getCompany_size());
            user.setContact_name(params.getContact_name());
            user.setFixed_telephone(params.getFixed_telephone());
            user.setJob(params.getJob());
            user.setMail(params.getMail());
            user.setMain_industry(params.getMain_industry());
            user.setMain_product(params.getMain_product());
            user.setOperation_mode(params.getOperation_mode());
            user.setPhone(params.getPhone());
            if (!TextUtils.isEmpty(params.getPortrait())) {
                user.setPortrait(params.getPortrait());
            }
            user.setTurnover(params.getTurnover());
            if (list.size() > 0) {
                dao.update(user);
            } else {
                dao.insert(user);
            }
            SPUtil.putValue(SpKey.USER_PHONE, params.getPhone());
            return true;
        } catch (Exception e) {
            LogUtil.printException(e);
            return false;
        }
    }

    public static void clearUser() {
        if (dao != null && user != null) {
            dao.delete(user);
        }
    }

    public static boolean initUser() {
        dao = DBHelper.getInstance(context).getUserDao();
        String phone = SPUtil.getValue(SpKey.USER_PHONE, "");
        if (!TextUtils.isEmpty(phone)) {
            List<User> list = dao.queryBuilder().where(UserDao.Properties.Phone.eq(phone)).list();
            if (list.size() > 0) {
                user = list.get(0);
                return true;
            }
        }
        return false;
    }
}
