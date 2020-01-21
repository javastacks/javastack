
#### 创建仓库


```
git init
```

在当前目录执行，会生成`.git`目录文件，这个和SVN一致。

#### 提交到仓库

```
git commit -m "first commit"
```

`-m`：表示提交描述，必须要填。

#### 添加到远端仓库

```
git remote add origin git@github.com:test/test.git
```


#### 推送到远端仓库


```
git push -u origin master
```

#### 克隆仓库

直接从远端把代码克隆下来。


```
git clone git@github.com:test/test.git
```

#### 仓库状态


```
git status
```

```
$ git status
On branch master

Initial commit

Untracked files:
  (use "git add <file>..." to include in what will be committed)

        README.md

nothing added to commit but untracked files present (use "git add" to track)
```

#### 仓库更新

```
git pull
```
就等同下面。

```
git fetch
git merge
```
