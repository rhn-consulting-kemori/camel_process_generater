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
public class RouteProcessTestErrorDataSet {

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

    ObjectMapper mapper = new ObjectMapper();

    public RouteProcessTestErrorDataSet() {
        setRoute_request();
        setRoute_response();
        setDeposit_category_request();
        setDeposit_category_response();
        setDeposit_allocation_request();
        setDeposit_allocation_response();
        setCheck_available_deposit_amount_request();
        setCheck_available_deposit_amount_response();
        setDeposit_result_message_request();
        setDeposit_result_message_response();
        setDeposit_request();
        setDeposit_response();
        setFormat_check_request();
        setFormat_check_response();
        setDeposit_entry_check_request();
        setDeposit_entry_check_response();
    }

    /** KijitsuNyukinRequestEntity route_request */
    public void setRoute_request() {
        route_request = new KijitsuNyukinRequestEntity();
    }

    /** KijitsuNyukinResponseEntity route_response */
    public void setRoute_response() {
        route_response = new KijitsuNyukinResponseEntity();
    }

    /** DepositCategoryRequestType deposit_category_request */
    public void setDeposit_category_request() {
        deposit_category_request = new DepositCategoryRequestType();
    }

    /** DepositCategoryResponseType deposit_category_response */
    public void setDeposit_category_response() {
        deposit_category_response = new DepositCategoryResponseType();
    }

    /** DepositAllocationRequestType deposit_allocation_request */
    public void setDeposit_allocation_request() {
        deposit_allocation_request = new DepositAllocationRequestType();
    }

    /** DepositAllocationResponseType deposit_allocation_response */
    public void setDeposit_allocation_response() {
        deposit_allocation_response = new DepositAllocationResponseType();
    }

    /** CheckAvailableDepositAmountRequestType check_available_deposit_amount_request */
    public void setCheck_available_deposit_amount_request() {
        check_available_deposit_amount_request = new CheckAvailableDepositAmountRequestType();
    }

    /** CheckAvailableDepositAmountResponseType check_available_deposit_amount_response */
    public void setCheck_available_deposit_amount_response() {
        check_available_deposit_amount_response = new CheckAvailableDepositAmountResponseType();
    }

    /** DepositResultMessageRequestType deposit_result_message_request */
    public void setDeposit_result_message_request() {
        deposit_result_message_request = new DepositResultMessageRequestType();
    }

    /** KijitsuNyukinResponseEntity deposit_result_message_response */
    public void setDeposit_result_message_response() {
        deposit_result_message_response = new KijitsuNyukinResponseEntity();
    }

    /** DepositRequestType deposit_request */
    public void setDeposit_request() {
        deposit_request = new DepositRequestType();
    }

    /** DepositResponseType deposit_response */
    public void setDeposit_response() {
        deposit_response = new DepositResponseType();
    }

    /** KijitsuNyukinRequestEntity format_check_request */
    public void setFormat_check_request() {
        format_check_request = new KijitsuNyukinRequestEntity();
    }

    /** FormatCheckResponseType format_check_response */
    public void setFormat_check_response() {
        format_check_response = new FormatCheckResponseType();
    }

    /** DepositEntryCheckRequestType deposit_entry_check_request */
    public void setDeposit_entry_check_request() {
        deposit_entry_check_request = new DepositEntryCheckRequestType();
    }

    /** DepositEntryCheckResponseType deposit_entry_check_response */
    public void setDeposit_entry_check_response() {
        deposit_entry_check_response = new DepositEntryCheckResponseType();
    }

}
