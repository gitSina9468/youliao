package com.seahorse.youliao.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.seahorse.youliao.common.ResponseEntity;
import com.seahorse.youliao.exception.BusinessException;
import com.seahorse.youliao.vo.request.UserRequestVO;
import com.zengtengpeng.annotation.Lock;
import com.zengtengpeng.operation.RedissonBinary;
import com.zengtengpeng.operation.RedissonCollection;
import com.zengtengpeng.operation.RedissonObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RExpirable;
import org.redisson.api.RKeys;
import org.redisson.api.RType;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * @ProjectName: youliao
 * @Package: com.seahorse.youliao.controller
 * @ClassName: RedissonObjectController
 * @Description: redisson 操作实列
 * github的redisson官方的中文文档
 * https://github.com/redisson/redisson/wiki/%E7%9B%AE%E5%BD%95
 * Redis命令和Redisson对象匹配列表：
 * https://github.com/redisson/redisson/wiki/
 * 11.-Redis%E5%91%BD%E4%BB%A4%E5%92%8CRedisson%E5%AF%B9%E8%B1%A1%E5%8C%B9%E9%85%8D%E5%88%97%E8%A1%A8
 * @author:songqiang
 * @Date:2019-12-31 14:40
 **/
@Api(value = "RedissonObjectController", tags = "redisson 操作实列")
@RestController
@RequestMapping(value = "/redisson")
public class RedissonObjectController {


    private static Logger logger = LoggerFactory.getLogger(RedissonObjectController.class);

    /**
     * 操作对象
     */
    @Resource
    private RedissonObject redissonObject;

    /**
     * 操作集合 map set
     */
    @Resource
    private RedissonCollection redissonCollection;

    /**
     * 操作文件流
     */
    @Resource
    private RedissonBinary redissonBinary;

    /**
     * 原始redisClient更多扩展
     */
    @Resource
    private RedissonClient redissonClient;


    /**
     * 获取指定redis键
     * @param pattern
     * @return
     */
    @ApiOperation(value = "获取指定redis键 * 表示全部")
    @GetMapping("/keys")
    public List<Map<String, String>> keys(@RequestParam String pattern)  {

        List<Map<String, String>> mapList = new ArrayList<>();
        RKeys keys = redissonClient.getKeys();
        Iterable<String> iterable = keys.getKeysByPattern(pattern);
        List<String> list = Lists.newArrayList(iterable);
        for (String s : list) {

            Map<String, String> map = new HashMap<>();
            map.put("key",s);

            RExpirable re = redissonClient.getPermitExpirableSemaphore(s);
            long alive = re.remainTimeToLive();
            logger.info("redis key :" + s +",过时时间："+ alive/1000L+"s");
            map.put("expire",alive == -1L?"-1":re.remainTimeToLive()/1000L+"s");

            RType type = keys.getType(s);
            switch (type){
                case OBJECT:
                    logger.info("object={}",JSONObject.toJSON(redissonObject.getValue(s)));
                    map.put("value",JSON.toJSONString(redissonObject.getValue(s)));
                    break;
                case MAP:
                    logger.info("map={}",JSONObject.toJSON(redissonCollection.getMap(s)));
                    map.put("value",JSON.toJSONString(redissonCollection.getMap(s)));
                    break;
                case SET:
                    logger.info("set={}",JSONObject.toJSON(redissonCollection.getSet(s)));
                    map.put("value",JSON.toJSONString(redissonCollection.getSet(s)));
                    break;
                case LIST:
                    logger.info("set={}",JSONObject.toJSON(redissonCollection.getList(s)));
                    map.put("value",JSON.toJSONString(redissonCollection.getList(s)));
                    break;
                case ZSET:
                    logger.info("zset={}",JSONObject.toJSON(redissonCollection.getSet(s)));
                    map.put("value",JSON.toJSONString(redissonCollection.getSet(s)));
                    break;
                default:
                    break;
            }
            map.put("type",type.name());
            mapList.add(map);
        }
       return mapList;
    }

    /**
     * 从redis获取user集合
     * @param pattern
     * @return
     */
    @ApiOperation(value = "从redis获取user集合")
    @PostMapping("/getUserListByKey")
    public List<List<UserRequestVO>> getUserListByKey(@RequestParam(defaultValue = "list-*") String pattern)  {

        if(StringUtils.isEmpty(pattern)){
            throw new BusinessException("pattern参数不能为空");
        }
        RKeys keys = redissonClient.getKeys();
        Iterable<String> iterable = keys.getKeysByPattern(pattern);
        List<String> list = Lists.newArrayList(iterable);

        List<List<UserRequestVO>> userList = new ArrayList<>();
        for (String s : list) {
           userList.add(redissonCollection.getList(s));
        }

       return userList;
    }

    /**
     * 设置对象信息
     * @param user
     * @return
     */
    @ApiOperation(value = "设置对象信息")
    @PostMapping("/setObject")
    public ResponseEntity setObject(@RequestBody UserRequestVO user)  {

        redissonObject.setValue("object-"+getKey(),user);
        return ResponseEntity.ok("缓存设置成功");
    }

    /**
     * 设置Map哈希表
     * @param map
     * @return
     */
    @ApiOperation(value = "设置Map哈希表")
    @PostMapping("/setMap")
    @ResponseBody
    public ResponseEntity setMap(@RequestBody JSONObject map)  {

        redissonCollection.setMapValues("map-"+getKey(),map);
        return ResponseEntity.ok("缓存设置成功");
    }

    /**
     * 设置List集合
     * @param list
     * @return
     */
    @ApiOperation(value = "设置List集合")
    @PostMapping("/setList")
    public ResponseEntity setList(@RequestBody List<UserRequestVO> list)  {

        redissonCollection.setListValues("list-"+getKey(),list);
        return ResponseEntity.ok("缓存设置成功");
    }

    /**
     * 设置Set集合
     * @param set
     * @return
     */
    @ApiOperation(value = "设置Set集合")
    @PostMapping("/setSet")
    public ResponseEntity setSet(@RequestBody HashSet<UserRequestVO> set)  {

        redissonCollection.setSetValues("set-"+getKey(),set);
        return ResponseEntity.ok("缓存设置成功");
    }

    /**
     * 设置zSet集合
     * @param set
     * @return
     */
    @ApiOperation(value = "设置zSet集合")
    @PostMapping("/setZSet")
    public ResponseEntity setZSet(@RequestBody HashSet<UserRequestVO> set)  {

        redissonCollection.setSetValues("zSet-"+getKey(),set);
        return ResponseEntity.ok("缓存设置成功");
    }

    /**
     * 文件放入缓存
     * @return
     */
    @ApiOperation(value = "文件放入缓存")
    @PostMapping("/setFile")
    public ResponseEntity setFile(@RequestParam("file") MultipartFile file) throws IOException {

        redissonBinary.setValue("file-"+ getKey(),file.getInputStream());
        return ResponseEntity.ok("缓存设置成功");
    }

    private String getKey() {
        return String.valueOf((int)((Math.random()*9+1)*100000));
    }

    /**
     * 根据 key 删除指定缓存
     * @return
     */
    @ApiOperation(value = "根据 key 删除指定缓存")
    @DeleteMapping("/deleteByKey")
    public ResponseEntity deleteByKey(@RequestParam String key) {

        RKeys keys = redissonClient.getKeys();
        keys.delete(key);
        return ResponseEntity.ok("删除成功");
    }

    /**
     * 如果对象不存在则设置,否则不设置
     * @return
     */
    @ApiOperation(value = "如果对象不存在则设置,否则不设置")
    @PostMapping("/trySetValue")
    public ResponseEntity trySetValue() {

        String result = redissonObject.trySetValue("object1", "object1-2").toString();
        return ResponseEntity.ok(result);
    }


    /**
     * 1.支持spel表达式,keyConstant是常量(可选)
     */
    @Lock(keys = "#user.name",keyConstant = "常量")
    @ApiOperation(value = "分布式锁测定")
    @PostMapping("/testLock")
    public ResponseEntity testLock(@RequestBody UserRequestVO user) {

        System.out.println("进来了lock test1"+user.toString());
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
           logger.error(e.toString());
        }
        logger.info("出去了lock test2"+user.toString());
        return ResponseEntity.ok("lock test");
    }
}
