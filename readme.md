# egg-rpc
## 一个rpc的简单调用协议, 目前仅设计支持以stub形式远程调用功能，后续会提供egg系列配套的注册中心、路由转发功能，以完成完整RPC框架的搭建

## 大致介绍

# RPC框架

## 主流的RPC框架

### Dubbo

- 架构

	- 角色

		- Registry

		  注册中心

		- Provider

		  服务提供者

			- Protocol
			- Service
			- Container

		- Consumer

		  服务消费者

			- Protocol
			- Cluster
			- Proxy

		- Monitor

		  监控系统

	- 过程

		- 1. registry

			- Provider注册到Registry（init）

		- 2. subscribe

			- Consumer订阅到Registry（init）

		- 3. notify

			- Registry通知Consumer能够调用方法的Provider信息，包括节点、状态等（async）

		- 4. invoke

			- Consumer调用Provider提供出来的方法（sync）

		- 5. count

			- Provider将方法发布数量、被调用数据统计到监控系统（async）
			- Consumer将调用的远程方法信息统计到监控系统（async）

- 能力

### Motan

- 架构

	- 角色

		- Registry

		  注册中心

		- Server

		  服务发布方

			- register

			  和Registry交互，包括注册服务、订阅服务、服务变更通知、服务心跳发送等功能

			- transport

			  用来进行远程通信，默认使用Netty NIO的tcp长链接模式

			- serialize

			  将RPC请求中的参数、结果等对象进行序列化和反序列化

			- protocol

			  用来进行RPC服务的描述和RPC服务的配置管理
			  这一层可以添加不同功能的filter用来完成统计、并发限制

			- service

			  暴露具体的接口服务

		- Client

		  服务消费方

			- register
			- transport
			- serialize
			- protocol
			- refer

			  引用sdk

- 能力

### Tars

- 架构
- 能力

### SpringCloud

- 架构

	- 由多个组件一起构成

- 能力

### gRPC

通过IDL（Interface Definition Language）文件定义服务接口的参数和返回类型，然后通过代码生成程序生成服务端和客户端的具体实现代码，通过这种方式，客户端可以像调用本地对象一样调用另一台服务器上对应的方法

- 架构
- 能力
- 特性

	- 通信协议采用HTTP/2

		- 连接复用
		- 双向流
		- 服务器推送
		- 请求优先级
		- 首部压缩

	- IDL使用了ProtoBuf

		- 压缩高效
		- 传输高效

	- 多语言支持

		- 基于多种语言自动生成对应语言的客户端和服务端代码

### Thrift

- 架构
- 能力
- 特性

	- 支持多种序列化格式

		- Binary
		- Compact
		- JSON
		- Multiplexed

	- 支持多种通信方式

		- Socket
		- Framed
		- Memory
		- zlib

	- 服务端支持多种处理方式

		- Simple
		- Thread Pool
		- Non-Blocking

## ERPC

### 架构

- 角色

	- Registry

	  注册中心

	- Provider

	  服务提供方

	- Subscriber

	  服务消费方

### RPC调用过程

- The client calls the client stub.The call is a local procedure call,with parameters pushed onto the stack in the normal way.
- The client stub packs the parameters into a message and makes a system call to send the message.Packing the parameters is called marshalling.
- The client's local operating system sends the message from the client machine to the server machin
- The local operating system on the server machine passes the incoming packets to the server stub.
- The server stub unpacks the parameters from the message.Unpacking the parameters is called unmarshalling.
- Finally, the server stub calls the server procedure.The reply traces the same steps in the reverse direction.

### 能力

- 仅用于Java之间的RPC调用

### 特性

### 使用

- XML配置文件
- 注解

## 使用到的开源工具包
- junit: 4.13
- slf4j: 1.7.30
- slf4j-simple: 1.7.30