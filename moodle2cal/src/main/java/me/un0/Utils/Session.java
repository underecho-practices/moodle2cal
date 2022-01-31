package me.un0.Utils;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * Moodleの認証とCookieの保持を行うクラス
 * 
 */
public final class Session {
    /**
     * Cookie
     */
    private Map<String, String> cookie;

    /**
     * Moodleの認証を行いセッション情報を保持します。
     * @param url Moodleの認証を行うためのURL
     */
    public Session(String url, ConfigLoader config){
        try {
            Connection.Response res = Jsoup.connect(url)
                                            .followRedirects(true)
                                            .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                                            .method(Connection.Method.GET)
                                            .execute();
            this.cookie = res.cookies();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * セッション情報を含むCookieを返却します。
     * @return Cookie
     */
    public Map<String, String> getCookie(){
        return this.cookie;
    }
}
