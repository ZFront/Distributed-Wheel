## spring cloud gateway 

actuator 监控

相关指令： 请求地址 ==> /actuator/gateway/

| ID              | HTTP Method        | Description                                     |
| :-------------- | :----------------- | :---------------------------------------------- |
| `globalfilters` | GET                | 展示所有的全局过滤器                            |
| `routefilters`  | GET                | 展示所有的过滤器工厂（GatewayFilter factories） |
| `refresh`       | POST【无消息体】   | 清空路由缓存                                    |
| `routes`        | GET                | 展示路由列表                                    |
| `routes/{id}`   | GET                | 展示指定id的路由的信息                          |
| `routes/{id}`   | POST【消息体如下】 | 新增一个路由                                    |
| `routes/{id}`   | DELETE【无消息体】 | 删除一个路由                                    |



## 方法说明

test-demo：com.wheel.demo.controller.DemoApiController



