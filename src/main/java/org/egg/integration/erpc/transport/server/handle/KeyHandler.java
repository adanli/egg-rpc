package org.egg.integration.erpc.transport.server.handle;

import org.egg.integration.erpc.transport.server.ex.HandlerKeyNotExistException;

public interface KeyHandler {
    void handle() throws HandlerKeyNotExistException;
}
