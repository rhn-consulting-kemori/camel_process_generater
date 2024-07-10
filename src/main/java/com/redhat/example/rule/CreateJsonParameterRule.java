package com.redhat.example.rule;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Spring
import org.springframework.stereotype.Component;

// Business Object
import com.redhat.example.entity.CreateRouteJsonEntity;
import com.redhat.example.entity.ProcessEntity;

@Component
public class CreateJsonParameterRule {

    /** Route Entity */
    private CreateRouteJsonEntity route_entity;

    /** Package */
    private String package_name;

    /** Json Parameter Create */
    public CreateRouteJsonEntity createJsonParameter(String umlString) {

        // ルートエンティー初期化
        route_entity = new CreateRouteJsonEntity();
        package_name = "com.redhat.example";

        // 行分割
        String umlStringList[] = umlString.split("\n");
        ArrayList<ArrayList<String>> list = getProcessList(umlStringList);

        // ブロックごとの処理
        for(ArrayList<String> str : list) {
            if(str.get(0).equals("@startuml")){
                setTitleBlock(str);
            } else if(str.get(0).equals("|START|")) {
                setStartBlock(str);
            } else if(str.get(0).equals("|END|")) {
                setEndBlock(str);
            } else {

            }
            System.out.println(str);
        }

        return route_entity;
    }

    /** プロセスリストの取得 */
    public ArrayList<ArrayList<String>> getProcessList(String[] umlStringList) {

        ArrayList<ArrayList<String>> processList = new ArrayList<>();
        ArrayList<String> blocklist = new ArrayList<>();

        for (int i = 0; i < umlStringList.length; i++) {
            if (umlStringList[i].matches("^[|].*[|]$")) {
                ArrayList<String> blocklist_push = new ArrayList<>();
                for (int j = 0; j < blocklist.size(); j++) {
                    blocklist_push.add(blocklist.get(j));
                }
                processList.add(blocklist_push);
                blocklist = new ArrayList<String>(Arrays.asList(umlStringList[i]));
            } else {
                blocklist.add(umlStringList[i]);
            }
        }

        ArrayList<String> blocklist_last = new ArrayList<>();
        for (int k = 0; k < blocklist.size(); k++) {
            blocklist_last.add(blocklist.get(k));
        }
        processList.add(blocklist_last);

        return processList;
    }

    /** Title Block */
    public void setTitleBlock(ArrayList<String> strblocklist){
        ArrayList<String> meanlist = new ArrayList<>();
        for (int i = 0; i < strblocklist.size(); i++) {
            if(strblocklist.get(i).equals("@startuml") || strblocklist.get(i).equals("title") || strblocklist.get(i).equals("end title")) {
            } else {
                meanlist.add(strblocklist.get(i));
            }
        }

        for (int j = 0; j < meanlist.size(); j++) {
            this.route_entity.setService_name_japanese(meanlist.get(0));
            this.route_entity.setService_name(meanlist.get(1));
            this.package_name = meanlist.get(2);
        }
    }

    /** Start Block */
    public void setStartBlock(ArrayList<String> strblocklist){
        for (int i = 0; i < strblocklist.size(); i++) {
            if(strblocklist.get(i).equals("|START|") || strblocklist.get(i).equals("start") || strblocklist.get(i).equals("note left") || strblocklist.get(i).equals("end note")) {
            } else {
                if(strblocklist.get(i).startsWith("#Yellow")){
                } else {
                    if(this.route_entity.getFrom_end_point_uri() == null || this.route_entity.getFrom_end_point_uri().isEmpty()) {
                        this.route_entity.setFrom_end_point_uri(strblocklist.get(i).replace("*", "").replace(" ", ""));
                    } else {
                        if(this.route_entity.getRecieve_message_entity_class() == null || this.route_entity.getRecieve_message_entity_class().isEmpty()) {
                            this.route_entity.setRecieve_message_entity_class(this.package_name + ".entity." + strblocklist.get(i).replace("*", "").replace(" ", ""));
                        }
                    }
                }
            }
        }
    }

    /** End Block */
    public void setEndBlock(ArrayList<String> strblocklist){
        for (int i = 0; i < strblocklist.size(); i++) {
            if(strblocklist.get(i).equals("|END|") || strblocklist.get(i).equals("stop") || strblocklist.get(i).equals("note left") || strblocklist.get(i).equals("end note") || strblocklist.get(i).equals("@enduml")) {
            } else {
                if(strblocklist.get(i).startsWith("#Yellow")){
                } else {
                    if(this.route_entity.getTo_end_point_uri() == null || this.route_entity.getTo_end_point_uri().isEmpty()) {
                        this.route_entity.setTo_end_point_uri("#Ready Set");
                    } else if(this.route_entity.getTo_end_point_uri().equals("#Ready Set")) {
                        this.route_entity.setTo_end_point_uri(strblocklist.get(i).replace("*", "").replace(" ", ""));
                    } else {
                    }
                }
            }
        }
    }
}
