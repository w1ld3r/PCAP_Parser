import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DisplayFilter {
    PcapDecoder pcapDecoder;
    PrintTCP printTCP;

    // specified protocol
    public String protocol = "";
    // specified follow stream
    public boolean followStream = false;

    public DisplayFilter(byte[] data) {
        this.pcapDecoder = new PcapDecoder(data);
        this.printTCP = new PrintTCP();
    }

    public boolean pcapValide() {
        return pcapDecoder.parseGlobalHeader();
    }

    public void printARP(RecordHeader record){
        if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x806)
            System.out.print(record.ethernet.arp);
    }

    public void printICMP(RecordHeader record){
        if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){
            if(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol()) == 0x01){
                if(record.ethernet.ipv4.helperIPv4.getMoreFragments() == 1)
                    System.out.print("\033[0;30m \033[47m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t\t\t" + "IPv" + record.ethernet.ipv4.helperIPv4.version + "\t" + "Fragmented IP protocol (proto=" + ProtocolConstants.PROTOCOL_LIST.get(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol())) + ")" + "\033[0m" + "\n");
                else
                    System.out.print("\033[0;30m \033[48;5;225m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t\t\t" + ProtocolConstants.PROTOCOL_LIST.get(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol())) + "\t" + ICMPTypeConstants.ICMP_TYPE.get(Helper.convertByteArrayToInt(record.ethernet.ipv4.icmp.getType())) + " ttl=" + Helper.convertByteArrayToInt(record.ethernet.ipv4.getTimeToLive()) + "\t" + ((Helper.convertByteArrayToInt(record.ethernet.ipv4.icmp.getCode()) == 0x03)?"(Port unreachable)":record.ethernet.ipv4.icmp.getData().replaceAll("[^\\x21-\\x7E]", "").substring(0,40) + " ..." ) + "\033[0m" + "\n");
            }
        }
    }

    public void printDNS(RecordHeader record){
        if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){
            if(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol()) == 0x11){
                if(record.ethernet.ipv4.udp.getSourcePort() == 53 || record.ethernet.ipv4.udp.getDestinationPort() == 53)
                    System.out.print("\033[0;30m \033[48;5;153m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + ((record.ethernet.ipv4.getSourceIPAddress().length() < 12 || record.ethernet.ipv4.getDestinationIPAddress().length() < 12)?"\t\t":"\t") + "DNS" + "\t" + record.ethernet.ipv4.udp.dns + "\033[0m" + "\n");
            }
        }
    }

    public void printDHCP(RecordHeader record){
        if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){
            if(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol()) == 0x11){
                if(record.ethernet.ipv4.udp.getSourcePort() == 68 || record.ethernet.ipv4.udp.getSourcePort() == 67)
                    System.out.print("\033[0;30m \033[48;5;153m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t\t" + "DHCP" + "\t" + record.ethernet.ipv4.udp.dhcp + "\tfrom " + Helper.formatMacAddr(record.ethernet.getSourceMACAdress()) + " to "  + Helper.formatMacAddr(record.ethernet.getDestinationMACAdress()) + "\033[0m" + "\n");    
            }
        }
    }

    public void printUDP(RecordHeader record){
        if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){
            if(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol()) == 0x11){
                if(record.ethernet.ipv4.udp.getSourcePort() == 53 || record.ethernet.ipv4.udp.getDestinationPort() == 53)
                    printDNS(record);
                else if(record.ethernet.ipv4.udp.getSourcePort() == 67 || record.ethernet.ipv4.udp.getDestinationPort() == 67)
                    printDHCP(record);
            }
        }
    }

    public void printTCP(RecordHeader record){
        if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){
            if(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol()) == 0x06){
                //System.out.println(record.ethernet.ipv4.tcp);
                this.printTCP.print(record, this.protocol);
            }
        }
    }

    public void printIPv4(RecordHeader record){
        if (Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){                
            printICMP(record);
            printUDP(record);
            printTCP(record);
        }
    }

    public void printStream() throws IOException{
        Date now = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMddhhmmss");
        for(RecordHeader record : this.pcapDecoder.recordHeaderList){
            if(Helper.convertByteArrayToInt(record.ethernet.getEthernetType()) == 0x800){
                if(Helper.convertByteArrayToInt(record.ethernet.ipv4.getProtocol()) == 0x06){
                    if(record.ethernet.ipv4.tcp.streamIndex != 0){
                        BufferedWriter writer = new BufferedWriter(new FileWriter("streamIndex" + record.ethernet.ipv4.tcp.streamIndex + "_" + ft.format(now) + ".txt" , true));
                        if((record.ethernet.ipv4.tcp.getSourcePort() == 21 || record.ethernet.ipv4.tcp.getDestinationPort() == 21) && record.ethernet.ipv4.tcp.ftp != null){
                            //System.out.println(record.ethernet.ipv4.tcp.ftp.getPayload());    
                            writer.append(record.ethernet.ipv4.tcp.ftp.getPayload());
                        }
                        else if((record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080 || record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080) && record.ethernet.ipv4.tcp.http != null){
                            //System.out.println(record.ethernet.ipv4.tcp.http.getPayload());    
                            writer.append(record.ethernet.ipv4.tcp.http.getPayload());
                        }
                        writer.close();
                    }
                }
            }
        }
    }

    public void print(){

        this.pcapDecoder.parseRecordHeader();

        if(this.protocol.equals("") || this.protocol.equals("eth")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList){
                printARP(record);
                printIPv4(record);
            }
            if(this.followStream){
                try {
                    printStream();
                } catch (IOException e) {
                    System.err.println("Error: Unable to write TCP stream in file !");
                }
            }
        }
        else if(this.protocol.equals("ip")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printIPv4(record);
        }
        else if(this.protocol.equals("arp")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printARP(record);
        }
        else if(this.protocol.equals("icmp")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printICMP(record);
        }
        else if(this.protocol.equals("udp")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printUDP(record);
        }
        else if(this.protocol.equals("tcp")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printTCP(record);
        }
        else if(this.protocol.equals("http")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printTCP(record);
        }
        else if(this.protocol.equals("ftp")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printTCP(record);
        }
        else if(this.protocol.equals("dns")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printDNS(record);
        }
        else if(this.protocol.equals("dhcp")){
            for(RecordHeader record : this.pcapDecoder.recordHeaderList)
                printDHCP(record);
        }
        else{
            System.err.println("Error: Bad protocol name or protocol not supported !");
        }
    }
}