package com.paxstore.cli;

import com.paxstore.cli.commands.*;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Main entry point for PAXStore CLI tool
 */
@Command(
    name = "paxstore-cli",
    version = "1.0.0",
    description = "CLI tool for PAXStore Developer SDK",
    mixinStandardHelpOptions = true,
    subcommands = {
        UploadCommand.class,
        SubmitCommand.class,
        GetAppInfoCommand.class,
        GetApkInfoCommand.class,
        GetApkVersionsCommand.class,
        CreateAppCommand.class,
        CreateApkCommand.class,
        DeleteApkCommand.class
    }
)
public class PaxStoreCLI implements Runnable {
    
    public static void main(String[] args) {
        int exitCode = new CommandLine(new PaxStoreCLI()).execute(args);
        System.exit(exitCode);
    }
    
    @Override
    public void run() {
        // Show help when no subcommand is provided
        CommandLine.usage(this, System.out);
    }
}
