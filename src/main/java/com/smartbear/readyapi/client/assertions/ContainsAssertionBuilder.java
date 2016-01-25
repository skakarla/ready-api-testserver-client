package com.smartbear.readyapi.client.assertions;

import com.smartbear.readyapi.client.model.SimpleContainsAssertion;

public interface ContainsAssertionBuilder extends AssertionBuilder<SimpleContainsAssertion> {
    ContainsAssertionBuilder useRegEx();

    ContainsAssertionBuilder ignoreCase();
}
