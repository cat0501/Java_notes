package com.atguigu.statistic.service;

import com.atguigu.statistic.bean.GeneralStatistic;
import com.atguigu.statistic.bean.Page;
import com.atguigu.statistic.bean.PagePath;
import com.atguigu.statistic.bean.TrafficStatistic;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class StatisticService {
    /**
     * 获取一天的总交易额
     */
    public static BigDecimal getGMV(String date) {
        try {
            Class.forName(DATABASE.CLICKHOUSE_DRIVER);
            var connection = DriverManager.getConnection(DATABASE.CLICKHOUSE_URL);
            var selectStatement = connection.prepareStatement(
                    "SELECT SUM(order_amount) AS order_amount  " +
                            "FROM product_statistics WHERE toYYYYMMDD(start_time) = ?"
            );
            selectStatement.setString(1, date);
            var resultSet = selectStatement.executeQuery();
            var result = new BigDecimal(0);
            if (resultSet.next()) {
                result = resultSet.getBigDecimal("order_amount");
            }

            selectStatement.close();
            connection.close();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new BigDecimal(0);
    }

    /**
     * 获取不同省份的交易额
     */
    public static List<GeneralStatistic> getProvinceStatistics(String date) {
        try {
            Class.forName(DATABASE.CLICKHOUSE_DRIVER);
            var connection = DriverManager.getConnection(DATABASE.CLICKHOUSE_URL);
            var selectStatement = connection.prepareStatement(
                    "SELECT province_name, SUM(order_amount) AS order_amount" +
                            "  FROM province_statistics WHERE toYYYYMMDD(start_time) = ?" +
                            "  GROUP BY province_id, province_name"
            );
            selectStatement.setString(1, date);
            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<GeneralStatistic>();
            while (resultSet.next()) {
                var provinceStatistics = new GeneralStatistic();
                provinceStatistics.setName(resultSet.getString("province_name"));
                provinceStatistics.setValue(resultSet.getBigDecimal("order_amount").toString());
                result.add(provinceStatistics);
            }

            selectStatement.close();
            connection.close();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<GeneralStatistic> getKeywordStatistics(String date) {
        String sql = "SELECT keyword," +
                " SUM(keyword_statistics.keyword_count *" +
                " multiIf(source='SEARCH', 10, source='ORDER', 3, source='CART', 2, source='CLICK', 1, 0)) AS ct" +
                " FROM keyword_statistics WHERE toYYYYMMDD(start_time) = ? GROUP BY keyword " +
                " ORDER BY SUM(keyword_statistics.keyword_count) DESC";
        try {
            Class.forName(DATABASE.CLICKHOUSE_DRIVER);
            var connection = DriverManager.getConnection(DATABASE.CLICKHOUSE_URL);
            var selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, date);
            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<GeneralStatistic>();
            while (resultSet.next()) {
                var keywordStatistics = new GeneralStatistic();
                keywordStatistics.setName(resultSet.getString("keyword"));
                keywordStatistics.setValue(resultSet.getBigDecimal("ct").toString());
                result.add(keywordStatistics);
            }

            selectStatement.close();
            connection.close();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<GeneralStatistic> getProductStatisticsGroupByCategory3(String date) {
        String sql = "SELECT category3_id, category3_name, SUM(order_amount) AS order_amount" +
                "  FROM product_statistics" +
                "  WHERE toYYYYMMDD(start_time) = ? GROUP BY category3_id, category3_name" +
                "  HAVING order_amount > 0 ORDER BY order_amount DESC";
        try {
            Class.forName(DATABASE.CLICKHOUSE_DRIVER);
            var connection = DriverManager.getConnection(DATABASE.CLICKHOUSE_URL);
            var selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, date);
            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<GeneralStatistic>();
            while (resultSet.next()) {
                var categoryStatistics = new GeneralStatistic();
                categoryStatistics.setName(resultSet.getString("category3_name"));
                categoryStatistics.setValue(resultSet.getBigDecimal("order_amount").toString());
                result.add(categoryStatistics);
            }

            selectStatement.close();
            connection.close();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<GeneralStatistic> getProductStatsByTrademark(String date) {
        String sql = "SELECT tm_name, SUM(order_amount) AS order_amount" +
                "  FROM product_statistics" +
                "  WHERE toYYYYMMDD(start_time) = ? GROUP BY tm_id, tm_name" +
                "  HAVING order_amount > 0 ORDER BY order_amount DESC";
        try {
            Class.forName(DATABASE.CLICKHOUSE_DRIVER);
            var connection = DriverManager.getConnection(DATABASE.CLICKHOUSE_URL);
            var selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, date);
            var resultSet = selectStatement.executeQuery();
            var result = new ArrayList<GeneralStatistic>();
            while (resultSet.next()) {
                var trademarkStatistics = new GeneralStatistic();
                trademarkStatistics.setName(resultSet.getString("tm_name"));
                trademarkStatistics.setValue(resultSet.getBigDecimal("order_amount").toString());
                result.add(trademarkStatistics);
            }

            selectStatement.close();
            connection.close();
            return result;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static Page<TrafficStatistic> getTrafficStatistics(Integer page, Integer limit, String dt, Integer recentDays) {
        String sql = "SELECT * FROM ads_traffic_stats_by_channel WHERE dt = ? AND recent_days = ? LIMIT ? OFFSET ?";
        try {
            Class.forName(DATABASE.MYSQL_DRIVER);
            var connection = DriverManager.getConnection(
                    DATABASE.MYSQL_URL,
                    DATABASE.MYSQL_USERNAME,
                    DATABASE.MYSQL_PASSWORD
            );
            var selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, dt);
            selectStatement.setInt(2, recentDays);
            selectStatement.setInt(3, limit);
            selectStatement.setInt(4, page - 1);
            var resultSet = selectStatement.executeQuery();
            var trafficStatistics = new ArrayList<TrafficStatistic>();
            int total = 0;
            while (resultSet.next()) {
                TrafficStatistic trafficStatistic = new TrafficStatistic();
                trafficStatistic.setDt(dt);
                trafficStatistic.setChannel(resultSet.getString("channel"));
                trafficStatistic.setAvgDurationSec(resultSet.getInt("avg_duration_sec"));
                trafficStatistic.setAvgPageCount(resultSet.getInt("avg_page_count"));
                trafficStatistic.setBounceRate(resultSet.getDouble("bounce_rate"));
                trafficStatistic.setSvCount(resultSet.getInt("sv_count"));
                trafficStatistic.setUvCount(resultSet.getInt("uv_count"));
                trafficStatistic.setRecentDays(recentDays);
                trafficStatistics.add(trafficStatistic);
                total++;
            }
            var trafficStatisticPage = new Page<TrafficStatistic>();
            trafficStatisticPage.current = page;
            trafficStatisticPage.records = trafficStatistics;
            trafficStatisticPage.size = limit;
            trafficStatisticPage.total = total;

            selectStatement.close();
            connection.close();
            return trafficStatisticPage;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Page<>();
    }

    public static Map<String, Object> getPagePathCount(TrafficStatistic trafficStatistic) {
        String sql = "SELECT source, target, path_count AS value FROM ads_page_path WHERE dt = ? AND recent_days = ?";
        try {
            Class.forName(DATABASE.MYSQL_DRIVER);
            var connection = DriverManager.getConnection(
                    DATABASE.MYSQL_URL,
                    DATABASE.MYSQL_USERNAME,
                    DATABASE.MYSQL_PASSWORD
            );
            var selectStatement = connection.prepareStatement(sql);
            selectStatement.setString(1, trafficStatistic.getDt());
            selectStatement.setInt(2, trafficStatistic.getRecentDays());
            var pagePaths = new ArrayList<PagePath>();
            var resultSet = selectStatement.executeQuery();
            while (resultSet.next()) {
                PagePath pagePath = new PagePath();
                pagePath.setSource(resultSet.getString("source"));
                pagePath.setTarget(resultSet.getString("target"));
                pagePath.setValue(resultSet.getInt("value"));
                pagePaths.add(pagePath);
            }

            var nodeSet = new HashSet<String>();
            List<Map<String, String>> nodeMapList = new ArrayList<>();
            for (PagePath pagePath : pagePaths) {
                String source = pagePath.getSource();
                String target = pagePath.getTarget();
                nodeSet.add(source);
                nodeSet.add(target);
            }
            for (var nodeName : nodeSet) {
                var nodeMap = new HashMap<String, String>();
                nodeMap.put("name", nodeName);
                nodeMapList.add(nodeMap);
            }

            HashMap<String, Object> rsMap = new HashMap<>();
            rsMap.put("nodeData", nodeMapList);
            rsMap.put("linksData", pagePaths);

            selectStatement.close();
            connection.close();
            return rsMap;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }
}
