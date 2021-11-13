package com.example.demo.controller;

import com.example.demo.entity.FileInfo;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("upload")
    public String uploadFile(MultipartFile file, HttpServletRequest request) {
        if (file == null || file.isEmpty()) {
            return "文件为空";
        }
        // 获取文件名
        String originalFilename = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = "";
        if (originalFilename != null) {
            suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uuid = UUID.randomUUID().toString();
        String filename = uuid + suffixName;

        // 准备文件夹,获取项目中upload文件夹的路径
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String format = dateFormat.format(date);

        String parentDir = request.getServletContext().getRealPath(format);
        File parent = new File(parentDir);
        if (!parent.exists() && !parent.mkdirs()) {
            throw new RuntimeException("创建文件夹出现问题");
        }
        FileInfo fileInfo = new FileInfo(null, uuid, filename, file.getSize(), file.getContentType(),
                originalFilename, parentDir, date);
        Boolean flag = fileService.saveFile(fileInfo);
        if (!flag) {
            throw new RuntimeException("保存到数据库失败");
        }
        // 执行保存文件
        File dest = new File(parent, filename);
        try {
            file.transferTo(dest);
            return uuid;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传失败";
    }

    @GetMapping("file_info")
    public List<FileInfo> getFileInfo(String uuid) {
        return fileService.queryFIleInfo(uuid);
    }

    @GetMapping("download")
    public void download(String uuid, HttpServletResponse response) {
        String filePath = fileService.queryFilePath(uuid);
        File file = new File(filePath);
        if ("".equals(filePath) || !file.exists()) {
            throw new RuntimeException("uuid错误或则文件不存在");
        }
        response.setContentType("application/force-download");// 设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());
        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis)) {
            OutputStream outputStream = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                outputStream.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
