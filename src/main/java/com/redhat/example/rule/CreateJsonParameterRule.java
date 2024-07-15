package com.redhat.example.rule;

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
        List<ProcessEntity> process_list = new ArrayList<ProcessEntity>();
        this.route_entity.setProcess_list(process_list);

        // Package Initialize
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
                setProcessBlock(str);
            }
            //System.out.println(str);
        }
        route_entity.setPackage_name(package_name);
        
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

    /** Process Block */
    public void setProcessBlock(ArrayList<String> strblocklist){
        ProcessEntity process = new ProcessEntity();

        // プロセス名取得
        String process_nameList[] = strblocklist.get(0).replace("|", "").split("@");
        process.setProcess_name_japanese(process_nameList[0]);
        process.setProcess_name(process_nameList[1]);

        ArrayList<ArrayList<String>> processCompList = new ArrayList<>();
        ArrayList<String> processCompBlockList = new ArrayList<>();

        for (int i = 1; i < strblocklist.size(); i++) {
            if(strblocklist.get(i).startsWith("#Aqua") || strblocklist.get(i).startsWith("#Orange") || strblocklist.get(i).startsWith("#Lime") || strblocklist.get(i).startsWith("#GreenYellow")) {

                // Block push
                ArrayList<String> processCompBlockList_push = new ArrayList<>();
                for (int j = 0; j < processCompBlockList.size(); j++) {
                    processCompBlockList_push.add(processCompBlockList.get(j));
                }
                if(processCompBlockList_push.size() > 0) {
                    processCompList.add(processCompBlockList_push);
                }
                
                // Initialize List
                processCompBlockList = new ArrayList<String>(Arrays.asList(strblocklist.get(i)));

            } else {
                if(strblocklist.get(i).equals("note left") || strblocklist.get(i).equals("end note")) {
                } else {
                    processCompBlockList.add(strblocklist.get(i));
                }
            }
        }
        // Last Block push
        ArrayList<String> processCompBlockList_push2 = new ArrayList<>();
        for (int l = 0; l < processCompBlockList.size(); l++) {
            processCompBlockList_push2.add(processCompBlockList.get(l));
        }
        if(processCompBlockList_push2.size() > 0) {
            processCompList.add(processCompBlockList_push2);
        }

        for (int k = 0; k < processCompList.size(); k++) {
            process = getProcessBlockEntity(process, processCompList.get(k));
        }

        this.route_entity.getProcess_list().add(process);
    }
    
    // process block Entity
    public ProcessEntity getProcessBlockEntity(ProcessEntity processEnt, ArrayList<String> processCompBlockList) {
        // Service: API
        if(processCompBlockList.get(0).startsWith("#Aqua")) {
            processEnt.setProcess_type("api");
            processEnt.setHttp_url(processCompBlockList.get(1).replace("*", "").replace(" ", ""));
            processEnt.setHttp_method(processCompBlockList.get(2).replace("*", "").replace(" ", ""));
        // Rule: Bean
        } else if(processCompBlockList.get(0).startsWith("#Orange")) {
            processEnt.setProcess_type("bean");
            processEnt.setRule_class(processCompBlockList.get(1).replace("*", "").replace(" ", ""));
            processEnt.setRule_method(processCompBlockList.get(2).replace("*", "").replace(" ", ""));
        // Request
        } else if(processCompBlockList.get(0).startsWith("#Lime")) {
            String process_request_set_parameter = "";
            for (int i = 2; i < processCompBlockList.size(); i++) {
                process_request_set_parameter = process_request_set_parameter + processCompBlockList.get(i).trim();
            }
            processEnt.setProcess_request_set_parameter(process_request_set_parameter);
        // Response
        } else if(processCompBlockList.get(0).startsWith("#GreenYellow")) {
            String class_str = processCompBlockList.get(1).replace("*", "").replace(" ", "");
            processEnt.setResponse_entity_class(class_str.substring(0,1).toLowerCase() + class_str.substring(1, class_str.length()));
            processEnt.setBusiness_data(processCompBlockList.get(2).replace("*", "").replace(" ", ""));
        } else {
        }
        return processEnt;
    }
}
