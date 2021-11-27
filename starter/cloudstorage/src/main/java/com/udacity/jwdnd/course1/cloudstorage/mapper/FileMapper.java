package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    File[] readAll(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File read(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int create(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileid}")
    void delete(Integer fileId);
}
