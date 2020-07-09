package com.seahorse.youliao.controller;

import com.seahorse.youliao.vo.response.base.RedisInfo;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.client.RedisClient;
import org.redisson.client.RedisClientConfig;
import org.redisson.client.RedisConnection;
import org.redisson.client.codec.StringCodec;
import org.redisson.client.protocol.RedisCommands;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: spring actuator 监控
 * @author: Mr.Song
 * @create: 2020-02-04 10:55
 **/
@Api(value = "ActuatorController", tags = "spring actuator 监控")
@RestController
@RequestMapping("/actuator/monitor")
public class ActuatorController {

	private static Logger logger = LoggerFactory.getLogger(ActuatorController.class);

  	/**
  	 * @功能：获取磁盘信息
  	 * @param request
  	 * @param response
  	 * @return
  	 */
  	@ApiOperation(value = "查询磁盘使用情况")
  	@GetMapping("/queryDiskInfo")
  	public List<Map<String,Object>> queryDiskInfo(HttpServletRequest request, HttpServletResponse response){
		List<Map<String,Object>> list = new ArrayList<>();
  		try {
  			// 当前文件系统类
  	        FileSystemView fsv = FileSystemView.getFileSystemView();
  	        // 列出所有windows 磁盘
  	        File[] fs = File.listRoots();
			logger.info("查询磁盘信息:"+fs.length+"个");

  	        
  	        for (int i = 0; i < fs.length; i++) {
  	        	if(fs[i].getTotalSpace()==0) {
  	        		continue;
  	        	}
  	        	Map<String,Object> map = new HashMap<>();
  	        	map.put("name", fsv.getSystemDisplayName(fs[i]));
  	        	map.put("max", fs[i].getTotalSpace());
  	        	map.put("rest", fs[i].getFreeSpace());
  	        	map.put("restPPT", (fs[i].getTotalSpace()-fs[i].getFreeSpace())*100/fs[i].getTotalSpace());
  	        	list.add(map);
				logger.info(map.toString());
  	        }
  		} catch (Exception e) {
			logger.error(e.toString());
  		}
  		return list;
  	}


	/**
	 * @功能：获取redis信息
	 * @param key
	 * @return
	 */
	@ApiOperation(value = "获取redis信息")
	@GetMapping("/redisInfo/{key}")
	public List<RedisInfo> redisInfo(@PathVariable String key){

		RedisConnection conn = getRedisConnection();

		List<RedisInfo> list = new ArrayList<>();
		Map<String,String> redisInfo = new HashMap<>();

		switch (key){
			case "1":
				//memory
				redisInfo = conn.sync(StringCodec.INSTANCE, RedisCommands.INFO_MEMORY);
				break;
			case "2":
				//keysize
				redisInfo = conn.sync(StringCodec.INSTANCE, RedisCommands.INFO_KEYSPACE);
				break;
			case "3":
				//cpu
				redisInfo = conn.sync(StringCodec.INSTANCE, RedisCommands.INFO_CPU);
				break;
			case "4":
				//server
				redisInfo = conn.sync(StringCodec.INSTANCE, RedisCommands.INFO_SERVER);
				break;
			default:
				break;
		}

		redisInfo.forEach((k,v)->{
			logger.info("key = " + k+",value = " + v);
			RedisInfo info = new RedisInfo(k,v,RedisInfo.map.get(k));
			list.add(info);
		});
		return list;
	}


	/**
	 * @功能：获取redis keySize
	 * @return
	 */
	@ApiOperation(value = "获取redis keySize")
	@GetMapping("/redisInfo/keySize")
	public List<Map<String,Object>> queryKeySize(){

		RedisConnection conn = getRedisConnection();
		List<Map<String,Object>> list = new ArrayList<>();

		//keysize
		Map<String,String> redisInfo = conn.sync(StringCodec.INSTANCE, RedisCommands.INFO_KEYSPACE);

		redisInfo.forEach((k,v)->{
			logger.info("key = " + k+",value = " + v);
			Map<String,Object> map = new HashMap<>();
			map.put("x",k);
			map.put("y",Integer.valueOf(v.substring(5,v.indexOf(",expires"))));
			list.add(map);
		});
		return list;
	}

	/**
	 * 获取redis连接
	 * @return
	 */
	private RedisConnection getRedisConnection() {
		EventLoopGroup group = new NioEventLoopGroup();
		RedisClientConfig config = new RedisClientConfig();
		//读取配置文件 暂时写死
		config.setAddress("redis://127.0.0.1:6379");
		config.setPassword("123456");
		config.setDatabase(1)
				.setClientName("myClient")
				.setGroup(group);
		RedisClient redisClient = RedisClient.create(config);
		return redisClient.connect();
	}

}
