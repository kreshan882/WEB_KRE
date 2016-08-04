/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.epic.util;

/**
 *
 * @author tharaka
 */
public class SystemMessage {

/////////////////////////////////////////////////////////////////////////
    //Common messages
    ////////////////////////////////////////////////////////////////////////
    public static final String COMMON_ERROR_PROCESS = "Error occurred while processing";

    /////////////////////////////////////////////////////////////////////////
    //Login managment
    ////////////////////////////////////////////////////////////////////////
    public static final String LOGIN_MSG = "User login successful ";
    public static final String LOGOUT_MSG = "User logout successful ";
    public static final String LOGIN_INVALID = "Invalid user login";
    public static final String LOGIN_INVALID_PW = "Invalid user password";
    public static final String LOGIN_INVALID_BANKUSER    = "Invalid bank user login";
    
    /////////////////////////////////////////////////////////////////////////
    //Customer Transacion managment
    ////////////////////////////////////////////////////////////////////////
    public static final String CUSTRA_ACCOUNT_NUM_EMPTY = "Empty account number ";
    public static final String CUSTRA_MOBILE_NUM_EMPTY = "Empty mobile number ";
    public static final String CUSTRA_AMOUNT_EMPTY = "Empty amount ";
    public static final String CUSTRA_MOBILE_INVALID = "Invalid mobile number ";
    public static final String CUSTRA_TRANSACTION_SUCCESS = "Customer transaction successful ";
    public static final String CUSTRA_TRANSACTION_FAIL = "Customer transaction failed ";
    public static final String CUSTRA_AMOUNT_INVALID = "Invalid amount ";
    public static final String CUSTRA_TRANSACTION_NOACCOUNT = "Customer account number not found";
    
    /////////////////////////////////////////////////////////////////////////////////
    //USER MANAGEMENT
    /////////////////////////////////////////////////////////////////////////////////
    public static final String USR_NAME_EMPTY = "Empty name";
    public static final String USR_NAME_INVALID = "Invalid name";
    public static final String USR_USERNAME_EMPTY = "Empty user name";
    public static final String USR_USERNAME_INVALID = "Invalid user name";
    public static final String USR_USERNAME_ALREADY = "User already exits ";
    public static final String USR_PASSWORD_EMPTY = "Empty user password";
    public static final String USR_PASSWORD_POLICY_VIALATION = "Password policy violation";
    public static final String USR_CONPASSWORD_EMPTY = "Empty confirm user password";
    public static final String USR_PASSWORD_NOT_MATCH = "Password not matched";

    public static final String USR_PROFILE_SELECT = "Select user profile ";
    public static final String USR_USERTYPE_SELECT = "Select user type ";

    public static final String USR_EMAIL_EMPTY = "Empty e-mail";
    public static final String USR_EMAIL_INVALID = "Invalid email";
    public static final String USR_ADDRESS_EMPTY = "Empty address ";
    public static final String USR_ADDRESS_INVALID = "Invalid address ";
    public static final String USR_PHONE_EMPTY = "Empty phone number ";
    public static final String USR_PHONE_INVALID = "Invalid phone number ";
    public static final String USR_NIC_EMPTY = "Empty NIC ";
    public static final String USR_NIC_INVALID = "Invalid NIC  ";

    public static final String USR_STATUS_SELECT = "Select user status ";

    public static final String USR_ADD = "User registration successful";
    public static final String USR_ADD_FAIL = "User registration fail";

    public static final String USR_PROFILE_UPDATED = "User updated successfully";
    public static final String USR_PROFILE_UPDATED_ERROR = "User update failed";

    public static final String USR_DELETED = "User deleted successfully";
    public static final String USR_DELETED_ERROR = "User delete failed";
    public static final String USR_DELETED_ERROR_SESSUSR = "Current User cannot deleted";

    public static final String USR_PW_CHG = "User password change successfully";
    public static final String USR_PW_UPDATE = "Password update successful";
    public static final String USR_PW_NOT_MAT = "User password not matched ";
    public static final String USR_PW_WORNG = "Old password incorrect";
    public static final String USR_PW_WORNG_OLD = "Old password Empty";
    public static final String USR_PW_POL_NOT_MAT = "User password does not comply with the password policy ";
    public static final String USR_PW_CNT_EQUAL_NWEPW = "Can not use the same password ";

    public static final String USRPROFILE_NAME_EMPTY = "Empty user profile name";
    public static final String USRPROFILE_NAME_INVALID = "Invalid user profile name";
    public static final String USRPROFILE_NAME_ALREADY = "Profile name exists";

    public static final String USRPROFILE_MODULE_SELECT = "Select  module";
    public static final String USRPROFILE_PAGE_SELECT = "Select  page";
    public static final String USRPROFILE_TASK_SELECT = "Select profile task";
    public static final String USRPROFILE_STATUS_SELECT = "Select status";

    public static final String USRPROFILE_ADD = "User profile registration successful";
    public static final String USRPROFILE_ADD_FAIL = "User profile registration fail";

    public static final String USRPROFILE_UPDATED = "User profile update successful";
    public static final String USRPROFILE_UPDATED_ERROR = "User profile update failed";

    public static final String USRPROFILE_DELETED = "User profile delete successful";
    public static final String USRPROFILE_DELETED_ERROR = "User profile delete failed";
    public static final String USRPROFILE_DELETED_ERROR_ADMIN = "Admin user profile cannot be deleted";

    /////////////////////////////////////////////////////////////////////////////////
    //CUSTOMER MANAGEMENT
    /////////////////////////////////////////////////////////////////////////////////
    public static final String CUST_ADD = "Customer registration successful";
    public static final String CUST_ADD_FAIL = "Customer registration failed";
    public static final String CUST_NOT_REGISTERED_NIC = "NIC not registered";

    public static final String CUST_NAME_EMPTY = "Empty name";
    public static final String CUST_NAME_INVALID = "Invalid name";
    public static final String CUST_USERTYPE_SELECT = "Select user type ";
    public static final String CUST_RISK_PROF_SELECT = "Select risk profile type ";
    public static final String CUST_SMS_PROF_SELECT = "Select SMS profile type ";
    public static final String CUST_CIF_EMPTY = "Empty CIF";
    public static final String CUST_CIF_INVALID = "Invalid CIF";
    public static final String CUST_ACC_NO_SELECT = "Select account number";
    public static final String CUST_ACC_NO_EMPTY = "Empty account number";
    public static final String CUST_MOBILE_INVALID = " Mobile number invalid";
    public static final String CUST_BRANCH_EMPTY = "Branch name empty";
    public static final String CUST_NIC_EMPTY = "NIC empty";
    public static final String CUST_NIC_INVALID = "Invalid NIC";
    public static final String CUST_MOBILE_DUPLICATE = "Mobile number duplicated";
    public static final String CUST_MOBILE_EMPTY = "Empty mobile";
    public static final String CUST_ADDRESS_EMPTY = "Empty address";
    public static final String CUST_ADDRESS_INVALID = "Invalid address";
    public static final String CUST_VALIDATE_REC_EMPTY = "Empty validate recipient";
    public static final String CUST_USER_MERCHANT_EMPTY = "Empty user merchant";
    public static final String CUST_STATUS_SELECT = "Select status";
    public static final String CUST_CHANNEL_TYPE_SELECT = "Select channel type";

    public static final String CUST_DELETED = "Customer delete successful";
    public static final String CUST_DELETED_ERROR = "Customer delete failed";

    public static final String CUST_UPDATED = "Customer update successful";
    public static final String CUST_UPDATED_ERROR = "Customer update failed";

    public static final String RECIPIENT_CUSTOMER_ID_SELECT = "Select customer";
    public static final String RECIPIENT_NAME_EMPTY = "Empty name";
    public static final String RECIPIENT_NAME_INVALID = "Invalid name";
    public static final String RECIPIENT_NIC_EMPTY = "Empty NIC";
    public static final String RECIPIENT_NIC_INVALID = "Invalid NIC";
    public static final String RECIPIENT_ADDRESS_INVALID = "Invalid address";
    public static final String RECIPIENT_MOBILE_EMPTY = "Empty mobile number";
    public static final String RECIPIENT_MOBILE_INVALID = "Invalid mobile number";
    public static final String RECIPIENT_STATUS_SELECT = "Select status";

    public static final String RECIPIENT_ADD = "Recipient registration successful";
    public static final String RECIPIENT_ADD_FAIL = "Recipient registration fail";

    public static final String RECIPIENT_UPDATED = "Recipient update successful";
    public static final String RECIPIENT_UPDATED_ERROR = "Recipient update failed";

    public static final String RECIPIENT_DELETED = "Recipient delete successful";
    public static final String RECIPIENT_DELETED_ERROR = "Recipient delete failed";

    public static final String CUSTRISKPROMGMNT_NAME_EMPTY = "Empty name";
    public static final String CUSTRISKPROMGMNT_MAXAMOUNTPERDAY_EMPTY = "Empty max amount per day";
    public static final String CUSTRISKPROMGMNT_MAX_AMOUNT_PER_DAY_INVALID = "Invalid Max amount per day";
    public static final String CUSTRISKPROMGMNT_TRANSFERMODE_SELECT = "Select transfer mode";
    public static final String CUSTRISKPROMGMNT_MSGDELMODEORDER_SELECT = "Select messege delivery mode order ";
    public static final String CUSTRISKPROMGMNT_CHARGESMODE_SELECT = "Select charges mode ";
    public static final String CUSTRISKPROMGMNT_MSGVALIDITYPERIOD_SELECT = "Select messge validity period";
    public static final String CUSTRISKPROMGMNT_GLOBAL_AMOUNT_EMPTY = "Empty gloabe amount";
    public static final String CUSTRISKPROMGMNT_NOOFTXNALLOWEDPERRECIPIENT_EMPTY = "Empty no of transaction allowed per recipient";
    public static final String CUSTRISKPROMGMNT_MINAMNTPERTXN_EMPTY = "Empty min amount per transaction";
    public static final String CUSTRISKPROMGMNT_MAXAMNTPERTXN_EMPTY = "Empty max amount per transaction";
    public static final String CUSTRISKPROMGMNT_FEECALCMETHOD_SELECT = "Select fee calculated method";
    public static final String CUSTRISKPROMGMNT_MSGDELMODESEC_SELECT = "Select messege delivery mode sec";
    public static final String CUSTRISKPROMGMNT_STATUS_SELECT = "Select status";
    public static final String CUSTRISKPROMGMNT_FEEVALUE_EMPTY = "Empty fee value";
    public static final String CUSTRISKPROMGMNT_GLOBAL_AMOUNT_INVALID = "Invalid gloabe amount";
    public static final String CUSTRISKPROMGMNT_MSGVALIDITYPERIOD_INVALID = "Invalid messege validity period";
    public static final String CUSTRISKPROMGMNT_NOOFTXNALLOWEDPERRECIPIENT_INVALID = "Invalid number of transaction allowed per recipient";
    public static final String CUSTRISKPROMGMNT_MINAMNTPERTXN_INVALID = "Invalid min amount per transaction";
    public static final String CUSTRISKPROMGMNT_MAXAMNTPERTXN_INVALID = "Invalid max amount per transaction";
    public static final String CUSTRISKPROMGMNT_FEEVALUE_INVALID = "Invalid fee value";
    public static final String CUSTRISKPROMGMNT_UPDATED_ERROR = "Risk profile update failed";
    public static final String CUSTRISKPROMGMNT_UPDATED = "Risk profile update successful";
    public static final String CUSTRISKPROMGMNT_ADD_FAIL = "Risk profile registration fail";
    public static final String CUSTRISKPROMGMNT_ADD = "Risk profile registration successful";
    public static final String CUSTRISKPROMGMNT_DELETED = "Risk profile delete successful";
    public static final String CUSTRISKPROMGMNT_DELETED_ERROR = "Risk profile delete failed";

    /////////////////////////////////////////////////////////////////////////////////
    //SMS MANAGEMENT
    /////////////////////////////////////////////////////////////////////////////////
    public static final String SMS_PROF_NAME_EMPTY = "Empty name";
    public static final String SMS_PROF_NAME_INVALID = "Invalid name";
    public static final String SMS_PROF_NAME_ALREADY = "Profile name exists ";

    public static final String SMS_PROF_STATUS_SELECT = "Select profile status ";

    public static final String SMS_PROF_ADD = "SMS profile registration successful";
    public static final String SMS_PROF_ADD_FAIL = "SMS profile registration fail";

    public static final String SMS_PROF_UPDATED = "SMS profile update successful";
    public static final String SMS_PROF_UPDATED_ERROR = "SMS profile update failed";

    public static final String SMS_PROF_DELETED = "SMS profile delete successful";
    public static final String SMS_PROF_DELETED_ERROR = "SMS profile delete failed";

    public static final String SMS_PROF_NAME_SELECT = "Select SMS profile name ";
    public static final String SMS_DELIVERY_PARTY_SELECT = "Select delivery party";
    public static final String SMS_TEMPLATE_CATEGORY_SELECT = "Select SMS template  category";
    public static final String SMS_TEMPLATE_STATUS_SELECT = "Select status";
    public static final String SMS_MESSAGE_EMPTY = "Empty message";
    public static final String SMS_MESSAGE_INVALID = "Invalid message";
    public static final String SMS_TEMPLATE_DUBLICATE = "This SMS category name already assigned to profile name";

    public static final String SMS_TEMP_ADD = "SMS template registration successful";
    public static final String SMS_TEMP_ADD_FAIL = "SMS template registration fail";

    public static final String SMS_TEMP_UPDATED = "SMS template update successful";
    public static final String SMS_TEMP_UPDATED_ERROR = "SMS template update failed";

    public static final String SMS_TEMP_DELETED = "SMS template delete successful";
    public static final String SMS_TEMP_DELETED_ERROR = "SMS template delete failed";

    ///////////////////////////////////////////////////////////////////////
    ///////////////////////CHANNEL MANAGEMENT/////////////////////////////
    /////////////////////////////////////////////////////////////////////
    public static final String CHANNEL_NAME_EMPTY = "Empty channel name";
    public static final String CHANNEL_NAME_INVALID = "Invalid channel name";
    public static final String CHANNEL_TYPE_SELECT = "Select channel type";
    public static final String CHANNEL_IP_EMPTY = "Empty IP";
    public static final String CHANNEL_IP_INVALID = "Invalid IP";
    public static final String CHANNEL_PORT_EMPTY = "Empty port";
    public static final String CHANNEL_PORT_INVALID = "Invalid port";
    public static final String CHANNEL_CONTIMEOUT_EMPTY = "Empty connection timeout";
    public static final String CHANNEL_CONTIMEOUT_INVALID = "Invalid connection timeout";
    public static final String CHANNEL_READTIMEOUT_EMPTY = "Empty read timeout";
    public static final String CHANNEL_READTIMEOUT_INVALID = "Invalid read timeout";
    public static final String CHANNEL_CONNECTION_TYPE_SELECT = "Select connection type";
    public static final String CHANNEL_HEADERSIZE_EMPTY = "Empty header size";
    public static final String CHANNEL_HEADERSIZE_INVALID = "Invalid url";
    public static final String CHANNEL_STATUS_SELECT = "Select status";

    public static final String CHANNEL_ADD = "Channel registration successful";
    public static final String CHANNEL_ADD_FAIL = "Channel registration fail";

    public static final String CHANNEL_UPDATED = "Channel update successful";
    public static final String CHANNEL_UPDATED_ERROR = "Channel update failed";

    public static final String CHANNEL_DELETED = "Channel delete successful";
    public static final String CHANNEL_DELETED_ERROR = "Channel delete failed";

    public static final String LISTENER_NAME_EMPTY = "Empty listener name";
    public static final String LISTENER_NAME_INVALID = "Invalid listener name";
    public static final String LISTENER_TYPE_SELECT = "Select listener type";
    public static final String LISTENER_UIDS_EMPTY = "Empty UIDS";
    public static final String LISTENER_UIDS_INVALID = "Invalid UIDS";
    public static final String LISTENER_PORT_EMPTY = "Empty port";
    public static final String LISTENER_PORT_INVALID = "Invalid port";
    public static final String LISTENER_CONTIMEOUT_EMPTY = "Empty connection timeout";
    public static final String LISTENER_CONTIMEOUT_INVALID = "Invalid connection timeout";
    public static final String LISTENER_READTIMEOUT_EMPTY = "Empty read timeout";
    public static final String LISTENER_READTIMEOUT_INVALID = "Invalid read tiimeout";
    public static final String LISTENER_CONNECTION_TYPE_SELECT = "Select connection type";
    public static final String LISTENER_BACKLOGSIZE_EMPTY = "Empty backlog size";
    public static final String LISTENER_BACKLOGSIZE_INVALID = "Invalid backlog size";
    public static final String LISTENER_HEADERSIZE_EMPTY = "Empty header size";
    public static final String LISTENER_HEADERSIZE_INVALID = "Invalid header size";
    public static final String LISTENER_STATUS_SELECT = "Select status";
    public static final String LISTENER_KEEP_ALIVE_STATUS_SELECT = "Select keep alive status";

    public static final String LISTENER_ADD = "Listener registration successful";
    public static final String LISTENER_ADD_FAIL = "Listener registration fail";

    public static final String LISTENER_UPDATED = "Listener update successful";
    public static final String LISTENER_UPDATED_ERROR = "Listener update failed";

    public static final String LISTENER_DELETED = "Listener delete successful";
    public static final String LISTENER_DELETED_ERROR = "Listener delete failed";

    public static final String LISTENER_PRO_COLLECTION_ACCOUNT_EMPTY = "Empty collection account number";
    public static final String LISTENER_PRO_GL_ACCOUNT_EMPTY = "Empty GL account number";
    public static final String LISTENER_PRO_COST_CENTER_ACCOUNT_EMPTY = "Empty cost center";

    public static final String LISTENER_PRO_COLLECTION_ACCOUNT_INVALID = "Invalid collection account number";
    public static final String LISTENER_PRO_GL_ACCOUNT_INVALID = "Invalid GL account number";
    public static final String LISTENER_PRO_COST_CENTER_ACCOUNT_INVALID = "Invalid cost center";

    public static final String LISTENER_PRO_AMOUNT_HOLD_STATUS_SELECT = "Select amount hold status";
    public static final String LISTENER_PRO_SENDER_VALIDATION_STATUS_SELECT = "Select sender validation status";
    public static final String LISTENER_PRO_TXN_FEE_STATUS_SELECT = "Select txn fee status";

    public static final String LISTENER_PRO_UPDATED = "Listener profile update successful";
    public static final String LISTENER_PRO_UPDATED_ERROR = "Listener profile update failed";

    /////////////////////////////////////////////////////////////////////////////////
    //LOG MANAGEMENT
    /////////////////////////////////////////////////////////////////////////////////
    public static final String LOG_UPDATE_SUCCESS = "Log level update succesful";
    public static final String LOG_UPDATE_ERROR = "Log level update error";

    public static final String LOG_DOWNLOAD_SUCCESS = "Log downloaded succesfully";
    public static final String LOG_DOWNLOAD_ERROR = "Log download error";

    /////////////////////////////////////////////////////////////////////////////////
    //CONFIGURATION MANAGEMENT
    /////////////////////////////////////////////////////////////////////////////////
    public static final String PASSPOLICY_UPDATED = "Password policy update successful";
    public static final String PASSPOLICY_UPDATED_ERROR = "Password policy update failed";
    public static final String PASSPOLICY_MINLENGTH_EMPTY = "Empty minimum length";
    public static final String PASSPOLICY_MINLENGTH_INVALID = "Invalid minimum length";
    public static final String PASSPOLICY_MAXLENGTH_EMPTY = "Empty maximum length";
    public static final String PASSPOLICY_MAXLENGTH_INVALID = "Invalid maximum length";
    public static final String PASSPOLICY_ALLOWSPECIALCHARS_EMPTY = "Empty allow special characters";
    public static final String PASSPOLICY_ALLOWSPECIALCHARS_INVALID = "Invalid allow special characters";
    public static final String PASSPOLICY_MINSPECIALCHARS_EMPTY = "Empty minimum special characters";
    public static final String PASSPOLICY_MINSPECIALCHARS_INVALID = "Invalid minimum special characters";
    public static final String PASSPOLICY_MAXSPECIALCHARS_EMPTY = "Empty maximum special characters";
    public static final String PASSPOLICY_MAXSPECIALCHARS_INVALID = "Invalid maximum special characters";
    public static final String PASSPOLICY_MAXSPECIALCHARS_INVALID2 = "maximum special characters should be greater than minimum special characters";
    public static final String PASSPOLICY_MINUPPERCHARS_EMPTY = "Empty minimum uppercase characters";
    public static final String PASSPOLICY_MINUPPERCHARS_INVALID = "Invalid minimum uppercase characters";
    public static final String PASSPOLICY_MINNUMERICALCHARS_EMPTY = "Empty minimum numarical characters";
    public static final String PASSPOLICY_MINNUMERICALCHARS_INVALID = "Invalid minimum numarical characters";
    public static final String PASSPOLICY_DESCRIPTION_EMPTY = "Empty description";
    public static final String PASSPOLICY_DESCRIPTION_INVALID = "Invalid description";

    public static final String SYSPARAMETER_MIN_POOL_EMPTY = "Empty minimum pool size";
    public static final String SYSPARAMETER_MIN_POOL_INVALID = "Invalid minimum pool size";
    public static final String SYSPARAMETER_MAX_POOL_EMPTY = "Empty maximum pool size";
    public static final String SYSPARAMETER_MAX_POOL_INVALID = "Invalid maximum pool size";
    public static final String SYSPARAMETER_MAX_POOL_QUESE_EMPTY = "Empty maximum queue size";
    public static final String SYSPARAMETER_ORD_LENGTH_EMPTY = "Empty ORD length";
    public static final String SYSPARAMETER_SEC_LENGTH_EMPTY = "Empty SEC length";
    public static final String SYSPARAMETER_MAX_RETRY_EMPTY = "Empty maximum PIN retry";
    public static final String SYSPARAMETER_MAX_POOL_QUESE_INVALID = "Invalid maximum queue size";
    public static final String SYSPARAMETER_ORD_LENGTH_INVALID = "Invalid ORD length";
    public static final String SYSPARAMETER_SEC_LENGTH_INVALID = "Invalid SEC length";
    public static final String SYSPARAMETER_ORD_LENGTH_LEN_INVALID = "Length between 4-10";
    public static final String SYSPARAMETER_SEC_LENGTH_LEN_INVALID = "Length between 4-8";
    public static final String SYSPARAMETER_SERVICE_PORT_EMPTY = "Empty service port";
    public static final String SYSPARAMETER_SERVICE_PORT_INVALID = "Invalid service port";
    public static final String SYSPARAMETER_SERVICE_READ_TIMEOUT_EMPTY = "Empty service read timeout";
    public static final String SYSPARAMETER_SERVICE_READ_TIMEOUT_INVALID = "Invalid service read timeout";
    public static final String SYSPARAMETER_LOG_BACKUP_PATH_EMPTY = "Empty log backup path";
    public static final String SYSPARAMETER_LOG_BACKUP_PATH_INVALID = "Invalid log backup path";
    public static final String SYSPARAMETER_NOF_LOG_FILE_EMPTY = "Empty nof log file";
    public static final String SYSPARAMETER_NOF_LOG_FILE_INVALID = "Invalid nof log file";
    public static final String SYSPARAMETER_SELECT_LOG_LEVEL = "Select log level";
    public static final String SYSPARAMETER_SELECT_STATUS = "Select status";
    public static final String SYSPARAMETER_OPERATION_PORT_EMPTY = "Empty operation port";
    public static final String SYSPARAMETER_OPERATION_PORT_INVALID = "Invalid service port";

    public static final String SYSPARAMETER_UPDATED = "System configurations update successful. A session restart is required to apply these changes";
    public static final String SYSPARAMETER_UPDATED_ERROR = "System parameter update failed";

    //////////////////////////////////////////////////////
    ////////BULK MESSAGE MANAGEMENT
    /////////////////////////////////////////////////////
    public static final String BULK_MSG_RECIPIENT_MOBILE_EMPTY = "Empty mobile number";
    public static final String BULK_MSG_RECIPIENT_MOBILE_INVALID = "Invalid mobile number";
    public static final String BULK_MSG_RECIPIENT_MOBILE_DUPLICATE = "Duplicate mobile number";
    public static final String BULK_MSG_AMOUNT_EMPTY = "Empty amount";
    public static final String BULK_MSG_AMOUNT_INVALID = "Invalid amount";

    public static final String BULK_MSG_ADD = "Bulk message add successful";
    public static final String BULK_MSG_ADD_FAIL = "Bulk message add fail";

    public static final String BULK_MSG_SEND_SUCCESSFULY = "Bulk messages sent";
    public static final String BULK_MSG_SEND_FAIL = "Bulk messages send fail";

    public static final String BULK_MSG_DELETED = "Message deleted";
    public static final String BULK_MSG_DELETED_FAIL = "Message delete fail";

    /////////////////////////////////////////////////////////////////////////////////
    //TRANSACTION MANAGEMENT
    /////////////////////////////////////////////////////////////////////////////////
    public static final String TRANSACTION_VIEWTRANS_CANCLED = "Transaction Canceled";
    public static final String TRANSACTION_VIEWTRANS_DOWNLOAD_PDF = "Transaction File Downloaded";
    public static final String TRANSACTION_CHANNELTYPE_EMPTY = "Empty Channel Type";
}
