package com.redhat.example.route;

// Util
import lombok.Data;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

// Business Object
import com.redhat.example.entity.KijitsuNyukinRequestEntity;
import com.redhat.example.entity.KijitsuNyukinResponseEntity;
import com.redhat.example.type.DepositCategoryRequestType;
import com.redhat.example.type.DepositCategoryResponseType;
import com.redhat.example.type.DepositAllocationRequestType;
import com.redhat.example.type.DepositAllocationResponseType;
import com.redhat.example.type.CheckAvailableDepositAmountRequestType;
import com.redhat.example.type.CheckAvailableDepositAmountResponseType;
import com.redhat.example.type.DepositResultMessageRequestType;
import com.redhat.example.type.DepositRequestType;
import com.redhat.example.type.DepositResponseType;
import com.redhat.example.type.FormatCheckResponseType;
import com.redhat.example.type.DepositEntryCheckRequestType;
import com.redhat.example.type.DepositEntryCheckResponseType;

@Data
@Component
public class RouteProcessTestDataProvider {

    /** Test Config */
    public static final boolean RULE_INTEGRATION_FLG = false;

    /** Expected Object Data */
    KijitsuNyukinRequestEntity route_request;
    KijitsuNyukinResponseEntity route_response;
    DepositCategoryRequestType deposit_category_request;
    DepositCategoryResponseType deposit_category_response;
    DepositAllocationRequestType deposit_allocation_request;
    DepositAllocationResponseType deposit_allocation_response;
    CheckAvailableDepositAmountRequestType check_available_deposit_amount_request;
    CheckAvailableDepositAmountResponseType check_available_deposit_amount_response;
    DepositResultMessageRequestType deposit_result_message_request;
    KijitsuNyukinResponseEntity deposit_result_message_response;
    DepositRequestType deposit_request;
    DepositResponseType deposit_response;
    KijitsuNyukinRequestEntity format_check_request;
    FormatCheckResponseType format_check_response;
    DepositEntryCheckRequestType deposit_entry_check_request;
    DepositEntryCheckResponseType deposit_entry_check_response;

    /** Expected Json Data */
    String[] route_process_json;
    String[] deposit_category_json;
    String[] deposit_allocation_json;
    String[] check_available_deposit_amount_json;
    String[] deposit_result_message_json;
    String[] deposit_json;
    String[] format_check_json;
    String[] deposit_entry_check_json;

    // Normal Data
    public void setNormalData() {

        RouteProcessTestNormalDataSet normalDataSet = new RouteProcessTestNormalDataSet();
        route_request = normalDataSet.getRoute_request();
        route_response = normalDataSet.getRoute_response();
        deposit_category_request = normalDataSet.getDeposit_category_request();
        deposit_category_response = normalDataSet.getDeposit_category_response();
        deposit_allocation_request = normalDataSet.getDeposit_allocation_request();
        deposit_allocation_response = normalDataSet.getDeposit_allocation_response();
        check_available_deposit_amount_request = normalDataSet.getCheck_available_deposit_amount_request();
        check_available_deposit_amount_response = normalDataSet.getCheck_available_deposit_amount_response();
        deposit_result_message_request = normalDataSet.getDeposit_result_message_request();
        deposit_result_message_response = normalDataSet.getDeposit_result_message_response();
        deposit_request = normalDataSet.getDeposit_request();
        deposit_response = normalDataSet.getDeposit_response();
        format_check_request = normalDataSet.getFormat_check_request();
        format_check_response = normalDataSet.getFormat_check_response();
        deposit_entry_check_request = normalDataSet.getDeposit_entry_check_request();
        deposit_entry_check_response = normalDataSet.getDeposit_entry_check_response();

        /** Set Json */
        setNormalJsonData();
    }

    // Error Data
    public void setErrorData() {

        RouteProcessTestErrorDataSet errorDataSet = new RouteProcessTestErrorDataSet();
        route_request = errorDataSet.getRoute_request();
        route_response = errorDataSet.getRoute_response();
        deposit_category_request = errorDataSet.getDeposit_category_request();
        deposit_category_response = errorDataSet.getDeposit_category_response();
        deposit_allocation_request = errorDataSet.getDeposit_allocation_request();
        deposit_allocation_response = errorDataSet.getDeposit_allocation_response();
        check_available_deposit_amount_request = errorDataSet.getCheck_available_deposit_amount_request();
        check_available_deposit_amount_response = errorDataSet.getCheck_available_deposit_amount_response();
        deposit_result_message_request = errorDataSet.getDeposit_result_message_request();
        deposit_result_message_response = errorDataSet.getDeposit_result_message_response();
        deposit_request = errorDataSet.getDeposit_request();
        deposit_response = errorDataSet.getDeposit_response();
        format_check_request = errorDataSet.getFormat_check_request();
        format_check_response = errorDataSet.getFormat_check_response();
        deposit_entry_check_request = errorDataSet.getDeposit_entry_check_request();
        deposit_entry_check_response = errorDataSet.getDeposit_entry_check_response();

        /** Set Json */
        setErrorJsonData();
    }

    // Normal Json Data
    public void setNormalJsonData() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            route_process_json = new String[] {
                mapper.writeValueAsString(route_request),
                mapper.writeValueAsString(route_response),
                mapper.writeValueAsString(route_response)
            };
            deposit_category_json = new String[] {
                mapper.writeValueAsString(deposit_category_request),
                mapper.writeValueAsString(deposit_category_response),
                mapper.writeValueAsString(deposit_category_response.getDeposit_category_code())
            };
            deposit_allocation_json = new String[] {
                mapper.writeValueAsString(deposit_allocation_request),
                mapper.writeValueAsString(deposit_allocation_response),
                mapper.writeValueAsString(deposit_allocation_response.getDeposit_allocation_data())
            };
            check_available_deposit_amount_json = new String[] {
                mapper.writeValueAsString(check_available_deposit_amount_request),
                mapper.writeValueAsString(check_available_deposit_amount_response),
                mapper.writeValueAsString(check_available_deposit_amount_response.getDeposit_available_amount_data())
            };
            deposit_result_message_json = new String[] {
                mapper.writeValueAsString(deposit_result_message_request),
                mapper.writeValueAsString(deposit_result_message_response),
                mapper.writeValueAsString(deposit_result_message_response)
            };
            deposit_json = new String[] {
                mapper.writeValueAsString(deposit_request),
                mapper.writeValueAsString(deposit_response),
                mapper.writeValueAsString(deposit_response.getDeposit_data())
            };
            format_check_json = new String[] {
                mapper.writeValueAsString(format_check_request),
                mapper.writeValueAsString(format_check_response),
                mapper.writeValueAsString(format_check_response)
            };
            deposit_entry_check_json = new String[] {
                mapper.writeValueAsString(deposit_entry_check_request),
                mapper.writeValueAsString(deposit_entry_check_response),
                mapper.writeValueAsString(deposit_entry_check_response.getResponse_result())
            };
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    // Error Json Data
    public void setErrorJsonData() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            route_process_json = new String[] {
                mapper.writeValueAsString(route_request),
                mapper.writeValueAsString(route_response),
                mapper.writeValueAsString(route_response)
            };
            deposit_category_json = new String[] {
                mapper.writeValueAsString(deposit_category_request),
                mapper.writeValueAsString(deposit_category_response),
                mapper.writeValueAsString(deposit_category_response.getDeposit_category_code())
            };
            deposit_allocation_json = new String[] {
                mapper.writeValueAsString(deposit_allocation_request),
                mapper.writeValueAsString(deposit_allocation_response),
                mapper.writeValueAsString(deposit_allocation_response.getDeposit_allocation_data())
            };
            check_available_deposit_amount_json = new String[] {
                mapper.writeValueAsString(check_available_deposit_amount_request),
                mapper.writeValueAsString(check_available_deposit_amount_response),
                mapper.writeValueAsString(check_available_deposit_amount_response.getDeposit_available_amount_data())
            };
            deposit_result_message_json = new String[] {
                mapper.writeValueAsString(deposit_result_message_request),
                mapper.writeValueAsString(deposit_result_message_response),
                mapper.writeValueAsString(deposit_result_message_response)
            };
            deposit_json = new String[] {
                mapper.writeValueAsString(deposit_request),
                mapper.writeValueAsString(deposit_response),
                mapper.writeValueAsString(deposit_response.getDeposit_data())
            };
            format_check_json = new String[] {
                mapper.writeValueAsString(format_check_request),
                mapper.writeValueAsString(format_check_response),
                mapper.writeValueAsString(format_check_response)
            };
            deposit_entry_check_json = new String[] {
                mapper.writeValueAsString(deposit_entry_check_request),
                mapper.writeValueAsString(deposit_entry_check_response),
                mapper.writeValueAsString(deposit_entry_check_response.getResponse_result())
            };
        } catch(JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}
