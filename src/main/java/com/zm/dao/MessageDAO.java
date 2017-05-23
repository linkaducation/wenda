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

//    @Select({"select ", SELECTFIELD, "from ", TABLE_NAME, "where entity_id = #{entityId} " +
//            "and entity_type = #{entityType} order by create_date desc"})
//    List<Comment> getCommentByEntity(@Param("entityId") int entityId,
//                                     @Param("entityType") int entityType);
//
//    @Select({"select count(id) from ", TABLE_NAME, "where entity_id = #{entityId} and entityType = #{entityType} order by create_date desc"})
//    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);

}
