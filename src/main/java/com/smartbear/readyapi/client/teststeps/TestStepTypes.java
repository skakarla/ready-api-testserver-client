package com.smartbear.readyapi.client.teststeps;

public enum TestStepTypes {
    REST_REQUEST("REST Request"),
    PROPERTY_TRANSFER("Property Transfer"),
    DATA_SOURCE("DataSource"),
    GROOVY_SCRIPT("Groovy"),
    JDBC_REQUEST("JDBC Request"),
    SOAP_REQUEST("SOAP Request");

    private String name;

    TestStepTypes(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
