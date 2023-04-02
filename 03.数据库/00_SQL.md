

## File 类构造器

```java
// 根据指定的文件或目录路径名创建File对象
File(String pathname);

// 根据指定的父路径名和子路径名字符串创建File对象
File(String parent, String child);

// 根据指定的父路径名和子路径名字符串创建File对象
File(File parent, String child);
```

在使用File类时应该注意跨平台的问题，应该使用 `File.separator` 或 `File.pathSeparator` 来代替具体的路径分隔符或路径列表分隔符。



## File 类常用方法

```java
// 一、判断
// 判断文件或目录是否存在
exists();
// 判断File对象是否是文件或目录
isFile()和isDirectory();

// 二、获取
// 获取绝对路径
getAbsolutePath();
//获取文件或目录的名称和路径
getName()和getPath();
// 获取文件大小，单位为字节
length();
// 获取文件最后修改时间
lastModified();
// 获取目录下的文件或子目录
list()和listFiles();

// 三、创建
// 四、删除
```



## IO流的分类

按数据单位、流向、角色

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20230402232912648.png)



IO流体系

![](https://notes2021.oss-cn-beijing.aliyuncs.com/2021/image-20230402233007223.png)













## JDBC API

JDBC API 定义了一组用于连接数据库、执行 SQL 语句和处理结果的类和接口。

JDBC 驱动程序将 JDBC API 转换为特定数据库的本机协议



```java
// 获取连接
conn = DriverManager.getConnection(url, username, pw);
```





IDEA 将一段代码抽取为一个方法





### Q&A

**hive 使用 `load` 导入数据时是否可以指定分隔符？**

hive load 数据只是单纯的把文件拷贝到 `hdfs` 的相应目录下面，并不作格式检查和解析。

只有在查询数据的时候，才会根据创建表时定义的序列化方式解析数据。建表的时候可以指定分隔符。





```java
public static ArrayList<String> readCsvByBufferedReader(String filePath) {
    File csv = new File(filePath);
    csv.setReadable(true);
    csv.setWritable(true);
    InputStreamReader isr = null;
    BufferedReader br = null;
    try {
        isr = new InputStreamReader(new FileInputStream(csv), "UTF-8");
        br = new BufferedReader(isr);
    } catch (Exception e) {
        e.printStackTrace();
    }
    String line = "";
    ArrayList<String> records = new ArrayList<>();
    try {
        while ((line = br.readLine()) != null) {
            System.out.println(line);
            records.add(line);
        }
        System.out.println("csv表格读取行数：" + records.size());
    } catch (IOException e) {
        e.printStackTrace();
    }
    System.out.println(records);
    return records;
}
```

























































































 
