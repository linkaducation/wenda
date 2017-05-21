package com.zm.dao;

import com.zm.model.LoginTicket;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by Ellen on 2017/5/20.
 */
@Mapper
public interface ticketLoginDAO {
    String TABLE = " login_ticket ";
    String INSERT_FIELDS = " user_id,ticket,expired,status ";
    String SELECT_FIELDS = " id " + INSERT_FIELDS;

    @Insert({"insert into",TABLE,"(",INSERT_FIELDS,") values (#{userId},#{ticket},#{expired},#{status})"})
    void addTicket(LoginTicket loginTicket);

    @Select({"select",SELECT_FIELDS,"from",TABLE,"where ticket=#{ticket}"})
    LoginTicket getTicket(@Param("ticket") String ticket);
}
