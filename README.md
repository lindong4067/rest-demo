# rest-demo
基于REST接口的RPC框架

##快速开始
1.下载源码

git clone https://github.com/lindong4067/rest-demo.git

2.编译

cd rest-demo
mvn clean install

##系统架构

```puml
p -> n
c -> n
c -> p
```

##待完成
1. producer和consumer的配置项支持独立配置
2. 框架的集成方式待完成
3. 兼容gRpc协议
4. 支持接口粒度和服务粒度的服务注册与发现机制