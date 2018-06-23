package com.github.knight704.urbanairshipbadgecounter;

public interface BadgeStorage {
    int getBadgeCount();

    void setBadgeCount(int value);

    int shiftWith(int howMany);
}
