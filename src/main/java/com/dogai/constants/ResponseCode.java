package com.dogai.constants;

import java.util.HashMap;
import java.util.Map;

public class ResponseCode {
    public static final int SUCCESS = 200;
    public static final int FAIL = 300;
    public static final int SERVER_EXCEPTION = 500;

    public static Map<Integer, String> messageMap = new HashMap<Integer, String>() {{
        put(SUCCESS, "success");
        put(FAIL, "fail");
        put(SERVER_EXCEPTION, "Internal server error");
    }};
}
