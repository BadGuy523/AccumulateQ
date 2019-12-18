###### windows环境
- 下载地址
```
http://jmeter.apache.org/download_jmeter.cgi
```
- 运行：解压后进入bin目录下点击jmeter.bat即可启动jmeter
![image](https://img-blog.csdn.net/20180921120451499?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
- 一个简单的压测实例(http://localhost:8080/goods/to_list)
    1. 新建一个线程组
    ![image](https://img-blog.csdn.net/20180921120508682?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    2. 设置线程组参数。这里配置为：10个线程，同时启动，循环一次
    ![image](https://img-blog.csdn.net/2018092112051715?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    3. 新增http请求默认值。 在上一步创建的线程组上，新增http请求默认值，所有的请求都会使用设置的默认值，这设置协议为http，IP为localhost，端口为8080。
    ![image](https://img-blog.csdn.net/20180921120527812?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    ![image](https://img-blog.csdn.net/20180921120539687?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    4. 添加要压测的http请求
    ![image](https://img-blog.csdn.net/20180921120552903?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    5. 下图第一个红框内的协议、IP、端口不需要设置，会使用步骤c中设置的默认值，只需设置请求路径Path即可，这里填入/goods/to_list
    ![image](https://img-blog.csdn.net/20180921120600481?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    6. 新增监听器，用于查看压测结果。这里添加三种：聚合报告、图形结果、用表格查看结果，区别在于结果展现形式不同。
    ![image](https://img-blog.csdn.net/20180921120606903?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
    7. 点击运行按钮开始压测，并查看结果。
    ![image](https://img-blog.csdn.net/20180921120612724?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3lhb3JvbmdrZQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)
[原文地址](https://blog.csdn.net/yaorongke/article/details/82799609)