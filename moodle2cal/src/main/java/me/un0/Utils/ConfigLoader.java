package me.un0.Utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * 設定ファイルのロードと保持を行うクラス
 * 
 *
 */
public class ConfigLoader {
    /**
     * プロパティ
     */
    private Properties prop;

    /**
     * このクラスのコンストラクタです。
     * パスは実行時パスを検索します。
     */
    public ConfigLoader() throws Exception{
        Properties prop = new Properties();
        String configFile = "./personal.conf";

        try(FileReader fr = new FileReader(configFile)) {
            prop.load(fr);
            this.prop = prop;

        } catch (IOException e){
            e.printStackTrace();
            return;
        }
    }

    /**
     * プロパティから任意のキーを参照して値を返却します
     * @param key キー
     * @return キーに対する値
     */
    public String load(String key){
        return prop.getProperty(key);
    }
}
