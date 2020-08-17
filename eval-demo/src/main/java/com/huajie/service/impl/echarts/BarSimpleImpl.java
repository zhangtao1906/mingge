package com.huajie.service.impl.echarts;

import com.huajie.echarts.AbstractBarSimple;
import com.huajie.entity.ExtMapData;
import com.huajie.entity.LegendStack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class BarSimpleImpl extends AbstractBarSimple {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    protected List<String> getLegendList() {
        return getLegendStack(parameters.get("teamName").toString());

//        return Arrays.asList("直接访问","邮件营销","联盟广告","视频广告","搜索引擎");
    }

    public ArrayList<String> getLegendStack(String teamName) {
        ArrayList<String> legends = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select update_date from `jindu` where station = '" + teamName + "'");
        for (Map<String, Object> map : maps) {
            legends.add(map.get("update_date").toString());
        }
        return legends;
    }

    public List<ExtMapData<String, String>> generateMap(String teamName) {
        List<ExtMapData<String, String>> emd = new ArrayList<>();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select update_date, actual_today_work from `jindu` where station = '" + teamName + "'");
        for (Map<String, Object> map : maps) {
            emd.add(new ExtMapData(map.get("update_date").toString(), map.get("actual_today_work").toString()));
        }
        return emd;
    }

    @Override
    protected List<ExtMapData<String, String>> getSeriesData() {
        String teamName = parameters.get("teamName").toString();
        return generateMap(teamName);

//        List<ExtMapData<String, String>> list = new ArrayList();
//        list.add(new ExtMapData("直接访问", "335"));
//        list.add(new ExtMapData("邮件营销", "310"));
//        list.add(new ExtMapData("联盟广告", "234"));
//        list.add(new ExtMapData("视频广告", "135"));
//        list.add(new ExtMapData("搜索引擎", "1548"));
//        return list;
    }

    @Override
    protected void setParameters(Map<String, Object> parameters) {
        super.parameters = parameters;
    }

}
