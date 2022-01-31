package me.un0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.*;

import javax.swing.UIDefaults.ProxyLazyValue;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import me.un0.Utils.ConfigLoader;
import me.un0.Utils.Session;

/**
 * 講義に関する情報を管理します
 */
public class Course extends MoodleUrlBase{
    /**
     * 講義名
     */
    private String title;
    /**
     * 講義のmoodleページ
     */
    private String url;
    /**
     * 提出ページを判別するための正規表現パターン
     */
    private String pattern = "https://service\\.cloud\\.teu\\.ac\\.jp/moodle_epyc/mod/assign/view\\.php\\?id=\\d+";

    /**
     * 提出ページが格納されるList
     */
    private List<Exam> examList = new ArrayList<Exam>();

    /**
     * コンストラクタ
     * @param title 講義名
     * @param url 講義のmoodleページ
     */
    public Course(String title, String url){
        super(title, url);
        this.title = title;
        this.url = url;
    }

    /**
     * 提出が求められる項目をListで返却します
     * @param config 設定情報のインスタンス
     * @param session 認証のインスタンス
     * @return 提出物がある項目のList
     */
    public List<Exam> getExam(ConfigLoader config, Session session){
        try {
            Document document = Jsoup.connect(this.url).cookies(session.getCookie()).get();
            Elements elements = document.select(".activityinstance");
            for (int i = 0; i < elements.size(); i++){
                if (isValidUrl(elements.get(i).childNode(0).attr("href"), this.pattern)){
                    this.examList.add(new Exam(elements.get(i).childNode(0).childNode(1).childNode(0).toString(), elements.get(i).childNode(0).attr("href")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.examList;
    }

    @Override
    public String toString(){
        return this.title + ": " + this.url; 
    }
}
