
## 主要方法



```
db.collection.updateOne()	更新单条数据，3.2开始支持。

db.collection.updateMany()	更新多条数据，3.2开始支持。

db.collection.replaceOne()	替换单条数据，3.2开始支持。

db.collection.update()	        默认更新或者替换多条数据，更新多条数据需要配置选项。
```

## 示例集合


```
db.inventory.insertMany( [
   { item: "canvas", qty: 100, size: { h: 28, w: 35.5, uom: "cm" }, status: "A" },
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "mat", qty: 85, size: { h: 27.9, w: 35.5, uom: "cm" }, status: "A" },
   { item: "mousepad", qty: 25, size: { h: 19, w: 22.85, uom: "cm" }, status: "P" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "P" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" },
   { item: "sketchbook", qty: 80, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "sketch pad", qty: 95, size: { h: 22.85, w: 30.5, uom: "cm" }, status: "A" }
]);
```


## 更新单条数据


```
db.inventory.updateOne(
   { item: "paper" },
   {
     $set: { "size.uom": "cm", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```
`$set`：更新一个集合里面元素的值。

`$currentDate`：更新lastModified的值为当前时间，如果不存在这个字段则创建。

## 更新多条数据


```
db.inventory.updateMany(
   { "qty": { $lt: 50 } },
   {
     $set: { "size.uom": "in", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```

## 替换单条数据


```
db.inventory.replaceOne(
   { item: "paper" },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 40 } ] }
)
```

## 注意事项

1、所有的单条数据的写操作都是原子性的。\
2、`_id`字段一旦生成了就不能被修改。\
3、如果更新操作自动添加的数据超过了集合限定的大小，更新操作会重新组织磁盘上的文档数据。\
4、更新操作会按照插入时候的顺序保存字段的顺序，但`_id`永远排第一位的，更新包括字段名的重命名可能导致文档中字段的重新排序。


## 其他

[点击](https://docs.mongodb.com/manual/reference/operator/update/)查看更多更新操作符。

