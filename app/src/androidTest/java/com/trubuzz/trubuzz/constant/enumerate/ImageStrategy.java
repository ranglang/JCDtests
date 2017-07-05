package com.trubuzz.trubuzz.constant.enumerate;

import com.trubuzz.trubuzz.constant.Conf;

/**
 * Created by king on 2017/7/5.
 */

public enum ImageStrategy {
    FIX {
        @Override
        public String getImageCode() {
            return Conf.FIXED_CODE;
        }
    },
    API {
        @Override
        public String getImageCode() {
            return null;
        }
    },
    OCR {
        @Override
        public String getImageCode() {
            return null;
        }
    };

    public abstract String getImageCode();
}
