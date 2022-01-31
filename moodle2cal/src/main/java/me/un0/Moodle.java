package me.un0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import me.un0.Utils.ConfigLoader;
import me.un0.Utils.Session;

/**
 * 講義情報を取得するクラスです
 */
public final class Moodle {
    /**
     * 講義情報が格納されるlist
     */
    private List<Course> courseList = new ArrayList<Course>();

    /**
     * このクラスのコンストラクタです
     * @param config 設定情報のインスタンス
     * @param session 認証のインスタンス
     */
    public Moodle(ConfigLoader config, Session session){
        List<Node> nodeList;
        try {
            Document document = Jsoup.connect(config.load("baseurl")).cookies(session.getCookie()).get();

            Elements elements = document.select("li[aria-labelledby$=label_2_11]");
            nodeList = elements.get(0).childNode(1).childNodes();

            Pattern ptn = Pattern.compile("https://service\\.cloud\\.teu\\.ac\\.jp/moodle_epyc/course/view\\.php\\?id=\\d+"); 
            for (int i = 0; i < nodeList.size() -1; i++) {
                String courseName = nodeList.get(i).childNode(0).childNode(0).attr("title");
                String courseUrl = nodeList.get(i + 1).childNode(0).childNode(0).attr("href");
                Course course = new Course(courseName, courseUrl);
                Matcher m = ptn.matcher(courseUrl);
                if (m.matches()){
                    this.courseList.add(course);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    /**
     * 講義の一覧を返却します
     * @return 講義情報のlist
     */
    public List<Course> getCourseList(){
        return this.courseList;
    }
}
