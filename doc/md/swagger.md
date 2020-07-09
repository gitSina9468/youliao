    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.5.0</version>
    </dependency>
    <!-- swagger-ui -->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
            
    @Configuration
    @EnableSwagger2
    @ComponentScan(basePackages= "com.seahorse.youliao")
    public class SwaggerConfig {
        @Value("${swagger.basePackage:com.seahorse.youliao}")
        private String basePackage;

        @Bean
        public Docket api(){
            ParameterBuilder ticketPar = new ParameterBuilder();
            List<Parameter> pars = new ArrayList<Parameter>();
            ticketPar.name("token").description("token")
                    .modelRef(new ModelRef("string")).parameterType("header")
                    .required(false).build();
            pars.add(ticketPar.build());
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .groupName("youliao-project-api")
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(basePackage))
                    .build().globalOperationParameters(pars);
        }
    
    
        private ApiInfo apiInfo(){
            return new ApiInfoBuilder()
                    .title("有料项目API文档")
                    .description("HTTP对外开放接口")
                    .version("1.0.0")
                    .termsOfServiceUrl("http://localhost:8080")
                    .license("LICENSE")
                    .build();
        }
    }      