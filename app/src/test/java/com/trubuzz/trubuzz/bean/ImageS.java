package com.trubuzz.trubuzz.bean;


import com.trubuzz.trubuzz.utils.Conf;

/**
 * Created by king on 2017/7/5.
 */

public enum ImageS {
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
