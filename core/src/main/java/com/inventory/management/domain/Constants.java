package com.inventory.management.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Constants {

    @Getter
    @RequiredArgsConstructor
    public enum Status{
        ACTIVE("ACTIVE"),
        INACTIVE("INACTIVE");

        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public enum Access{
        LOCKED("LOCKED"),
        NOT_LOCKED("NOT_LOCKED");

        private final String value;

        @Override
        public String toString() {
            return value;
        }
    }

}