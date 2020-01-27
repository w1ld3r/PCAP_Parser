import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;

class PCAP_Parser {
    // Program Main
    public static void main(String[] args) {

        if (args.length > 1) {

            // init byte array for data read
            byte[] data = new byte[] {};

            if (args[0].equals("-f")) {
                // put readed data in byte array
                data = read(args[1]);
                // if data in array
                if (data.length > 0) {
                    // init the pcap parser class
                    DisplayFilter display = new DisplayFilter(data);
                    // if valid header
                    if (display.pcapValide()) {
                        if (args.length > 2) {
                            if (args[2].equals("-p")) {
                                try {
                                    display.protocol = args[3].toLowerCase();
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.err.println("Error: No protocol specified !");
                                    System.exit(1);
                                }
                            }
                            else if (args[2].equals("-s")) {
                                display.followStream = true;
                            }
                            else {
                                System.err.println("Error: Invalid argument");
                            }
                        }
                        display.print();
                    } else {
                        System.err.println("Error: Decoder failure");
                    }
                } else {
                    System.err.println("Error: File is empty");
                }
            } else {
                System.err.println("Error: Invalid argument");
            }
        } else {
            System.err.println("Error: Insufficient argument");
        }
    }

    // Read bytes from file
    private static byte[] read(String ipath) {
        Path path = Paths.get(ipath);

        byte[] data = new byte[] {};

        try {
            data = Files.readAllBytes(path);
        } catch (IOException e) {
        }

        return data;
    }
}
