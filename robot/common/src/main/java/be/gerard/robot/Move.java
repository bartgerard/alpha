package be.gerard.robot;

import lombok.Value;

@Value
public class Move implements Command {

    String type;

}
