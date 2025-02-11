package org.joelson.turf.scorecalc.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final Integer USER_ID = 1;

    @Test
    void testUserId() {
        assertThrows(NullPointerException.class, () -> new User(null));
        assertThrows(IllegalArgumentException.class, () -> new User(0));
        assertThrows(IllegalArgumentException.class, () -> new User(-3));

        User user = new User(USER_ID);
        assertEquals(USER_ID, user.getUserId());
    }

    @Test
    void testEquals() {
        User user = new User(USER_ID);
        assertEquals(user, user);
        assertEquals(user, new User(USER_ID));
        assertEquals(new User(USER_ID), user);
        assertNotEquals(user, null);
        assertNotEquals(user, new User());
        assertNotEquals(new User(), user);

        Integer userId = USER_ID + 1;
        assertNotEquals(USER_ID, userId);
        assertNotEquals(user, new User(userId));
    }

    @Test
    void testHashCode() {
        User user = new User(USER_ID);
        assertEquals(user.hashCode(), user.hashCode());
        assertEquals(user.hashCode(), new User(USER_ID).hashCode());
        assertEquals(new User(USER_ID).hashCode(), user.hashCode());
        assertNotEquals(user.hashCode(), new User().hashCode());
        assertNotEquals(new User().hashCode(), user.hashCode());

        Integer userId = USER_ID + 1;
        assertNotEquals(USER_ID.hashCode(), userId.hashCode());
        assertNotEquals(user.hashCode(), new User(userId).hashCode());
    }
}
