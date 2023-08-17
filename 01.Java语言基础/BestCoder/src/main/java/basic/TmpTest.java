package basic;

public class TmpTest {
    public static void main(String[] args) {
        String data_type = "hdfs://nameservice1/warehouse/tablespace/external/hive/bitnei_ods.db/bitnei_rtm_dim_reg_cars_base";

        String path = "";
        int index = data_type.indexOf("1");
        if (index != -1) {
            path = data_type.substring(index + 1);
        }
        System.out.println(path);
    }
}
