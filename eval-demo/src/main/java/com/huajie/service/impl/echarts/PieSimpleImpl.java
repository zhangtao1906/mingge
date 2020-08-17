package com.huajie.service.impl.echarts;

import com.huajie.echarts.AbstractPieSimple;
import com.huajie.entity.ExtMapData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PieSimpleImpl extends AbstractPieSimple {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ExtMapData<String, String>> generateMap() {
        String teamName = parameters.get("teamName").toString();
        String major = parameters.get("major").toString();
        String date = parameters.get("date").toString();

        List<ExtMapData<String, String>> emd = new ArrayList<>();
        String sql = String.format("select goods_name, goods_num from `major_consume_records` where station = '%s' and major = '%s' and update_date = '%s'",
                teamName, major, date);
        System.out.println(sql);
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            emd.add(new ExtMapData(map.get("goods_name").toString(), map.get("goods_num").toString()));
        }
        return emd;
    }

    // 显示各专业的材料消耗
    @Override
    protected List<ExtMapData<String, String>> ListExtMapData() {
        return generateMap();
//        List<ExtMapData<String, String>> list = new ArrayList();
//        list.add(new ExtMapData("直接访问","335"));
//        list.add(new ExtMapData("邮件营销","310"));
//        list.add(new ExtMapData("联盟广告","234"));
//        list.add(new ExtMapData("视频广告","135"));
//        list.add(new ExtMapData("搜索引擎","1548"));
//        return list;
    }

    @Override
    protected void setParameters(Map<String, Object> parameters) {
      super.parameters = parameters;
    }
}
