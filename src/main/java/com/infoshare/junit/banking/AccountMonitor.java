package com.infoshare.junit.banking;

import java.util.Observer;

public interface AccountMonitor extends Observer {
    void connect();
    void close();
}
