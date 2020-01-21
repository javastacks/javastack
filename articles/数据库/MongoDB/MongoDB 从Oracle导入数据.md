
## 数据导出

从Oracle中把表中的数据导出为csv格式。

## 数据导入

使用MongoDB自带的工具：mongoimport。


```
mongoimport -h localhost -d dev -c t_data --type csv --file d:\t_data.csv --headerline --upsert
```
## 参数说明

`-h`：指定连接主机\
`-d`：指定数据库\
`-c`：指定集合\
`--type`：指定导入文件类型\
`--file`：指定导入文件\
`--headerline`：第一行作为字段名，CSV/TSV可用。\
`--upsert`：如果数据存在就使用替换模式。

更多输入`mongoimport --help`查看

或者查看官方定义：
> https://docs.mongodb.com/manual/reference/program/mongoimport/