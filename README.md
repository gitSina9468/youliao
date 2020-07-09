<h1 style="text-align: center">YOU-LIAO 后台管理系统</h1>

#### 项目简介
一个基于 Spring Boot 2.0.4、Mybatis、JWT、WebSocket、Spring Security、Redis、Ant Design Pro Vue的前后端分离的后台管理系统。
此处只是项目的简介，更多的技术内容使用实现方案会完善到wiki 文档，敬请期待。

> 如果该项目对您有帮助，您可以点右上角 "Star" 支持一下 谢谢！

> 或者您可以 "follow" 一下，该项目将持续更新，不断完善功能。

**前端项目地址：**  [https://gitee.com/sinaC/ant-vue-youliao](https://gitee.com/sinaC/ant-vue-youliao)

**体验地址：**  [http://132.232.43.102](http://132.232.43.102)

**账号密码：**  `guest/guest` 需要微信和支付宝扫码付支付账号的伙伴;请联系QQ:470472264.

**交流：**  QQ交流群号:129557872;也可描述详细问题email到470472264@qq.com 或则Issue;

**文档：**  地址:[https://sinac.gitee.io/blog](https://sinac.gitee.io/blog).<文档还在完善中>

## 更新日志
[更新请查看](update.md)

#### 软件架构
- 后端:框架:Spring Boot + 安全框架:spring security + 缓存:Redis + 数据库:Mysql  多模块集成框架
- 前端:ANTD PRO VUE 开箱即用的中台前端/设计解决方案


## 项目功能模块
```
├─仪表盘
│  ├─工作台
│  ├─分析页
│  ├─测试功能
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─菜单管理
│  ├─部门管理
│  ├─岗位管理
│  ├─系统日志
├─外部网页链接
│  ├─swagger-ui(在线接口文档)
│  ├─SQL监控
│  ├─Swagger-Bootstrap-UI
│  ├─排队大厅(websocket)
│  ├─进程日志(websocket)
├─系统监控
│  ├─磁盘监控
│  ├─jvm信息
│  ├─服务器信息
│  ├─Tomcat信息
│  ├─Redis信息
├─常见案列
│  ├─打印测试
│  ├─OSS存储
│  ├─订单管理<微信 支付宝 扫码付>
│  ├─pdf小票预览
│  │  ├─采血回执单
│  │  ├─销售票据
│  │  ├─销售小票
│  │  ├─销售凭证
│  ├─redisson缓存操作
│  ├─邮件发送
│  ├─动态文档解析预览
├─开发中模块
│  ├─ES加入文档检索<开发中>
│  ├─在线office 文档转Pdf<开发中>
├─优化模块模块
│  ├─订单下单延时队列处理过期
│  ├─redisson各类数据的操作

└─其他模块
   └─更多功能开发中。。
   
```
   
## 文档 目录
1. <a href="#1">项目解读</a>
      1. <a href="#1.1">项目预览</a>
      2. <a href="#1.2">项目文档、监控集成</a>
      3. <a href="#1.3">pdf小票预览</a>
    
2. <a href="#2">安全框架SpringSecurity</a>
      1. <a href="#2.1">SpringSecurity集成</a>
      2. <a href="#2.2">订单管理扫码付</a>
      3. <a href="#2.3">腾讯云api功能</a>
3. <a href="#3">项目技术</a>
      1. <a href="#3.1">后端技术栈</a>
      2. <a href="#3.2">前端技术栈</a>
      3. <a href="#3.3">集成技术点</a>
4. <a href="#4">案列解读(以博客形式)</a>
      1. <a href="https://blog.csdn.net/Soinq/article/details/106178984" target="_blank">邮件发送服务解读</a>
      2. <a href="https://blog.csdn.net/Soinq/article/details/106257204" target="_blank">动态文档解析预览解读</a>

#### 使用说明 前段项目启动请前往前段项目

1.  拉取项目代码 
git clone <a target="_blank" href="git clone https://gitee.com/sinaC/youliao.git">https://gitee.com/sinaC/youliao.git</a>

2.  数据库执行语句 [sql语句](doc/sql/youliao.sql) 

3.  项目在中央仓库无法下载的jar(word解析相关包)  在项目/doc 目录下

4.  启动后看到美女图案表示启动成功;
![美女](https://images.gitee.com/uploads/images/2020/0408/205017_bef2a324_1182566.png)

### 1.1 <a name="1.1">项目预览</a>


<table>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205017_5289c83a_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205017_2abe55b9_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205017_c2f056a7_1182566.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205017_a31eb61c_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205017_b1c5bbbf_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205018_c144e369_1182566.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205018_19b9cf9c_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230321_02bc0257_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230321_3256731c_1182566.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230321_d2e109df_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230321_eb13a2ea_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230321_be11e068_1182566.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230321_970be245_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230322_f4c14a7a_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0606/230322_71fec6cd_1182566.png"/></td>
    </tr>
</table>


### 1.2 <a name="1.2">项目监控 pdf文档预览</a>

>1.集成阿里Druid数据库连接池，监控DB池连接和SQL的执行情况；账号 root  密码 root 可自行在项目中设置；[demo实例](doc/md/druid.md)

![global](https://images.gitee.com/uploads/images/2020/0408/205019_3f9c8549_1182566.png)

>2.集成Swagger：一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务；[demo实例](doc/md/swagger.md)

![global](https://images.gitee.com/uploads/images/2020/0408/205019_9988cd2a_1182566.png)

>3.集成Swagger-Bootstrap-UI文档<swagger升级版 测试更方便>；启动后访问 http://localhost:8080/api/doc.html；

![global](https://images.gitee.com/uploads/images/2020/0408/205019_12da1bcd_1182566.png)

>4.webSocket 日志实时推送；启动后访问 http://localhost:8080/api/log/view

![global](https://images.gitee.com/uploads/images/2020/0408/205019_a9b60e61_1182566.png)

>5.pdf预览；启动后默认访问 http://localhost:8080/api/generic/web/viewer.html  后面跟 ?file=文件名.pdf 可访问pdf文件

![global](https://images.gitee.com/uploads/images/2020/0408/205019_67351760_1182566.png)

### 1.3 <a name="1.3">pdf小票预览
<table>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205019_d4aa9ca2_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205019_898685e0_1182566.png"/></td>
    </tr>
    <tr>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205019_d3595696_1182566.png"/></td>
        <td><img src="https://images.gitee.com/uploads/images/2020/0408/205020_55fd0d6e_1182566.png"/></td>
    </tr>
</table>

### 2.1 <a name="2.1">SpringSecurity集成</a>
>1.基于springSecurity 安全框架做的用户权限体系。权限体系为：用户-角色-菜单 功能权限（控制到按钮级别权限 还在完善） 部门-岗位 数据权限。


### 2.2 <a name="2.2">订单管理扫码付</a>
>1.基于支付案列的扫码付已经完善接入,个人已自测。更多支付类型后期再不断更新。案列预览。
<table>
        <tr>
            <td><img src="https://images.gitee.com/uploads/images/2020/0408/205019_cd7d4fdb_1182566.jpeg"/></td>
            <td><img src="https://images.gitee.com/uploads/images/2020/0408/205020_d45b6603_1182566.jpeg"/></td>
            <td><img src="https://images.gitee.com/uploads/images/2020/0408/205021_871da4c8_1182566.jpeg"/></td>
        </tr>
        <tr>
            <td><img src="https://images.gitee.com/uploads/images/2020/0408/205020_9e3c0e5a_1182566.jpeg"/></td>
            <td><img src="https://images.gitee.com/uploads/images/2020/0408/205020_388b4cce_1182566.jpeg"/></td>
            <td><img src="https://images.gitee.com/uploads/images/2020/0408/205020_b3d029d0_1182566.jpeg"/></td>
        </tr>
</table>


### 3.1 <a name="3.1">后端技术栈</a>

- 基础框架：Spring Boot 2.0.4.RELEASE
- 安全框架: Spring Security 5.0.7.RELEASE
- 持久层框架: Mybatis
- 数据库: Mysql
- Jar包管理: Maven
- 身份验证: JWT
- 缓存框架：Redis
- 日志打印：logback
- 长连接：webSocket
- 模板解析 freemarker
- 文档预览 pdfJs xdocJs
- Html转Pdf和图片 html2canvas
- 导入导出 Easy POI
- 接口文档 swagger2
- 其他：fastJson、xml、xdoc 等

### 3.2 <a name="3.2">前端技术栈</a>

- node
- yarn
- webpack
- @vue/cli 3.2.1
- [ant-design-vue](https://github.com/vueComponent/ant-design-vue) - Ant Design Of Vue 实现
- [vue-cropper](https://github.com/xyxiao001/vue-cropper) - 头像裁剪组件
- [@antv/g2](https://antv.alipay.com/zh-cn/index.html) - Alipay AntV 数据可视化图表
- [Viser-vue](https://viserjs.github.io/docs.html#/viser/guide/installation)  - antv/g2 封装实现

### 3.3 <a name="3.3">集成技术点</a>

> 集成了多个开源技术点,非常简单.只需要短短的几部就能完成集成

> 1.Swagger-Bootstrap-UI github开源接口文档  [项目地址](https://github.com/xiaoymin/Swagger-Bootstrap-UI)

> 2.XDOC-专注文档处理 处理在线动态文档预览 [项目地址](https://gitee.com/xdoc/xdoc)

> 3.EasyPoi Excel和 Word简易工具类 [项目地址](https://gitee.com/lemur/easypoi)

> 3.自动化代码生成集成 [项目地址](https://gitee.com/ztp/auto-code-ui-spring-boot-starter)

> 4.Redisson封装扩展分布式锁类型 单机 集群 哨兵模式 应用 [项目地址](https://gitee.com/ztp/redisson-spring-boot-starter)

#### 码云特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5.  码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)

#### 给开发的小伙伴留言

此项目希望给大家提供一个共同交流的平台.目前项目文档还在完善中,如有问题请邮件到 470472264@qq.com;
操作账号只提供给对此项目有捐赠的小伙伴.谢谢大家!!!

### 捐赠 

如果觉得还不错，请作者喝杯咖啡吧 ☺

![](https://images.gitee.com/uploads/images/2020/0408/205058_80576a18_1182566.jpeg)