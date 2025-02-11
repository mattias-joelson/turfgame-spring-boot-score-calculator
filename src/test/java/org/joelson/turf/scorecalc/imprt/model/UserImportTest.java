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
        assertEquals(new UserImport(USER_ID), user);
        assertNotEquals(user, null);
        assertNotEquals(user, new UserImport());
        assertNotEquals(new UserImportTest(), user);

        Integer userId = USER_ID + 1;
        assertNotEquals(USER_ID, userId);
        assertNotEquals(user, new UserImport(userId));
    }

    @Test
    void testHashCode() {
        UserImport user = new UserImport(USER_ID);
        assertEquals(user.hashCode(), user.hashCode());
        assertEquals(user.hashCode(), new UserImport(USER_ID).hashCode());
        assertEquals(new UserImport(USER_ID).hashCode(), user.hashCode());
        assertNotEquals(user.hashCode(), new UserImport().hashCode());
        assertNotEquals(new UserImport().hashCode(), user.hashCode());

        Integer userId = USER_ID + 1;
        assertNotEquals(USER_ID.hashCode(), userId.hashCode());
        assertNotEquals(user.hashCode(), new UserImport(userId).hashCode());
    }
}