package org.joelson.turf.scorecalc.imprt.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserImportTest {

    private static final Integer USER_ID = 1;

    @Test
    void testUserId() {
        assertThrows(NullPointerException.class, () -> new UserImport(null));
        assertThrows(IllegalArgumentException.class, () -> new UserImport(0));
        assertThrows(IllegalArgumentException.class, () -> new UserImport(-3));

        UserImport user = new UserImport(USER_ID);
        assertEquals(USER_ID, user.getUserId());
    }

    @Test
    void testEquals() {
        UserImport user = new UserImport(USER_ID);
        assertEquals(user, user);
        assertEquals(user, new UserImport(USER_ID));
        assertNotEquals(user, null);
        assertNotEquals(user, new UserImport());

        Integer userId = USER_ID + 1;
        assertNotEquals(USER_ID, userId);
        assertNotEquals(user, new UserImport(userId));
    }

    @Test
    void testHashCode() {
        UserImport user = new UserImport(USER_ID);
        assertEquals(user.hashCode(), user.hashCode());
        assertEquals(user.hashCode(), new UserImport(USER_ID).hashCode());
        assertNotEquals(user.hashCode(), new UserImport().hashCode());

        Integer userId = USER_ID + 1;
        assertNotEquals(USER_ID.hashCode(), userId.hashCode());
        assertNotEquals(user.hashCode(), new UserImport(userId).hashCode());
    }
}