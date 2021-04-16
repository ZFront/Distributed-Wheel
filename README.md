## wheel

译为"轮子"：

是在业务开发过程中，自己整理的一套分布式常用套路。

实现网关分发、限流、熔断；分布式锁(zk锁、数据库锁、redis锁)；redis（锁、布隆过滤器、限流器）；mq；quartz定时器、基于美团leaf实现分布式ID等功能。

并在使用这些过程中，做了一些常用功能的扩展。


配合以下系列文章食用更加：

[分布式解决方案](https://www.yuque.com/zfront/rb6xfk)



## api-gateway

spring-cloud-gateway +  hystrix

实现了常用的网关功能，比如验参、分发、限流、熔断等功能。



[分布式限流](https://www.yuque.com/zfront/rb6xfk/rz7b16)



## service-lock

实现 zk锁，数据库锁，redis锁



[分布式锁](https://www.yuque.com/zfront/rb6xfk/ibgyls)



## service-mq

实现ActiveMQ、 RocketMQ基本收发功能

针对ActiveMQ做了一个扩展。



ActiveMQ健壮性监控：

原理：通过调用activemq Jolokia api来实现监控功能

包位置：com.wheel.mq.monitor

- broker消息积压的监控
- 无消费者队列的监控



ActiveMQ扩展消息轨迹功能：

//TODO



[MQ高可用](https://www.yuque.com/zfront/rb6xfk/mt1cxr)



## service-redis

实现redis常用操作、redis分布式限流器

[分布式限流](https://www.yuque.com/zfront/rb6xfk/rz7b16)

[Redis高可用](https://www.yuque.com/zfront/rb6xfk/pk9xrk)



## service-timer

quartz

实现通用定时器任务

[定时器设计](https://www.yuque.com/zfront/rb6xfk/hc6r43)



## service-unique

基于美团leaf实现的分布式ID

[分布式ID](https://www.yuque.com/go/doc/19449079)



## test-demo

对一些例子的测试