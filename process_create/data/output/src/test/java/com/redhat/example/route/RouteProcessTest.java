package com.redhat.example.route;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.CamelContext;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.redhat.example.route.RouteProcessTestDataProvider;

@SpringBootTest
@CamelSpringBootTest
@UseAdviceWith
public class RouteProcessTest {

    /** Route ID */    private static final String TARGET_ROUTE_ID_ROUTE_PROCESS = "route-process";
    private static final String TARGET_ROUTE_ID_INITIAL_PROCESS = "initial-process";
    private static final String TARGET_ROUTE_ID_FORMAT_CHECK = "format-check";
    private static final String TARGET_ROUTE_ID_DEPOSIT_ENTRY_CHECK = "deposit-entry-check";
    private static final String TARGET_ROUTE_ID_DEPOSIT_CATEGORY = "deposit-category";
    private static final String TARGET_ROUTE_ID_CHECK_AVAILABLE_DEPOSIT_AMOUNT = "check-available-deposit-amount";
    private static final String TARGET_ROUTE_ID_DEPOSIT_ALLOCATION = "deposit-allocation";
    private static final String TARGET_ROUTE_ID_DEPOSIT = "deposit";
    private static final String TARGET_ROUTE_ID_DEPOSIT_RESULT_MESSAGE = "deposit-result-message";
    private static final String TARGET_ROUTE_ID_FINISH_PROCESS = "finish-process";

    /** CamelContext */
    @Autowired
    protected CamelContext camelContext;

    /** Expected Data */
    @Autowired
    RouteProcessTestDataProvider dataProvider;

    /** Start EndPoint */
    @Autowired
    protected ProducerTemplate start;

    /** Mock EndPoint */
    @EndpointInject("mock:direct:initial-process")
    protected MockEndpoint mock_direct_initial_process;

    @EndpointInject("mock:direct:format-check")
    protected MockEndpoint mock_direct_format_check;

    @EndpointInject("mock:direct:deposit-entry-check")
    protected MockEndpoint mock_direct_deposit_entry_check;

    @EndpointInject("mock:direct:deposit-category")
    protected MockEndpoint mock_direct_deposit_category;

    @EndpointInject("mock:direct:check-available-deposit-amount")
    protected MockEndpoint mock_direct_check_available_deposit_amount;

    @EndpointInject("mock:direct:deposit-allocation")
    protected MockEndpoint mock_direct_deposit_allocation;

    @EndpointInject("mock:direct:deposit")
    protected MockEndpoint mock_direct_deposit;

    @EndpointInject("mock:direct:deposit-result-message")
    protected MockEndpoint mock_direct_deposit_result_message;

    @EndpointInject("mock:to-kafka-service-end")
    protected MockEndpoint mock_to_kafka_service_end;

    @EndpointInject("mock:direct:finish-process")
    protected MockEndpoint mock_direct_finish_process;

    @EndpointInject("mock:bean-format-check-rule")
    protected MockEndpoint mock_bean_format_check_rule;

    @EndpointInject("mock:http-deposit-entry-check-service")
    protected MockEndpoint mock_http_deposit_entry_check_service;

    @EndpointInject("mock:http-deposit-category-service")
    protected MockEndpoint mock_http_deposit_category_service;

    @EndpointInject("mock:http-check-available-deposit-amount-service")
    protected MockEndpoint mock_http_check_available_deposit_amount_service;

    @EndpointInject("mock:http-deposit-allocation-service")
    protected MockEndpoint mock_http_deposit_allocation_service;

    @EndpointInject("mock:http-deposit-service")
    protected MockEndpoint mock_http_deposit_service;

    @EndpointInject("mock:bean-deposit-result-message-rule")
    protected MockEndpoint mock_bean_deposit_result_message_rule;

    @BeforeEach
    void beforeEach() throws Exception {

        // Route Mock Setting
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_ROUTE_PROCESS,
            advice -> {
                advice.replaceFromWith("direct:start");
                advice.weaveById("to-chosabu-kijitsu-deposit-service").replace().to("mock:to-kafka-service-end").id("to-kafka-service-end");
                advice.mockEndpoints("direct:.+");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_INITIAL_PROCESS,
            advice -> {
                advice.mockEndpoints("direct:.+");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_FORMAT_CHECK,
            advice -> {
                advice.mockEndpoints("direct:.+");
                if(!dataProvider.RULE_INTEGRATION_FLG) {
                    advice.weaveById("bean-format-check-rule").replace().to("mock:bean-format-check-rule").id("bean-format-check-rule");
                }
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_DEPOSIT_ENTRY_CHECK,
            advice -> {
                advice.mockEndpoints("direct:.+");
                advice.weaveById("to-deposit-entry-check-service").replace().to("mock:http-deposit-entry-check-service").id("to-deposit-entry-check-service");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_DEPOSIT_CATEGORY,
            advice -> {
                advice.mockEndpoints("direct:.+");
                advice.weaveById("to-deposit-category-service").replace().to("mock:http-deposit-category-service").id("to-deposit-category-service");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_CHECK_AVAILABLE_DEPOSIT_AMOUNT,
            advice -> {
                advice.mockEndpoints("direct:.+");
                advice.weaveById("to-check-available-deposit-amount-service").replace().to("mock:http-check-available-deposit-amount-service").id("to-check-available-deposit-amount-service");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_DEPOSIT_ALLOCATION,
            advice -> {
                advice.mockEndpoints("direct:.+");
                advice.weaveById("to-deposit-allocation-service").replace().to("mock:http-deposit-allocation-service").id("to-deposit-allocation-service");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_DEPOSIT,
            advice -> {
                advice.mockEndpoints("direct:.+");
                advice.weaveById("to-deposit-service").replace().to("mock:http-deposit-service").id("to-deposit-service");
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_DEPOSIT_RESULT_MESSAGE,
            advice -> {
                advice.mockEndpoints("direct:.+");
                if(!dataProvider.RULE_INTEGRATION_FLG) {
                    advice.weaveById("bean-deposit-result-message-rule").replace().to("mock:bean-deposit-result-message-rule").id("bean-deposit-result-message-rule");
                }
            }
        );
        AdviceWith.adviceWith(camelContext, TARGET_ROUTE_ID_FINISH_PROCESS,
            advice -> {
                advice.mockEndpoints("direct:.+");
            }
        );
        camelContext.start();
    }

    @Test
    public void testRun() throws Exception {
        
        // Normal Test
        testNormal();
        MockEndpoint.resetMocks(camelContext);

        // Error Test
        testError();
        MockEndpoint.resetMocks(camelContext);
        
    }

    /** Normal Test */
    public void testNormal() throws Exception {

        // Given
        dataProvider.setNormalData();
        if(!dataProvider.RULE_INTEGRATION_FLG) {
            setMockBeanEndpoint();
        }
        System.out.println("Rule Integration: " + dataProvider.RULE_INTEGRATION_FLG);
        setMockHttpEndpoint();
        setNormalAssertCondition();

        // When
        start.sendBody("direct:start", dataProvider.getRoute_process_json()[0]);

        //Then
        MockEndpoint.assertIsSatisfied(camelContext);

    }

    /** Error Test */
    public void testError() throws Exception {

        // Given
        dataProvider.setErrorData();
        if(!dataProvider.RULE_INTEGRATION_FLG) {
            setMockBeanEndpoint();
        }
        System.out.println("Rule Integration: " + dataProvider.RULE_INTEGRATION_FLG);
        setMockHttpEndpoint();
        setErrorAssertCondition();

        // When
        start.sendBody("direct:start", dataProvider.getRoute_process_json()[0]);

        //Then
        MockEndpoint.assertIsSatisfied(camelContext);

    }

    // Assert Condition: Normal
    // *** Edit Assert Condition ***
    public void setNormalAssertCondition() {
        // ----------------------------------------------------------------
        // Mock Direct Endpoint: Expected Message Count
        // ----------------------------------------------------------------
        mock_direct_initial_process.expectedMessageCount(1);
        mock_direct_format_check.expectedMessageCount(1);
        mock_direct_deposit_entry_check.expectedMessageCount(1);
        mock_direct_deposit_category.expectedMessageCount(1);
        mock_direct_check_available_deposit_amount.expectedMessageCount(1);
        mock_direct_deposit_allocation.expectedMessageCount(1);
        mock_direct_deposit.expectedMessageCount(1);
        mock_direct_deposit_result_message.expectedMessageCount(1);
        mock_to_kafka_service_end.expectedMessageCount(1);
        mock_direct_finish_process.expectedMessageCount(1);
        // ----------------------------------------------------------------
        // Mock Rule/API Endpoint: Expected Message Count
        // ----------------------------------------------------------------
        if(!dataProvider.RULE_INTEGRATION_FLG) {
            mock_bean_format_check_rule.expectedMessageCount(1);
            mock_bean_deposit_result_message_rule.expectedMessageCount(1);
        }
        mock_http_deposit_entry_check_service.expectedMessageCount(1);
        mock_http_deposit_category_service.expectedMessageCount(1);
        mock_http_check_available_deposit_amount_service.expectedMessageCount(1);
        mock_http_deposit_allocation_service.expectedMessageCount(1);
        mock_http_deposit_service.expectedMessageCount(1);
        // ----------------------------------------------------------------
        // Exchange Property: Expected Data
        // ----------------------------------------------------------------
        mock_direct_finish_process.expectedPropertyReceived("process_request", dataProvider.getRoute_request());
        mock_direct_finish_process.expectedPropertyReceived("format-check_response", dataProvider.getFormat_check_response());
        mock_direct_finish_process.expectedPropertyReceived("response_result", dataProvider.getDeposit_entry_check_response().getResponse_result());
        mock_direct_finish_process.expectedPropertyReceived("deposit_category_code", dataProvider.getDeposit_category_response().getDeposit_category_code());
        mock_direct_finish_process.expectedPropertyReceived("deposit_available_amount_data", dataProvider.getCheck_available_deposit_amount_response().getDeposit_available_amount_data());
        mock_direct_finish_process.expectedPropertyReceived("deposit_allocation_data", dataProvider.getDeposit_allocation_response().getDeposit_allocation_data());
        mock_direct_finish_process.expectedPropertyReceived("deposit_data", dataProvider.getDeposit_response().getDeposit_data());
        mock_direct_finish_process.expectedPropertyReceived("deposit-result-message_response", dataProvider.getDeposit_result_message_response());
    }

    // Assert Condition: Error
    // *** Edit Assert Condition ***
    public void setErrorAssertCondition() {
        // ----------------------------------------------------------------
        // Mock Direct Endpoint: Expected Message Count
        // ----------------------------------------------------------------
        mock_direct_initial_process.expectedMessageCount(1);
        mock_direct_format_check.expectedMessageCount(1);
        mock_direct_deposit_entry_check.expectedMessageCount(1);
        mock_direct_deposit_category.expectedMessageCount(1);
        mock_direct_check_available_deposit_amount.expectedMessageCount(1);
        mock_direct_deposit_allocation.expectedMessageCount(1);
        mock_direct_deposit.expectedMessageCount(1);
        mock_direct_deposit_result_message.expectedMessageCount(1);
        mock_to_kafka_service_end.expectedMessageCount(1);
        mock_direct_finish_process.expectedMessageCount(1);
        // ----------------------------------------------------------------
        // Mock Rule/API Endpoint: Expected Message Count
        // ----------------------------------------------------------------
        if(!dataProvider.RULE_INTEGRATION_FLG) {
            mock_bean_format_check_rule.expectedMessageCount(1);
            mock_bean_deposit_result_message_rule.expectedMessageCount(1);
        }
        mock_http_deposit_entry_check_service.expectedMessageCount(1);
        mock_http_deposit_category_service.expectedMessageCount(1);
        mock_http_check_available_deposit_amount_service.expectedMessageCount(1);
        mock_http_deposit_allocation_service.expectedMessageCount(1);
        mock_http_deposit_service.expectedMessageCount(1);
        // ----------------------------------------------------------------
        // Exchange Property: Expected Data
        // ----------------------------------------------------------------
        mock_direct_finish_process.expectedPropertyReceived("process_request", dataProvider.getRoute_request());
        mock_direct_finish_process.expectedPropertyReceived("format-check_response", dataProvider.getFormat_check_response());
        mock_direct_finish_process.expectedPropertyReceived("response_result", dataProvider.getDeposit_entry_check_response().getResponse_result());
        mock_direct_finish_process.expectedPropertyReceived("deposit_category_code", dataProvider.getDeposit_category_response().getDeposit_category_code());
        mock_direct_finish_process.expectedPropertyReceived("deposit_available_amount_data", dataProvider.getCheck_available_deposit_amount_response().getDeposit_available_amount_data());
        mock_direct_finish_process.expectedPropertyReceived("deposit_allocation_data", dataProvider.getDeposit_allocation_response().getDeposit_allocation_data());
        mock_direct_finish_process.expectedPropertyReceived("deposit_data", dataProvider.getDeposit_response().getDeposit_data());
        mock_direct_finish_process.expectedPropertyReceived("deposit-result-message_response", dataProvider.getDeposit_result_message_response());
    }

    // Mock設定: Bean
    public void setMockBeanEndpoint() {
        mock_bean_format_check_rule.whenAnyExchangeReceived(
            e -> { 
                assertThat(e.getMessage().getBody(), is(dataProvider.getFormat_check_request()));
                e.getMessage().setBody(dataProvider.getFormat_check_response());
            });
        mock_bean_deposit_result_message_rule.whenAnyExchangeReceived(
            e -> { 
                assertThat(e.getMessage().getBody(), is(dataProvider.getDeposit_result_message_request()));
                e.getMessage().setBody(dataProvider.getDeposit_result_message_response());
            });
    }

    // Mock設定: Http
    public void setMockHttpEndpoint() {
        ObjectMapper mapper = new ObjectMapper();
        mock_http_deposit_entry_check_service.whenAnyExchangeReceived(
            e -> { 
                assertEquals(mapper.readTree(dataProvider.getDeposit_entry_check_json()[0]), mapper.readTree(e.getMessage().getBody().toString()));
                e.getMessage().setBody(dataProvider.getDeposit_entry_check_json()[1]);
            });
        mock_http_deposit_category_service.whenAnyExchangeReceived(
            e -> { 
                assertEquals(mapper.readTree(dataProvider.getDeposit_category_json()[0]), mapper.readTree(e.getMessage().getBody().toString()));
                e.getMessage().setBody(dataProvider.getDeposit_category_json()[1]);
            });
        mock_http_check_available_deposit_amount_service.whenAnyExchangeReceived(
            e -> { 
                assertEquals(mapper.readTree(dataProvider.getCheck_available_deposit_amount_json()[0]), mapper.readTree(e.getMessage().getBody().toString()));
                e.getMessage().setBody(dataProvider.getCheck_available_deposit_amount_json()[1]);
            });
        mock_http_deposit_allocation_service.whenAnyExchangeReceived(
            e -> { 
                assertEquals(mapper.readTree(dataProvider.getDeposit_allocation_json()[0]), mapper.readTree(e.getMessage().getBody().toString()));
                e.getMessage().setBody(dataProvider.getDeposit_allocation_json()[1]);
            });
        mock_http_deposit_service.whenAnyExchangeReceived(
            e -> { 
                assertEquals(mapper.readTree(dataProvider.getDeposit_json()[0]), mapper.readTree(e.getMessage().getBody().toString()));
                e.getMessage().setBody(dataProvider.getDeposit_json()[1]);
            });
    }

}
