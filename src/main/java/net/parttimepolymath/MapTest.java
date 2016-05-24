package net.parttimepolymath;

import com.google.common.base.Strings;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * main executable class. Note that there are no unit tests against this class, as it's just the runtime framework.
 */
public class MapTest {
    /**
     * application properties loaded from the classpath.
     */
    private static final Properties PROPERTIES = loadProperties();


    /**
     * main entry point.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("?", "help", false, "print this message");
        options.addOption("v", "version", false, "print version");
        options.addOption("t", "trusted", true, "trusted interface");

        CommandLineParser parser = new PosixParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption('?')) {
                doHelp(options);
            } else if (cmd.hasOption('v')) {
                doVersion();
            } else {
                executeServer(cmd.getOptionValue('t'));
            }
        } catch (ParseException ex) {
            doHelp(options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void executeServer(String trusted) {

        try (Server instance = new Server(Strings.isNullOrEmpty(trusted) ? "172.17.0.*" : trusted)) {
            instance.start();

            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    try {
                        instance.close();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            int count = 0;
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    instance.touch(count++);
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    instance.close();
                    Thread.currentThread().interrupt();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * report the application version to standard out.
     */
    private static void doVersion() {
        String name = PROPERTIES.getProperty("project.name");
        String version = PROPERTIES.getProperty("project.version");
        System.out.println(String.format("%s [%s]", name, version));
    }

    /**
     * print command line options to standard out.
     *
     * @param options the command line options.
     */
    private static void doHelp(final Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(PROPERTIES.getProperty("project.name"), options);
    }


    /**
     * load properties from the class path.
     *
     * @return a Properties object, which should be non-null unless theres an exception.
     */
    private static Properties loadProperties() {
        Properties props = new Properties();
        try {
            InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("project.properties");
            props.load(in);
        } catch (IOException ioe) {
            System.err.println("Failed to read application properties");
        }
        return props;
    }

}
