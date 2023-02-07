package com.atguigu.government.service;

import com.atguigu.government.bean.GovernmentDetail;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class HiveMetadataMonitor {
    public static void calculateHiveMetadataDetail() {
        try {
            var hiveMetastoreConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hcatalog?useSSL=false",
                    "root",
                    "root"
            );
            var selectTableNameDatabaseName = hiveMetastoreConnection.prepareStatement(
                    "SELECT TBLS.TBL_NAME, DBS.NAME, TBLS.TBL_ID FROM TBLS INNER JOIN DBS ON TBLS.DB_ID = DBS.DB_ID"
            );

            var resultSet = selectTableNameDatabaseName.executeQuery();
            var governanceDetails = new ArrayList<GovernmentDetail>();

            while (resultSet.next()) {
                var detail = new GovernmentDetail();
                detail.setTableName(resultSet.getString("TBLS.TBL_NAME"));
                detail.setDatabaseName(resultSet.getString("DBS.NAME"));
                detail.setTableId(resultSet.getLong("TBLS.TBL_ID"));
                var selectTableComment = hiveMetastoreConnection.prepareStatement("SELECT * FROM TABLE_PARAMS WHERE TBL_ID = ? and PARAM_KEY='comment'");
                selectTableComment.setLong(1, detail.getTableId());
                var commentResultSet = selectTableComment.executeQuery();
                if (commentResultSet.next()) {
                    String comment = commentResultSet.getString("PARAM_VALUE");
                    detail.setHasTableComment(true);
                    detail.setHasTechnicalOwner(comment.contains("技术负责人"));
                    detail.setHasBusinessOwner(comment.contains("业务负责人"));
                } else {
                    detail.setHasTableComment(false);
                    detail.setHasTechnicalOwner(false);
                    detail.setHasBusinessOwner(false);
                }

                var selectLastDdlTime = hiveMetastoreConnection.prepareStatement("SELECT * FROM TABLE_PARAMS WHERE TBL_ID = ? AND PARAM_KEY = 'transient_lastDdlTime'");
                selectLastDdlTime.setLong(1, detail.getTableId());
                var lastDdlTimeResultSet = selectLastDdlTime.executeQuery();
                if (lastDdlTimeResultSet.next()) {
                    long lastDdlTime = lastDdlTimeResultSet.getLong("PARAM_VALUE");
                    long currentTimestamp = System.currentTimeMillis() / 1000;
                    detail.setHasOutputLastSevenDay((currentTimestamp - lastDdlTime) / (24 * 60 * 60) <= 7);
                } else {
                    detail.setHasOutputLastSevenDay(false);
                }

                var selectFieldsInfo = hiveMetastoreConnection.prepareStatement("SELECT * FROM COLUMNS_V2 WHERE CD_ID = ?");
                selectFieldsInfo.setLong(1, detail.getTableId());
                ResultSet fieldsResultSet = selectFieldsInfo.executeQuery();
                detail.setFieldsNumber(0);
                detail.setMissingCommentFieldsNumber(0);

                while (fieldsResultSet.next()) {
                    detail.setFieldsNumber(detail.getFieldsNumber() + 1);
                    if (fieldsResultSet.getString("comment") == null) {
                        detail.setMissingCommentFieldsNumber(detail.getMissingCommentFieldsNumber());
                    }
                }

                governanceDetails.add(detail);
            }

            System.out.println(governanceDetails);

            var governanceDetailConnection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/yuntai_government?useSSL=false",
                    "root",
                    "root"
            );

            // 删除治理表
            var preparedStatement = governanceDetailConnection.prepareStatement(
                    "DELETE FROM hive_metadata_monitor"
            );
            preparedStatement.execute();
            preparedStatement.close();

            var insertGovernanceDetails = governanceDetailConnection.prepareStatement(
                    "INSERT INTO hive_metadata_monitor" +
                            " (table_name, database_name, has_table_comment," +
                            " fields_number, missing_comment_fields_number," +
                            " has_technical_owner, has_business_owner, has_output_last_seven_days)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );

            for (var detail : governanceDetails) {
                insertGovernanceDetails.setString(1, detail.getTableName());
                insertGovernanceDetails.setString(2, detail.getDatabaseName());
                insertGovernanceDetails.setBoolean(3, detail.getHasTableComment());
                insertGovernanceDetails.setInt(4, detail.getFieldsNumber());
                insertGovernanceDetails.setInt(5, detail.getMissingCommentFieldsNumber());
                insertGovernanceDetails.setBoolean(6, detail.getHasTechnicalOwner());
                insertGovernanceDetails.setBoolean(7, detail.getHasBusinessOwner());
                insertGovernanceDetails.setBoolean(8, detail.getHasOutputLastSevenDay());
                insertGovernanceDetails.execute();
            }

            insertGovernanceDetails.close();
            selectTableNameDatabaseName.close();
            governanceDetailConnection.close();
            hiveMetastoreConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        calculateHiveMetadataDetail();
    }
}
