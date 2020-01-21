## 示例集合

```
db.inventory.insertMany([
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "A" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" }
]);
```


## 查询所有


```
db.inventory.find()

db.inventory.find({})
```


## 按条件查询

查询status='D'的数据。


```
db.inventory.find( { status: "D" } )
```


## IN范围查询

查询status in ('A', D')的数据。

```
db.inventory.find( { status: { $in: [ "A", "D" ] } } )
```

## AND条件查询

查询status='A'并且qty<30的数据。

```
db.inventory.find( { status: "A", qty: { $lt: 30 } } )
```

## OR条件查询

查询status='A'或者qty<30的数据。

```
db.inventory.find( { $or: [ { status: "A" }, { qty: { $lt: 30 } } ] } )
```

## AND OR联合条件查询

查询status='A'并且(qty<30或者item以p开头)的数据。


```
db.inventory.find( {
     status: "A",
     $or: [ { qty: { $lt: 30 } }, { item: /^p/ } ]
} )
```

## 其他

[点击](https://docs.mongodb.com/manual/reference/operator/query/)查看各种操作运算符用法。

[点击](https://docs.mongodb.com/manual/reference/operator/query/regex/#op._S_regex)查看正则表达式用法。

