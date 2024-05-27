package org.example.stock;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

public class TestStock {
    @DisplayName("Valid Stock")
    @Test
    void testValidStock() {
        assert Stock.getPriceFromSymbol("AAPL") > 0;
    }

    @DisplayName("Invalid Stock")
    @Test
    void testInvalidStock() {
        assert Stock.getPriceFromSymbol("") == -2;
    }
}
