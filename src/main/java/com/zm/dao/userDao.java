package com.zm.dao;

import com.zm.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by Ellen on 2017/5/19.
 */
@Mapper
public interface userDao {
    String TABLE_NAME = " User ";
    String INSERT_FIELDS = " id,name,password,salt,head_url ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(name,password,salt,head_url ) values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where id=#{id}",})
    User selectById(int id);

    @Select({"select", SELECT_FIELDS, "from", TABLE_NAME, "where name=#{name}",})
    User selectByName(String name);

    @Update({"update", TABLE_NAME, "set password=#{password} where id=#{id}"})
    int updatePassword(User user);

    @Delete({"delete from ", TABLE_NAME, "where id=#{id}"})
    int deleteById(int id);
}
