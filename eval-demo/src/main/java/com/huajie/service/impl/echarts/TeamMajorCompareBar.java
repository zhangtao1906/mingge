package com.huajie.service.impl.echarts;

import com.huajie.echarts.AbstractBarStack;
import com.huajie.echarts.AbstractBarYCategory;
import com.huajie.entity.ExtMapData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TeamMajorCompareBar extends AbstractBarStack {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    protected List<String> getLegendList() {
//        return getLegendStack(parameters.get("teamName").toString());
        return Arrays.asList("计划完成量","实际完成量");
    }

    public void getExpMap(String teamName, String date, Map<String, List<ExtMapData<String, String>>> mapResult) {
        List<ExtMapData<String, String>> emd = new ArrayList<>();
        String sql = "select major, expect_day_work from `major_jindu` where station = '" + teamName + "' and update_date = '" + date + "'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            emd.add(new ExtMapData(map.get("major").toString(), map.get("expect_day_work").toString()));
//            Set<Map.Entry<String, Object>> entries = map.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                System.out.println(entry.getKey() + "====>" + entry.getValue());
//            }
        }
        mapResult.put("计划完成量", emd);
    }
    public void getActMap(String teamName, String date, Map<String, List<ExtMapData<String, String>>> mapResult) {
        List<ExtMapData<String, String>> emd = new ArrayList<>();
        String sql = "select major, actual_today_work from `major_jindu` where station = '" + teamName + "' and update_date = '" + date + "'";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            emd.add(new ExtMapData(map.get("major").toString(), map.get("actual_today_work").toString()));
//            Set<Map.Entry<String, Object>> entries = map.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                System.out.println(entry.getKey() + "====>" + entry.getValue());
//            }
        }
        mapResult.put("实际完成量", emd);
    }

    @Override
    protected Map<String, List<ExtMapData<String, String>>> getSeriesData() {
        Map<String, List<ExtMapData<String, String>>> map = new HashMap<>();
        String teamName = parameters.get("teamName").toString().trim();
        String date = parameters.get("date").toString().trim();
        getExpMap(teamName, date, map);
        getActMap(teamName, date, map);

//        List<ExtMapData<String, String>> list1 = Arrays.asList(new ExtMapData("1月", "2.0"), new ExtMapData("2月", "4.9"),
//                new ExtMapData("3月", "7.0"), new ExtMapData("4月", "23.2"),
//                new ExtMapData("5月", "25.6"), new ExtMapData("6月", "76.7"),
//                new ExtMapData("7月", "135.6"), new ExtMapData("8月", "162.2"),
//                new ExtMapData("9月", "32.6"), new ExtMapData("10月", "20.0"),
//                new ExtMapData("11月", "6.4"), new ExtMapData("12月", "3.2"));
//        List<ExtMapData<String, String>> list2 = Arrays.asList(new ExtMapData("1月", "2.6"), new ExtMapData("2月", "5.9"),
//                new ExtMapData("3月", "9.0"), new ExtMapData("4月", "26.4"),
//                new ExtMapData("5月", "28.7"), new ExtMapData("6月", "70.7"),
//                new ExtMapData("7月", "175.6"), new ExtMapData("8月", "182.2"),
//                new ExtMapData("9月", "48.7"), new ExtMapData("10月", "18.0"),
//                new ExtMapData("11月", "6.4"), new ExtMapData("12月", "2.2"));
//        map.put("计划完成量", list1);
//        map.put("实际完成量", list2);
        return map;
    }

    @Override
    protected void setParameters(Map<String, Object> parameters) {
        super.parameters = parameters;
    }
}
