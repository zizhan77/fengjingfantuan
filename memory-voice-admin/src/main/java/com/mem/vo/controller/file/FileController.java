package com.mem.vo.controller.file;

import com.mem.vo.business.base.model.po.Reward;
import com.mem.vo.business.base.model.po.RewardQuery;
import com.mem.vo.business.base.model.vo.ExcelDataVO;
import com.mem.vo.business.base.service.RewardService;
import com.mem.vo.business.biz.service.token.TokenService;
import com.mem.vo.common.constant.PrizeEnum;
import com.mem.vo.common.dto.ResponseDto;
import com.mem.vo.common.exception.BizException;
import com.mem.vo.common.util.ExcelUtil;
import com.mem.vo.common.util.FileUtil;
import com.mem.vo.config.annotations.CommonExHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lvxiao
 * @version V1.0
 * @date 2019-06-17 23:49
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private FileUtil fileUtil;
    @Resource
    private RewardService rewardService;

    @RequestMapping("/uploadFile")
    public ResponseDto<String> uploadFile(@RequestHeader("token") String token, @RequestParam("fileName") MultipartFile file) {

        ResponseDto<String> responseDto = ResponseDto.successDto();
        if (file.isEmpty()) {
            return responseDto.failData("文件不能为空!");
        }
        if (StringUtils.isBlank(file.getOriginalFilename()) || file.getOriginalFilename().contains(" ")) {
            return responseDto.failData("文件名不能包含空格!");
        }
        try {
            return responseDto.successData(fileUtil.uploadMultipartFile(file, file.getOriginalFilename(), true));
        } catch (BizException e) {

            log.error("上传文件异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("上传文件异常", e);
            return responseDto.failSys();

        }
    }

    @PostMapping("/uploadToken")
    public ResponseDto<Map<String, String>> uploadToken(@RequestHeader("token") String token, @RequestParam("fileName") String fileName) {

        ResponseDto<Map<String, String>> responseDto = ResponseDto.successDto();
        if (fileName.isEmpty()) {
            return responseDto.failData("文件不能为空!");
        }
        if (fileName.contains(" ")) {
            return responseDto.failData("文件名不能包含空格!");
        }
        try {
            return responseDto.successData(fileUtil.getUploadToken(fileName, true));
        } catch (BizException e) {

            log.error("上传token失败，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("上传token失败", e);
            return responseDto.failSys();

        }
    }

    @PostMapping("/uploadFile")
    public ResponseDto<String> uploadFile(@RequestHeader("token") String token, @RequestParam("fileName") String fileName) {

        ResponseDto<String> responseDto = ResponseDto.successDto();
        if (fileName.isEmpty()) {
            return responseDto.failData("文件不能为空!");
        }
        if (fileName.contains(" ")) {
            return responseDto.failData("文件名不能包含空格!");
        }
        try {
            return responseDto.successData(fileUtil.getUploadFile(fileName));
        } catch (BizException e) {

            log.error("上传文件失败，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("上传文件失败", e);
            return responseDto.failSys();

        }
    }

    @PostMapping("/uploadBigFile")
    public ResponseDto<String> uploadBigFile(@RequestHeader("token") String token, @RequestParam("fileName") String fileName, @RequestParam("key") String key) {

        ResponseDto<String> responseDto = ResponseDto.successDto();
        if (fileName.isEmpty()) {
            return responseDto.failData("文件不能为空!");
        }
        if (fileName.contains(" ")) {
            return responseDto.failData("文件名不能包含空格!");
        }
        try {
            return responseDto.successData(fileUtil.getUploadBigFile(fileName, key));
        } catch (BizException e) {

            log.error("上传文件失败，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {

            log.error("上传文件失败", e);
            return responseDto.failSys();

        }
    }

    @RequestMapping("/downloadFile")
    public ResponseDto<String> downloadFile(String fileName) {
        ResponseDto<String> responseDto = ResponseDto.successDto();
        if (StringUtils.isBlank(fileName) || fileName.contains(" ")) {
            return responseDto.failData("文件名不能包含空格!");
        }
        try {
            return responseDto.successData(fileUtil.getFileUrl(fileName));
        } catch (BizException e) {

            log.error("下载文件异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("下载文件异常", e);
            return responseDto.failSys();

        }
    }

    @RequestMapping("/all")
    public ResponseDto<List<String>> getAllFile() {
        ResponseDto<List<String>> responseDto = ResponseDto.successDto();
        try {
            return responseDto.successData(fileUtil.findFiles());
        } catch (BizException e) {

            log.error("查询所有文件异常，原因：{}", e.getMessage());
            return responseDto.failData(e.getMessage());
        } catch (Exception e) {
            log.error("查询所有文件异常", e);
            return responseDto.failSys();

        }
    }

    @CommonExHandler(key = "导入excel")
    @PostMapping("/uploadExcel")
    public ResponseDto<Integer> uploadExcel(@RequestHeader("token") String token, @RequestParam("fileName") MultipartFile file, Long activityId) {
        ResponseDto<Integer> responseDto = ResponseDto.successDto();
        // 检查前台数据合法性
        if (null == file || file.isEmpty()) {
            log.error("上传的Excel商品数据文件为空！上传时间：" + new Date());
            throw new BizException("上传文件为空");
        }
        // 解析Excel
        List<ExcelDataVO> parsedResult = ExcelUtil.readExcel(file);
        RewardQuery query = new RewardQuery();
        query.setActivityId(Math.toIntExact(activityId));
        query.setPrizeType(PrizeEnum.TICKET.getCode());
        List<Integer> idList = rewardService.selectIdByEt(query);
        Reward reward = new Reward();
        BeanUtils.copyProperties(reward, query);
        int index = 0;
        if (CollectionUtils.isEmpty(idList) || CollectionUtils.isEmpty(parsedResult)) {
            return responseDto.successData(0);
        } else {
            for (Integer id : idList) {
                if (index == parsedResult.size()) {
                    return responseDto.successData(index - 1);
                }
                reward.setDrawCode(parsedResult.get(index).getBarcode());
                reward.setId(id);
                rewardService.updateById(reward);
            }
        }
        return responseDto.successData(Math.min(idList.size(), parsedResult.size()));
    }
}
