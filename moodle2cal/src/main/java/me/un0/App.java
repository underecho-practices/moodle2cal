package me.un0;

import java.util.List;
import java.util.Scanner;

import me.un0.Utils.ConfigLoader;
import me.un0.Utils.Session;

/**
 * このアプリケーションのエントリーポイントです
 */
public final class App {
    public static void main(String[] args) throws Exception{
        /**
         * 設定情報のインスタンス
         */
        ConfigLoader config;
        /**
         * 認証のインスタンス
         */
        Session session;

        System.out.println("input your portal url (need include auth_tkt)");
        System.out.print(">> ");
        Scanner scanner = new Scanner(System.in);
        try {
            config = new ConfigLoader();
            String url = scanner.nextLine();
            session = new Session(url, config);
            
            Moodle moodle = new Moodle(config, session);

            for(int i = 0; i < moodle.getCourseList().size(); ++i){
                System.out.println(moodle.getCourseList().get(i));
                List<Exam> listExam = moodle.getCourseList().get(i).getExam(config, session);
                Boolean firstFlag = true;
                for(int j = 0; j < listExam.size(); j++){
                    listExam.get(j).setStatus(config, session);
                    if (!listExam.get(j).getIsSubmitted()){
                        if(firstFlag){
                            System.out.println("  ┌" + listExam.get(j));
                            firstFlag = false;
                        }else{
                            System.out.println("  ├" + listExam.get(j));
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
