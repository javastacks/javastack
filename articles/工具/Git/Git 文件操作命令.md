
#### 添加文件

> git add

添加指定文件：

```
git add test.txt Test.java
```

添加所有文件：

```
git add .
```

#### 取消添加文件

> git reset HEAD -- filename


```
git reset HEAD -- Test.java
```

这样Test.java将不会被提交到仓库中。

#### 删除文件

> git rm [--cached] fileName

默认会取消并删除文件，`--cached`表示不删除文件。

#### 移动/重命名文件

```
git mv filename1 filename2
```

该文件必须被add到仓库中才能操作。

#### 查看文件提交记录

> git log


```
$ git log
commit a3eb048ca74c3881f70264de90671d95474f241e (HEAD -> master, origin/master, origin/HEAD, javastack)
Author: javastack <javastack@qq.com>
Date:   Fri Sep 22 10:38:37 2017 +0800

    commit

commit 75336d6769e79581af8aefe2a15c9b2f305064c5
Author: javastack <javastack@qq.com>
Date:   Wed Sep 20 11:19:29 2017 +0800
```

