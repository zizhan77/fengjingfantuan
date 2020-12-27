package com.mem.vo.controller.picture;

import java.io.File;
import java.io.IOException;

import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author litongwei
 * @description:
 * @date 2019/6/1 14:58
 */

@Controller
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Value("${picture.root.path}")
    private String rootPath;


    @RequestMapping("/uploadFile")
    public ResponseDto<String> uploadFile(
        @RequestParam("fileName") MultipartFile file) {

        ResponseDto<String> responseDto = ResponseDto.successDto();

        try {
            String path =  uploadPicture(file);
            return responseDto.successData(path);

        } catch (BizException e) {

            log.error("上传图片异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("上传图片异常", e);
            return responseDto.failSys();

        }
    }

    private String uploadPicture(@RequestParam("fileName") MultipartFile file) {
        log.info("上传文件ing");
        //判断文件是否为空
        if (file.isEmpty()) {
            return "上传文件不可为空";
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        //加个时间戳，尽量避免文件名称重复
        fileName = System.currentTimeMillis()+"-"+fileName;

        String path = rootPath+fileName;

        //文件绝对路径
        log.info("保存文件绝对路径:{}",path);

        //创建文件路径
        File dest = new File(path);

        //判断文件是否已经存在
        if (dest.exists()) {
            return "文件已经存在";
        }

        //判断文件父目录是否存在
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        try {
            //上传文件
            file.transferTo(dest); //保存文件
            log.info("上传文件成功,路径：{}",path);
        } catch (IOException e) {
            return "上传失败";
        }

        return path;
    }

}
