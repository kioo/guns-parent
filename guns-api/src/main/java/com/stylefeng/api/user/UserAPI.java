package com.stylefeng.api.user;

import com.stylefeng.api.user.vo.UserInfoModel;
import com.stylefeng.api.user.vo.UserModel;

public interface UserAPI {

    int login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int uuid);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);
}
