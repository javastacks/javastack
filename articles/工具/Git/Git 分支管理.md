
#### 添加分支

> git branch <name>

```
git branch test1.0
```

#### 切换分支

> git checkout <name>

```
git checkout test1.0
```

#### 添加分支并切换

> git checkout -b <name>

```
git checkout -b test1.0
```

#### 查看所有分支

> git branch


```
$ git branch
* master
  test1.0
```

#### 删除分支

> git branch -d <name>

```
git branch -d test1.0
```

#### 合并分支

合并到主干。

> git merge <name>

```
git merge test1.0
```

#### 提交分支

> git push origin <name>

```
git push origin test1.0
```

#### 查看分支差异

> git diff <source_branch> <target_branch>

```
git diff master test1.0
```
