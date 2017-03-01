Welcome to Galaxy
==============
> Galaxy for server side of mobile application with RESTful architectures.

## Requirements

  ```JDK >= 1.8.x```

## Modules
### core
基础模块，定义容器、异常、通用工具类等。

### data
数据&缓存模块，包含Redis-Cluster、Druid连接池、数据源配置等。

### mq
消息队列模块，包含Kafka的Producer和Consumer。

### worker
定时任务（数据刷新）。


## Usage
### worker
`mvn`打包会生成`tar`包，`scp`到目标机后执行`tar xvf galaxy-worker.tar.gz`，切换到目录`cd galaxy-worker/bin`执行`start.sh`即可。

## More
参考[Dubbo](https://github.com/alibaba/dubbo)
