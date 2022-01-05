package com.example.demo.service;

import com.example.demo.entity.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class FileService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean saveFile(FileInfo fileInfo) {
        System.out.println("ceshi");
        String sql = "insert into file_info values(null, ?, ?, ?, ?, ?, ?, ?)";
        int update = jdbcTemplate.update(sql, fileInfo.getUuid(), fileInfo.getFileName(), fileInfo.getFileSize()
                , fileInfo.getFileType(), fileInfo.getOriginalName(), fileInfo.getFilePath(), fileInfo.getCreateTime());
        return update == 1;
    }

    public List<FileInfo> queryFIleInfo(String uuid) {
        String sql = "select * from file_info where uuid = ?";
        List<FileInfo> query = jdbcTemplate.query(sql, new RowMapper<FileInfo>() {
            @Override
            public FileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new FileInfo(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getLong(4),
                        rs.getString(5), rs.getString(6)
                        , rs.getString(7), rs.getDate(8));
            }
        }, uuid);
        return query;
    }

    public String queryFilePath(String uuid) {
        List<FileInfo> fileInfos = queryFIleInfo(uuid);
        if (fileInfos.size() != 1) {
            return "";
        }
        FileInfo fileInfo = fileInfos.get(0);
        return fileInfo.getFilePath() + File.separator + fileInfo.getFileName();
    }
}
