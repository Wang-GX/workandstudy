package com.example.demo.mapper;

import com.example.demo.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    User getUser();

    void insertUser(@Param("user")User user);
}
