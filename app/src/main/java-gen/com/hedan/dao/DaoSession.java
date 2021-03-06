package com.hedan.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.hedan.dao.BlackNumber;

import com.hedan.dao.BlackNumberDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig blackNumberDaoConfig;

    private final BlackNumberDao blackNumberDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        blackNumberDaoConfig = daoConfigMap.get(BlackNumberDao.class).clone();
        blackNumberDaoConfig.initIdentityScope(type);

        blackNumberDao = new BlackNumberDao(blackNumberDaoConfig, this);

        registerDao(BlackNumber.class, blackNumberDao);
    }
    
    public void clear() {
        blackNumberDaoConfig.getIdentityScope().clear();
    }

    public BlackNumberDao getBlackNumberDao() {
        return blackNumberDao;
    }

}
