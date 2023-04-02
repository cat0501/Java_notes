



## JDBC API

JDBC API 定义了一组用于连接数据库、执行 SQL 语句和处理结果的类和接口。

JDBC 驱动程序将 JDBC API 转换为特定数据库的本机协议



```java
// 获取连接
conn = DriverManager.getConnection(url, username, pw);
```





IDEA 将一段代码抽取为一个方法







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

























































































 
