# egg-rpc
## 一个rpc的简单调用协议, 目前仅设计支持以stub形式远程调用功能，后续会提供egg系列配套的注册中心、路由转发功能，以完成完整RPC框架的搭建

## 大致介绍

# RPC框架

## ERPC

### 架构

- 角色

	- Registry

	  注册中心

	- Provider

	  服务提供方

		- register

			- 这块专门与注册中心Registry交互，目前没有用到，等到后续Registry完备后，补充这块的功能

		- transport

			- 这一层用来接收请求

				- 暴露一个端口，专门用于接口之间的通信

					- 默认12200
					- 可以通过application.properties中的erpc.transport.port修改

				- 粘包处理
				- 断包处理

		- serialize

			- 序列化相关

				- 默认使用Google的ProtoBuf序列化框架
				- 对参数进行打包，创建Packet包

					- Header

						- Length
						- Protocol

							- 默认erpc

						- requestId

						  请求id，面对断包的情况，需要设置一个id，保证接收多个数据包的时候，最终能够被组合起来

						- packetCount

						  包数量，表示当前请求一共有多少个包，用于应对断包的情况

						- packetNumber

						  当前包位于整个包数据中的位置，起点是1，在出现断包的情况时，该标志表示当前包是整个数据包中第几个。其他情况下默认是0

					- Body

						- Class
						- Method
						- Parameters

				- 后续会考虑自定义

		- protocol

			- 设置协议，包括默认的erpc协议
			- 支持协议转换

				- erpc <=> http
				- erpc <=> bolt
				- erpc <=> dubbo rpc

		- service

			- 用户自定义

				- XML配置文件
				- 注解

	- Subscriber

	  服务消费方

		- register

		  与Provider中的register一致

		- transport

			- 这一层用来发出请求

				- 断包处理

					- 设置requestId， packetCount，packetNumber

		- serialize

		  与Provider中的serialize功能一致，此处主要是对参数做序列化处理

		- protocol

		  与Provider中的protocol一致

		- refer

			- 引入sdk，用户自己设置引入的方式

				- XML配置文件
				- 注解

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

- 超时阈值，调用时长超出阈值进行处理

	- 直接抛错，ErpcInterruptedException
	- 重试机制，通过erpc.request.retry=true开发，默认是false

		- 重试次数通过参数erpc.request.retry.times=3设置

### 使用

- XML配置文件
- 注解



## 使用到的开源工具包
- junit: 4.13
- slf4j: 1.7.30
- slf4j-simple: 1.7.30