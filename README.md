## 我的商城简介




## 内置功能

分成三种内置功能：
* 系统功能
* 研发工具


### 系统功能

|  | 功能    | 描述 |
| --- |-------| --- |
|  | 用户管理  | 用户是系统操作者，该功能主要完成系统用户配置 |
|  | 订单管理  | 当前系统中活跃用户状态监控，支持手动踢下线 |
|  | 商品服务  | 角色菜单权限分配、设置角色按机构进行数据范围权限划分 |
|  | 优惠券服务 | 配置系统菜单，操作权限，按钮权限标识等 |
|  | 商品服务  | 配置系统组织机构（公司、部门、小组），树结构展现支持数据权限 |
|  | 前台服务  | 配置系统用户所属担任职务 |



### 研发工具

|  | 功能 | 描述 |
| --- | --- | --- |
| 🚀 | 代码生成 |前后端代码的生成（Java、Vue、SQL、单元测试），支持 CRUD 下载 |
| 🚀 | 系统接口 | 基于 Swagger 自动生成相关的 RESTful API 接口文档 |
| 🚀 | 数据库文档 | 基于 Screw 自动生成数据库文档，支持导出 Word、HTML、MD 格式 |
| | 表单构建 | 拖动表单元素生成相应的 HTML 代码 |

## 在线体验

演示地址：<http://dashboard.yudao.iocoder.cn>
* 账号密码：admin/admin123  

文档地址：<http://www.iocoder.cn/categories/Yudao/>
* [《如何搭建环境》](http://www.iocoder.cn/categories/Yudao/?yudao)

> 未来会补充文档和视频，方便胖友冲冲冲！

## 技术栈

| 项目                  | 说明           |
|---------------------|--------------|
| `mall-dependencies` | Maven 依赖版本管理 |
| `mall-framework`    | Java 框架拓展    |
| `mall-member`       | 会员服务         |
| `mall-gateway`      | 服务网关         |


### 后端

| 框架                                                                          | 说明               | 版本      |  |
|-----------------------------------------------------------------------------|------------------|---------| --- |
| [Spring Boot](https://spring.io/projects/spring-boot)                       | 应用开发框架           | 2.6.7   | 
| [Spring Cloud](https://spring.io/projects/spring-cloud)                      | Spring Cloud     | 2021.0.1   | 
| [Spring Cloud Alibaba](https://github.com/alibaba/spring-cloud-alibaba.git) | 阿里巴巴云服务          | 2021.0.1.0  | 
| [MySQL](https://www.mysql.com/cn/)                                          | 数据库服务器           | 5.7     |  |
| [Druid](https://github.com/alibaba/druid)                                   | JDBC 阿里连接池、监控组件  | 1.2.11  |  |
| [MyBatis Plus](https://mp.baomidou.com/)                                    | MyBatis 增强工具包    | 3.5.2   |  |
| [Redis](https://redis.io/)                                                  | key-value 数据库    | 5.0     |  |
| [Spring Security](https://github.com/spring-projects/spring-security)       | Spring 安全框架      | 5.6.3   |  |
| [Knife4j](https://gitee.com/xiaoym/knife4j)                                 | Swagger 增强 UI 实现 | 3.0.3   |  |
| [SkyWalking](https://skywalking.apache.org/)                                | 分布式应用追踪系统        | 8.11.0  |  |
| [Spring Boot Admin](https://github.com/codecentric/spring-boot-admin)       | Spring Boot 监控平台 | 2.7.2   |  |
| [Lombok](https://projectlombok.org/)                                        | 消除冗长的 Java 代码    | 1.18.24 |  |
| [JUnit](https://junit.org/junit5/)                                          | Java 单元测试框架      | 5.7.1   | - |
| [Mockito](https://github.com/mockito/mockito)                               | Java Mock 框架     | 3.6.28  | - |
| [hutool](https://www.hutool.cn/)                                            | 甜品工具类            | 5.7.19  | - |

