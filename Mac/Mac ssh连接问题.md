- 连接报错
```
BadGuyMac:bin a$ ssh -p 22 root@xxx.xx.xxx.xxx
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@    WARNING: REMOTE HOST IDENTIFICATION HAS CHANGED!     @
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
IT IS POSSIBLE THAT SOMEONE IS DOING SOMETHING NASTY!
Someone could be eavesdropping on you right now (man-in-the-middle attack)!
It is also possible that a host key has just been changed.
The fingerprint for the ECDSA key sent by the remote host is
SHA256:6Qj8MMUoagecSBCEAVQYMgc64cc0/x4szkDZAAG40LI.
Please contact your system administrator.
Add correct host key in /Users/a/.ssh/known_hosts to get rid of this message.
Offending ECDSA key in /Users/a/.ssh/known_hosts:2
ECDSA host key for xxx.xx.xxx.xxx has changed and you have requested strict checking.
Host key verification failed.
```
- 原因分析：
    1. 每次远程连接后，会将远程机器的信息写到/Users/a/.ssh/known_hosts里
    2. 而远程机器信息改变(如重装系统)后，与本机存储的信息不符，所以出现以上报错
- 解决方案：
    1. 进入/Users/a/.ssh/known_hosts文件 
    2. 将远程主机对应ip的信息删除即可