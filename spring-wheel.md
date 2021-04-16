## wheel

翻译为"轮子"，是在业务开发过程中，自己整理的一套分布式常用套路。

包含了spring-gateway网关、分布式锁、redis、mq、定时器、分布式ID等功能



## api-gateway

spring-cloud-gateway +  hystrix

实现了常用的网关功能，比如验参、分发、限流、熔断等功能



[分布式限流](https://www.yuque.com/zfront/rb6xfk/rz7b16)



## service-lock

实现 zk锁，数据库锁，redis锁



[分布式锁](https://www.yuque.com/zfront/rb6xfk/ibgyls)



## service-mq

实现ActiveMQ、 RocketMQ



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





## test-demo