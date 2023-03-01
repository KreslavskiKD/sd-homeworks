package commands;

import java.util.List;

public interface Command {

    void run(String params);
    Boolean returnsResult();
    String getResult();
}
