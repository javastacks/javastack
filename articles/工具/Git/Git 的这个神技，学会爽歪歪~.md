现在大多数公司都有 GIT 来管理代码版本控制了，既然用到 GIT，相信大家都接触过 Github、Gitlab、Gitee 这些远程仓库，或者是公司内部自行搭建的 GIT 仓库。

当用到 SSH 方式来连接 GIT 仓库的时候，难免会同时用到多个仓库，一般生成公私钥的默认配置文件为：

- 私钥：C:\Users\xxx\.ssh\id_rsa
- 公钥：C:\Users\xxx\.ssh\id_rsa.pub

那么问题来了，我先生成 Github 的，再生成 GitLab 的，那么后面配置的 Gitlab 的公私钥文件会覆盖前面配置 Github 的，从而导致 Github 仓库无法连接。。

这样的配置只能同时连接一种类型的仓库，如何在同一台电脑做到同时连接多个不同的仓库呢？

## 一、生成多个仓库公私钥

1、生成 Github 的：

> ssh-keygen -t rsa -b 4096 -C "your_email@github.com" -f ~/.ssh/github_id_rsa

2、生成 Gitlab 的：

> ssh-keygen -t rsa -b 4096 -C "your_email@gitlab.com" -f ~/.ssh/gitlab_id_rsa

后面有多个仓库都依此类推，用 `-f` 来指定不同的文件名称：`xxx_id_rsa`，从而区分不同的仓库类型，而不用担心被后面默认生成的覆盖。 

## 二、将公钥添加到仓库里面

复制 `xxx_id_rsa.pub` 公钥文件里面的内容到对应仓库里面，以下 Github 示例：

![](http://img.javastack.cn/20190821105635.png)

## 三、添加 config 配置

在 `~/.ssh` 目录下创建 `config` 文件，添加以下内容：

```
# github
Host github.com
HostName github.com
PreferredAuthentications publickey
IdentityFile ~/.ssh/github_id_rsa

# gitlab
Host gitlab.com
HostName gitlab.com
PreferredAuthentications publickey
IdentityFile ~/.ssh/gitlab_id_rsa

# 多个依此类推
# ...
```

## 四、测试连通性

分别测试多个仓库的连通性，验证配置是否生效。

1、测试Github：

> $ ssh -T git@github.com

2、测试Gitlab：

> $ ssh -T git@gitlab.com

以下是 Github 连通示例：

```
$ ssh -T git@github.com
Enter passphrase for key '/c/Users/xxx/.ssh/github_id_rsa':
Hi javastacks! You've successfully authenticated, but GitHub does not provide shell access.
```

这样配置完，我们就能愉快的使用各种不同的仓库了~

本文原创首发于微信公众号：Java技术栈（id:javastack），获取更多 Git 神技，请在公众号后台回复：git，转载请原样保留本信息。

