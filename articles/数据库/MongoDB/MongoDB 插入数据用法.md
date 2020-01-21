
## 主要方法

```
db.collection.insertOne()	插入单条数据。
db.collection.insertMany()	插入多条数据
db.collection.insert()	        插入单条或者多条数据。
```


## 插入单条

`db.collection.insertOne()`：MongoDB3.2开始支持。

语法：


```
db.collection.insertOne(
   <document>,
   {
      writeConcern: <document>
   }
)
```

使用：

```
db.inventory.insertOne(
   { item: "canvas", qty: 100, tags: ["cotton"], size: { h: 28, w: 35.5, uom: "cm" } }
)
```

insertOne不支持db.collection.explain()，需要使用insert。

## 插入多条

`db.collection.insertMany()`：MongoDB3.2开始支持。

语法：

```
db.collection.insertMany(
   [ <document 1> , <document 2>, ... ],
   {
      writeConcern: <document>,
      ordered: <boolean>
   }
)
```

使用：


```
db.inventory.insertMany([
   { item: "journal", qty: 25, tags: ["blank", "red"], size: { h: 14, w: 21, uom: "cm" } },
   { item: "mat", qty: 85, tags: ["gray"], size: { h: 27.9, w: 35.5, uom: "cm" } },
   { item: "mousepad", qty: 25, tags: ["gel", "blue"], size: { h: 19, w: 22.85, uom: "cm" } }
])
```

## 注意事项

1、所有的insert操作，如果集合不存在则会自动创建。\
2、_id是唯一主键，如果没有指定MongoDB会自动生成。\
3、所有的单条数据写操作都是原子性的。

