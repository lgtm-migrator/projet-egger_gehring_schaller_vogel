
package commands;
import java.io.IOException;
import java.io.*;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.io.File;


@Command(name = "build", description = "Build a static site")
public class Build implements Callable<Integer> {

    @CommandLine.Parameters(paramLabel = "rootDirectory", description = "root directory of markdown files")
    public File rootDirectory;

    @Override public Integer call() throws IOException {
        if(rootDirectory.exists()){
            if(rootDirectory.isDirectory()){
                String[] file = rootDirectory.list();
                assert file != null;
                
                for( String md : file){
                    File md_file = new File(rootDirectory + "/" + md);

                    //only parse if it's a file
                    if(md_file.isFile()){

                        String name = md_file.getName();
                        int ext = name.lastIndexOf('.');
                        String extension = name.substring(ext);

                        //check extension is md
                        if(extension.equals(".md")) {
                            String fileNameWithOutExt = name.substring(0, ext);
                            System.out.println("nom: " + fileNameWithOutExt + " extension: " + extension);


                        }

                    }
                }
                System.out.println("OKAY");

            }else{
                System.out.println("Error: not a directory");
            }

        }else{
            System.out.println("Error: incorrect directory");
        }


        return 0;
    }

}