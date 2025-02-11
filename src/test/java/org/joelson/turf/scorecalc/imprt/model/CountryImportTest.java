package org.joelson.turf.scorecalc.imprt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryImportTest {

    private static final String COUNTRY = "se";

    @Test
    void testCountry() {
        assertThrows(NullPointerException.class, () -> new CountryImport(null));
        assertThrows(IllegalArgumentException.class, () -> new CountryImport(""));

        CountryImport country = new CountryImport(COUNTRY);
        assertEquals(COUNTRY, country.getCountry());
    }

    @Test
    void testEquals() {
        CountryImport country = new CountryImport(COUNTRY);
        assertEquals(country, country);
        assertEquals(country, new CountryImport(COUNTRY));
        assertNotEquals(country, null);
        assertNotEquals(country, new CountryImport());

        String countryName = "fi";
        assertNotEquals(COUNTRY, countryName);
        assertNotEquals(country, new CountryImport(countryName));
    }

    @Test
    void testHashCode() {
        CountryImport country = new CountryImport(COUNTRY);
        assertEquals(country.hashCode(), country.hashCode());
        assertEquals(country.hashCode(), new CountryImport(COUNTRY).hashCode());
        assertNotEquals(country.hashCode(), new CountryImport().hashCode());

        String countryName = "fi";
        assertNotEquals(COUNTRY.hashCode(), countryName.hashCode());
        assertNotEquals(country.hashCode(), new CountryImport(countryName).hashCode());
    }
}