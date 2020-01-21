
## Git是什么

Git是一款免费、开源的分布式版本控制系统，可以有效、高速的处理从很小到非常大的项目版本管理。

与常用的版本控制工具CVS、Subversion等不同的是它采用了分布式版本库的方式，不必服务器端软件支持，使源代码的发布和交流极其方便。Git的速度很快，最为出色的是它的合并跟踪的能力。

Git当初是Linus Torvalds为了帮助管理Linux内核源码替换BitKeeper 而开发的一个开放源码的版本控制软件。

## Git安装

安装地址：

> https://git-scm.com/downloads

可以在Mac OS X，Windows，Linux，Solaris这4个平台上进行安装。

安装过程略。

## Git配置

配置Git上个人的用户名称和电子邮件地址，用来记录提交人的信息。

```
$ git config --global user.name "your name"
$ git config --global user.email "your_email@youremail.com"
```

`--global`：表示全局配置，配置文件用户主目录，如C:\Users\Administrator\\.gitconfig`，如果没有这个选项只针对目前项目生效，配置文件为项目下的`.git/config`。



