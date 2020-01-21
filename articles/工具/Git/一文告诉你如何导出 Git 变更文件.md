![](http://img.javastack.cn/18-6-7/58683179.jpg)

有时候我们想导出某次版本提交时有哪些变更的文件，在 svn 中有一个 export 功能，很方便，如下图所示。

![](http://img.javastack.cn/18-5-24/63533749.jpg)

**在 Git 中我也找到了以下两种方法。**

#### 方法1

使用 git 自带命令 `git archive`, 语法如下。

```
git archive -o c:/Users/yourusername/Desktop/export.zip NewCommitId $(git diff --name-only OldCommitId NewCommitId)
```

使用示例如下：

```
git archive -o c:/Users/yourusername/Desktop/export.zip 479d554cf570edcc28c20ce264c6f216f8223bf3 $(git diff --name-only a838d0512e84e5eb42569cce3ef305d3ac1c44d0 479d554cf570edcc28c20ce264c6f216f8223bf3)
```

这样会在桌面生成一个 `export.zip` 压缩包。

这个方法需要敲命令，且每次拷贝前后提交的版本号，会稍显麻烦。

#### 方法2

下载 `TortoiseGit` Git 客户端软件，使用方法和 svn 的 export 功能类似。

![](http://img.javastack.cn/18-5-24/17828748.jpg)

很明显，方法2不用敲命令，不用拷贝提交版本号，方法2更方便，更实用！

但遗憾的是，我没有在 Source Tree 软件中找到类似的功能，需要装两个客户端，有点蛋疼。如果你知道 Source Tree 中类似的导出变更文件功能，可以在下方留言。



