package org.dubok;

import org.dubok.java.CompilationUnit;
import org.apache.commons.cli.*;
import org.dubok.classfile.ClassFile;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

final class Dubok {
    private final CommandLine cmd;
    private final Options options;
    private Dubok(String[] args) throws ParseException {
        options = new Options();
        options.addOption( new Option("help", "help"));
        options.addOption( new Option("dump", "dump"));
        options.addOption( new Option("java", "java"));

        final Option classFile  = Option.builder("classfile")
                .hasArg()
                .desc( "class file" )
                .argName( "file" )
                .build( );
        options.addOption(classFile);

        final CommandLineParser parser  = new DefaultParser();
        cmd = parser.parse(options, args);
    }

    private void usage() {
        final HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp( "dubok", options);
    }


    private void execute() throws Exception {


        if(!cmd.hasOption("classfile")) {
            usage();
            return;
        }

        if(cmd.hasOption("help")) {
            usage();
            return;
        }

        String classFileName = cmd.getOptionValue("classfile");
        InputStream inputStream = new FileInputStream(classFileName);
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ClassFile classFile = new ClassFile(dataInputStream);
        boolean x = false;


        if(cmd.hasOption("java")) {
            new CompilationUnit(classFile).print(System.out,"");
            x = true;
        }

        if(cmd.hasOption("dump")) {
            classFile.print(System.out, "");
            x = true;
        }
        //if(cmd.hasOption("graph")) {
        //    //classFile.printGraph(System.out, "");
        //    x = true;
        //}

        if(!x) {
            usage();
        }
    }

    public static void main(String[] args) throws ParseException {
        try {
            Dubok dubok = new Dubok(args);
            dubok.execute();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
}
