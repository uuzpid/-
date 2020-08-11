package com.shangcheng.controller;

import com.shangcheng.file.FastDFSFile;
import com.shangcheng.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/upload")
@CrossOrigin
public class FileUploadController {
    /**
     * 文件上传
     */
    @PostMapping
    public Result upload(@RequestParam(value = "file")MultipartFile file) throws Exception {
        //封装文件信息
        FastDFSFile fastDFSFile =
                new FastDFSFile(file.getOriginalFilename(),//文件名字
                                file.getBytes(),//文件字节数组
                                StringUtils.getFilenameExtension(file.getOriginalFilename()));//获取文件扩展名

        //调用FastDFSUtil工具类将文件传入到FastDFS中
        String[] uploads = FastDFSUtil.upload(fastDFSFile);

        //8080是Nginx的端口
        //拼接一个访问地址 url=http:/192.168.88.131:8080/group1/M00/00/00/xxx.xxx
        String url = "http:/192.168.88.131:8080/"+uploads[0]+"/"+uploads[1];

        return new Result(true, StatusCode.OK,"上传成功",url);
    }
}
