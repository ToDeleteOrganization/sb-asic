package app.board.command;

import app.exception.BoardCommandException;

public interface Command {

    void executeCommand() throws BoardCommandException;
}
