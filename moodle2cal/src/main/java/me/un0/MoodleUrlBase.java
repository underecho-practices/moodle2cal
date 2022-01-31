package me.un0;

import java.util.regex.*;

/**
 * Moodleのページに関する基底クラスです
 */
class MoodleUrlBase {
    /**
     * このクラスのコンストラクタです
     * @param title ページタイトル
     * @param url URL
     */
    public MoodleUrlBase(String title, String url){

    }

    /**
     * インスタンスのurlがパターンにマッチするかを返却します
     * @param url URL
     * @param pattern 正規表現パターン
     * @return マッチしているか
     */
    public boolean isValidUrl(String url, String pattern){
        Pattern ptn = Pattern.compile(pattern);
        Matcher m = ptn.matcher(url);
        if (m.matches()){
            return true;
        }else{
            return false;
        }
    }
}
