
如果使用ssh的方式管理，需要配置ssh key.

#### 1、打开git bash命令窗口

#### 2、生成ssh key

```
ssh-keygen -t rsa -b 4096 -C "your_email@example.com"
```

`your_email@example.com`为github上你注册的email地址。

如下面完整创建过程：

```
$ ssh-keygen -t rsa -b 4096 -C "test@qq.com"
Generating public/private rsa key pair.
Enter file in which to save the key (/c/Users/Administrator/.ssh/id_rsa):
Enter passphrase (empty for no passphrase):
Enter same passphrase again:
Your identification has been saved in /c/Users/Administrator/.ssh/id_rsa.
Your public key has been saved in /c/Users/Administrator/.ssh/id_rsa.pub.
The key fingerprint is:
SHA256:chS9dRlB6Ee+E6/Ob9plXh5HXgDhhOk6mpST+oqrCxA test@qq.com
The key's randomart image is:
+---[RSA 4096]----+
|        .. o++++ |
|         .+oo.+  |
|E       .. +.+.  |
| .     .  o . +. |
|.     .oS.   . +o|
|.     =oo     ooo|
|.    o + .     +*|
|.  .. o      ..=*|
|ooo..o.      .++=|
+----[SHA256]-----+

```
上面默认生成在用户主目录的`.ssh`目录下，可以自己输入自定义位置。

#### 3、把ssh key添加到github

复制文件`c/Users/Administrator/.ssh/id_rsa.pub`内容，把key添加到：github > settings > SSH and GPG keys > New SSH key > 粘贴保存。

#### 4、测试SSH连接


```
$ ssh -T git@github.com
```

如下面表示已经连接成功：


```
$ ssh -T git@github.com
Hi Javastack! You've successfully authenticated, but GitHub does not provide shell access.
```

现在你可以通过SSH方式来clone及提交代码了。

更多详细配置请参考官方配置：

```
https://help.github.com/articles/connecting-to-github-with-ssh/
```
