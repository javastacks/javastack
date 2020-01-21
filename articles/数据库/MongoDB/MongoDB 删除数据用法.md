
## 主要方法

```
db.collection.deleteOne() 删除单条数据，3.2开始支持。

db.collection.deleteMany() 删除多条数据，3.2开始支持。

db.collection.remove()    删除单条或者多条数据。
```

## 示例集合


```
db.inventory.insertMany( [
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "P" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" },
]);
```

## 删除单条数据


```
db.inventory.deleteOne( { status: "D" } )
```

即使有多条满足条件也只删除一条。


## 删除多条数据


```
db.inventory.deleteMany({ status : "A" })
```

## 删除所有数据


```
db.inventory.deleteMany({})
```

## 注意事项

1、删除操作不会删除索引，即使删除所有数据。\
2、所有的单条数据写操作都是原子性的。
