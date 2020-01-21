
## MongoDB中的索引

MongoDB中的索引和其他关系数据库中的索引类似，MongoDB索引是集合级别的，支持在任何字段及子字段上创建索引。

索引支持MongoDB高效的查询，没有索引将会进行全集合扫描，而如果使用索引则会大大缩小扫描的范围。

索引是特殊的数据结构（B-tree），它将收集数据集的一小部分存储在一个易于遍历的表单中。索引存储指定字段的值并按值排序，支持高效的值相等匹配查询及范围查询，也能根据索引中值的顺序直接返回有序结果。

下图演示了索引中的匹配和排序查询，1代表升序，－1代表降序。

![image](https://docs.mongodb.com/manual/_images/index-for-sort.bakedsvg.svg)

## 索引类型

MongoDB提供了许多索引类型来支持不同数据和查询的类型。

#### 1、默认的_id索引

在创建集合的时候就会为`_id`字段创建一个唯一索引，目的是为了防止`_id`字段值重复，这个索引不能被删除。

#### 2、单字段索引

MongoDB支持在单个字段上创建用户定义的顺序索引，而不用管是升序还是降序。

![image](https://docs.mongodb.com/manual/_images/index-ascending.bakedsvg.svg)

#### 3、复合索引

MongoDB也支持在多个字段上建立复合索引，复合索引中字段的顺序非常重要，如`{ userid: 1, score: -1 }`：先按userid升序，每个userid再按score进行降序。

#### 4、多键索引

MongoDB使用多键索引来索引存储在数组中的内容，如果索引包含数组值的字段，MongoDB将为数组的每个元素创建单独的索引条目。这些多键索引允许查询选择包含数组元素或数组元素的文档，MongoDB会自动决定是否创建一个多键索引，如果索引字段包含数组值;您不需要显式地指定多键类型。

![image](https://docs.mongodb.com/manual/_images/index-multikey.bakedsvg.svg)

#### 5、地理空间索引

为了支持对地理空间坐标数据的有效查询，MongoDB提供了两个特殊的索引:在返回结果时使用平面几何的2d索引和使用球形几何返回结果的2d球面索引。

#### 6、全文索引

MongoDB提供了一个文本索引类型，它支持在集合中搜索字符串内容。这些文本索引不存储特定于语言的stop单词(例如“the”、“a”、“或”)，并将单词集中在集合中，只存储根单词。

#### 7、哈希索引

为了支持基于散列的分片，MongoDB提供了一个散列索引类型，它索引了字段值的散列。这些索引在它们的范围内有一个更随机的值分布，但是只支持相等匹配，并且不能支持范围基。

## 索引命令操作

`db.collection.createIndex()`：创建索引。

`db.collection.dropIndex()`：删除指定索引。

`db.collection.dropIndexes()`：删除所有索引。

`db.collection.getIndexes()`：获取所有索引。

`db.collection.reIndex()`：重建所有索引。

