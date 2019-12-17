#### 定义与理解
- RESTful只是一种架构方式的约束，给出一种约定的标准，完全严格遵守RESTful标准并不是很多，也没有必要。但是在实际运用中，有RESTful标准可以参考，是十分有必要的。
- REST：Representational State Transfer（表象层状态转变）
    1. 每一个URI代表一种资源；
    2. 客户端和服务器之间，传递这种资源的某种表现层；
    3. 客户端通过四个HTTP动词（get、post、put、delete），对服务器端资源进行操作，实现”表现层状态转化”。
#### 六大原则
###### C-S架构
- 数据的存储在Server端，Client端只需使用就行。两端彻底分离的好处使client端代码的可移植性变强，Server端的拓展性变强。两端单独开发，互不干扰。
###### 无状态
- http请求本身就是无状态的，基于C-S架构，客户端的每一次请求带有充分的信息能够让服务端识别。请求所需的一些信息都包含在URL的查询参数、header、body，服务端能够根据请求的各种参数，无需保存客户端的状态，将响应正确返回给客户端。无状态的特征大大提高的服务端的健壮性和可拓展性。
- 当然这总无状态性的约束也是有缺点的，客户端的每一次请求都必须带上相同重复的信息确定自己的身份和状态（这也是必须的），造成传输数据的冗余性，但这种确定对于性能和使用来说，几乎是忽略不计的。
###### 统一的接口
- 这个才是REST架构的核心，统一的接口对于RESTful服务非常重要。客户端只需要关注实现接口就可以，接口的可读性加强，使用人员方便调用。
###### 一致的数据格式
- 服务端返回的数据格式要么是XML，要么是Json（获取数据），或者直接返回状态码
- 自我描述的信息，每项数据应该是可以自我描述的，方便代码去处理和解析其中的内容。比如通过HTTP返回的数据里面有 [MIME type ]信息，我们从MIME type里面可以知道数据的具体格式，是图片，视频还是JSON，客户端通过body内容、查询串参数、请求头和URI（资源名称）来传送状态。服务端通过body内容，响应码和响应头传送状态给客户端。这项技术被称为超媒体（或超文本链接）。
- 除了上述内容外，HATEOS也意味着，必要的时候链接也可被包含在返回的body（或头部）中，以提供URI来检索对象本身或关联对象。
- 如请求一条微博信息，服务端响应信息应该包含这条微博相关的其他URL，客户端可以进一步利用这些URL发起请求获取感兴趣的信息，再如分页可以从第一页的返回数据中获取下一页的URT也是基于这个原理。
###### 系统分层
- 客户端通常无法表明自己是直接还是间接与端服务器进行连接，分层时同样要考虑安全策略
###### 可缓存
- 在万维网上，客户端可以缓存页面的响应内容。因此响应都应隐式或显式的定义为可缓存的，若不可缓存则要避免客户端在多次请求后用旧数据或脏数据来响应。管理得当的缓存会部分地或完全地除去客户端和服务端之间的交互，进一步改善性能和延展性
###### 按需编码、可定制代码（可选）
- 服务端可选择临时给客户端下发一些功能代码让客户端来执行，从而定制和扩展客户端的某些功能。比如服务端可以返回一些 Javascript 代码让客户端执行，去实现某些特定的功能。
提示：REST架构中的设计准则中，只有按需编码为可选项。如果某个服务违反了其他任意一项准则，严格意思上不能称之为RESTful风格。
#### 实践
###### 版本
- 就是将版本放在url，简洁明了，一般的项目加版本v1，v2，v3
```
https://example.com/api/v1/
```
###### 参数命名规范
- query parameter可以采用驼峰命名法，也可以采用下划线命名的方式，推荐采用下划线命名的方式，据说后者比前者的识别度要高
```
https://example.com/api/users/today_login 获取今天登陆的用户 
https://example.com/api/users/today_login&sort=login_desc 获取今天登陆的用户、登陆时间降序排列
```
###### url命名规范
- API 命名应该采用约定俗成的方式，保持简洁明了。在RESTful架构中，每个url代表一种资源所以url中不能有动词，只能有名词，并且名词中也应该使用复数。实现者应使用相应的Http动词GET、POST、PUT、PATCH、DELETE、HEAD来操作这些资源即可
```
#不规范的的url，冗余没有意义，形式不固定，不同的开发者还需要了解文档才能调用。
https://example.com/api/getallUsers GET 获取所有用户 
https://example.com/api/getuser/1 GET 获取标识为1用户信息 
https://example.com/api/user/delete/1 GET/POST 删除标识为1用户信息 
https://example.com/api/updateUser/1 POST 更新标识为1用户信息 
https://example.com/api/User/add POST 添加新的用户
#规范后的RESTful风格的url，形式固定，可读性强，根据users名词和http动词就可以操作这些资源
https://example.com/api/users GET 获取所有用户信息 
https://example.com/api/users/1 GET 获取标识为1用户信息 
https://example.com/api/users/1 DELETE 删除标识为1用户信息 
https://example.com/api/users/1 Patch 更新标识为1用户部分信息,包含在body中 
https://example.com/api/users POST 添加新的用户
```
###### 统一返回数据格式
- 对于合法的请求应该统一返回数据格式，这里演示的是json
    1. code——包含一个整数类型的HTTP响应状态码。
    2. status——包含文本：”success”，”fail”或”error”。HTTP状态响应码在500-599之间为”fail”，在400-499之间为”error”，其它均为”success”（例如：响应状态码为1XX、2XX和3XX）。这个根据实际情况其实是可要可不要的。
    3. message——当状态值为”fail”和”error”时有效，用于显示错误信息。参照国际化（il8n）标准，它可以包含信息号或者编码，可以只包含其中一个，或者同时包含并用分隔符隔开。
    4. data——包含响应的body。当状态值为”fail”或”error”时，data仅包含错误原因或异常名称、或者null也是可以的

```
#返回成功的响应json格式
{
  "code": 200,
  "message": "success",
  "data": {
    "userName": "123456",
    "age": 16,
    "address": "beijing"
  }
}
#返回失败的响应json格式
{
  "code": 401,
  "message": "error  message",
  "data": null
}
```
###### http状态码
- HTTP状态码本身就有足够的含义，根据http status code就可以知道删除、添加、修改等是否成功。服务端向用户返回这些状态码并不是一个强制性的约束。简单点说你可以指定这些状态，但是不是强制的。
- 常用HTTP状态码对照表，HTTP状态码也是有规律的
    1. 1**请求未成功
    2. 2**请求成功、表示成功处理了请求的状态代码。
    3. 3**请求被重定向、表示要完成请求，需要进一步操作。 通常，这些状态代码用来重定向。
    4. 4** 请求错误这些状态代码表示请求可能出错，妨碍了服务器的处理。
    5. 5**（服务器错误）这些状态代码表示服务器在尝试处理请求时发生内部错误。这些错误可能是服务器本身的错误，而不是请求出错。
###### 合理使用query parameter
- 在请求数据时，客户端经常会对数据进行过滤和分页等要求，而这些参数推荐采用HTTP Query Parameter的方式实现
```
#比如设计一个最近登陆的所有用户
https://example.com/api/users?recently_login_day=3
#搜索用户，并按照注册时间升序、活跃度降序
https://example.com/api/users?q=key&sort=create_title_asc,liveness_desc
```
[原文地址](https://blog.csdn.net/kebi007/article/details/102927209)