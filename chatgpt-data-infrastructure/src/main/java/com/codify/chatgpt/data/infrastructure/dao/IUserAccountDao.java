package com.codify.chatgpt.data.infrastructure.dao;

import com.codify.chatgpt.data.infrastructure.po.UserAccountPO;
import org.apache.ibatis.annotations.Mapper;

/**
 *用户账户DAO
 */
@Mapper
public interface IUserAccountDao {
    int subAccountQuota(String openid);
    UserAccountPO queryUserAccount(String openid);

    int addAccountQuota(UserAccountPO userAccountPOReq);

    void insert(UserAccountPO userAccountPOReq);
}
