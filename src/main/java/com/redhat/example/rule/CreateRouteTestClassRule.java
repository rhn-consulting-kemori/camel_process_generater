package com.redhat.example.rule;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

// Spring
import org.springframework.stereotype.Component;
import org.springframework.core.io.ClassPathResource;

// Business Object
import com.redhat.example.entity.CreateRouteJsonEntity;
import com.redhat.example.entity.ProcessEntity;
import com.redhat.example.entity.ClassYamlClassSetEntity;
import com.redhat.example.entity.TestRouteDataUnitEntity;

@Component
public class CreateRouteTestClassRule {

    // Default Import
    String[] defaultImportList = {
        "lombok.Data", 
        "org.springframework.stereotype.Component", 
        "com.fasterxml.jackson.databind.ObjectMapper", 
        "com.fasterxml.jackson.core.JsonProcessingException"
    };

    // Test Route Import
    String[] testRouteImportList = {
        "static org.junit.jupiter.api.Assertions.assertEquals",
        "static org.hamcrest.MatcherAssert.assertThat",
        "static org.hamcrest.CoreMatchers.is",
        "com.fasterxml.jackson.databind.ObjectMapper",
        "org.apache.camel.builder.AdviceWith",
        "org.apache.camel.CamelContext",
        "org.apache.camel.component.mock.MockEndpoint",
        "org.apache.camel.EndpointInject",
        "org.apache.camel.ProducerTemplate",
        "org.apache.camel.test.spring.junit5.CamelSpringBootTest",
        "org.apache.camel.test.spring.junit5.UseAdviceWith",
        "org.junit.jupiter.api.BeforeEach",
        "org.junit.jupiter.api.Test",
        "org.springframework.beans.factory.annotation.Autowired",
        "org.springframework.boot.test.context.SpringBootTest"
    };

    // Format
    private String FORMATPATH = "src/main/resources/testclass/";
    private String FORMATFILENAME = "routetest.txt";
    private String class_format;

    // Create Route Test Class
    public List<ClassYamlClassSetEntity> createTestClass(CreateRouteJsonEntity entity) {
        List<ClassYamlClassSetEntity> class_set_list = new ArrayList<ClassYamlClassSetEntity>();
        class_set_list.add(createDataProvider(entity));
        class_set_list.add(createRouteTestClass(entity));
        class_set_list.add(createNormalTestDataSetClass(entity));
        class_set_list.add(createErrorTestDataSetClass(entity));
        return class_set_list;
    }

    // Create DataProvider
    public ClassYamlClassSetEntity createDataProvider(CreateRouteJsonEntity entity) {
        ClassYamlClassSetEntity class_set = new ClassYamlClassSetEntity();
        class_set.setClassName("RouteProcessTestDataProvider");
        class_set.setPackageName(getFolderName(entity.getPackage_name() + ".route"));
        class_set.setJavaCode(createDataProviderCode(entity));
        return class_set;
    }

    // Create TestClass
    public ClassYamlClassSetEntity createRouteTestClass(CreateRouteJsonEntity entity) {
        ClassYamlClassSetEntity class_set = new ClassYamlClassSetEntity();
        class_set.setClassName("RouteProcessTest");
        class_set.setPackageName(getFolderName(entity.getPackage_name() + ".route"));
        class_set.setJavaCode(createRouteTestCode(entity));
        return class_set;
    }

    // Create Normal TestDataSetClass
    public ClassYamlClassSetEntity createNormalTestDataSetClass(CreateRouteJsonEntity entity) {
        ClassYamlClassSetEntity class_set = new ClassYamlClassSetEntity();
        class_set.setClassName("RouteProcessTestNormalDataSet");
        class_set.setPackageName(getFolderName(entity.getPackage_name() + ".route"));
        class_set.setJavaCode(createTestDataSetCode(entity, "RouteProcessTestNormalDataSet"));
        return class_set;
    }

    // Create Error TestDataSetClass
    public ClassYamlClassSetEntity createErrorTestDataSetClass(CreateRouteJsonEntity entity) {
        ClassYamlClassSetEntity class_set = new ClassYamlClassSetEntity();
        class_set.setClassName("RouteProcessTestErrorDataSet");
        class_set.setPackageName(getFolderName(entity.getPackage_name() + ".route"));
        class_set.setJavaCode(createTestDataSetCode(entity, "RouteProcessTestErrorDataSet"));
        return class_set;
    }

    // Create DataProvider Code
    public String createDataProviderCode(CreateRouteJsonEntity entity) {
        Map<String, TestRouteDataUnitEntity> object_map = getObject_map(entity);

        String javaCode = "";

        // Package
        javaCode = javaCode + "package " + entity.getPackage_name() + ".route;\n\n";

        // Import
        /** Default */
        javaCode = javaCode + "// Util\n";
        for (String import_unit: defaultImportList) {
            javaCode = javaCode + "import " + import_unit + ";\n";
        }
        /** Object */
        javaCode = javaCode + "\n";
        javaCode = javaCode + "// Business Object\n";
        List<String> import_type_list = new ArrayList<String>();
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            if(!import_type_list.contains(obj.getProcess_request_import())) {
                javaCode = javaCode + "import " + obj.getProcess_request_import() + ";\n";
                import_type_list.add(obj.getProcess_request_import());
            }
            if(!import_type_list.contains(obj.getProcess_response_import())) {
                javaCode = javaCode + "import " + obj.getProcess_response_import() + ";\n";
                import_type_list.add(obj.getProcess_response_import());
            }
        }
        javaCode = javaCode + "\n";

        // Annnotation
        javaCode = javaCode + "@Data\n";
        javaCode = javaCode + "@Component\n";
        
        // Class Start
        javaCode = javaCode + "public class RouteProcessTestDataProvider {\n";

        // Property
        javaCode = javaCode + "\n";
        javaCode = javaCode + "    /** Test Config */\n";
        javaCode = javaCode + "    public static final boolean RULE_INTEGRATION_FLG = false;\n\n";
        //----------------------------------------------------------------
        javaCode = javaCode + "    /** Expected Object Data */\n";
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "    " + obj.getProcess_request_class() + " " + obj.getProcess_request_name() + ";\n";
            javaCode = javaCode + "    " + obj.getProcess_response_class() + " " + obj.getProcess_response_name() + ";\n";
        }
        javaCode = javaCode + "\n";
        //----------------------------------------------------------------
        javaCode = javaCode + "    /** Expected Json Data */\n";
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "    String[] " + obj.getProcess_json_name() + ";\n";
        }
        javaCode = javaCode + "\n";

        // setNormalData()
        javaCode = javaCode + "    // Normal Data\n";
        javaCode = javaCode + "    public void setNormalData() {\n\n";
        javaCode = javaCode + "        RouteProcessTestNormalDataSet normalDataSet = new RouteProcessTestNormalDataSet();\n";
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "        " + obj.getProcess_request_name() + " = normalDataSet." + getGetterName(obj.getProcess_request_name()) + ";\n";
            javaCode = javaCode + "        " + obj.getProcess_response_name() + " = normalDataSet." + getGetterName(obj.getProcess_response_name()) + ";\n";
        }
        javaCode = javaCode + "\n";
        javaCode = javaCode + "        /** Set Json */\n";
        javaCode = javaCode + "        setNormalJsonData();\n";
        javaCode = javaCode + "    }\n\n";

        // setErrorData()
        javaCode = javaCode + "    // Error Data\n";
        javaCode = javaCode + "    public void setErrorData() {\n\n";
        javaCode = javaCode + "        RouteProcessTestErrorDataSet errorDataSet = new RouteProcessTestErrorDataSet();\n";
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "        " + obj.getProcess_request_name() + " = errorDataSet." + getGetterName(obj.getProcess_request_name()) + ";\n";
            javaCode = javaCode + "        " + obj.getProcess_response_name() + " = errorDataSet." + getGetterName(obj.getProcess_response_name()) + ";\n";
        }
        javaCode = javaCode + "\n";
        javaCode = javaCode + "        /** Set Json */\n";
        javaCode = javaCode + "        setErrorJsonData();\n";
        javaCode = javaCode + "    }\n\n";

        // setNormalJsonData()
        javaCode = javaCode + "    // Normal Json Data\n";
        javaCode = javaCode + "    public void setNormalJsonData() {\n";
        javaCode = javaCode + "        ObjectMapper mapper = new ObjectMapper();\n\n";
        javaCode = javaCode + "        try {\n";
        // ----------------------------------------------------------------
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "            " + obj.getProcess_json_name() + " = new String[] {\n";
            javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_request_name() + "),\n";
            javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_response_name() + "),\n";
            if(obj.getProcess_response_businessdata() == null || obj.getProcess_response_businessdata().isEmpty()) {
                javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_response_name() + ")\n";
            } else {
                javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_response_name() + "." + getGetterName(obj.getProcess_response_businessdata()) + ")\n";
            }
            javaCode = javaCode + "            };\n";
        }
        // ----------------------------------------------------------------
        javaCode = javaCode + "        } catch(JsonProcessingException e) {\n";
        javaCode = javaCode + "            e.printStackTrace();\n";
        javaCode = javaCode + "        }\n";
        javaCode = javaCode + "    }\n\n";

        // setErrorJsonData()
        javaCode = javaCode + "    // Error Json Data\n";
        javaCode = javaCode + "    public void setErrorJsonData() {\n";
        javaCode = javaCode + "        ObjectMapper mapper = new ObjectMapper();\n\n";
        javaCode = javaCode + "        try {\n";
        // ----------------------------------------------------------------
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "            " + obj.getProcess_json_name() + " = new String[] {\n";
            javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_request_name() + "),\n";
            javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_response_name() + "),\n";
            if(obj.getProcess_response_businessdata() == null || obj.getProcess_response_businessdata().isEmpty()) {
                javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_response_name() + ")\n";
            } else {
                javaCode = javaCode + "                mapper.writeValueAsString(" + obj.getProcess_response_name() + "." + getGetterName(obj.getProcess_response_businessdata()) + ")\n";
            }
            javaCode = javaCode + "            };\n";
        }
        // ----------------------------------------------------------------
        javaCode = javaCode + "        } catch(JsonProcessingException e) {\n";
        javaCode = javaCode + "            e.printStackTrace();\n";
        javaCode = javaCode + "        }\n";
        javaCode = javaCode + "    }\n\n";

        // Class End
        javaCode = javaCode + "}\n";

        return javaCode;
    }

    // Create TestClass Code
    public String createRouteTestCode(CreateRouteJsonEntity entity) {

        String javaCode = "";

        // Package
        javaCode = javaCode + "package " + entity.getPackage_name() + ".route;\n\n";

        // Import
        for (String import_unit: testRouteImportList) {
            javaCode = javaCode + "import " + import_unit + ";\n";
        }
        javaCode = javaCode + "import " + entity.getPackage_name() + ".route.RouteProcessTestDataProvider;\n\n";

        // Annotations
        javaCode = javaCode + "@SpringBootTest\n";
        javaCode = javaCode + "@CamelSpringBootTest\n";
        javaCode = javaCode + "@UseAdviceWith\n";

        // Class Start
        javaCode = javaCode + "public class RouteProcessTest {\n\n";

        // Properties
        /** Route ID */
        javaCode = javaCode + "    /** Route ID */";
        javaCode = javaCode + "    private static final String TARGET_ROUTE_ID_ROUTE_PROCESS = \"route-process\";\n";
        javaCode = javaCode + "    private static final String TARGET_ROUTE_ID_INITIAL_PROCESS = \"initial-process\";\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            javaCode = javaCode + "    private static final String " + getRouteIdName(process_unit.getProcess_name()) + " = \"" + process_unit.getProcess_name() + "\";\n";
        }
        javaCode = javaCode + "    private static final String TARGET_ROUTE_ID_FINISH_PROCESS = \"finish-process\";\n\n";

        /** CamelContext, DataProvider, Start EndPoint */
        javaCode = javaCode + "    /** CamelContext */\n";
        javaCode = javaCode + "    @Autowired\n";
        javaCode = javaCode + "    protected CamelContext camelContext;\n\n";
        javaCode = javaCode + "    /** Expected Data */\n";
        javaCode = javaCode + "    @Autowired\n";
        javaCode = javaCode + "    RouteProcessTestDataProvider dataProvider;\n\n";
        javaCode = javaCode + "    /** Start EndPoint */\n";
        javaCode = javaCode + "    @Autowired\n";
        javaCode = javaCode + "    protected ProducerTemplate start;\n\n";

        /** Mock EndPoint */
        javaCode = javaCode + "    /** Mock EndPoint */\n";
        javaCode = javaCode + "    @EndpointInject(\"mock:direct:initial-process\")\n";
        javaCode = javaCode + "    protected MockEndpoint mock_direct_initial_process;\n\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            javaCode = javaCode + "    @EndpointInject(\"mock:direct:" + process_unit.getProcess_name() + "\")\n";
            javaCode = javaCode + "    protected MockEndpoint mock_direct_" + process_unit.getProcess_name().replace("-", "_") + ";\n\n";
        }
        if(entity.getTo_end_point_uri().contains("kafka")) {
            javaCode = javaCode + "    @EndpointInject(\"mock:to-kafka-service-end\")\n";
            javaCode = javaCode + "    protected MockEndpoint mock_to_kafka_service_end;\n\n";
        } else if(entity.getTo_end_point_uri().contains("http")) {
            javaCode = javaCode + "    @EndpointInject(\"mock:to-http-service-end\")\n";
            javaCode = javaCode + "    protected MockEndpoint mock_to_http_service_end;\n\n";
        } else {
            javaCode = javaCode + "    @EndpointInject(\"mock:to-other-service-end\")\n";
            javaCode = javaCode + "    protected MockEndpoint mock_to_other_service_end;\n\n";
        }        
        javaCode = javaCode + "    @EndpointInject(\"mock:direct:finish-process\")\n";
        javaCode = javaCode + "    protected MockEndpoint mock_direct_finish_process;\n\n";

        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("bean") || process_unit.getProcess_type().equals("bean-end")) {
                javaCode = javaCode + "    @EndpointInject(\"mock:bean-" + process_unit.getProcess_name() + "-rule\")\n";
                javaCode = javaCode + "    protected MockEndpoint mock_bean_" + process_unit.getProcess_name().replace("-", "_") + "_rule;\n\n";
            } else if(process_unit.getProcess_type().equals("api")) {
                javaCode = javaCode + "    @EndpointInject(\"mock:http-" + process_unit.getProcess_name() + "-service\")\n";
                javaCode = javaCode + "    protected MockEndpoint mock_http_" + process_unit.getProcess_name().replace("-", "_") + "_service;\n\n";
            } else {
            }
        }

        // BeforeEach
        javaCode = javaCode + "    @BeforeEach\n";
        javaCode = javaCode + "    void beforeEach() throws Exception {\n\n";
        javaCode = javaCode + "        // Route Mock Setting\n";
        javaCode = javaCode + "        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_ROUTE_PROCESS,\n";
        javaCode = javaCode + "            advice -> {\n";
        javaCode = javaCode + "                advice.replaceFromWith(\"direct:start\");\n";
        if(entity.getTo_end_point_uri().contains("kafka")) {
            javaCode = javaCode + "                advice.weaveById(\"to-" + entity.getService_name() + "\").replace().to(\"mock:to-kafka-service-end\").id(\"to-kafka-service-end\");\n";
        } else if(entity.getTo_end_point_uri().contains("http")) {
            javaCode = javaCode + "                advice.weaveById(\"to-" + entity.getService_name() + "\").replace().to(\"mock:to-http-service-end\").id(\"to-http-service-end\");\n";
        } else {
            javaCode = javaCode + "                advice.weaveById(\"to-" + entity.getService_name() + "\").replace().to(\"mock:to-other-service-end\").id(\"to-other-service-end\");\n";
        }
        javaCode = javaCode + "                advice.mockEndpoints(\"direct:.+\");\n";
        javaCode = javaCode + "            }\n";
        javaCode = javaCode + "        );\n";
        javaCode = javaCode + "        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_INITIAL_PROCESS,\n";
        javaCode = javaCode + "            advice -> {\n";
        javaCode = javaCode + "                advice.mockEndpoints(\"direct:.+\");\n";
        javaCode = javaCode + "            }\n";
        javaCode = javaCode + "        );\n";
        
        for (ProcessEntity process_unit: entity.getProcess_list()){
            javaCode = javaCode + "        AdviceWith.adviceWith(camelContext, " + getRouteIdName(process_unit.getProcess_name()) + ",\n";
            javaCode = javaCode + "            advice -> {\n";
            javaCode = javaCode + "                advice.mockEndpoints(\"direct:.+\");\n";
            if(process_unit.getProcess_type().equals("bean") || process_unit.getProcess_type().equals("bean-end")) {
                javaCode = javaCode + "                if(!dataProvider.RULE_INTEGRATION_FLG) {\n";
                javaCode = javaCode + "                    advice.weaveById(\"bean-" + process_unit.getProcess_name() + "-rule\").replace().to(\"mock:bean-" + process_unit.getProcess_name() + "-rule\").id(\"bean-" + process_unit.getProcess_name() + "-rule\");\n";
                javaCode = javaCode + "                }\n";
            } else if(process_unit.getProcess_type().equals("api")) {
                javaCode = javaCode + "                advice.weaveById(\"to-" + process_unit.getProcess_name() + "-service\").replace().to(\"mock:http-" + process_unit.getProcess_name() + "-service\").id(\"to-" + process_unit.getProcess_name() + "-service\");\n";
            } else {
            }
            javaCode = javaCode + "            }\n";
            javaCode = javaCode + "        );\n";
        }

        javaCode = javaCode + "        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_FINISH_PROCESS,\n";
        javaCode = javaCode + "            advice -> {\n";
        javaCode = javaCode + "                advice.mockEndpoints(\"direct:.+\");\n";
        javaCode = javaCode + "            }\n";
        javaCode = javaCode + "        );\n";
        javaCode = javaCode + "        camelContext.start();\n";
        javaCode = javaCode + "    }\n\n";

        // Test
        getClassFormat();
        javaCode = javaCode + class_format;
        javaCode = javaCode + "\n";

        // Assert Condition: Normal
        javaCode = javaCode + "    // Assert Condition: Normal\n";
        javaCode = javaCode + "    // *** Edit Assert Condition ***\n";
        javaCode = javaCode + "    public void setNormalAssertCondition() {\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        // Mock Direct Endpoint: Expected Message Count\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        mock_direct_initial_process.expectedMessageCount(1);\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            javaCode = javaCode + "        mock_direct_" + process_unit.getProcess_name().replace("-", "_") + ".expectedMessageCount(1);\n";
        }
        if(entity.getTo_end_point_uri().contains("kafka")) {
            javaCode = javaCode + "        mock_to_kafka_service_end.expectedMessageCount(1);\n";
        } else if(entity.getTo_end_point_uri().contains("http")) {
            javaCode = javaCode + "        mock_to_http_service_end.expectedMessageCount(1);\n";
        } else {
            javaCode = javaCode + "        mock_to_other_service_end.expectedMessageCount(1);\n";
        }
        javaCode = javaCode + "        mock_direct_finish_process.expectedMessageCount(1);\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        // Mock Rule/API Endpoint: Expected Message Count\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        if(!dataProvider.RULE_INTEGRATION_FLG) {\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("bean") || process_unit.getProcess_type().equals("bean-end")) {
                javaCode = javaCode + "            mock_bean_" + process_unit.getProcess_name().replace("-", "_") + "_rule.expectedMessageCount(1);\n";
            }
        }
        javaCode = javaCode + "        }\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("api")) {
                javaCode = javaCode + "        mock_http_" + process_unit.getProcess_name().replace("-", "_") + "_service.expectedMessageCount(1);\n";
            }
        }
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        // Exchange Property: Expected Data\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        mock_direct_finish_process.expectedPropertyReceived(\"process_request\", dataProvider.getRoute_request());\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getBusiness_data() == null || process_unit.getBusiness_data().isEmpty()) {
                javaCode = javaCode + "        mock_direct_finish_process.expectedPropertyReceived(\"" + process_unit.getProcess_name() + "_response" + "\", dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_response") + ");\n";
            } else {
                javaCode = javaCode + "        mock_direct_finish_process.expectedPropertyReceived(\"" + process_unit.getBusiness_data() + "\", dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_response") + "." + getGetterName(process_unit.getBusiness_data()) + ");\n";
            }
        }
        javaCode = javaCode + "    }\n\n";

        // Assert Condition: Error
        javaCode = javaCode + "    // Assert Condition: Error\n";
        javaCode = javaCode + "    // *** Edit Assert Condition ***\n";
        javaCode = javaCode + "    public void setErrorAssertCondition() {\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        // Mock Direct Endpoint: Expected Message Count\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        mock_direct_initial_process.expectedMessageCount(1);\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            javaCode = javaCode + "        mock_direct_" + process_unit.getProcess_name().replace("-", "_") + ".expectedMessageCount(1);\n";
        }
        if(entity.getTo_end_point_uri().contains("kafka")) {
            javaCode = javaCode + "        mock_to_kafka_service_end.expectedMessageCount(1);\n";
        } else if(entity.getTo_end_point_uri().contains("http")) {
            javaCode = javaCode + "        mock_to_http_service_end.expectedMessageCount(1);\n";
        } else {
            javaCode = javaCode + "        mock_to_other_service_end.expectedMessageCount(1);\n";
        }
        javaCode = javaCode + "        mock_direct_finish_process.expectedMessageCount(1);\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        // Mock Rule/API Endpoint: Expected Message Count\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        if(!dataProvider.RULE_INTEGRATION_FLG) {\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("bean") || process_unit.getProcess_type().equals("bean-end")) {
                javaCode = javaCode + "            mock_bean_" + process_unit.getProcess_name().replace("-", "_") + "_rule.expectedMessageCount(1);\n";
            }
        }
        javaCode = javaCode + "        }\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("api")) {
                javaCode = javaCode + "        mock_http_" + process_unit.getProcess_name().replace("-", "_") + "_service.expectedMessageCount(1);\n";
            }
        }
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        // Exchange Property: Expected Data\n";
        javaCode = javaCode + "        // ----------------------------------------------------------------\n";
        javaCode = javaCode + "        mock_direct_finish_process.expectedPropertyReceived(\"process_request\", dataProvider.getRoute_request());\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getBusiness_data() == null || process_unit.getBusiness_data().isEmpty()) {
                javaCode = javaCode + "        mock_direct_finish_process.expectedPropertyReceived(\"" + process_unit.getProcess_name() + "_response" + "\", dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_response") + ");\n";
            } else {
                javaCode = javaCode + "        mock_direct_finish_process.expectedPropertyReceived(\"" + process_unit.getBusiness_data() + "\", dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_response") + "." + getGetterName(process_unit.getBusiness_data()) + ");\n";
            }
        }
        javaCode = javaCode + "    }\n\n";

        // Mock設定: Bean
        javaCode = javaCode + "    // Mock設定: Bean\n";
        javaCode = javaCode + "    public void setMockBeanEndpoint() {\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("bean") || process_unit.getProcess_type().equals("bean-end")) {
                javaCode = javaCode + "        mock_bean_" + process_unit.getProcess_name().replace("-", "_") + "_rule.whenAnyExchangeReceived(\n";
                javaCode = javaCode + "            e -> { \n";
                javaCode = javaCode + "                assertThat(e.getMessage().getBody(), is(dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_request") + "));\n";
                javaCode = javaCode + "                e.getMessage().setBody(dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_response") + ");\n";
                javaCode = javaCode + "            });\n";
            }
        }
        javaCode = javaCode + "    }\n\n";

        // Mock設定: Http
        javaCode = javaCode + "    // Mock設定: Http\n";
        javaCode = javaCode + "    public void setMockHttpEndpoint() {\n";
        javaCode = javaCode + "        ObjectMapper mapper = new ObjectMapper();\n";
        for (ProcessEntity process_unit: entity.getProcess_list()){
            if(process_unit.getProcess_type().equals("api")) {
                javaCode = javaCode + "        mock_http_" + process_unit.getProcess_name().replace("-", "_") + "_service.whenAnyExchangeReceived(\n";
                javaCode = javaCode + "            e -> { \n";
                javaCode = javaCode + "                assertEquals(mapper.readTree(dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_json") + "[0]), mapper.readTree(e.getMessage().getBody().toString()));\n";
                javaCode = javaCode + "                e.getMessage().setBody(dataProvider." + getGetterName(process_unit.getProcess_name().replace("-", "_") + "_json") + "[1]);\n"; 
                javaCode = javaCode + "            });\n";
            }
        }
        javaCode = javaCode + "    }\n\n";

        // Class End
        javaCode = javaCode + "}\n";

        return javaCode;
    }

    // Test Data Set Class
    public String createTestDataSetCode(CreateRouteJsonEntity entity, String class_name) {
        Map<String, TestRouteDataUnitEntity> object_map = getObject_map(entity);

        String javaCode = "";

        // Package
        javaCode = javaCode + "package " + entity.getPackage_name() + ".route;\n\n";

        // Import
        /** Default */
        javaCode = javaCode + "// Util\n";
        for (String import_unit: defaultImportList) {
            javaCode = javaCode + "import " + import_unit + ";\n";
        }
        /** Object */
        javaCode = javaCode + "\n";
        javaCode = javaCode + "// Business Object\n";
        List<String> import_type_list = new ArrayList<String>();
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            if(!import_type_list.contains(obj.getProcess_request_import())) {
                javaCode = javaCode + "import " + obj.getProcess_request_import() + ";\n";
                import_type_list.add(obj.getProcess_request_import());
            }
            if(!import_type_list.contains(obj.getProcess_response_import())) {
                javaCode = javaCode + "import " + obj.getProcess_response_import() + ";\n";
                import_type_list.add(obj.getProcess_response_import());
            }
        }
        javaCode = javaCode + "\n";

        // Annnotation
        javaCode = javaCode + "@Data\n";
        javaCode = javaCode + "@Component\n";
        
        // Class Start
        javaCode = javaCode + "public class " + class_name + " {\n\n";

        // Property
        javaCode = javaCode + "    /** Expected Object Data */\n";
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "    " + obj.getProcess_request_class() + " " + obj.getProcess_request_name() + ";\n";
            javaCode = javaCode + "    " + obj.getProcess_response_class() + " " + obj.getProcess_response_name() + ";\n";
        }
        javaCode = javaCode + "\n";
        javaCode = javaCode + "    ObjectMapper mapper = new ObjectMapper();\n\n";

        // Constructor
        javaCode = javaCode + "    public " + class_name + "() {\n";
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "        " + getSetterName(obj.getProcess_request_name()) + ";\n";
            javaCode = javaCode + "        " + getSetterName(obj.getProcess_response_name()) + ";\n";
        }
        javaCode = javaCode + "    }\n\n";

        // Setter
        for(Map.Entry<String, TestRouteDataUnitEntity> entry : object_map.entrySet()) {
            TestRouteDataUnitEntity obj = entry.getValue();
            javaCode = javaCode + "    /** " + obj.getProcess_request_class() + " " + obj.getProcess_request_name() + " */\n";
            javaCode = javaCode + "    public void " + getSetterName(obj.getProcess_request_name()) + " {\n";
            javaCode = javaCode + "        " + obj.getProcess_request_name() + " = new " + obj.getProcess_request_class() + "();\n";
            javaCode = javaCode + "    }\n\n";
            javaCode = javaCode + "    /** " + obj.getProcess_response_class() + " " + obj.getProcess_response_name() + " */\n";
            javaCode = javaCode + "    public void " + getSetterName(obj.getProcess_response_name()) + " {\n";
            javaCode = javaCode + "        " + obj.getProcess_response_name() + " = new " + obj.getProcess_response_class() + "();\n";
            javaCode = javaCode + "    }\n\n";
        }

        // Class End
        javaCode = javaCode + "}\n";

        return javaCode;
    }

    // Get Simple Class Name
    public String getSimpleClassName(String fullClassName) {
        String[] class_part = fullClassName.split("\\.");
        return class_part[class_part.length - 1];
    }

    // GetterName
    public String getGetterName(String property_name) {
        return "get" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1) + "()";
    }

    // SetterName
    public String getSetterName(String property_name) {
        return "set" + property_name.substring(0, 1).toUpperCase() + property_name.substring(1) + "()";
    }

    // Get Route ID Name
    public String getRouteIdName(String process_name) {
        return "TARGET_ROUTE_ID_" + process_name.replace("-", "_").toUpperCase();
    }

    /** Folder Name */
    public String getFolderName (String package_name) {
        String foldername = "";
        String[] packagelist = package_name.split(Pattern.quote("."));
        for (int i = 0; i < packagelist.length; i++) {
            foldername = foldername + "/" + packagelist[i];
        }
        return foldername;
    }

    // Get Object Map
    public Map<String, TestRouteDataUnitEntity> getObject_map(CreateRouteJsonEntity entity) {
        Map<String, TestRouteDataUnitEntity> object_map = new HashMap();
        object_map.put(
            "route_process", 
            new TestRouteDataUnitEntity(
                "route_process", 
                "route_process_json",
                "route_request",
                getSimpleClassName(entity.getRecieve_message_entity_class()), 
                entity.getRecieve_message_entity_class(),
                "route_response",
                getSimpleClassName(entity.getResponse_message_entity_class()),
                entity.getResponse_message_entity_class(),
                ""
            )
        );

        for (ProcessEntity process_unit: entity.getProcess_list()){
            String process_name = process_unit.getProcess_name().replace("-", "_");
            object_map.put(
                process_name, 
                new TestRouteDataUnitEntity(
                    process_name, 
                    process_name + "_json",
                    process_name + "_request",
                    getSimpleClassName(process_unit.getRequest_entity_class()), 
                    process_unit.getRequest_entity_class(),
                    process_name + "_response",
                    getSimpleClassName(process_unit.getResponse_entity_class()),
                    process_unit.getResponse_entity_class(),
                    process_unit.getBusiness_data()
                )
            );
        }
        return object_map;
    }

    /** Get Class Format */
    public void getClassFormat() {
        File resource = null;
        try {
            Path path = Paths.get(FORMATPATH, FORMATFILENAME);
            class_format = Files.readString(path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }            
    }

}
