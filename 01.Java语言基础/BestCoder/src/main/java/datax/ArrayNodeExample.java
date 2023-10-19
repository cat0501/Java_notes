package datax;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ArrayNodeExample {
    public static void main(String[] args) {
        String inputString = "sessionId=f8896d64-fade-4477-ada8-5fc6ff40c29b; rememberMe=true; username=zhangjianlin; userId=1859; pNames=[{%22name%22:%22Role%22%2C%22menuCate%22:3}%2C{%22name%22:%22Limit%22%2C%22menuCate%22:3}%2C{%22name%22:%22Index%22%2C%22menuCate%22:2}%2C{%22name%22:%22Alldata%22%2C%22menuCate%22:2}%2C{%22name%22:%22Mydata%22%2C%22menuCate%22:2}%2C{%22name%22:%22Info%22%2C%22menuCate%22:2}%2C{%22name%22:%22User%22%2C%22menuCate%22:3}%2C{%22name%22:%22Role%22%2C%22menuCate%22:3}%2C{%22name%22:%22Menu%22%2C%22menuCate%22:3}%2C{%22name%22:%22Dept%22%2C%22menuCate%22:3}%2C{%22name%22:%22Post%22%2C%22menuCate%22:3}%2C{%22name%22:%22Datalimit%22%2C%22menuCate%22:3}%2C{%22name%22:%22Audit%22%2C%22menuCate%22:3}%2C{%22name%22:%22Table%22%2C%22menuCate%22:2}%2C{%22name%22:%22/allData/detail%22%2C%22menuCate%22:2}%2C{%22name%22:%22Data-list%22%2C%22menuCate%22:1}%2C{%22name%22:%22Data-dev-index%22%2C%22menuCate%22:1}%2C{%22name%22:%22Data-dev-table%22%2C%22menuCate%22:1}%2C{%22name%22:%22Data-ser%22%2C%22menuCate%22:1}%2C{%22name%22:%22Data-source%22%2C%22menuCate%22:1}%2C{%22name%22:%22Data-recycle%22%2C%22menuCate%22:1}%2C{%22name%22:%22Real-time-task%22%2C%22menuCate%22:1}%2C{%22name%22:%22Real-time-task/instance%22%2C%22menuCate%22:1}]; password=F13Em8o3Q9ZbVtvrPjuWJzMSDjPlPr4L7xX9Sf/cZ0SuE/MxSMX+Hm6cDZsFn5tRf/wounUmWhkPIfSICjI1LQ==; Admin-Token=eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjJlYzgyNTY4LTZlYjItNGEzYS05NTIwLTZhZGJmZTg1MDExOCJ9.xHZKqsk6JM4i131SPWPsdnJRcr6pGlQm-L3UGeM7bFApA6JBEzH161depZYCNw5gjERkt_iqKFYEuf7Mxw7LLA; sessionId=2ec82568-6eb2-4a3a-9520-6adbfe850118; language=zh_CN";

        String outputString = inputString.replaceFirst("pNames=.*?(;|$)", "");

        System.out.println(outputString);




        // 创建一个 ObjectMapper 对象，用于操作 JSON 数据
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNodeFactory factory = objectMapper.getNodeFactory();
        ArrayNode arrayNode = factory.arrayNode();

        ObjectNode node1 = factory.objectNode();
        node1.put("name", "vin");

        ObjectNode node2 = factory.objectNode();
        node2.put("name", "last_cha_time");

        ObjectNode node3 = factory.objectNode();
        node3.put("name", "id");

        ObjectNode node4 = factory.objectNode();
        node4.put("name", "kkkkk");

        arrayNode.insert(3, node3);
        arrayNode.insert(1, node1);
        arrayNode.insert(2, node2);
        arrayNode.insert(0, node4);

        // 打印数组节点的内容
        System.out.println(arrayNode);

        for (JsonNode jsonNode : arrayNode) {
            System.out.println(jsonNode);
        }
    }
}