package com.bs.basic.json;

import com.mongodb.client.*;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBTest {
    public static void main(String[] args) {
        // Set up the connection string with appropriate credentials
        String connectionString = "mongodb://screen:123456@10.11.9.7:27019/?authSource=nation-screen";

        // Create MongoClientSettings
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();

        // Create the MongoDB client
        try (MongoClient client = MongoClients.create(settings)) {
            // Get the database reference
            MongoDatabase database = client.getDatabase("nation-screen");

            // List the collections
            int count = 0;
            for (String collectionName : database.listCollectionNames()) {
                count += 1;
                System.out.println(collectionName);
            }
            System.out.println(count);

            // 获取表字段----------------------------------
            // 获取集合引用
            MongoCollection<Document> collection = database.getCollection("BAT_RETIRE_INS");

            // 获取集合中的一个文档
            Document sampleDocument = collection.find().first();

            // 遍历文档的所有键（字段）
            for (String fieldName : sampleDocument.keySet()) {
                //System.out.println(fieldName);
                Object fieldType = sampleDocument.get(fieldName);
                System.out.println(fieldName + ": " + fieldType.getClass().getSimpleName());
            }


            //// 获取集合的索引信息
            //ListIndexesIterable<Document> indexes = collection.listIndexes();
            //
            //// 遍历索引并获取字段信息
            //for (Document index : indexes) {
            //    Document key = (Document) index.get("key");
            //    for (String fieldName : key.keySet()) {
            //        Object fieldType = key.get(fieldName);
            //        System.out.println(fieldName + ": " + fieldType.getClass().getSimpleName());
            //    }
            //}


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//public class MongoDBTest {
//    public static void main(String[] args) {
//        String host = "10.11.9.7";
//        int port = 27019;
//        String database = "nation-screen";
//
//        // 连接到MongoDB
//        MongoClient client = new MongoClient(host, port);
//        // 获取数据库
//        MongoDatabase db = client.getDatabase(database);
//
//        // 获取所有集合
//        MongoIterable<String> collectionNames = db.listCollectionNames();
//        for (String collectionName : collectionNames) {
//            System.out.println("Collection: " + collectionName);
//        }
//
//        // 关闭连接
//        client.close();
//    }
//}

