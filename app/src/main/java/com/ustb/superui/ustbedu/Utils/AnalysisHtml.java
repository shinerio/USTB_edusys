package com.ustb.superui.ustbedu.Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 解析html，提取信息
 */
public class AnalysisHtml {
    private AnalysisHtml() {
    }

    //提取课程信息
    public static ArrayList<String> findClassByHtml(String text) {
        ArrayList<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(text);
        Element element = doc.select("table.classtable").first();
        Elements classLists = element.getElementsByTag("tr");
        for (int i = 1; i < classLists.size() - 1; i++) {
            Element item = classLists.get(i);
            Elements classNames = item.getElementsByTag("td");
            for (int j = 1; j < classNames.size(); j++) {
                //
                String str = classNames.get(j).text()
                        .replaceAll(Jsoup.parse("&nbsp;").text(), "无课程");
                if (!"无课程".equals(str)) {
                    str = str.replaceAll("无课程", "");
                    str = str.replaceAll("null", "");
                }
                list.add(str);
            }
        }
        return list;
    }

    //获取学生信息
    public static ArrayList<String> findUserInfo(String text) {
        ArrayList<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(text);
        Elements elements = doc.select("TD[style=LINE-HEIGHT: 14pt][align=center]");
        for (Element info :
                elements) {
            list.add(info.text().toString());
        }
        return list;
    }

    //获取学期列表
    public static ArrayList<String> findTermList(String text) {
        ArrayList<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(text);
        Element element = doc.select("form[name=XNXQ]").first().select("select").first();
        Elements terms = element.getElementsByTag("option");
        for (Element term : terms) {
            list.add(term.text());
        }
        return list;
    }

    //获取成绩信息
    public static Map<String, ArrayList> findScore(String text) {
        Map<String, ArrayList> map = new HashMap<>();
        Document doc = Jsoup.parse(text);
        Elements elements = doc.select("table[border=1][borderColorDark=#ffffff]").first().select("tr");
        for (Element e : elements) {
            Elements items = e.getElementsByTag("td");
            ArrayList<String> list = new ArrayList<>();
            String name = "";
            String xuefen = "";
            String xueshi = "";
            String score = "";
            String classType = "";
            for (int i = 0; i < items.size(); i++) {
                if (i == 1)
                    name = items.get(i).text();
                if (i == 2) {
                    xuefen = items.get(i).text();
                    list.add(xuefen);
                }
                if (i == 3) {
                    xueshi = items.get(i).text();
                    list.add(xueshi);
                }
                if (i == 4) {
                    score = items.get(i).text();
                    list.add(score);
                } if (i ==5) {
                    classType = items.get(i).text();
                    list.add(classType);
                }
            }
            if (name != null && !"".equals(name)&&!"课程名称".equals(name))
                map.put(name, list);
        }
        return map;
    }
}
