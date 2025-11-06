package com.pp.app.text.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.pp.app.text.entity.TextComponent;
import com.pp.app.text.provider.IntegrationTextCompositeArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class IntegrationTextCompositeTest {

    @ParameterizedTest(name = "collect() for composite should return {1}")
    @ArgumentsSource(IntegrationTextCompositeArgumentsProvider.class)
    @DisplayName("Integration: TextComposite.collect")
    void testCollect(TextComponent composite, String expected) {
        assertEquals(expected, composite.collect());
    }
}