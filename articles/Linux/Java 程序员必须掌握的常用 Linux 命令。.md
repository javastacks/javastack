![](http://img.javastack.cn/18-1-15/89474934.jpg)

Java程序员也是半个运维了，在日常开发中经常会接触到Linux环境操作。小公司的开发人员甚至是兼了全运维的工作，下面整理了一些常用的Linux操作命令。

**Linux常用指令**

```
ls　　        显示文件或目录

     -l       列出文件详细信息l(list)

     -a       列出当前目录下所有文件及目录，包括隐藏的a(all)
     
mkdir         创建目录

     -p       创建目录，若无父目录，则创建p(parent)

cd            切换目录

touch         创建空文件

vim / vi      创建/编辑文件

     insert   编辑
     
     :q       退出
     
     :q!      强制退出
     
     :wq      保存并退出
     
     esc      退出编辑

echo          创建带有内容的文件

cat           查看文件内容

tar           打包压缩

     -c       建立压缩档案
     
     -x       解压缩文件
     
     -z       gzip压缩文件
     
     -j       bzip2压缩文件
     
     -v       显示所有过程
     
     -f       使用档名
    
cp            拷贝

     -r       递归拷贝目录

mv            移动或重命名

rm            删除文件

     -r       递归删除，可删除子目录及文件

     -f       强制删除
     
chmod         变更文件或目录的权限

kill          杀进程

find          在文件系统中搜索某文件

wc            统计文本中行数、字数、字符数

grep          在文本文件中查找某个字符串

rmdir         删除空目录

tree          树形结构显示目录，需要安装tree包

pwd           显示当前目录

ln            创建链接文件

date          显示系统时间

more / less   分页显示文本文件内容

head / tail   显示文件头、尾内容

sudo          用来以其他身份来执行命令，预设的身份为root

su            换当前用户身份到其他用户身份

stat          显示指定文件的详细信息，比ls更详细

who           显示在线登陆用户

whoami        显示当前操作用户

hostname      显示主机名

uname         显示系统信息

top           动态显示当前耗费资源最多进程信息

ps            显示瞬间进程状态
     
     -e       显示所有进程
     
     -f       全格式

du            查看目录大小

     -s       只显示目录大小的总合
     
     -h       带单位显示目录大小

df            查看磁盘大小df 
    
     -h       带有单位显示磁盘信息
     
free          查看内存情况

     -b       单位（bytes）
     
     -k       单位（KB）
     
     -m       单位（MB）
     
     -g       单位（GB）

ifconfig      查看网络情况

ping          测试网络连通

netstat       显示网络状态信息

     -ano     查看某个端口是否被占用
     
     -tlnp    根据端口查找PID

man           查看Linux中的指令帮助

clear         清屏

kill          杀进程

reboot        重启系统

shutdown

     -r       关机重启

     -h       关机不重启

     now      立刻关机
```

以上命令仅供参考，欢迎留言补充。

