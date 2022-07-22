KBase 非结构化数据库管理系统

参考：《K-SQL 10参考大全》

<hr>



# 一、KSQL字符集、标识符、语句分类

- 字符可以是数字、字母、专用字符和汉字
- KBASE 系统处理的字符类型：一般字段类型完全支持GBK编码、UTF-8编码。



- 标识符分为正规标识符和定界标识符两大类。
  - 正规标识符以字母、下划线或汉字开头，后面可以跟随字母、数字、下划线或者汉字
    - 正规标识符的例子：A，test1，_TABLE_B，表1
  - 定界标识符的标识符体用英文单引号和双引号括起来时，标识符体可以包含任意字符
    - 定界标识符的例子："table"，"A"，"!@#$"。
    - 如果定界标识符的标识符体中包含英文单引号和双引号，则需要特别方式处理。



- 根据功能的不同，KSQL语句的功能分类如下
  - 定义
    - 库的创建和删除、表的创建、修改和删除、视图的创建和删除、索引的创建和删除。
  - 查询
    - 精确查询、模糊查询、多表查询、连接查询、分组查询、排序查询等。
  - 操纵
    - 插入记录、删除记录、更新记录、重整表、计算全字段的外部因子等。
  - 统一扩展管理
    - 包括所有以关键字 DBUM 开头的 对SQL做了大量扩展的其他KBase语句。

# 二、运算符（逻辑、符合、比较、检索通配、位置描述、数据分割、匹配运算、模糊匹配、相关匹配）

- 逻辑
  - 主要用于KSQL语句的WHERE子句，用于查询条件的各种逻辑组合。

| 符号 | 意义     |
| ---- | -------- |
| AND  | 逻辑“与” |
| OR   | 逻辑“或” |
| NOT  | 逻辑“非” |

- 复合
  - 主要用于检索关键字的复合表示，可以表达复杂、高效的检索语句，并且可以简化KSQL语句（相对于使用逻辑运算符）。

| 符号 | 意义     |
| ---- | -------- |
| +    | 逻辑“或” |
| -    | 逻辑“非” |
| *    | 逻辑“与” |

- 比较
  - 没有不等于比较运算符，有不等于的要求时，可以采用逻辑操作符“NOT”或者复合列值运算符“-”通过构造逻辑表达式来代替。

| 符号 | 意义     |
| ---- | -------- |
| >    | 大于     |
| <    | 小于     |
| >=   | 大于等于 |
| <=   | 小于等于 |

- 检索通配符
  - 主要用于CHAR、VCHAR、MVCHAR、WORD、STRING、STRCHAR等类型索引的检索匹配。

| 符号 | 意义              |
| ---- | ----------------- |
| ?    | 相隔1个字符       |
| *    | 相隔0个或多个字符 |

- 位置描述符
  - 主要用于TEXT、TEXTCHAR、LTEXT、LTEXTCHAR、TITLE、QTEXT等等文本索引类型，用来完成复杂的KSQL查询语句

| 符号    | 意义                                                         |
| ------- | ------------------------------------------------------------ |
| #       | ’ STR1 # STR2’：表示包含STR1和STR2，且STR1、STR2在同一句中   |
| %       | ’ STR1 % STR2’：表示包含STR1和STR2，且STR1 与STR2在同一句中, 且STR1在STR2前面 |
| /NEAR N | ’ STR1 /NEAR N STR2’：表示包含STR1和STR2，且STR1 与 STR2 在同一句中，且相隔不超过N个字词 |
| /PREV N | ’ STR1 /PREV N STR2’：表示包含STR1和STR2，且STR1 与 STR2 在同一句中，STR1在STR2前面不超过N个字词 |
| /AFT N  | ’ STR1 /AFT N STR2’：表示包含STR1和STR2，且STR1 与 STR2 在同一句中，STR1在STR2后面且超过N个字词 |
| /SEN N  | ’ STR1 /SEN N STR2’：表示包含STR1和STR2，且STR1 与 STR2 在同一段中，且这两个词所在句子的序号差不大于N。 |
| /PRG N  | ’ STR1 /PRG N STR2’：表示包含STR1和STR2，且STR1 与 STR2 相隔不超过N段 |
| $ N     | ‘STR $ N’：表示所查关键词STR最少出现N次                      |

- 数据分隔符
  - 主要用于多值型数据存储类型，即一条记录的单一字段可以有多个值，我们一般将这里的多个值称作子值。

| 编码    |          | 支持的分隔符 |      |      |      |      |      |
| ------- | -------- | ------------ | ---- | ---- | ---- | ---- | ---- |
| GBK     | 半角字符 | ,            | ;    | !    | \    |      |      |
|         | 全角字符 | ，           | ；   | ！   | 、   | ＠   | ￥   |
| UNICODE | 半角字符 | ,            | ;    | !    | \    |      |      |
|         | 全角字符 | ，           | ；   | ！   | 、   | ＠   | ￥   |

- 匹配
  - 可用于各种索引类型中，不同的索引类型，匹配代表的意义不完全一致。

| 符号 | 意义       |
| ---- | ---------- |
| =    | 匹配运算符 |



- 模糊匹配
  - 模糊匹配运算符可用于各种索引类型中，不同的索引类型，模糊匹配代表的意义不完全一致。

| 符号 | 意义           |
| ---- | -------------- |
| %    | 模糊匹配运算符 |



- 相关匹配
  - 主要用在文本索引类型中

| 符号 | 意义           |
| ---- | -------------- |
| %=   | 相关匹配运算符 |



# 三、数据类型（存储类型+索引类型、索引模式）

- 数据类型由数据存储类型和数据索引类型来表达。
  - 存储类型和索引类型的组合，大大丰富了 KBase 系统能提供的数据特性。

## 1 数据存储类型

| 存储类型 | 最大长度(字节) | 支持的编码      | 变长 | 说明                                                         |
| -------- | -------------- | --------------- | ---- | ------------------------------------------------------------ |
| INTEGER  | 1-32(需指定)   | ASCII           | 否   | 整数，长度为用字符串形式保存数据的长度，数据范围是 32 位有符号整数的范围。 |
| DATE     | 8-32(需指定)   |                 |      | 日期                                                         |
| TIME     | 14-32(需指定)  |                 |      | 时间                                                         |
| NUM      | 1-32(需指定)   |                 |      | 浮点数，长度为用字符串形式保存数据的长度                     |
| INT64    | 14-32(需指定)  |                 |      | 整数，长度为用字符串形式保存数据的长度，数据范围是 64 位有符号整数的范围。 |
| AUTO     | 12             |                 |      | 自增，从1自动增加，不能指定值 总是有索引                     |
| CHAR     | 1-254(需指定)  | ASCII、 UNICODE |      | 字符串，支持“单一”索引                                       |
| VCHAR    | 254            |                 | 是   | 变长字符串，支持“单一”索引                                   |
| MVCHAR   | 32K            |                 |      | 多值字符串                                                   |
| LMVCHAR  | 64K/2G         |                 |      | 多值字符串                                                   |
| MTEXT    | 4K             |                 |      | 文本                                                         |
| LTEXT    | 16M            |                 |      | 文本                                                         |
| VECTOR   | 16M            | ASCII           |      | 向量字段                                                     |
| MAP      | \              | \               | \    | 虚字段，根据映射关系确定                                     |
| SOB      | 254            | BIN             | 是   | 二进制，不能索引                                             |
| LOB      | 16M            |                 |      | 二进制，不能索引                                             |
| DOB      | 2G             |                 |      | 数字对象字段                                                 |
| DOCUMENT | 16M            | ASCII           |      | 文本                                                         |
| DOCPATH  | 1024           |                 |      | 文本，保存的是连接到文档的文件名                             |
| TNAME    | \              | \               | \    | 虚拟字段，返回表名称                                         |
| TANAME   | \              | \               | \    | 虚拟字段，返回表的显示名称                                   |
| RECORDID | \              | \               | \    | 虚拟字段，返回物理记录号                                     |
| RELEVANT | \              | \               | \    | 虚拟字段，返回检索结果的相关度                               |
| SNAPSHOT | \              | \               | \    | 虚拟字段，返回与检索相关的特定字段的检索快照                 |
| VIRTUAL  | \              | \               | \    | 虚拟字段，返回其映射到的其他一个或多个字段                   |





### 1.1 INTEGER、NUM和INT64类型

分别表示32位整数， 64位整数和浮点数

| 类型    | 最小值          | 最大值         |
| ------- | --------------- | -------------- |
| INTEGER | -2^31 +1        | 2^31-2         |
| INT64   | -2^63+1         | 2^63-2         |
| NUM     | -3.4028233e+038 | 3.4028233e+038 |

- 与一般传统数据库不一样，KBase系统中的数值型数据存储类型的精度不仅受限于由其可表达的最大数据范围，还受限于其定义长度。
- NUM类型为浮点数类型，它有着比整数更大的数据范围，但在计算中会产生四舍五入的误差。KBase系统中，浮点数类型数据可以用科学记数法表达。



### 1.2 AUTO类型

数据类型AUTO为自增字段。一般其数据为从1开始随物理记录号递增的自然数。

### 1.3 DATE 和 TIME类型

用于记录日期、时间。



DATE类型表示日期，KBase系统的日期的由“年月日”表达，标准的日期格式有：

| 格式一 | yyyy/mm/dd     |
| ------ | -------------- |
| 格式二 | yyyy:mm:dd     |
| 格式三 | yyyy-mm-dd     |
| 格式四 | yyyymmdd       |
| 格式五 | yyyy.mm.dd     |
| 格式六 | yyyy年mm月dd日 |

> 其中：yyyy 表示年份，mm 表示月份，dd 表示日期



TIME类型表示时间，KBase系统的时间有“年月日”和“时分秒”两部分组成

| 格式一： | hh:mm:ss     |
| -------- | ------------ |
| 格式二： | hh-mm-ss     |
| 格式三： | hh/mm/ss     |
| 格式四： | hhmmss       |
| 格式五： | hh时mm分ss秒 |



### 1.3 CHAR 和 VCHAR类型

- CHAR类型用于存储定长字符串，其最大长度可由设计者自行设定，但最大长度不能超过254个字符。
  - CHAR数据类型通常用于容纳如人名、公司名、地址等内容，因为这类数据的长度通常差别不大。

- VCHAR类型用于存储变长字符串，但最大长度超过254个字符。



如果数据最大长度小于16，总是应该选用CHAR类型，如果数据最大长度大于64，一般选择VCHAR会有更好的性能。



### 1.4 MVCHAR类型

是多值的CHAR类型。每个子值以多值分隔符分隔。

| 编码    |          | 支持的分隔符 |      |      |      |      |      |
| ------- | -------- | ------------ | ---- | ---- | ---- | ---- | ---- |
| GBK     | 半角字符 | ,            | ;    | !    | \    |      |      |
|         | 全角字符 | ，           | ；   | ！   | 、   | ＠   | ￥   |
| UNICODE | 半角字符 | ,            | ;    | !    | \    |      |      |
|         | 全角字符 | ，           | ；   | ！   | 、   | ＠   | ￥   |

### 1.5 LMVCHAR类型

另一种多值的CHAR类型。

与MVCHAR提供的应用级特性完全一样，区别在于系统底层的处理上，LMVCHAR更适合记录内容的反复修改。



### 1.6 MTEXT 和 LTEXT类型

MTEXT类型用于存储较小的文本，最大长度不能超过32K个字符。

LTEXT类型用于存储较大的文本内容，最大长度可以达到4M个字符。



> KBase系统利用MTEXT和LTEXT来储存各种文本数据，如期刊全文、报纸全文、图书全文等等。
>
> LTEXT实际存储的长度达到4M，这个数据长度对于实际的应用而言，几乎没有限制。这相当于一条记录单一字段容量达到2百万汉字，20万字图书都可以存放10本左右。



### 1.7 SOB、LOB和DOB类型

这三个字段用于处理无结构字节流，如存储压缩视频图像、音频数据、可执行代码等数据。

- SOB处理小对象，最大长度为254字节

- LOB处理大对象，最大长度为4M字节

- 而DOB则有能力处理更大的对象，长度可达2G字节。



### 1.8 VECTOR类型

KBASE系统提供的通用的向量模型。

### 1.9 DOCUMENT / DOCPATH类型

KBase用于管理文档数据的数据存储类型。

### 1.10 VIRTUAL类型

我们称之为虚字段。这种字段实际上并不占有物理存储空间。

但是，一旦对这种字段定义了相应的映射关系，就可以在这种字段上进行各种实际的操作。包括，读取内容、建立索引、进行检索等等。

### 1.11 SNAPSHOT等类型

为了保持与旧版系统的兼容性，保留了TNAME、SNAPSHOT等一类函数型字段。

**新的应用中，应该尽量避免使用这些类型，而使用相应的函数。**

| 类型     | 意义                               | 替换函数                         |
| -------- | ---------------------------------- | -------------------------------- |
| TNAME    | 返回表名称                         | GETSYSFIELD(‘_ _TABLENAME ’ )    |
| TANAME   | 返回表的显示名称                   | GETSYSFIELD(‘_ _TABLEALIASNAME’) |
| RECORDID | 返回物理记录号                     | GETSYSFIELD(‘_ _RID’)            |
| RELEVANT | 返回检索结果的相关度               | GETSYSFIELD(‘_ _RELEVANT’)       |
| SNAPSHOT | 返回与检索相关的特定字段的检索快照 | GETSNAPSHOT ( ‘被映射的列名’ )   |



## 2 数据索引类型（4类）

### 数值型数据索引类型

包括INTEGER、QINTEGER、NUM、DATE、QDATE、TIME、INT64，这几种数据类型特点与作用传统关系数据库类似。

### 字符串数据索引类型

包括CHAR、ECHAR、MVCHAR、EMVCHAR、WORD，这几种数据类型特点适用于不同应用目标的字符串型数据，提供不同的字符串检索。

### 文本型数据索引类型

包括STRING、STRCHAR、MSTRCHAR、TEXT、TEXTCHAR、LTEXT、LTEXTCHAR、LFTEXT、TITLE、EXTITLE、EXTITLECHAR、QSTRING、QSTRCHAR、ABSTRACT、COMPACTQTEXT、QTEXT、TYTDTEXT、TYTEXT主要是为了管理非结构化文档而设计的。

```bash
这是KBase系统与传统关系数据库主要区别之一，借助于这些数据类型可以在KBase中实现高速简捷的全文检索。
```



### 其他特定用途的数据索引类型

包括VECTOR、FINGERPRINT、SEARCHENGINE、MIX

```bash
这些字段类型各有特点，为KBase系统引入很多有特色的查询功能。
```



**对于各种数据存储类型，我们可以根据应用的需要选用各种索引类型，如下是各种数据存储类型通常情况下可以选用的索引类型对照表。**

| 存储类型                                | 推荐的索引类型                              | 可能合适的索引类型                  |
| --------------------------------------- | ------------------------------------------- | ----------------------------------- |
| INTEGER                                 | INTEGER、QINTEGER                           | INT64、NUM                          |
| DATE                                    | DATE、QDATE                                 |                                     |
| TIME                                    | TIME                                        |                                     |
| NUM                                     | NUM                                         |                                     |
| INT64                                   | INT64                                       | INTEGER 、NUM                       |
| AUTO                                    | INTEGER                                     |                                     |
| CHAR VCHAR                              | CHAR、ECHAR、STRING、STRCHAR、WORD、EXTITLE | 所有其他字符串、文本型索引类型      |
| MVCHAR LMVCHAR                          | MVCHAR、EMVCHAR、MSTRCHAR                   | 所有其他文本型索引类型              |
| VECTOR                                  | VECTOR                                      |                                     |
| MTEXT                                   | EXTITLE、QSTRING、QTEXT                     | 所有其他文本型索引类型              |
| LTEXT                                   | EXTITLE、QSTRING、QTEXT                     | FINGERPRINT、所有其他文本型索引类型 |
| VIRTUAL                                 | SEARCHENGINE、MIX                           | 根据映射关系,所有其他类型           |
| SOB LOB DOB                             | 无                                          |                                     |
| DOCUMENT                                | EXTITLE、QSTRING、QTEXT                     | FINGERPRINT、所有其他文本型索引类型 |
| DOCPATH                                 | EXTITLE、QSTRING、QTEXT                     | FINGERPRINT、所有其他文本型索引类型 |
| TNAME TANAME RECORDID RELEVANT SNAPSHOT | 无                                          |                                     |



### 2.1 INTEGER、QINTEGER 类型（重要）

在INTEGER索引上支持的检索特性主要是为了实现整数这种数值的查询需求。

QINTEGER 检索特性与INTEGER完全一致，但QINTEGER通过额外的索引数据，使其“比较查询”的性能很高。



KBase 系统在INTEGER索引上支持的检索特性包括：精确查询、比较查询、范围查询。

```bash
# 精确查询
返回字段值与“检索数值”相等的记录。

# 比较查询
返回字段值与“检索数值”相比，符合比较关系的记录。

SELECT	*
FROM	CCND2009
WHERE	下载频次= ‘350’

# 范围查询
 <检索字段名>   BETWEEN ( <最小值>, <最大值>)

```

### 2.2 INT64类型

INT64检索功能特性与INTEGER完全一样。

### 2.3 NUM类型

按浮点数做索引，支持科学计数法。NUM支持的检索功能特性与INTEGER完全一样。



### 2.4 DATE、TIME和QDATE类型

在DATE、TIME索引上支持的检索特性主要是为了实现日期、时间这种数值的查询需求

- DATE对应日期类型

- TIME时间上对应的是时间戳类型。

KBase 系统在这几个索引上支持的检索特性包括：精确查询、比较查询、模糊查询、范围查询。

```sql
# 精确查询
<检索字段名>  = <检索值>

# 比较查询
<检索字段名><比较运算符><检索值>

# 模糊查询
<检索字段名> % <检索值>

# 范围查询
<检索字段名>   BETWEEN ( <最小值>, <最大值>)

------------------------------------------------------------------------------------------

## 查找“CCND2009”表中“发表时间”为“2009年05月01日”的记录，利用精确查询可以用 如下 SQL 语句实现。
SELECT	*
FROM	CCND2009
WHERE	发表时间=”2009-05-01” 

## 查找“CCND2009”表中“发表时间”为2009年05月的记录。利用比较查询可以用 如下 SQL 语句实现。
SELECT	*
FROM	CCND2009
WHERE	发表时间>=”2009-05-01”  
AND	发表时间<”2009-06-01” 

## 我们也可以利用模糊查询用 如下 SQL 语句实现
SELECT	*
FROM	CCND2009
WHERE	发表时间%”2009-05”  

```



### 2.5 CHAR和 ECHAR类型

ECHAR与CHAR具有完全相同的检索特性，只是ECHAR更适合索引“性别”这种有大量重复数据的列。

```sql
# （1）精确查询
<检索字段名>  = 检索词



## 检索“CCND2009”表的“文件名”列为“SZSB20020514A011”的记录
SELECT	*
FROM	CCND2009
WHERE	文件名='SZSB20020514A011'  
- 在KBase系统中，所有检索值都是大小不敏感的，等价如下
SELECT	*
FROM	CCND2009
WHERE	文件名='szsb20020514A011'  

# （2）前方一致查找
语法：<检索字段名>  = 检索词 + <*/?>
结果：返回字段值前面部分与“检索词”完全匹配（但忽略字符大小写）的记录。

## 检索“CCND2009”表的“文件名”列中，以字符串“SZSB”开始的所有记录。
SELECT	*
FROM	CCND2009
WHERE	文件名='SZSB*'  


# （3）包含查找
语法： <检索字段名>  = <*/?> + 检索词
结果： 返回字段值中包含“检索词”（但忽略字符大小写）的记录。

- 建议：同样建议使用‘*’ 匹配操作符
## 检索“CCND2009”表的“文件名”列中，包含字符串’ 2002’的所有记录。（如下也能命中“文件名”为“SZSB20020514A011”的记录。）
SELECT	*
FROM	CCND2009
WHERE	文件名='*2002'  

# （4）?/* 模式查找
语法： <检索字段名> = 检索模式串
结果： 返回与“检索模式串”完全匹配的记录。

## 检索“CCND2009”表的“文件名”列中，以字符串’ SZSB’开头，并且’ SZSB’ 和’ A’ 之间相隔4个或4个以上个字符，子串’A’后有任意个字符记录。
SELECT	*
FROM	CCND2009
WHERE	文件名='SZSB????*A*' 

# （5）比较查找
语法： <检索字段名><比较运算符><检索词>

## 检索“CCND2009”表的“文件名”列中，查出大于等于子串’ SZSB20020514A1’的记录。
SELECT	*
FROM	CCND2009
WHERE	文件名>='SZSB20020514A1' 

# （6）模糊查找
语法： <检索字段名>  %  <检索词>

## 检索“CCND2009”表的“分类号”列中，查出与“E12”匹配或与前面部分串匹配的记录。
SELECT	*
FROM	CCND2009
WHERE	分类号% 'E12' 

- 这个SQL将能命中分类号为“E12”、“E1”、“E”的记录，注意，不能命中分类号为“E12_03”等形式的记录。
```



### 2.6 MVCHAR和 EMVCHAR类型

MVCHAR就是多值的CHAR索引，即一个MVCHAR字段，由多个CHAR组成，每个CHAR为一个子值，子值由分隔符分隔。

MVCHAR索引类型能够识别的子值分隔符同数据存储类型的MVCHAR一致。

```bash
## 检索“CCND2009”表的“专题代码”列中，查出含有子值“F092”的记录。
SELECT	*
FROM	CCND2009
WHERE	专题代码='F092'

## 查询“专题代码”既为'E072'又为'E073'的记录
SELECT	*
FROM	CCND2009
WHERE	专题代码='E072'
AND	专题代码='E073'
```



### 2.7 WORD类型

KSQL支持对WORD数据类型?和*两种匹配检索。

- 其中? 代表一个字符，* 代表任意个字符。
- ? 和* 可以出现在检索条件的任意位置，出现任意次。



```sql
# 精确查询
同 CHAR 索引一样，支持精确查询。

# ?/* 模式查找
语法： <检索字段名> = 检索模式串

## 检索“CCND2009”表的“题名”列中，以“城市”和 “发展”之间相隔3个或3个以上字符，且以“城市”开始，“发展”结尾的记录。
SELECT	*
FROM	CCND2009
WHERE	题名='城市*???发展'

## 检索“CCND2009”表的“题名”列中，以“城市”和“道路”之间相隔任意个字符，“道路”和“发展”之间相隔1个字符，且以“城市”开始，“发展”结尾的记录。
SELECT	*
FROM	CCND2009
WHERE	题名='城市*道路?发展'


```

### 2.8 STRING和 STRCHAR类型

KBase系统中最小型的索引文本数据的类型，它仅仅针对数据的前面254字节做索引。

STRING和STRCHAR区别是：

- STRING在做索引时，是分词后索引的。
- 而STRCHAR是按字切分索引的，“按字切分索引”我们通常称之为不分词索引。



```sql
# (1)精确查找

## 不能命中“篇名”为“近代美国农业科技的引进及其影响评述”的记录，但可以命中“篇名”为“农业科宣传记事”的记录。
SELECT	*
FROM	CJFD2003
WHERE	篇名='农业科'

## 命中“篇名”为“近代美国农业科技的引进及其影响评述”的记录。
SELECT	*
FROM	CJFD2003
WHERE	篇名='农业科技'

# (2)?/* 模式查找
## 将能查出“篇名”字段中，包含子串'债务'和'经济'
SELECT	*
FROM	CJFD2003
WHERE	篇名='债务*经济'
```

### 2.9 MSTRCHAR类型

另一种多值类型索引。可以简单的看作MVCHAR和STRCHAR类型的结合体。

```sql
# 精确查询
## 检索“CCND2009”表的“作者”列为“梅承恩”的记录
SELECT	*
FROM	CCND2009
WHERE	作者='梅承恩'

# 模糊查找
## 检索“CCND2009”表的“作者”列含有“李”的记录
SELECT	*
FROM	CCND2009
WHERE	作者%'李'

# 序位查询
## 检索“期刊”表的“作者”列的第一个子值为“梅承恩”的记录(这个KSQL将会命中第一作者是“梅承恩”的记录。)
SELECT	*
FROM	CCND2009
WHERE	作者='梅承恩 /SUB 1'
```

### 2.10 TEXT、TEXTCHAR、LTEXT和LTEXTCHAR类型

TEXT和TEXTCHAR是KBase系统中最早提供的大文本数据索引类型



LTEXT和LTEXTCHAR是TEXT和TEXTCHAR的改进索引类型。

> 新的系统中，应尽可能采用这些字段类型，其差别体现在性能上。改进后的LTEXT对比TEXT，在检索速度有显著提高，并且索引的数据量节省了25%左右。



```sql
# 精确查找

# 位置查找
## 检索“CCND2009”表的“全文”列中，包含子串'网格'和'系统'，且'网格'和'系统'出现在同一句中的记录。
SELECT	*
FROM	CCND2009
WHERE	全文='网格 # 系统'

# 词频查找
## 在表CCND2009库中检索’计算机’这个词在“正标题”中至少出现两次的记录的结果。

SELECT	*
FROM	CCND2009
WHERE	正标题='计算机 $ 2'

# 自动扩展查询
## 自动扩检是利用KBase系统自带的扩展检索词典（SYS_XLDICT）来实现的
### 针对以上，如果扩展检索词典（SYS_XLDICT）的“计算机”对应的扩展词条有：“电脑”、“computer”，那么实际上相当于我们执行了如下检索：
SELECT	*
FROM	CCND2009
WHERE	正标题='计算机'
OR	正标题='电脑'
OR	正标题=' computer '

# 模糊查找

自动扩展查询也提供模糊查找的方式。
```



| 格式                          | 含义                                                         |
| ----------------------------- | ------------------------------------------------------------ |
| ‘STR1***\*#\**** STR2’        | 表示包含STR1和STR2，且STR1、STR2在同一句中；                 |
| ‘STR1 ***\*%\**** STR2’       | 表示包含STR1和STR2，且STR1 与STR2在同一句中, 且STR1在STR2前面； |
| ‘STR1 ***\*/NEAR N\**** STR2’ | 表示包含STR1和STR2，且STR1 与 STR2 在同一句中，且相隔不超过N个词/字； |
| ‘STR1 ***\*/PREV N\**** STR2’ | 表示包含STR1和STR2，且STR1 与 STR2 在同一句中，STR1在STR2前面不超过N个词/字； |
| ‘STR1 ***\*/AFT N\**** STR2’  | 表示包含STR1和STR2，且STR1 与 STR2 在同一句中，STR2在STR1后面至少N个词/字； |
| ‘STR1 ***\*/SEN N\**** STR2’  | 表示包含STR1和STR2，且STR1 与 STR2 在同一段中，且这两个词所在句子的序号差不大于N； |
| ‘STR1 ***\*/PRG N\**** STR2’  | 表示包含STR1和STR2，且这两个词所在段落的序号差不大于N。      |

### 2.11 TITLE、EXTITLE、EXTITLECHAR、QSTRING、QSTRCHAR类型

TITLE索引类型，是为了专门适应针对文章标题这种数据来设计的。其支持的检索与LTEXT完全一样。



EXTITLE基本同TITLE，其区别是TITLE索引时采用了停用字，EXTITLE索引时不采用停用字，在实际应用中可能更适合文章标题这种数据类型。

EXTITLECHAR 与 EXTITLE 检索特性一致，区别仅仅在分词处理上，也就是说，EXTITLE支持分词，而EXTITLECHAR 不分词，按字符来做索引。

```sql
# 精确查找
# 位置查找
# 词频查找
# 自动扩展查询
# 模糊查找
# 全文索引中停用词的影响
```

### 2.12 ABSTRACT类型

是为了提高全文检索性能来实现的。其支持的检索功能与TEXT完全一样。

```sql
# 精确查找
# 位置查找
# 词频查找
# 自动扩展查询
# 模糊查找
```



### 2.13 COMPACTQTEXT类型

### 2.14 QTEXT类型

```sql
# 精确查找
# 位置查找
# 词频查找
# 自动扩展查询
# 模糊查找

# 相关查找
语法： <检索字段名>  %=  <检索词>
结果： 返回字段值与“检索词”相比，最相关的部分记录。


```

### 2.15 LFTEXT类型

其功能检索特性与QTEXT几乎完全一样，差别在于LFTEXT仅仅使用回车和换行符作为分句符号。



### 2.16 TYTDTEXT和TYTEXT类型

基本检索功能与LTEXTCHAR完全一样。

```sql
# 表 TEST 字段“词汇”，其五条记录如下
记录序号	“词汇”字段值
1					实施
2					试试
3					石狮
4					事实
5					實施


# 执行KSQL：
SELECT	*
FROM	test
WHERE	词汇=实施

如果字段“词汇”为 LTEXTCHAR，“正常”索引模式，则能命中记录序号为 1 的记录。
如果字段“词汇”为 LTEXTCHAR，“词干”索引模式，则能命中记录序号为 1、5 的记录。
如果字段“词汇”为 TYTDTEXT，“正常”索引模式，则能命中记录序号为 1、3、5 的记录。
如果字段“词汇”为 TYTEXT，“正常”索引模式，则能命中记录序号为 1、2、3、4、5 全部记录。
```

### 2.17 VECTOR类型

```sql
# 精确查找
# 模糊查找
```

### 2.18 FINGERPRINT类型

全文指纹检索类型。

### 2.19 SEARCHENGINE类型

提供搜索引擎类似的检索功能。



### 2.20 MIX类型

MIX 类型并不提供实际的索引，一般情况下，它给VIRTUAL数据字段实现检索功能。

其实现检索的方法是直接查找VIRTUAL数据字段映射的字段的索引。因此，其检索特性由数据字段本身的映射字段的索引来决定。

### 2.21 SMARTS类型

是用于全文字段的一种索引，它给虚字段提供索引。



## 3 数据索引模式

KBASE系统不仅提供了丰富的数据索引类型，来实现各种检索特性，还进一步同时支持多种不同的索引模式，来实现更多的检索特性。

KBASE系统支持的索引模式包括： ***\*NON\****（无索引）、***\*NORMAL\****（普通索引）、***\*UNIQ\****（单一索引）、***\*STEM\****（词干索引）。

```sql
1 NON（无索引）
只能支持精确查询，对正常索引和唯一索引都支持前面提到的所有检索方式。
正常索引和唯一索引在使用上是一致的，其差别体现在性能上。唯一索引更适合不重复的字段。

2 NORMAL（普通索引）
适合几种数值型索引以外的索引类型。其特征是，在索引结构中每个关键字只记录一次，即关键字唯一。

3 UNIQ（单一索引）
适合数值型索引和CHAR系列的索引类型。
其特征是，在索引结构中每条记录记录一次，这意味着索引结构中关键字可以重复。

4 STEM（词干索引）
适合所有的文本型索引类型。
其特征是，对英文的数据，会统一按词干进行索引。中文数据会统一繁简来索引，而不同于以前仅仅忽略大小写来索引。
```





# 四、KSQL数据定义语句（库、表、视图、索引等）

## 库

```sql
## 创建数据库MYDB。
CREATE DATABASE	MYDB
## 删除数据库MYDB。
DROP DATABASE	MYDB
```



## 表

```sql
# 创建表
CREATE [<表类型>] TABLE <表定义>  [<表的附加定义>] (<列列表>)  [WITHSTORAGESPACE<存储空间定义列表>]
<表定义> ::=  <表名> [PATH <路径名>] [AT <数据库名>]
<列列表> ::=  <列定义> { , <列定义>}
<列定义> ::=  <列名><数据存储类型> [(<列长度>)] [<编码>] [INDEX <数据索引类型>] [<索引模式>] [<索引方法>] [ALIASNAME<列别名>]  [DISPLAYNAME<列显示名>] [DEFAULT <缺省值>]
<表的附加定义> ::= ALIASNAME <表别名> [<表拥有者><表密码>]
<存储空间定义> ::= <存储空间> {, <存储空间> }
<存储空间> ::= <存储空间类型><存储空间名字> [(<列名> {，<列名>} )]

## 
CREATE TABLE	STUDENT
PATH	'C:\DATA\STUDENT'
AT	MYDB
	(SNO INTEGER NORMAL, NAME CHAR(30),AGE INTEGER)
	
	
# 修改表
## 添加列
## 为表STUDENT添加一个ADDRESS列，在该列上建有普通索引，该列位置定为第二列,再添加一个POLITICS列，该列上无索引。	
ALTER TABLE 	STUDENT
ADD	ADDRESS CHAR(40) NORMAL AT 1, POLITICS CHAR(10) NON

## 修改列
## 修改表STUDENT的ADDRESS列，将其列名改为“家庭住址”，不建立索引，别名改为“住址”，位置改为第四列。
ALTER TABLE 	STUDENT
ALTER	ADDRESS
AS	家庭住址 CHAR(40) NON ALIASNAME 住址 AT 3

## 删除列
## 删除表STUDENT的“ADDRESS”列和“POLITICS”列。
ALTER TABLE 	STUDENT
DROP	ADDRESS, POLITICS


# 修改表的物理路径
## 表STUDENT的物理路径为D:\KBase\STUDENT，修改其物理路径变为D:\NewData\ STUDENT。
ALTER TABLE 	STUDENT
PATH	‘D:\NewData\STUDENT’

# 删除表
## 删除表STUDENT。
DROP TABLE 	STUDENT

# 表的高级操作


```





## 视图

```sql
# （1）创建
## 语法
CREATE VIEW <视图名字> AS <SELECT语句>
## 创建视图 VSTUDENT
CREATE VIEW	VSTUDENT
AS	SELECT * FROM STUDENT WHERE

# （2）删除
创建视图 VSTUDENT。
DROP  VIEW	VSTUDENT

# （3）视图嵌套
KBASE 系统支持两级的视图嵌套。即允许视图作为上面<SELECT语句>中的检索对象，但已经包含视图的视图，不能再被嵌套。
```



## 索引

支持四种索引模式：***\*NON\****（无索引）、***\*NORMAL\****（普通索引）、***\*UNIQ\****（单一索引）、***\*STEM\****（词干索引），缺省为***\*NON\****。

```sql
# 建立
## 给STUDENT表中姓名重新建立索引。
INDEX	STUDENT ON NAME

# 删除
## 删除表STUDENT中篇名列的索引。
DBUM DROP INDEX	篇名
OF TABLE	STUDENT

# 激活/禁止表的索引
## 禁止表STUDENT篇名列的索引。
DBUM ACTIVATE INDEX	篇名
OF TABLE	STUDENT
	FALSE
```





## 存储空间

存储空间**旨在提高读取数据的速度**，其基本原理是将一个表内的数据存储位置按照列的不同分成多个部分，用户检索时可能不需要访问所有部分，以此减少不必要的磁盘I/O。

```sql
# 在创建表时指定存储空间
# 创建表后定义存储空间
CREATE STORAGESPACE  [SHARED/INDEPENDENT] <存储空间名>ONTABLE<表名>

# 添加某列到指定的存储空间
ALTER TABLE	STUDENT
ADD	COL2 INTEGER
ON STORAGESPACE	S1
```



## 索引空间

```sql
# 创建一个索引空间
CREATE INDEXSPACE <索引空间名>
	ON TABLE <表名>

# 修改一个或多个索引的索引空间位置
CREATE INDEXSPACE	姓名
ON TABLE	STUDENT

DBUM MOVE INTO INDEXSPACE	姓名
	NAME
ON TABLE	STUDENT

CREATE INDEXSPACE	频次
ON TABLE	期刊
```





## 字段映射关系

```sql
# 添加字段映射关系
ALTER TABLE <表名>ADD FIELDMAP 
	<映射字段名><源字段名><权重>
	[, <映射字段名><源字段名><权重> ]
	[, <映射字段名><源字段名><权重> ]

## 添加表STUDENT的字段映射关系：源字段NAME映射到字段SNO，权重为1
ALTER TABLE	STUDENT
ADD FIELDMAP	SNO
	NAME
	1
	
# 删除字段映射关系
ALTER TABLE <表名>DROP FIELDMAP 
	<映射字段名><源字段名>
	[, <映射字段名><源字段名>]
	[, <映射字段名><源字段名>]
```





# 五、KSQL数据查询语句（单表、多表、跨库、排序、分组、过滤、嵌套过滤、其它）

KBase功能特性，主要通过KBase提供的数据查询功能来体现。

如同标准SQL语法，KSQL的查询语法，和 SQL的查询语法类似，下面详细阐述。

## 1 单表查询

```sql
# 语法格式
SELECT <选择列表>
FROM  <表名>
[<WHERE 子句>] 
[<FILTER BY 子句>] 
[<FILTER BY NESTEDFILTER 子句>] 
[<GROUP BY 子句>] 
[<ORDER BY 子句>]  


<FILTER BY 子句> 详细说明见后面“过滤查询”章节。
<FILTER BY NESTEDFILTER子句> 详细说明见后面“嵌套过滤查询”章节。
<GROUP BY 子句> 详细说明见后面“分组查询”章节。
<ORDER BY 子句> 详细说明见后面“排序查询”章节。
```





- <复合检索关键词> 是一种复杂、高效的检索表示，+ 表示取或(OR),- 表示取非(NOT),*表示取(AND)。

```sql
# 设表“CJFD2004”存储了2004年中国期刊文章，其创建表的语句为
CREATE 	
TABLE	CJFD2004
	(
篇名 VCHAR(255) ASCII INDEX TITLE NORMAL,
作者 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
机构 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
关键词 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
中文摘要MTEXT(32767) ASCII INDEX ABSTRACT NORMAL,
全文 LTEXT ASCII INDEX LTEXT NORMAL),
引文 LTEXT(2147483647) ASCII INDEX COMPACTQTXT NORMAL,
被引频次 INTEGER(8) ASCII INDEX INTEGER UNIQ,
分类号 MVCHAR(32767) ASCII INDEX MVCHAR NORMAL ALIASNAME 中图分类号,
专题子栏目代码 MVCHAR(32767) ASCII INDEX EMVCHAR NORMAL,
中文刊名 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL ALIASNAME 显示来源,
出版日期 DATE(16) ASCII INDEX QDATE UNIQ ALIASNAME 发表时间,
文件名 CHAR(24) ASCII INDEX CHAR UNIQ
)


注：下面的检索都是针对“CJFD2004”表进行。

# 检索1：检索全部文章
SELECT 	*
FROM	CJFD2004

# 检索2：检索篇名出现了“计算机”的文章
SELECT 	*
FROM	CJFD2004
WHERE	篇名=”计算机”

# 检索篇名出现了“计算机”或“电脑”的文章。
SELECT 	*
FROM	CJFD2004
WHERE	篇名=”计算机”+”电脑”

# 检索篇名出现了“计算机”并且是“清华大学”发表的文章。
SELECT 	*
FROM	CJFD2004
WHERE	篇名=”计算机” AND 机构=”清华大学”
```



## 2 多表查询

```sql
# 语法格式
SELECT <选择列表>
FROM <表名列表>
[<WHERE 子句>] 
[<FILTER BY 子句>] 
[<FILTER BY NESTEDFILTER 子句>] 
[<GROUP BY 子句>] 
[<ORDER BY 子句>] 
```

- <表名列表> 中的多个表或视图应该具有相同的结构，如果结构不一致，最好在<选择列表>中指明需要返回的列
- 如果用‘*’作为<选择列表>，KBASE 系统会默认<表名列表>所有的表与第一个表的结构相同，并以第一个表的列作为返回结果集的列



```sql
# 设表“CJFD2004”存储了2004年中国期刊文章，“CJFD2003”存储了2003年中国期刊文章。检索篇名出现了“计算机”并且是清华大学发表的2003和2004两年的文章，KSQL查询语句如下

SELECT 	*
FROM	CJFD2004,CJFD2003
WHERE	篇名=”计算机” AND 机构=”清华大学”
```





## 3 跨库查询



- 跨库查询是指在一次检索中，同时指定多个库作为检索目标，其查询结果等价于分别查询各库得到查询结果的“并集”。
- 所谓“并集”，是指跨库查询不是简单的将多个库的查询结果混合到一起，它对查询结果做了“排重”处理。
  - 排重的任务是识别多个库间的“重复记录”，只保留一条记录在结果集中。
- 跨库查询时，多个库有主库和从库之分。当所有主库的查询都完成时，跨库查询才可以返回，但跨库查询返回时并不要求从库的查询都执行完毕。主库和从库往往有两个区别：
  - 主库的优先级别高于从库的优先级别。
  - 主库查询的速度高于从库的速度。



```sql
# KBase数据库针对跨库查询提供了专门的KSQL语句，语法格式
VARSELECT <选择列表>
FROM <库名列表> | <视图名>
[<WHERE 子句>] 
[<FILTER BY 子句>] 
[<FILTER BY NESTEDFILTER 子句>] 
[<GROUP BY 子句>] 
[<ORDER BY 子句>]  
WITH <主库信息> ',' <重复库信息> ',' <排重字段信息>

```





```sql
# 1
# 查询“中国学术期刊全文数据库”和“重庆维普期刊数据库”中篇名包含“空间数据库”的所有文章，
# 主库是“中国学术期刊全文数据库”，“中国学术期刊全文数据库”和“重庆维普期刊数据库”有重复记录，需要排重，排重字段是篇名。

VARSELECT 	*
FROM	中国学术期刊全文数据库, 重庆维普期刊数据库
WHERE	篇名=”空间数据库”
WITH	MAINDB=中国学术期刊全文数据库, DUPDB=中国学术期刊全文数据库, REFCOL=篇名
# MAINDB主库、DUPDB重复库、REFCOL排重字段信息

# 2
# 查询“中国学术期刊全文数据库”， “中国优秀博硕士学位论文全文数据库”和“重庆维普期刊数据库”中关键词包含“空间数据库”的所有文章，
# 主库是“中国学术期刊全文数据库”和“中国优秀博硕士学位论文全文数据库”，“中国学术期刊全文数据库”和“重庆维普期刊数据库”有重复记录，需要排重，排重字段是篇名。”
VARSELECT 	*
FROM	中国学术期刊全文数据库, 中国优秀博硕士学位论文全文数据库，重庆维普期刊数据库
WHERE	关键词=”空间数据库”
WITH	MAINDB=(中国学术期刊全文数据库, 中国优秀博硕士学位论文全文数据库), DUPDB=中国学术期刊全文数据库, REFCOL=篇名

# 3
# 查询“重庆维普期刊数据库”和“GOOGLE”中关键词包含“空间数据库”的所有文章，没有主库，因而不需要排重。”
VARSELECT 	*
FROM	重庆维普期刊数据库，GOOGLE
WHERE	关键词=”空间数据库”
WITH	MAINDB=NULL, DUPDB=NULL, REFCOL=NULL

```



## 4 排序查询



```sql
# 查询：查找篇名含有“计算机”的文章，并按照相关度排序。
SELECT 	*
FROM	CJFD2004
WHERE	篇名=计算机
ORDER BY	RELEVANT

# KBase 作为管理海量信息的数据库系统，其查询结果集往往会比较大，为提高性能和可用性，系统排序默认返回前2000条结果。也就是说，在默认情况下，前2000条结构排序是有序的，2000条以后的结果不保证有序。

# 如果用户要求结果中，有序的范围更大，可以使用TOP N 条件。如上例，如果希望前3000条有序，则可以写成
SELECT 	被引频次
FROM	CJFD2004
ORDER BY	TOP 3000 被引频次 Desc
```



如下：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220523232649855.png)



## 5 分组查询

- 按照指定列分组。KBASE中的分组与常规关系数据库基本相同。

```sql
# 查询：按照机构对CJFD2004分组。
SELECT 	*
FROM	CJFD2004
GROUP BY	机构
```



## 6 过滤查询



- 过滤查询是KBASE系统提供的一种查询优化的方法。

```sql
# 1
SELECT 	篇名,分类号
FROM	CJFD2004
WHERE	分类号 = "A?" OR分类号 = "B?"

# 利用过滤查询可以写作
SELECT 	篇名,分类号
FROM	CJFD2004
FILTER BY	(分类号, "A?, B?" ) 


# 2
SELECT 	*
FROM	CJFD2004
WHERE	全文=计算机 and (专题子栏目代码= A?+B01?+C?+E?+F?)

# 采用过滤查询方案，将能极大提高性能
SELECT 	*
FROM	CJFD2004
WHERE	全文=计算机
FILTER BY	(专题子栏目代码, ‘A?,B01?,C?,E?,F?’ )

```



## 7 嵌套过滤查询

- 嵌套过滤查询是KBASE系统提供的另一种过滤查询方法。

```sql
# 这里期刊库和作者库的结构如下
CREATE 	
TABLE	CJFD2004
	(
篇名 VCHAR(255) ASCII INDEX TITLE NORMAL,
作者 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
机构 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
关键词 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
中文摘要MTEXT(32767) ASCII INDEX ABSTRACT NORMAL,
全文 LTEXT ASCII INDEX LTEXT NORMAL),
引文 LTEXT(2147483647) ASCII INDEX COMPACTQTXT NORMAL,
被引频次 INTEGER(8) ASCII INDEX INTEGER UNIQ,
分类号 MVCHAR(32767) ASCII INDEX MVCHAR NORMAL ALIASNAME 中图分类号,
专题子栏目代码 MVCHAR(32767) ASCII INDEX EMVCHAR NORMAL
)

CREATE 	
TABLE	CNKI_SCHOLAR
	(
学者 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
学者代码 CHAR(20) ASCII INDEX CHAR NORMAL,
学者机构代码 CHAR(20) ASCII INDEX CHAR NORMAL,
学者单位 MVCHAR(32767) ASCII INDEX MSTRCHAR NORMAL,
学者职称 MVCHAR(32767) ASCII INDEX MVCHAR NORMAL,
文献篇数 INTEGER(8) ASCII INDEX INTEGER UNIQ,
职务 MVCHAR(32767) ASCII INDEX MVCHAR NORMAL,
职务级别 MVCHAR(32767) ASCII INDEX MVCHAR NORMAL,
科技成果文献数 INTEGER(8) ASCII INDEX INTEGER UNIQ)
)



# 从“CJFD2004”中检索“篇名”中含有“激光”，并且其作者所属机构是“清华大学”职称为“教授”发表文献大于200篇作者的文献。

SELECT 	*
FROM	CJFD2004
WHERE	篇名=激光 
FILTER BY	 NESTEDFILTER ( 作者代码 in 
select 作者代码 FROM CNKI_SCHOLAR WHERE 学者单位='清华大学'
AND 学者职称=教授
AND 文献篇数>200 )
```



## 8 其它KSQL高级查询



### 8.1 聚集函数

- 对一列求聚集函数

```sql
# 语法格式
SELECT <聚集函数>
FROM <表名列表>
[<WHERE 子句>]
[<GROUP BY 子句>] 
[<ORDER BY 子句>] 

## 系统支持的聚集函数如下表，参与聚集运算的列必须是数值型列。
函数	含义
MIN(列名)	对结果集中指定的列，求最小值
MAX(列名)	对结果集中指定的列，求最大值  
SUM(列名)	对结果集中指定的列，求和
AVG(列名)	对结果集中指定的列，求均值
```



```sql
# 求学生表中的所有学生的平均年龄。
SELECT 	AVG(AGE)
FROM	STUDENT


```



### 8.2 复杂组合

- 复杂组合查询是采用复合列的一种高级查询方式，用以帮助用户进行复杂检索功能的实现。
- <复合检索关键词> 是一种复杂、高效的检索表示，是将列通过运算符组合的表示形式。共有三种运算符：
  - 表示或(OR)
  - - 表示非(NOT)
  - *表示与(AND)。

```sql
# 检索篇名出现了“计算机”但没有出现“数据库”的文章
SELECT 	*
FROM	CJFD2004
WHERE	篇名=‘计算机’-‘数据库’

# 检索全文中出现了“计算机科学”或“信息科学”，但是没有出现“人文科学”，且作者姓刘的文章。
SELECT 	*
FROM	CJFD2004
WHERE	全文=计算机科学+信息科学-人文科学 AND 作者%刘

# 检索篇名同时出现了“计算机”和“软件”的文章
SELECT 	*
FROM	CJFD2004
WHERE	篇名= 计算机*软件
```



### 8.3 自动扩展

- XLS(<检索关键词>[,扩展词典]) 是自动扩展查询





### 8.4 模糊查询 %

- 模糊查询并不考虑检索条件中词的出现顺序

```sql
# 如检索期刊中出现“计算机”和“教学”的文章，下面的两个KSQL语句是等价的。
SELECT	*
FROM	CJFD2004
WHERE	篇名 % “计算机教学”

和

SELECT	*
FROM	CJFD2004
WHERE	篇名 % “教学计算机”
```



![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220519223629352.png)



### 8.5 BELONG TO查询

- KBASE系统在检索语句的WHERE 子句中的条件项中支持 BELONG TO 查询。

```sql
SELECT	篇名,分类号
FROM	CJFD
WHERE	分类号 = "A?" OR分类号 = "B?"
利用BELONG TO 可以写作：
SELECT	篇名,分类号
FROM	CJFD
WHERE	分类号 BELONG_TO ( "A?, B?" )
```





### 8.6 函数查询

- KBASE系统在检索语句的SELECT 子句中，还支持其他特定的查询函数，以实现特定的功能。



| 类型     | 意义                               | 替换函数                         |
| -------- | ---------------------------------- | -------------------------------- |
| TNAME    | 返回表名称                         | GETSYSFIELD(‘_ _TABLENAME ’ )    |
| TANAME   | 返回表的显示名称                   | GETSYSFIELD(‘_ _TABLEALIASNAME’) |
| RECORDID | 返回物理记录号                     | GETSYSFIELD(‘_ _RID’)            |
| RELEVANT | 返回检索结果的相关度               | GETSYSFIELD(‘_ _RELEVANT’)       |
| SNAPSHOT | 返回与检索相关的特定字段的检索快照 | GETSNAPSHOT ( ‘被映射的列名’ )   |



```sql
# 表示按照专题子栏目代码排序列分组，取对应的题名及其发文量。第一种是第二种的简写。
SELECT	专题子栏目代码 as 代码,groupcodename() as 题名,count(*) as 发文量
FROM	CJFD2013
GROUP BY 	(专题子栏目代码,CCL2)

SELECT	专题子栏目代码 as 代码,codename(__GROUPNAME,SYS_CODENAME,0,1) as 题名,count(*) as 发文量
FROM	CJFD2013
GROUP BY 	(专题子栏目代码,CCL2)
```



### 8.7 分页查询

KBase能自动处理超大的结果集，如果用户希望将结果集按页进行切分，KBase通过两个SQL指令来实现。

#### TOP选项

- 如同标准SQL，用户可以在SELECT 后书写 TOP N， 返回前面 N 条记录。
  - 需要注意的是，KBase 每页最大记录数是 512，如果前面N值超过512，最多只会返回512条记录了。

```sql
# 该语句返回分数最高的10名同学的分数。
SELECT	TOP 10 score
FROM	student
ORDER BY	score
```

#### LIMIT选项

- TOP N 相当于返回第一页的数据，如果用户想返回其他页的数据，可以在SQL语句最后，使用LIMIT  F, N  选型
  - 其中 F 表示从第几条记录开始（注意，F从0开始），N表示返回多少条记录。

```sql
# 该语句返回同学分数的第21名到30名。
SELECT	score
FROM	student
ORDER BY	score
LIMIT	20, 10
```

- LIMIT 还有一种用法是 LIMIT N，其含义同前面的TOP N。





# 六、KSQL数据操作语句（操作记录、操作表）

- 插入记录

```sql
## 向STUDENT表插入姓名为张三，年龄为15的记录。
INSERT INTO	STUDENT
	(NAME,AGE)
VALUES	(张三,15)
```



- 更新记录

```sql
## 将STUDENT表中张三的年龄修改为16。
UPDATE	STUDENT
SET	AGE=16
WHERE	NAME=张三
```



- 删除记录

```sql
## 删除STUDENT表中姓名为“张三”的记录。
DELETE FROM	STUDENT
WHERE	NAME=张三
```





- 重整表
  - 重整指定的表，重整表的目的是为了提高表的存储空间利用率，并可以提高表的访问效率。

```sql
## 重整表CJFD2004。
PACK TABLE	CJFD2004
```



- 清空表
  - 清空一个表中的所有数据，包括索引。

```sql
# 清空表CJFD2004。
CLEAR TABLE	CJFD2004
```



- 文档存储类型管理

```sql
# 创建文本抽取器
CREATE TEXTEXTRACTOR <文本抽取器名>
		PIPENAME <管道名>
		[MODULENAME <模块名>] 
		ON <文件扩展名>

# 删除文本抽取器
DROP TEXTEXTRACTOR <文本抽取器名>

# 文档型数据插入格式
文档型数据对应的原始文件，保存在文件系统中，KBase 的文档型数据类型，只在数据库中保存文档的文件路径等信息，或保存通过文本抽取器抽取的文档文本内容。
```





# 七、KSQL统一扩展管理语句

针对 KBASE 的功能接口需要，KSQL 对SQL做了大量扩展，这些扩展的语句，都统一以 DBUM 开头，在前面的章节中已经出现过。

**本章就其他扩展管理语句进行说明。**

## 1 排序词典与排序索引管理

Kbase 中提供了对海量数据进行**快速排序和分组**的机制。

<hr>

进行这种排序之前，必须对数据根据排序词典建立排序索引。在排序索引建立之后，才可以利用排序索引进行排序和分组。



KBase系统支持两种排序词典，系统排序词典和用户排序词典。

- 系统排序词典主要处理可以预定义排序范围的数据类型，有 INTEGER、DATE等类型。

- 用户排序词典则需要用户来建立。



根据排序词典，就可以给一个表建立排序索引，排序索引存储在扩展名为 .NST的文件中。

- 如cjfd0608的排序索引存储在cjfd0608.nst文件中。

### 1.1 排序词典管理

#### 创建用户排序词典

```sql
# 创建用户排序词典
CREATE SORTDICT <词典名>
		PATH <存储目录>
		ALIASNAME <词典名>
		ORDER BY <SPELL|STOKE|CODE|UNICODE|Formula( <公式> )>
		MAXWORDLEN <词条最大长度>
		SORTVALUENUM <排序值个数>
```

KBase 支持的用于定义排序词典的函数有：

| 函数名 | 函数的C风格声明                            | 说明                                        |
| ------ | ------------------------------------------ | ------------------------------------------- |
| KEY    | char* KEY()                                | 返回待处理的列值，返回值为字符串            |
| ATOI   | int ATOI(char *str)                        | 将字符串参数str转换为32位整数返回           |
| LEFT   | char*	LEFT(char* str,int len)           | 将字符串参数str的左len字节截取返回          |
| RIGHT  | char*	RIGHT(char* str,int len)          | 将字符串参数str的右len字节截取返回          |
| SUBSTR | char*	SUBSTR(char* str,int pos,int len) | 将字符串参数str的第pos开始的len字节截取返回 |
| TRIM   | char*	TRIM(char* str,char c)            | 将字符串参数str的两端字符c去掉后返回        |
| TRIM   | char*	TRIM(char* str)                   | 将字符串参数str的两端空格字符去掉后返回     |
| RTRIM  | char*	RTRIM(char* str,char c)           | 将字符串参数str的右端字符c去掉后返回        |
| RTRIM  | char*	RTRIM(char* str)                  | 将字符串参数str的右端空格字符去掉后返回     |
| LTRIM  | char*	LTRIM(char* str,char c)           | 将字符串参数str的左端字符c去掉后返回        |
| LTRIM  | char*	LTRIM(char* str)                  | 将字符串参数str的左端空格字符去掉后返回     |

#### 引入排序词典

```sql
DBUM IMPORT SORTDICT <存储目录+词典名>
```

#### 导入导出排序词典文本

```sql
# 导出
DBUM EXPORT 
SORTDICT <词典名><词典文本文件名>

# 导入
DBUM LOAD 
SORTDICT <词典名><词典文本文件名>
```

#### 刷新与压缩排序词典

```sql
# 刷新词典语法：
	DBUM REFRESH SORTDICT <词典名> UNCOMPRESS
# 压缩词典存储语法：
	DBUM REFRESH SORTDICT <词典名> COMPRESS
```



### 1.2 表排序文件管理

```sql
# 定义排序索引
## 利用排序词典建立排序索引：
DBUM MAKE SORTCOL 
BY <词典名> (表名.列名[,<表名.列名>…])

DBUM MAKE SORTFILE 
BY <词典名> (表名.列名[,<表名.列名>…])

## 例如：
DBUM MAKE SORTCOL BY	来源名称 (CJFD2004.中文刊名)

DBUM MAKE SORTCOL BY	‘DATE’ (CJFD2004.发表时间)
```





### 1.3 表排序文件同步

本地KBase使用远程表数据，当排序列发生变化后，9.0版本以后，支持自动同步排序文件。

系统默认30分钟内同步远程表的排序文件到本地KBase服务器上。如果想修改默认同步时间，修改系统参数 TELEAUTOSYNCSORTFILE即可。

```sql
# 如果希望每隔 5 秒同步一次 CJFDTEMP表的排序文件，可以使用如下指令。
UPDATE 	sys_hotstar_system
SET	querycache=5
WHERE	tablename=cjfdtemp
```



## 2 大内存文件缓存

KBase系统通过使用内存对各种文件进行缓存来改善IO性能，进而提升系统整体性能。

KBase 的64位版本，可以更有效的对大内存提供支持，其支持的内存容量仅受操作系统限制。

KBase启用大内存来支持个性化文件缓存功能，需要修改配置参数：MEMAWE, 该参数为0时，本功能不启用。

我们可以个性化定义每个文件的内存缓存用量、方法，配置信息可以通过 配置文件AWE.CFG 来指定。该文件存放在KBase安装目录system文件夹下。



配置文件 AWE.CFG  可以手动创建，也可以通过热点统计分析工具支持自动创建，以下分别介绍两种创方法：

### 手动创建



### 通过热点统计分析工具自动创建

### AWE 参数说明

```sql
# MEMAWE
设置最大可用内存数，单位为M。
MEMAWE参数默认值为28672，在开启AWE功能时，MEMAWE参数值的设置必须小于系统最大内存。

## 32G内存的服务器，设置AWE可用最大内存为28G，语句如下：
 DBUM SET MEMAWE	28672
```



### 一些其它参数

```sql
# VIRTUAL_MEM_TOP
系统虚拟内存的上限。 
64位系统下，0表示总的物理内存大小。
32位系统下，0表示虚拟内存为2G。

# MEM_TOP_SAFE_PERCENT、MEM_TOP_WARNING_PERCENT、MEM_TOP_ERROR_PERCENT
以上三个参数指已用内存占VIRTUAL_MEM_TOP的百分比，根据内存占用的百分比程序作出相应的处理，保证系统安全。

# MEM_TOTAL、MEM_AVAILABLE
以上两个参数是只读的，记录kbase启动时系统的总内存大小和可用内存大小。
```





## 3 检索缓存

```sql
检索缓存功能实现了灵活选择对检索结果是否缓存，提高检索效率。系统参数SQL_CACHE_TYPE可以控制检索缓存的使用情况。

```

SQL_CACHE_TYPE 参数默认为1，有0、1、2三个参数值，不同参数值下检索结果缓存情况对应下表：

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20220520110056361.png)



## 4 集群



集群由中心节点和数据节点两种角色组成。

中心节点

- 负责收集集群中表的分布信息；
- 负责监测数据节点有效性；
- 平衡调度任务；
- 记录集群运行日志及性能分析

数据结点（又分为数据检索节点和数据卫星节点）

- 数据检索节点提供数据库管理功能，连接远程表；并负责向中心节点注册有哪些本地表。
- 数据卫星节点只负责本地数据表服务。





## 5 其他杂项

```sql
# 系统变量设置

DBUM SET <变量名><变量值>

## 例如常用的：
a) DBUM SET SQLLOGON 0 
表示将变量SQLLOGON 设为 0，即不记录SQL日志
b) DBUM SET MAXTHREADNUM 200
将系统线程数设置为 200

# 不同分词类型的表实现兼容的办法
# 排序文件同步时间设置
语法格式：
UPDATE SYS_HOTSTAR_SYSTEM
SET QUERYCACHE=<刷新时间间隔(秒)>
WHERE TABLENAME=<表名>
参数：
<表名> 待修改刷新同步文件时间间隔的表名。
<刷新时间间隔>设置时间间隔参数，为整数，单位为秒。

# 表读写分离应用设置
支持表读写分离功能的KBase版本，安装后在系统数据库SYSTEM库会存在一张名为SYS_PREPUB_INFO的系统表，通过设置该表字段值完成报纸预出版的数据库配置。
```

<br>

| 字段名      | 用途                                                         |
| ----------- | ------------------------------------------------------------ |
| TABLENAME   | 指定日更新表的表名。                                         |
| KEYFIELD    | 表间合并时用的主键字段名称，用来唯一标识记录。               |
| COMMITTABLE | 指定接收数据的临时表表名称。                                 |
| ASSISTTABLE | 指定临时检索记录的表名称。                                   |
| COMMITTIME  | 指定记录从COMMITTABLE转移到ASSISTTABLE的时间间隔，单位为秒。 |
| MERGETIME   | 指定记录从ASSISTTABLE合并到TABLENAME表的物理时刻，格式为hhmmss。 |
| TABLEPATH   | 指定COMMITTABLE和ASSISTTABLE表的存放路径。                   |





# 八、KSQL批量数据处理语句（关键点：批量）

KBase 主要面向企业市场，它提供多种高效率的批量的数据处理方面，以实现工厂模式下高性能的数据处理机制。

数据处理方法包括：

## 8.1 排重合并表

功能：把数据表<表名2>中的所有数据追加到数据表<表名1>中，指定字段重复的记录不参加合并，包括索引信息。

```sql
# 语法
BULKLOAD TABLE <表名1>
FROM TABLE <表名2>
WITH KEY = <关键字字段名> , 
MASTER = <表名1或 表名2>

# 参数
<表名1> 待合并的表名。
<关键字字段名> 表1和表2中用来唯一 确定记录的字段名称。

# 合并表	CJFD2003的记录到CJFD2004中，重复时保留CJFD2004中记录。
BULKLOAD TABLE	CJFD2004
FROM TABLE	CJFD2003
WITH KEY	='记录标识' , 
MASTER	=CDFD2004
```

## 8.2 系统变量设置



```sql
# 语法
DBUM SET RECPARM <参数>

# 参数4种
00B：导入/导出REC文件时，转义符无效；导出REC文件同时，不生成INI配置文件。
01B：导入/导出REC文件时，转义符无效；导出REC文件同时，生成INI配置文件。
10B：导入/导出REC文件时，转义符生效；导出REC文件同时，生成ini配置文件，且配置文件中仅记录ESC=1，没有FIELDLIST描述信息。
11B：导入/导出REC文件时，转义符生效；导出REC文件同时，生成INI配置文件。
```



## 8.3 批量导入数据

往表中批量导入数据有两种方法

- 一是通过标准REC文件导入数据
- 另一种是导入另一张表中的数据



```sql
# 语法格式一：
BULKLOAD TABLE <表名>
<REC文件名>  [FROM  <记录号>]
参数：
<表名> 待导入REC记录的表名。
<REC文件名>待导入REC文件路径及文件名。

## 将D:\\cjfd2004目录下名为cjfd2004.txt 的REC文件中所有记录导入到表cjfd2004中，执行语句如下：

BULKLOAD TABLE	CJFD2004
	 'D:\\cjfd2004\\cjfd2004.txt'  


# 语法格式二：
BULKLOAD TABLE <表名1>
FROM  TABLE <表名2>
参数：
<表名1> 待导入记录的表名。
<表名2> 记录来源表的表名。

## 将表CJFD2003中所有记录导入到表CJFD2004中，执行语句如下：
BULKLOAD TABLE	CJFD2004
FROM  TABLE	CJFD2003
```



## 8.4 批量导出数据

```sql
# 格式：
SELECT * /[<字段名1>,<字段名2>,<字段名3>……] 
FROM  <表名>
[<WHERE 条件>]
[<GROUP BY 子句> 
[<ORDER BY 子句>  
INTO < “文件名”>

# 将文章表中的标题含有“计算机”的文章，按照期刊号对文章表分组，并且按照相关度排序导出，导出到D:\issuetemp下，文件名为issuetemp_dlrb.txt，执行语句如下：

SELECT	*
FROM	文章表
WHERE	标题=计算机
GROUP BY 	期刊号
ORDER BY	RELEVANT
INTO	'D:\issuetemp\issuetemp_dlrb.txt’
```



## 8.5 批量更新方法

KBase 系统提供的批量更新方法，是利用REC文件来进行的数据更新模式。



```sql
# 格式: 
DBUM UPDATE TABLE <表名>
FROM <文件名>
WITH KEY <主键列>
[ APPENDING  ( <列名>  [, <列名>…]) ]
```



## 8.6 脚本更新方法



# 九、KSQL文本处理语句

## 1 文本处理  KSQL概述

- 文本处理是服务器端的智能文本挖掘扩展组件。它主要用来完成对文本信息中知识的挖掘。

- 而文本处理 KSQL则是驱动这些具体功能的KSQL语句。

- 按照文本处理 KSQL的形式，我们可以把其分成两类。
  - 第一类文本处理 KSQL主要用来用于学习指令或者预处理指令
  - 第二类文本处理 KSQL语句多用于处理数据



## 2 文本挖掘引擎结束

## 3 创建知识域





















