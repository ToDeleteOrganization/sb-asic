package app.board.command;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandRepository {

    private Map<String, String> commandMap;

    public CommandRepository() {
        commandMap = new HashMap<>();
        commandMap.put("left", "sv-[10]");
        commandMap.put("right", "sv+[10]");
        commandMap.put("startStream", "start_stream");
        commandMap.put("stopStream", "stop_stream");
    }

    public String transformCommand(final String commandName) {
        return this.commandMap.getOrDefault(commandName, commandName);
    }
}
