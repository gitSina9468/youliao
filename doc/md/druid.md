    @Configuration
    public class DruidConfig {


        @Bean
        public ServletRegistrationBean druidServlet() {
            ServletRegistrationBean reg = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
            //配置用户名
            reg.addInitParameter("loginUsername", "root");
            //配置密码
            reg.addInitParameter("loginPassword", "root");
            //在日志中打印执行慢的sql语句
            reg.addInitParameter("logSlowSql", "true");
            //另外还可配置黑白名单等信息，可参考druid官网介绍
            return reg;
        }
    
        @Bean
        public FilterRegistrationBean filterRegistrationBean() {
            FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
            filterRegistrationBean.addUrlPatterns("/*");
            //过滤文件类型
            filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
            //监控单个url调用的sql列表
            filterRegistrationBean.addInitParameter("profileEnable", "true");
            return filterRegistrationBean;
        }

    }
