package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.UserFile;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserFileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<UserFile> findByUserId(Integer userId);

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata) VALUES (#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(UserFile file);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    List<UserFile> findByFileName(String fileName, Integer userId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    int delete(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    UserFile findByFileId(Integer fileId);
}
