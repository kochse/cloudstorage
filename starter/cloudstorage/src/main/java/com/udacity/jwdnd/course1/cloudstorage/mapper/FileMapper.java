package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    File[] readAll(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File read(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int create(File file);

    @Select("UPDATE FILES SET (filename = #{fileName}, contenttype = #{contentType}, filesize = #{fileSize}, filedata = #{fileData}) WHERE fileid = #{fileid}")
    int update(File file);

    @Select("DELETE FROM FILES WHERE fileid = #{fileid}")
    int delete(Integer fileId);
}