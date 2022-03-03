import jdk.jshell.spi.ExecutionControl;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Command;


@Command(name = "utils", description = "Utils for the project to make it easier : " +
        "new, clean, build, serve")
class Main implements Runnable {

    @Command(name = "new", description = "Create a new thing")
    public static void newCmd(){
        System.out.println("New commande not implemented yet");
    }
    @Command(name = "Clean", description = "Clean everything")
    public static void CleanCmd(){
       System.out.println("Clean commande not implemented yet");
    }
    @Command(name = "Build", description = "Build a new thing")
    public static void BuildCmd(){
        System.out.println("Build commande not implemented yet");
    }
    @Command(name = "Serve", description = "Serve a thing")
    public static void ServeCmd(){
        System.out.println("Serve commande not implemented yet");
    }

    @Spec
    Model.CommandSpec spec;
    @Override
    public void run() {
        throw new ParameterException(spec.commandLine(), "Specify a subcommand");
    }

    // this example implements Callable, so parsing, error handling and handling user
    // requests for usage help or version help can be done with one line of code.
    public static void main(String... args) {
        CommandLine cmd = new CommandLine(new Main());
        if (args.length == 0) {
            cmd.usage(System.out);
        }
        else {
            cmd.execute(args);
        }
    }
}