package commands;

import java.util.List;

public interface Command {
    void run(String params, boolean piped);

    Boolean returnsResult();
    String getResult();
}
