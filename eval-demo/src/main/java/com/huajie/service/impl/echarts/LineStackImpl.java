package com.huajie.service.impl.echarts;

import com.huajie.echarts.AbstractLineStack;
import com.huajie.entity.ExtMapData;
import com.huajie.entity.LegendStack;
//import com.sun.javafx.charts.Legend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.ui.ExtendedModelMap;

import java.util.*;

@Service
public class LineStackImpl extends AbstractLineStack {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected List<LegendStack> getlegendStackList() {
        List<LegendStack> legends = new ArrayList<>();
        if (parameters.get("station").toString().equals("all")) {
            legends.add(new LegendStack("1号站", "进度"));
            legends.add(new LegendStack("2号站", "进度"));
            legends.add(new LegendStack("3号站", "进度"));
            legends.add(new LegendStack("4号站", "进度"));
            legends.add(new LegendStack("5号站", "进度"));
            legends.add(new LegendStack("6号站", "进度"));
        } else {
            legends.add(new LegendStack(parameters.get("station").toString(), "进度"));
        }
        return legends;
    }

    public void generateMap(String params, Map<String, List<ExtMapData<String, String>>> mapResult) {
        List<ExtMapData<String, String>> emd = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select update_date, current_total_work from `jindu` where station = '" + params + "'");
        for (Map<String, Object> map : maps) {
            emd.add(new ExtMapData(map.get("update_date").toString(), map.get("current_total_work").toString()));
//            Set<Map.Entry<String, Object>> entries = map.entrySet();
//            for (Map.Entry<String, Object> entry : entries) {
//                System.out.println(entry.getKey() + "====>" + entry.getValue());
//            }
        }
        mapResult.put(params, emd);
    }

    @Override
    protected Map<String, List<ExtMapData<String, String>>> ListSeriesData() {
        Map<String, List<ExtMapData<String, String>>> map = new HashMap<>();
        if (parameters.get("station").toString().equals("all")) {
            generateMap("1号站", map);
            generateMap("2号站", map);
            generateMap("3号站", map);
            generateMap("4号站", map);
            generateMap("5号站", map);
            generateMap("6号站", map);
        } else {
            generateMap(parameters.get("station").toString(), map);
        }
        return map;
    }

    @Override
    protected void setParameters(Map<String, Object> parameters) {
        super.parameters = parameters;
    }
}
