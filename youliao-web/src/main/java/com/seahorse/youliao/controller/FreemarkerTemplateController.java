package com.seahorse.youliao.controller;

import com.hg.doc.DocUtil;
import com.hg.doc.XDocXml;
import com.hg.doc.XFont;
import com.hg.util.StrUtil;
import com.hg.xdoc.XDoc;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.seahorse.youliao.entity.freemarker.ContractBusinessDetailsDto;
import com.seahorse.youliao.entity.freemarker.ContractBusinessDto;
import com.seahorse.youliao.utils.ContractUtil;
import com.seahorse.youliao.utils.FtpHelperw;
import com.seahorse.youliao.utils.IDGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description: freemarker 模板控制器
 * @author: Mr.Song
 * @create: 2019-12-15 20:20
 **/
@RestController
@Api(value = "FreemarkerTemplateController", tags = "freemarker模板生成pdf预览和下载")
@RequestMapping("freemarker")
@Validated
public class FreemarkerTemplateController {

    @Autowired
    protected HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(FreemarkerTemplateController.class);

    @Value("${xdoc.fonts:/usr/web/fonts}")
    private String xdocFonts;

    @PostConstruct
    void init() {
        XFont.init(xdocFonts);
    }


    /**
     * freemarker 测试hello
     * @param request
     * @return
     */
    @ApiOperation(value = "freemarker 测试hello")
    @GetMapping("/hello")
    public ModelAndView hello(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("/test");
        modelAndView.addObject("name", "hello");
        return modelAndView;
    }

    /**
     * xdoc 预览固定文档数据
     * @param request
     * @return
     */
    @ApiOperation(value = " xdoc 预览固定文档数据")
    @GetMapping("/show")
    public ModelAndView show(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("/show");
        return modelAndView;
    }

    /**
     * freemarker模板动态数据预览查看
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "freemarker模板动态数据预览查看")
    @GetMapping("/view")
    public ModelAndView freeMarkerTemplateView(HttpServletRequest request) throws Exception {

        ModelAndView modelAndView = new ModelAndView("/preview");
        StringBuilder builder = new StringBuilder();
        //先查询合同
        builder.append(templateContract("4028849467c660570167d8c1fd041974", "", "", request, new HashMap<>()));

        //再查库房作业单
        modelAndView.addObject("loadId", "4028849467c660570167d8c1fd041974");
        modelAndView.addObject("context", builder.toString());
        logger.info(builder.toString());
        modelAndView.addObject("type", "preview");
        return modelAndView;
    }


    /**
     * freemarker模板数据打印
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "freemarker模板数据打印")
    @GetMapping("/saveAndPrint")
    @ResponseBody
    public String freeMarkerTemplatePrint(HttpServletRequest request) throws Exception {

        StringBuilder builder = new StringBuilder();

        //先查询合同
        builder.append(templateContract("4028849467c660570167d8c1fd041974", "", "", request, new HashMap<>()));

        //文件名称
        String pdfName = IDGenerator.getUUID();

        //目标文件
        File fileDest = File.createTempFile(pdfName, ".pdf");
        String[] tmpArray = new String[1];

        //判断是否存在虚拟订单
        String ip = "";
        ip = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/statics/image/yp.jpg";
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formatDate = format.format(date);
        String xdoc = String.format("<xdoc version=\"11.1.5\"><meta modifyDate=\"%s\" author=\"Administrator\" id=\"3ulahulfbrdwnfujnnjulqjcpm\" title=\"车辆配载合同\" " +
                "createDate=\"%s\"/><paper width=\"812\" height=\"1124\" topMargin=\"15\" bottomMargin=\"15\" leftMargin=\"61\" rightMargin=\"71\"/><body fillImg='" + ip + "'>" +
                "%s</body></xdoc>", formatDate, formatDate, builder);

        logger.info("xdoc="+xdoc);

        request.setCharacterEncoding("UTF-8");

        setXDoc(request, xdoc);
        String tmpName = IDGenerator.getUUID();
        File tmpFile = File.createTempFile(tmpName, ".pdf");
        XDoc.write(xdoc, tmpFile);
        //设置打印份数
        for (int k = 0; k < tmpArray.length; k++) {
            tmpArray[k] = tmpFile.getCanonicalPath();
        }
        comBinPdf(tmpArray, fileDest.getCanonicalPath());
        //上传目标文件
        InputStream fileIn = new FileInputStream(fileDest);
        FtpHelperw ftpHelper = new FtpHelperw();
        String pdfUrl = ftpHelper.pdfupload(pdfName + ".pdf", fileIn, "ftp");
        pdfUrl = getIP() + pdfUrl;
        //删除临时文件
        try {
            deleteTmpFile(tmpArray);
            fileDest.delete();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return pdfUrl;
    }

    private void deleteTmpFile(String[] tmpFiles) {
        for (String file : tmpFiles) {
            File f = new File(file);
            if (f.exists()) {
                f.delete();
            }
        }

    }

    public String getIP() {
        String ip = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        return ip;
    }


    //*********合并  pdfFilenames为文件路径数组，targetFileName为目标pdf路径
    private void comBinPdf(String[] pdfFilenames, String targetFilename)
            throws Exception {
        PdfReader reader = null;
        Document doc = new Document();
        PdfCopy pdfCopy = new PdfCopy(doc, new FileOutputStream(targetFilename));
        int pageCount = 0;
        doc.open();
        for (int i = 0; i < pdfFilenames.length; ++i) {
            logger.info(pdfFilenames[i]);
            reader = new PdfReader(pdfFilenames[i]);
            pageCount = reader.getNumberOfPages();
            for (int j = 1; j <= pageCount; ++j) {
                pdfCopy.addPage(pdfCopy.getImportedPage(reader, j));
            }
        }
        doc.close();
    }


    /**
     * 模板生成
     *
     * @param loadId 配载单ID
     * @return
     */
    private String templateContract(String loadId, String fingerPrintImages, String userNameImg, HttpServletRequest request, Map<String, String> map) throws Exception {
        String content = "";
        StringBuilder builder = new StringBuilder("");
        try {

            //组装数据
            List<ContractBusinessDto> dtoList = getContractBusinessDtos();

            //生成PDF
            for (ContractBusinessDto dto : dtoList) {
                //读取模板
//                InputStream inputStream = servletContext.getResourceAsStream("/templates/contract.ftl");　无法读取文件；适用于基于tomcat的web服务
//                ClassPathResource classPathResource = new ClassPathResource("/templates/contract.ftl");
//                InputStream inputStream = classPathResource.getInputStream();
                InputStream inputStream = this.getClass().getResourceAsStream("/templates/contract.ftl");
                content = ContractUtil.createTemplateContract(dto, inputStream);
                builder.append(content);
                builder.append(ContractUtil.createSplitPage());
                map.put("pz", dto.getCodeUrl());

            }
            return builder.toString();
        } catch (Exception ex) {
            logger.info(ex.getMessage());
            return "";
        }
    }

    private List<ContractBusinessDto> getContractBusinessDtos() {
        //组合数据
        List<ContractBusinessDto> dtoList = new ArrayList<>();
        List<ContractBusinessDetailsDto> mvDetails = new ArrayList<>(5);
        ContractBusinessDetailsDto dto1 = new ContractBusinessDetailsDto();
        dto1.setId("4028849467c660570167d8c1fd041975");
        dto1.setOmsNum("XS18121500088");
        dto1.setSkuName("粉尘尿素");
        dto1.setUnitName("吨");
        dto1.setSpecName("50Kg");
        dto1.setPiecesQuantity("700.00");
        dto1.setWeightQuantity("35.00");
        dto1.setVolumeQuantity("0.00");
        dto1.setQuantity("35.00");
        dto1.setRemark("白分成");
        dto1.setErpNum("181205008");
        dto1.setDealerId("baa0a599-8660-11e8-87fe-7cd30ae8d645");
        mvDetails.add(dto1);
        List<ContractBusinessDetailsDto> detailsDtoList = new ArrayList<>();
        detailsDtoList.add(dto1);

        ContractBusinessDto dto = new ContractBusinessDto();
        dto.setCodeUrl("http://localhost:8080/api/img/qrCode.png");
        dto.setStowageOrderId("4028849467c660570167d8c1fd041974");
        dto.setStowageNum("PZ181223000009");
        dto.setWaybillNum("Y181223000009");
        dto.setCustomerName("新惠利江苏金龙化肥有限公司");
        dto.setDriverName("蒋集体");
        dto.setVehicleNo("豫NY3297");
        dto.setConsigneeName("严红");
        dto.setConsigneeContact("13505221970");
        dto.setDeliveryContact("13956797208");
        dto.setConsigneeAddress("江苏省徐州市睢宁县/睢宁县");
        dto.setTransportType("汽运");
        dto.setPickType("自提");
        dto.setOppointment("2018-12-23 0:00-24:00");
        dto.setDeliveryTime("2019-12-03 14:46");
        dto.setSumNum("35.00");
        dto.setVersion("1545529851140");
        dto.setDealerId("baa0a599-8660-11e8-87fe-7cd30ae8d645");
        dto.setVersionNum("1-1");
        dto.setSignImages("http://localhost:8080/api/img/name.bmp");
        dto.setFingerPrintImages("http://localhost:8080/api/img/print.bmp");

        dto.setMvDetails(mvDetails);
        dto.setDetailsDtoList(detailsDtoList);
        dtoList.add(dto);
        return dtoList;
    }


    @PostMapping("/xdoc")
    public void xdoc(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8");
        ServletOutputStream out = response.getOutputStream();
        response.setContentType("application/pdf");
        String xdoc = null;
        try {
            xdoc = request.getParameter("_xdoc");
            if ("true".equals(request.getParameter("_de"))) {
                xdoc = StrUtil.URLDecode(xdoc);
            }

        } catch (Exception e) {
            xdoc = XDocXml.toXml(DocUtil.errDoc(e));
        }

        if (StringUtils.isEmpty(xdoc)) {
            xdoc = getXDoc(request);
        } else {
            setXDoc(request, xdoc);
        }

        try {
            String format = request.getParameter("_format");
            if (format == null) {
                format = "pdf";
            } else if (format.equals("jpg")) {
                format = "jpeg";
            }
            if (format.equals("pdf")) {
                response.setContentType("application/pdf");
            } else if ((format.equals("png")) || (format.equals("jpeg"))) {
                response.setContentType("image/" + format);
            } else {
                response.setContentType("text/xml;charset=utf-8");
            }
            XDoc.write(xdoc, out, format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }


    /**
     * 由于下载的时候xdoc数据可能没有带过来，
     * 这里先暂时存在session中
     *
     * @param request
     * @param xdoc
     */
    private void setXDoc(HttpServletRequest request, String xdoc) {
        String key = request.getParameter("t");
        if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(xdoc)) {
            request.getSession().setAttribute(key, xdoc);
        }
    }

    private String getXDoc(HttpServletRequest request) {
        String key = request.getParameter("t");
        if (!StringUtils.isEmpty(key)) {
            Object xdoc = request.getSession().getAttribute(key);
            if (xdoc != null) {
                return xdoc.toString();
            }
        }
        return null;
    }

}
