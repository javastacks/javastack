![image](https://docs.mongodb.com/manual/_images/crud-annotated-collection.bakedsvg.svg)

## Databases（数据库）

在MongoDB中，数据库是所有文档记录构成的集合的汇总，类似关系数据库中数据库与表的概念。

**创建数据库**


```
use test;
```


创建/切换数据库，如果不存在则创建后切换，存在则直接切换。


## Collections（集合）

集合类似关系数据库中的表的概念，不过集合是没有表结构的。

**创建集合**


```
db.myNewCollection1.insertOne( { x: 1 } )
db.myNewCollection2.createIndex( { y: 1 } )
```


插入记录、创建索引如果集合不存在都会自动创建该集合。

当然MongoDB也提供了[db.createCollection()](https://docs.mongodb.com/manual/reference/method/db.createCollection/#db.createCollection)方法来创建集合，它提供了更多的创建选项，如最大集合容量、文档验证规则等。如果你要使用默认参数创建集合用上面的自动创建的方式就可以了。

## Document Validation（文档验证）

这是MongoDB3.2版本推出来的新功能，默认情况下MongoDB不强制要求文档记录有着相同的字段和数据类型，也可以说是集合里面的记录可以有着不同的字段和不同的数据类型，这和关系数据库完全不同，关系数据库表结构类型是固定好的。

但在3.2之后你可以为update和insert制定验证规则，如：


```
db.createCollection( "contacts",
   { validator: { $or:
      [
         { phone: { $type: "string" } },
         { email: { $regex: /@mongodb\.com$/ } },
         { status: { $in: [ "Unknown", "Incomplete" ] } }
      ]
   }
} )
```

如果插入或者更新的数据不符合规则MongoDB可以设定不允许更新并报错的形式或者可以允许更新但同时会在日志中记录违反规则的数据。

验证规则更多细节请[点击](https://docs.mongodb.com/manual/core/document-validation/)参考官方描述。

## 文档结构修改

可以更新MongoDB集合中的文档结构，如添加新字段、删除现有字段或更改字段类型、更新文档的新结构。

