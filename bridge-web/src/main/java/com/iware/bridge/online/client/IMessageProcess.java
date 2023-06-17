package com.iware.bridge.online.client;

public interface IMessageProcess {

    void onOpen();

    void onMessage(String message);

    void onError(String message);

    void onClose();
}
