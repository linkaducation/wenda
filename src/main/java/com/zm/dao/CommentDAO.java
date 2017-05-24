package com.zm.dao;

import ch.qos.logback.classic.db.names.TableName;
import com.zm.model.Comment;
import com.zm.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Ellen on 2017/5/22.
 */
@Mapper
public interface CommentDAO {
    String TABLE_NAME = " comment ";
    String UPDATEFIELDS = " content,user_id,create_date,entity_id,entity_type,status ";
    String SELECTFIELD = " id, " + UPDATEFIELDS;

    @Insert({"insert into", TABLE_NAME, " (", UPDATEFIELDS,
            ") values (#{content},#{userId},#{createDate},#{entityId},#{entityType},#{status})"})
    int addComment(Comment comment);

    @Select({"select ", SELECTFIELD, "from ", TABLE_NAME, "where entity_id = #{entityId} " +
            "and entity_type = #{entityType} order by create_date desc"})
    List<Comment> getCommentByEntity(@Param("entityId") int entityId,
                                     @Param("entityType") int entityType);

    @Select({"select count(id) from ", TABLE_NAME, "where entity_id = #{entityId} and entityType = #{entityType} order by create_date desc"})
    int getCommentCount(@Param("entityId") int entityId,@Param("entityType") int entityType);

    @Update({"update ", TABLE_NAME, "set status = #{status} where user_id = #{userId}"})
    int updateComment(Comment comment);

    @Select({"select ", SELECTFIELD, "from ", TABLE_NAME, "where id = #{id}"})
    Comment getCommentById(int id);
}
