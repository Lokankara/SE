package com.pp.app.text.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.pp.app.text.provider.TextCompositeCollectArgumentsProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

class CompositeTest {

    @ParameterizedTest(name = "collect() for composite should return {1}")
    @ArgumentsSource(TextCompositeCollectArgumentsProvider.class)
    @DisplayName("TextComposite.collect returns correct string")
    void testCollect(TextComponent composite, String expected) {
        assertEquals(expected, composite.collect());
    }
}