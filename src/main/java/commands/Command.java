package commands;

import java.util.List;

public interface Command {

    void run(List<String> params);
    Boolean returnsResult();
    String getResult();
}
