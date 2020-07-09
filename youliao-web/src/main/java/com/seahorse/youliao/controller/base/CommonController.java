package com.seahorse.youliao.controller.base;

import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description: 公共控制器
 * @author: Mr.Song
 * @create: 2020-02-01 19:59
 **/
@Api(value = "CommonController", tags = "公共控制器")
@RestController
@RequestMapping("/sys/common")
public class CommonController {

    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.attach}")
    private String attachPath;

    /**
     * @功能：pdf预览Iframe
     * @return
     */
    @ApiOperation(value = "扩展pdf预览展示")
    @GetMapping("/pdf/pdfPreviewIframe")
    public ModelAndView pdfPreviewIframe() {

        ModelAndView modelAndView = new ModelAndView("/pdfPreviewIframe");
        modelAndView.addObject("file", "http://storage.xuetangx.com/public_assets/xuetangx/PDF/PlayerAPI_v1.0.6.pdf");
        return modelAndView;
    }


    /**
     * html2canvas 截图测试
     * @return
     */
    @ApiOperation(value = "html2canvas")
    @GetMapping(value = "html2canvas")
    public ModelAndView html2canvas(){

        ModelAndView modelAndView = new ModelAndView("/html2canvas");
        return modelAndView;
    }


    /**
     * 上传图片返回路径
     *
     * @return
     */
    @ApiOperation(value = "上传图片资源")
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file){

        ResponseEntity<Object> response = new ResponseEntity();
        if (null == file) {
            throw new BusinessException("上传失败，无法找到文件！");
        }
        // BMP、JPG、JPEG、PNG、GIF
        String fileName = file.getOriginalFilename().toLowerCase();
        if (!fileName.endsWith(".bmp") && !fileName.endsWith(".jpg")
                && !fileName.endsWith(".jpeg") && !fileName.endsWith(".png")
                && !fileName.endsWith(".gif")) {
            throw new BusinessException("上传失败，请选择BMP、JPG、JPEG、PNG、GIF文件！");
        }
        //逻辑处理 根据imgType的类型不同 区别图片资源的来源 保存到相应的文件路径
        //图片资源路径  = 来源路径 + 日期字符串 + 文件   文件名防止重复以 UUid 生成文件名

        String uploadFileName = renameToUUID(fileName);
        String yyyyMMdd = DateUtils.dateToString(new Date(), DateUtils.PatternEnum.DATE_FOR_DAY_DEFAULT);
        String uploadFile = uploadPath+"/"+ yyyyMMdd;

        //判断文件路径是否存在
        File targetFile = new File(uploadFile);

        //上传
        fileUploadQuietly(file, uploadFileName, uploadFile, targetFile);

        //返回文件路径
        String path = yyyyMMdd+"/"+uploadFileName;
        response.setResult(path);
        return response;

    }



    /**
     * 上传附件
     * 返回文件实际地址
     * @return
     */
    @ApiOperation(value = "上传附件")
    @PostMapping("/uploadAttach")
    public ResponseEntity uploadAttach(@RequestParam("file") MultipartFile file){

        ResponseEntity<Object> response = new ResponseEntity();
        if (null == file) {
            throw new BusinessException("上传失败，无法找到文件！");
        }

        String fileName = file.getOriginalFilename().toLowerCase();
        String uploadFileName = fileName;
        String yyyyMMdd = DateUtils.dateToString(new Date(), DateUtils.PatternEnum.DATE_FOR_DAY_DEFAULT);
        String uploadFile = attachPath + yyyyMMdd;

        //判断文件路径是否存在
        File targetFile = new File(uploadFile);

        //上传
        fileUploadQuietly(file, uploadFileName, uploadFile, targetFile);

        Map map = new HashMap<>();
        map.put("name",uploadFileName);
        //返回文件路径
        map.put("url",uploadFile +"/"+ uploadFileName);
        response.setResult(map);
        return response;

    }

    private void fileUploadQuietly(MultipartFile file, String uploadFileName, String uploadFile, File targetFile) {
        try {
            FileUtils.forceMkdir(targetFile);

            FileOutputStream out = new FileOutputStream(uploadFile +"/"+ uploadFileName);
            BufferedOutputStream bufferedStream = new BufferedOutputStream(out);
            bufferedStream.write(file.getBytes());

            IOUtils.closeQuietly(bufferedStream);
            IOUtils.closeQuietly(out);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException={}",e.toString());
        } catch (IOException e){
            logger.error("IOException={}",e.getMessage());
        }
    }


    /**
     * 文件uuid名
     * @param fileName
     * @return
     */
    private String renameToUUID(String fileName) {
        return UUID.randomUUID() + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
