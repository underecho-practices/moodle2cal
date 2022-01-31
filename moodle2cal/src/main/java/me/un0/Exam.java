package me.un0;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import me.un0.Utils.ConfigLoader;
import me.un0.Utils.Session;

/**
 * 提出ページを管理するクラス
 */
public class Exam extends MoodleUrlBase{
    /**
     * 提出物名
     */
    private String title;
    /**
     * 提出ページのurl
     */
    private String url;

    /**
     * 提出期限
     */
    private String deadLine;
    /**
     * 提出されているかどうか
     */
    private boolean isSubmitted = false;

    /**
     * このクラスのコンストラクタです
     * @param title 提出物名
     * @param url 提出ページのurl
     */
    public Exam(String title, String url){
        super(title, url);
        this.title = title;
        this.url = url;
    }

    /**
     * 提出ページから詳細情報を取得しインスタンスに適用します
     * @param config 設定情報のインスタンス
     * @param session 認証のインスタンス
     */
    public void setStatus(ConfigLoader config, Session session){
        Document document;
        try {
            document = Jsoup.connect(this.url).cookies(session.getCookie()).get();
            Elements elements = document.select("tr");
            for(int i = 0; i < elements.size(); i++){
                // System.out.println(elements.get(i).child(0));
                if (elements.get(i).child(0).text().equals("終了日時")){
                    this.deadLine = elements.get(i).child(1).text();
                }else if(elements.get(i).child(0).text().equals("提出ステータス")){
                    if(elements.get(i).child(1).text().equals("評定のために提出済み")){
                        this.isSubmitted = true;
                    }
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提出物名を返却します
     * @return 提出物名
     */
    public String getTitle(){
        return this.title;
    }

    /**
     * 提出済みかどうかを返却します
     * @return 提出済みかどうかのbool値
     */
    public boolean getIsSubmitted(){
        return this.isSubmitted;
    }

    @Override
    public String toString(){
        return this.title + ": " + this.deadLine;
    }
}
