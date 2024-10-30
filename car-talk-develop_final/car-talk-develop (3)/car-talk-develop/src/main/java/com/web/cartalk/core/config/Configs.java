package com.web.cartalk.core.config;

import java.util.Collections;
import java.util.List;

public class Configs {
    public final static List<String> CORS = Collections.unmodifiableList(
            List.of("http://localhost:3000",
                    "http://localhost:8080"
                    /*,"http://cartalk.net",
                    "https://cartalk.net",
                    "http://www.cartalk.net",
                    "https://www.cartalk.net"*/)
    );
}
