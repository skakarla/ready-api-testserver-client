package com.smartbear.readyapi.client.assertions;

import com.smartbear.readyapi.client.model.SchemaComplianceAssertion;

public class DefaultSchemaComplianceAssertionBuilder extends AbstractAssertionBuilder<SchemaComplianceAssertion>
        implements SchemaComplianceAssertionBuilder {

    private final SchemaComplianceAssertion schemaComplianceAssertion = new SchemaComplianceAssertion();

    @Override
    public DefaultSchemaComplianceAssertionBuilder named(String name) {
        schemaComplianceAssertion.setName(name);
        return this;
    }

    @Override
    public SchemaComplianceAssertion build() {
        schemaComplianceAssertion.setType(Assertions.SCHEMA_COMPLIANCE_TYPE);
        return schemaComplianceAssertion;
    }

    public final static SchemaComplianceAssertion create() {
        return new DefaultSchemaComplianceAssertionBuilder().build();
    }
}
