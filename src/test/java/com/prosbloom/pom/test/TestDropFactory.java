package com.prosbloom.pom.test;

import com.prosbloom.pom.Pom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestDropFactory {
    @BeforeEach
    public void setup() {

    }

    @Test
    public void testDropGeneration() {
        Assertions.assertNotNull(Pom.dropFactory.generateWeightedCurrency());
    }
}
