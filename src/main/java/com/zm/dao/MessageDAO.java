package com.zm.dao;

import com.zm.model.Comment;
import com.zm.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Ellen on 2017/5/22.
 */
@Mapper
public interface MessageDAO {

    String TABLE_NAME = " message ";
    String UPDATEFIELDS = " from_id, to_id, created_date, has_read, conversation_id, content ";
    String SELECTFIELD = " id, " + UPDATEFIELDS;

    @Insert({"insert into", TABLE_NAME, " (", UPDATEFIELDS,
            ") values (#{fromId},#{toId},#{createdDate},#{hasRead},#{conversationId},#{content})"})
    int addMessage(Message message);

    @Select({"select ", SELECTFIELD, "from ", TABLE_NAME, "where from_id = #{fromId} or to_id = #{fromId} group by conversation_id order by created_date desc"})
    List<Message> getMessagetByFromId(@Param("fromId") int fromId);

    @Select({"select count(id) from ", TABLE_NAME, "where entity_id = #{entityId} and entityType = #{entityType} order by created_date desc"})
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

    /**
     * select *, count(id) as id from ( select * from message where from_id = 30
     * or to_id = 30 order by id desc) tt group by conversation_id order by created_date desc limit 0,10
     */
    @Select({"select", UPDATEFIELDS, " ,count(*) as id from ( select", UPDATEFIELDS, " from ", TABLE_NAME, " where from_id = #{userId} ",
            " or to_id = #{userId} order by id desc) tt group by conversation_id order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationList(@Param("userId") int userId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    @Select({"select count(*) from ", TABLE_NAME, "where has_read = 0 and conversation_id = #{conversationId}"})
    int getConversationUnreadCount(@Param("userId") int userId,
                                   @Param("conversationId") String conversationId);

    @Select({"select ", SELECTFIELD, " from ", TABLE_NAME, "where conversation_id = #{conversationId} order by created_date desc limit #{offset},#{limit}"})
    List<Message> getConversationDetail(@Param("conversationId") String conversationId,
                                        @Param("offset") int offset,
                                        @Param("limit") int limit);

    @Update({"update ", TABLE_NAME, " set has_read = 1 where conversation_id = #{conversationId}"})
    void updateCoversationStatus(String conversationId);
}
