package com.cqcqya.application.mapper;

import com.cqcqya.application.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    public User selectUserByMail(String mail);
    public User selectUserByTel(String tel);
    public int createUser(@Param("mail")String mail,@Param("tel")String tel,@Param("password")String password);

}
