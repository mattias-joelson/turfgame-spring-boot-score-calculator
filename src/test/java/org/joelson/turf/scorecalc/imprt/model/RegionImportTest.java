package org.joelson.turf.scorecalc.imprt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegionImportTest {

    private static final Integer REGION_ID = 1;
    public static final String COUNTRY_NAME = "se";
    private static final CountryImport COUNTRY = new CountryImport(COUNTRY_NAME);

    @Test
    void testRegionId() {
        assertThrows(NullPointerException.class, () -> new RegionImport(null, COUNTRY));
        assertThrows(IllegalArgumentException.class, () -> new RegionImport(0, COUNTRY));
        assertThrows(IllegalArgumentException.class, () -> new RegionImport(-3, COUNTRY));

        RegionImport region = new RegionImport(REGION_ID, COUNTRY);
        assertEquals(REGION_ID, region.getRegionId());
    }

    @Test
    void testCountry() {
        RegionImport region = new RegionImport(REGION_ID, COUNTRY);
        assertEquals(COUNTRY, region.getCountry());

        RegionImport regionNull = new RegionImport(REGION_ID, null);
        assertEquals(null, regionNull.getCountry());
    }

    @Test
    void testEquals() {
        RegionImport region = new RegionImport(REGION_ID, COUNTRY);
        assertEquals(region, region);
        assertEquals(region, new RegionImport(REGION_ID, COUNTRY));
        assertNotEquals(region, null);
        assertNotEquals(region, new RegionImport());

        Integer regionId = REGION_ID + 1;
        assertNotEquals(REGION_ID, regionId);
        assertNotEquals(region, new RegionImport(regionId, COUNTRY));

        CountryImport country = new CountryImport("fi");
        assertNotEquals(COUNTRY, country);
        assertNotEquals(region, new RegionImport(REGION_ID, country));
    }

    @Test
    void testHashCode() {
        RegionImport region = new RegionImport(REGION_ID, COUNTRY);
        assertEquals(region.hashCode(), region.hashCode());
        assertEquals(region.hashCode(), new RegionImport(REGION_ID, COUNTRY).hashCode());
        assertNotEquals(region.hashCode(), new RegionImport().hashCode());

        Integer regionId = REGION_ID + 1;
        assertNotEquals(REGION_ID.hashCode(), regionId.hashCode());
        assertNotEquals(region.hashCode(), new RegionImport(regionId, COUNTRY).hashCode());

        CountryImport country = new CountryImport("fi");
        assertNotEquals(COUNTRY.hashCode(), country.hashCode());
        assertNotEquals(region.hashCode(), new RegionImport(REGION_ID, country).hashCode());
    }
}