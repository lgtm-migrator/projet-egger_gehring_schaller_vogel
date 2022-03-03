import picocli.CommandLine;
import picocli.CommandLine.Spec;
import picocli.CommandLine.Command;


@Command(name = "utils", description = "Utils for the project to make it easier : " +
        "new, clean, build, serve")
class Main implements Runnable {

    @Command(name = "new", description = "Create a new thing")
    public static void newCmd(){
        System.out.println("new commande not implemented yet");
    }
    @Command(name = "clean", description = "Clean everything")
    public static void cleanCmd(){
       System.out.println("clean commande not implemented yet");
    }
    @Command(name = "build", description = "Build a new thing")
    public static void buildCmd(){
        System.out.println("build commande not implemented yet");
    }
    @Command(name = "serve", description = "Serve a thing")
    public static void serveCmd(){
        System.out.println("serve commande not implemented yet");
    }

    @Spec
    CommandLine.Model.CommandSpec spec;
    @Override
    public void run() {
        throw new CommandLine.ParameterException(spec.commandLine(), "Specify a subcommand");
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