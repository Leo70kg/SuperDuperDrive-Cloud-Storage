package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    List<Note> findByUserId(Integer userId);

    @Insert("INSERT INTO NOTES (noteid, notetitle, notedescription, userid) VALUES (#{noteId}, #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid=#{noteId}")
    int update(Integer noteId, String noteTitle, String noteDescription);

    @Delete("DELETE FROM NOTES WHERE noteid = #{fileId}")
    int delete(Integer fileId);

}
