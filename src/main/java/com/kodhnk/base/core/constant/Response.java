package com.kodhnk.base.core.constant;

public enum Response {

    SUCCESS("Success"),
    CREATED("Created"),
    REMOVED("Removed"),
    UPDATED("Updated"),
    DATALISTED("Data Listed"),


    USER_CREATED("User created"),
    USER_UPDATED("User updated"),
    USER_ALREADY_EXISTS("User already exists"),
    USER_NOT_CREATED("User not created"),


    LOGIN_SUCCESS("Login Success"),
    LOGIN_FAILED("Login Failed"),

    ERROR("Error"),
    NOT_FOUND("Not Found"),
    UNAUTHORIZED("Unauthorized"),
    FORBIDDEN("Forbidden"),
    BAD_REQUEST("Bad Request"),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    GETALL_PATIENT("Hastalar Listelendi."),
    PATIENT_NOT_FOUND("Patient Not Found"),
    User_LIST("Kullanıcılar Listelendi"),
    USER_NOT_FOUND("Kullanıcı Listelenemedi."),
    DOCTOR_NOT_FOUND("Doctor Not Found"),
    GET_DOCTOR("Doktorlar Listelendi."), HOSPITAL_NOT_FOUND("Hastane bulunamadı"), DELETE_DOCTOR("Doktor Silindi."), 
    DEPARTMENT_LISTED("Departmanlar Listelendi."), 
    DEPARTMENT_FOUND("Departman bulundu."), DEPARTMENT_NOT_FOUND("Departman bulunamadı."), 
    CREATE_DEPARTMENT("Departman Eklendi."), UPDATE_DEPARTMENT("Departman Güncellendi."), DELETE_DEPARTMENT("Departman Silindi."),
    HOSPITAL_GET_ALL("Hastaneler Listelendi."), HOSPITAL_BY_ID("Hastane Getirildi"),
    DELETE_HOSPITAL("Hastane Silindi."), UPDATE_HOSPITAL("Hastane Güncellendi."), CREATE_HOSPITAL("Hastane Eklendi."), 
    CREATE_DOCTOR("Doktor Eklendi."), UPDATE_DOCTOR("Doktor Güncellend.");

    private final String message;

    Response(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}