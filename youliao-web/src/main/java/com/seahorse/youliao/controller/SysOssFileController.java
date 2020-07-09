package com.seahorse.youliao.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.seahorse.youliao.config.OSSUtils;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.logfilter.Log;
import com.seahorse.youliao.service.SysOssFileService;
import com.seahorse.youliao.service.entity.SysOssFileDTO;
import com.seahorse.youliao.util.SecurityUtils;
import com.seahorse.youliao.utils.BeanUtil;
import com.seahorse.youliao.utils.DateUtils;
import com.seahorse.youliao.utils.FileTypeUtil;
import com.seahorse.youliao.vo.request.SysOssFileQueryVO;
import com.seahorse.youliao.vo.response.SysOssFilePageInfoVO;
import com.seahorse.youliao.vo.response.SysOssFileResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
* describe: oss文件管理
* @author : songqiang
* @date: 2020-03-16 02:55:41.341
**/
@RestController
@Api(value = "SysOssFileController",tags = "系统-文件资源管理")
@RequestMapping("/sys/oss")
@Validated
public class SysOssFileController {
	
    private static final Logger logger = LoggerFactory.getLogger(SysOssFileController.class);
    
	@Autowired
    private SysOssFileService sysOssFileService;

	@Autowired
    private OSSUtils ossUtils;

    /**
     * 分页查询
     * @param sysOssFileQueryVO 分页查询参数
     * @return 分页参数
     */
    @ApiOperation(value = "分页查询")
    @PostMapping("getPageList")
    public SysOssFilePageInfoVO selectPageList(@RequestBody @Valid SysOssFileQueryVO sysOssFileQueryVO) {
        String info = String.format("The method name[selectPageList] params:%s", sysOssFileQueryVO.toString());
        logger.info(info);
        SysOssFileDTO sysOssFile = BeanUtil.convert(sysOssFileQueryVO, SysOssFileDTO.class);

        Page<SysOssFileResponseVO> page = PageHelper.startPage(sysOssFileQueryVO.getPageNum(), sysOssFileQueryVO.getPageSize());
        List<SysOssFileDTO> sysOssFileList = sysOssFileService.getList(sysOssFile);
        SysOssFilePageInfoVO sysOssFilePageInfo = new SysOssFilePageInfoVO();
        BeanUtils.copyProperties(page.toPageInfo(), sysOssFilePageInfo);
        List<SysOssFileResponseVO> voList = BeanUtil.convert(sysOssFileList, SysOssFileResponseVO.class);
        sysOssFilePageInfo.setList(voList);

        return sysOssFilePageInfo;
    }

    /**
     * 上传oss文件
     * @param file
     */
    @Log(type = Log.OperationType.UPLOAD,button = "上传文件",menu = "文件管理")
    @PreAuthorize("hasAuthority('oss:upload')")
    @ApiOperation(value = "上传oss文件")
    @PostMapping("upload")
    public boolean uploadOssFile(@RequestParam("file") MultipartFile file) throws IOException {

        //上传文件
        String originalFilename = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("/")+1);
        //文件大小 默认单位是字节
        long size = file.getSize();
        //文件类型 不能根据后缀来判断 容易篡改 根据文件头部信息 Apache tika的可以了解和使用 封装了文件类型获取
        String type = FileTypeUtil.getFileType(file.getInputStream());
        if(type == null){
            //没有匹配到文件类型
            type = "other";
        }
        //对象名 类型+日期+原始名
        String objectName = type+"/"+DateUtils.getNow(DateUtils.PatternEnum.DATE_FOR_YEARDAY) +"/"+ originalFilename;
        //oss 上传文件
        ossUtils.putInputStream(file.getInputStream(),objectName);

        //添加上传记录到数据库
        SysOssFileDTO ossFileDTO = new SysOssFileDTO();
        ossFileDTO.setFileName(originalFilename);
        //保留2位小数 kb
        ossFileDTO.setFileSize(BigDecimal.valueOf(size).divide(new BigDecimal(1024),2,BigDecimal.ROUND_HALF_UP));
        ossFileDTO.setType(type);
        //相对路径 访问需加bucketName + endpoint
        ossFileDTO.setFileUrl(objectName);
        ossFileDTO.setCreateTime(new Date());
        ossFileDTO.setCreateBy(SecurityUtils.getUsername());
        return sysOssFileService.insert(ossFileDTO);
    }

    /**
     * 根据oss对象名称下载文件
     * @param objectName
     * @param response
     * @throws Exception
     */
    @Log(type = Log.OperationType.DOWNLOAD,button = "下载文件",menu = "文件管理")
    @PreAuthorize("hasAuthority('oss:downLoad')")
    @GetMapping("downLoad")
    public void downLoadOssFile(@RequestParam String objectName,HttpServletResponse response) throws Exception {

        //流式下载
        ossUtils.getObjectInputstream(objectName,response);

    }

    /**
     * 根据oss对象名称删除文件
     * @param id 文件主键id
     */
    @Log(type = Log.OperationType.DELETE,button = "删除文件",menu = "文件管理")
    @PreAuthorize("hasAuthority('oss:delete')")
    @DeleteMapping("delete")
    public void deleteOssFile(@RequestParam Integer id) {

        SysOssFileDTO ossFile = sysOssFileService.getById(id);
        if(ossFile == null){
            throw new BusinessException("文件已被删除");
        }
        sysOssFileService.delete(ossFile);
        //删除oss存在的文件
        ossUtils.deleteObject(ossFile.getFileUrl());

    }

}
