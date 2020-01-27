import java.util.ArrayList;
import java.util.Arrays;

public class PcapDecoder {

    // pcap header size
    private static int HEADER_SIZE = 24;
    // pcap record header size
    private static int RECORD_HEADER_SIZE = 16;
    // global header structure
    public GlobalHeader globalHearder;
    // record header structure
    public ArrayList<RecordHeader> recordHeaderList;
    // data to parse
    private byte[] data;
    // encoding type
    private String endianness = "";

    // Constructor
    public PcapDecoder(byte[] data) {
        this.globalHearder = new GlobalHeader();
        this.recordHeaderList = new ArrayList<RecordHeader>();
        this.data = data;
    }

    // Set endianness
    private void detectEndianness(byte[] magicNumber) {
        // if byte array look like A1 B2 C3 D4
        if (Helper.compare32Bytes(MagicNumber.MAGIC_NUMBER_BIG_ENDIAN, magicNumber)) {
            this.endianness = "BE";
            // if bytes array look like D4 C3 B2 A1
        } else if (Helper.compare32Bytes(MagicNumber.MAGIC_NUMBER_LITTLE_ENDIAN, magicNumber)) {
            this.endianness = "LE";
        } else {
            this.endianness = "";
        }
    }

    public boolean parseGlobalHeader() {
        // if length data > pcap header size
        if (data != null || Arrays.copyOfRange(data, 0, 24).length == HEADER_SIZE) {
            detectEndianness(Arrays.copyOfRange(data, 0, 4));
            // if enable to parse magic_number
            if (this.endianness == "") {
                System.err.println("Error: Unknown magic number");
                return false;
                // if Little Endian -> convert to Big Endian
            } else if (this.endianness == "LE") {
                this.globalHearder.setMagic_number(
                        Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 0, 4))));
                this.globalHearder.setVersionMajor(
                        (short) Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 4, 6))));
                this.globalHearder.setVersionMinor(
                        (short) Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 6, 8))));
                this.globalHearder.setThiszone(
                        Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 8, 12))));
                this.globalHearder.setSigFigs(
                        Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 12, 16))));
                this.globalHearder.setSnaplen(
                        Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 16, 20))));
                this.globalHearder.setNetwork(
                        Helper.convertByteArrayToInt(Helper.convertLeToBe(Arrays.copyOfRange(data, 20, 24))));
                // if Big Endian
            } else if (this.endianness == "BE") {
                this.globalHearder.setMagic_number(Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 0, 4)));
                this.globalHearder
                        .setVersionMajor((short) Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 4, 6)));
                this.globalHearder
                        .setVersionMinor((short) Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 6, 8)));
                this.globalHearder.setThiszone(Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 8, 12)));
                this.globalHearder.setSigFigs(Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 12, 16)));
                this.globalHearder.setSnaplen(Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 16, 20)));
                this.globalHearder.setNetwork(Helper.convertByteArrayToInt(Arrays.copyOfRange(data, 20, 24)));
            }
            // System.out.println(this.globalHearder);
            return true;
            // if file empty or legth < 4
        } else {
            System.err.println("Error: Bad input format");
            return false;
        }
    }

    public void parseRecordHeader() {
        // position of the 1st header
        int position = HEADER_SIZE;
        // while position of the pointer is not equal at the data sample size and record
        // header size is valide
        while (this.data.length != position && Arrays.copyOfRange(this.data, position,
                position + RECORD_HEADER_SIZE).length == RECORD_HEADER_SIZE) {
            // int pposition = position;
            // create a new record header object
            RecordHeader recordHeader = new RecordHeader();
            // if data in little endian format
            if (this.endianness == "LE") {
                recordHeader.setTs_sec(Helper.convertByteArrayToInt(
                        Helper.convertLeToBe(Arrays.copyOfRange(this.data, position, position = position + 4))));
                recordHeader.setTs_usec(Helper.convertByteArrayToInt(
                        Helper.convertLeToBe(Arrays.copyOfRange(this.data, position, position = position + 4))));
                recordHeader.setIncl_len(Helper.convertByteArrayToInt(
                        Helper.convertLeToBe(Arrays.copyOfRange(this.data, position, position = position + 4))));
                recordHeader.setOrig_len(Helper.convertByteArrayToInt(
                        Helper.convertLeToBe(Arrays.copyOfRange(this.data, position, position = position + 4))));
                // if data in big endian format
            } else if (this.endianness == "BE") {
                recordHeader.setTs_sec(
                        Helper.convertByteArrayToInt(Arrays.copyOfRange(this.data, position, position = position + 4)));
                recordHeader.setTs_usec(
                        Helper.convertByteArrayToInt(Arrays.copyOfRange(this.data, position, position = position + 4)));
                recordHeader.setIncl_len(
                        Helper.convertByteArrayToInt(Arrays.copyOfRange(this.data, position, position = position + 4)));
                recordHeader.setOrig_len(
                        Helper.convertByteArrayToInt(Arrays.copyOfRange(this.data, position, position = position + 4)));
            }
            // System.out.println(recordHeader);
            // System.out.println(Helper.byteArrayToString(Arrays.copyOfRange(this.data,
            // pposition, position+recordHeader.getIncl_len())));

            // parse record data and set pointer to next record header
            recordHeader.ethernet = parseRecordData(position, position = position + recordHeader.getIncl_len());

            // add a record header in a arraylist
            this.recordHeaderList.add(recordHeader);
            
        }
    }

    public Ethernet2Frame parseRecordData(int start, int end) {
        int position = start;
        Ethernet2Frame ethernet2Frame = new Ethernet2Frame();
        ethernet2Frame.setDestinationMACAdress(Arrays.copyOfRange(this.data, position, position = position + 6));
        ethernet2Frame.setSourceMACAdress(Arrays.copyOfRange(this.data, position, position = position + 6));
        ethernet2Frame.setEthernetType(Arrays.copyOfRange(this.data, position, position = position + 2));
        // ethernet2Frame.setPayload(Arrays.copyOfRange(this.data, position, end));
        // System.out.println(ethernet2Frame);
        //System.out.println(Helper.byteArrayToString(Arrays.copyOfRange(this.data, start, end)));
        if (Helper.convertByteArrayToInt(ethernet2Frame.getEthernetType()) == 0x0800) {
            ethernet2Frame.ipv4 = parseIPv4(position, end);
        } else if (Helper.convertByteArrayToInt(ethernet2Frame.getEthernetType()) == 0x0806) {
            ethernet2Frame.arp = parseARP(position, end);
        }
        return ethernet2Frame;
    }

    public IPv4 parseIPv4(int start, int end) {
        int position = start;
        IPv4 ipv4 = new IPv4();
        ipv4.setVersion(Arrays.copyOfRange(this.data, position, position = position + 1));
        // ipv4.setInternetHeaderLength(Arrays.copyOfRange(this.data, position,
        // position=position+1));
        ipv4.setDifferentialServicesFiled(Arrays.copyOfRange(this.data, position, position = position + 1));
        // ipv4.setExplicitCongestionNotification(Arrays.copyOfRange(this.data,
        // position, position=position+1));
        ipv4.setTotalLength(Arrays.copyOfRange(this.data, position, position = position + 2));
        ipv4.setIdentification(Arrays.copyOfRange(this.data, position, position = position + 2));
        ipv4.setFlags(Arrays.copyOfRange(this.data, position, position = position + 2));
        // ipv4.setFragmentOffset(Arrays.copyOfRange(this.data, position,
        // position=position+1));
        ipv4.setTimeToLive(Arrays.copyOfRange(this.data, position, position = position + 1));
        ipv4.setProtocol(Arrays.copyOfRange(this.data, position, position = position + 1));
        ipv4.setHeaderChecksum(Arrays.copyOfRange(this.data, position, position = position + 2));
        ipv4.setSourceIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        ipv4.setDestinationIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        // System.out.println(ipv4);
        if (Helper.convertByteArrayToInt(ipv4.getProtocol()) == 0x0001) {
            ipv4.icmp = parseICMP(position, end);
        } else if (Helper.convertByteArrayToInt(ipv4.getProtocol()) == 0x0011) {
            ipv4.udp = parseUDP(position, end);
        } else if (Helper.convertByteArrayToInt(ipv4.getProtocol()) == 0x0006) {
            ipv4.tcp = parseTCP(position, end);
        }
        return ipv4;
    }

    public ARP parseARP(int start, int end) {
        int position = start;
        ARP arp = new ARP();
        arp.setHardwareType(Arrays.copyOfRange(this.data, position, position = position + 2));
        arp.setProtocolType(Arrays.copyOfRange(this.data, position, position = position + 2));
        arp.setHardwareSize(Arrays.copyOfRange(this.data, position, position = position + 1));
        arp.setProtocolSize(Arrays.copyOfRange(this.data, position, position = position + 1));
        arp.setOperation(Arrays.copyOfRange(this.data, position, position = position + 2));
        arp.setSenderMACAddress(Arrays.copyOfRange(this.data, position, position = position + 6));
        arp.setSenderIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        arp.setTargetMACAddress(Arrays.copyOfRange(this.data, position, position = position + 6));
        arp.setTargetIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        return arp;
    }

    public ICMP parseICMP(int start, int end) {
        int position = start;
        ICMP icmp = new ICMP();
        icmp.setType(Arrays.copyOfRange(this.data, position, position = position + 1));
        icmp.setCode(Arrays.copyOfRange(this.data, position, position = position + 1));
        icmp.setChecksum(Arrays.copyOfRange(this.data, position, position = position + 2));
        icmp.setRestOfHeader(Arrays.copyOfRange(this.data, position, position = position + 4));
        icmp.setData(Arrays.copyOfRange(this.data, position, position = end));
        return icmp;
    }

    public UDP parseUDP(int start, int end) {
        int position = start;
        UDP udp = new UDP();
        udp.setSourcePort(Arrays.copyOfRange(this.data, position, position = position + 2));
        udp.setDestinationPort(Arrays.copyOfRange(this.data, position, position = position + 2));
        udp.setLength(Arrays.copyOfRange(this.data, position, position = position + 2));
        udp.setChecksum(Arrays.copyOfRange(this.data, position, position = position + 2));
        // udp.setData(Arrays.copyOfRange(this.data, position, position=end));
        //System.out.println(udp);
        if (udp.getSourcePort() == 53 || udp.getDestinationPort() == 53) {
            udp.dns = parseDNS(position, end);
        }
        else if (udp.getSourcePort() == 68 || udp.getSourcePort() == 67) {
            udp.dhcp = parseDHCP(position, end);
        }
        else{
            udp.setData(Arrays.copyOfRange(this.data, position, position=end));
        }
        return udp;
    }

    public DNS parseDNS(int start, int end) {
        int position = start;
        DNS dns = new DNS();
        dns.setTransaction(Arrays.copyOfRange(this.data, position, position = position + 2));
        dns.setFlags(Arrays.copyOfRange(this.data, position, position = position + 2));
        dns.setQuestion(Arrays.copyOfRange(this.data, position, position = position + 2));
        dns.setAnswerRRs(Arrays.copyOfRange(this.data, position, position = position + 2));
        dns.setAuthorityRRs(Arrays.copyOfRange(this.data, position, position = position + 2));
        dns.setAdditionalRRs(Arrays.copyOfRange(this.data, position, position = position + 2));
        int nameLength = position + 1;
        while (this.data[nameLength] != 0x00) {
            ++nameLength;
        }
        dns.setQueriesName(Arrays.copyOfRange(this.data, position, position = nameLength + 1));
        dns.setQueriesType(Arrays.copyOfRange(this.data, position, position = position + 2));
        dns.setQueriesClass(Arrays.copyOfRange(this.data, position, position = position + 2));
        if (Helper.convertByteArrayToInt(dns.getAnswerRRs()) != 0x00) {
            DNSAnswer dnsAnswer;
            for (int i = 0; i < Helper.convertByteArrayToInt(dns.getAnswerRRs()); i++) {
                dnsAnswer = new DNSAnswer();
                nameLength = position + 1;
                while (this.data[nameLength] != 0x00) {
                    ++nameLength;
                }
                dnsAnswer.setName(dns.getQueriesName());
                dnsAnswer.setType(Arrays.copyOfRange(this.data, position = nameLength, position = position + 2));
                dnsAnswer.setdClass(Arrays.copyOfRange(this.data, position, position = position + 2));
                dnsAnswer.setTimeToLive(Arrays.copyOfRange(this.data, position, position = position + 4));
                dnsAnswer.setDataLength(Arrays.copyOfRange(this.data, position, position = position + 2));
                // dnsAnswer.setPreference(Arrays.copyOfRange(this.data, position, position =
                // position + 1));
                dnsAnswer.setData(Arrays.copyOfRange(this.data, position,
                        position = position + Helper.convertByteArrayToInt(dnsAnswer.getDataLength())));
                dns.dnsAnswers.add(dnsAnswer);
            }
        }
        if (Helper.convertByteArrayToInt(dns.getAdditionalRRs()) != 0x00) {
            DNSAdditionalRecords dnsAdditionalRecord;
            for (int i = 0; i < Helper.convertByteArrayToInt(dns.getAnswerRRs()); i++) {
                dnsAdditionalRecord = new DNSAdditionalRecords();
                nameLength = position + 1;
                while (this.data[nameLength] != 0x00) {
                    ++nameLength;
                }
                dnsAdditionalRecord.setName(dns.dnsAnswers.get(i).getData());
                dnsAdditionalRecord
                        .setType(Arrays.copyOfRange(this.data, position = nameLength, position = position + 2));
                dnsAdditionalRecord.setdClass(Arrays.copyOfRange(this.data, position, position = position + 2));
                dnsAdditionalRecord.setTimeToLive(Arrays.copyOfRange(this.data, position, position = position + 4));
                dnsAdditionalRecord.setDataLength(Arrays.copyOfRange(this.data, position, position = position + 2));
                // dnsAdditionalRecord.setPreference(Arrays.copyOfRange(this.data, position,
                // position = position + 1));
                dnsAdditionalRecord.setData(Arrays.copyOfRange(this.data, position,
                        position = position + Helper.convertByteArrayToInt(dnsAdditionalRecord.getDataLength())));
                dns.dnsAdditionalRecords.add(dnsAdditionalRecord);
            }
        }
        return dns;
    }

    public DHCP parseDHCP(int start, int end) {
        int position = start;
        DHCP dhcp = new DHCP();
        DHCPOption dhcpOption;
        dhcp.setMessageType(Arrays.copyOfRange(this.data, position, position = position + 1));
        dhcp.setHardwareType(Arrays.copyOfRange(this.data, position, position = position + 1));
        dhcp.setHardwareAddressLength(Arrays.copyOfRange(this.data, position, position = position + 1));
        dhcp.setHops(Arrays.copyOfRange(this.data, position, position = position + 1));
        dhcp.setTransactionID(Arrays.copyOfRange(this.data, position, position = position + 4));
        dhcp.setSecondsElapsed(Arrays.copyOfRange(this.data, position, position = position + 2));
        dhcp.setBootpFlags(Arrays.copyOfRange(this.data, position, position = position + 2));
        dhcp.setClientIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        dhcp.setYourIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        dhcp.setNextServerIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        dhcp.setRelayAgentIPAddress(Arrays.copyOfRange(this.data, position, position = position + 4));
        dhcp.setClientMACAddress(Arrays.copyOfRange(this.data, position, position = position + 6));
        dhcp.setClientHardwareAddressPadding(Arrays.copyOfRange(this.data, position, position = position + 10));
        dhcp.setServerHostname(Arrays.copyOfRange(this.data, position, position = position + 64));
        dhcp.setBootFileName(Arrays.copyOfRange(this.data, position, position = position + 128));
        dhcp.setMagicCookie(Arrays.copyOfRange(this.data, position, position = position + 4));
        while (position <= end) {
            dhcpOption = new DHCPOption();
            dhcpOption.setId(Arrays.copyOfRange(this.data, position, position = position + 1));
            // test if End (Padding)
            if (dhcpOption.getEndId() != 0xFF) {
                dhcpOption.setLength(Arrays.copyOfRange(this.data, position, position = position + 1));
                dhcpOption.setOption(
                        Arrays.copyOfRange(this.data, position, position = position + dhcpOption.getLength()));
                dhcp.options.add(dhcpOption);
            } else {
                position = end + 1;
            }
        }
        return dhcp;
    }

    public TCP parseTCP(int start, int end) {
        int position = start;
        TCP tcp = new TCP();
        tcp.setSourcePort(Arrays.copyOfRange(this.data, position, position = position + 2));
        tcp.setDestinationPort(Arrays.copyOfRange(this.data, position, position = position + 2));
        tcp.setSequenceNumber(Arrays.copyOfRange(this.data, position, position = position + 4));
        tcp.setAcknowledgmentNumber(Arrays.copyOfRange(this.data, position, position = position + 4));
        tcp.setHeaderLength(Arrays.copyOfRange(this.data, position, position + 1));
        tcp.setFlags(Arrays.copyOfRange(this.data, position, position = position + 2));
        tcp.setWindowSizeValue(Arrays.copyOfRange(this.data, position, position = position + 2));
        tcp.setChecksum(Arrays.copyOfRange(this.data, position, position = position + 2));
        tcp.setUrgentPointer(Arrays.copyOfRange(this.data, position, position = position + 2));
        // get options
        // 6 * 4 = 24 - 20 = 4
        if (tcp.getHeaderLength() == 6) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 4));
        }
        // 7 * 4 = 28 - 20 = 8
        else if (tcp.getHeaderLength() == 7) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 8));
        }
        // 8 * 4 = 32 - 20 = 12
        else if (tcp.getHeaderLength() == 8) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 12));
        }
        // 9 * 4 = 36 - 20 = 16
        else if (tcp.getHeaderLength() == 9) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 16));
        }
        // 10 * 4 = 40 - 20 = 20
        else if (tcp.getHeaderLength() == 10) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 20));
        }
        // 11 * 4 = 44 - 20 = 24
        else if (tcp.getHeaderLength() == 11) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 24));
        }
        // 12 * 4 = 48 - 20 = 28
        else if (tcp.getHeaderLength() == 12) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 28));
        }
        // 13 * 4 = 52 - 20 = 32
        else if (tcp.getHeaderLength() == 13) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 32));
        }
        // 14 * 4 = 56 - 20 = 36
        else if (tcp.getHeaderLength() == 14) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 36));
        }
        // 15 * 4 = 60 - 20 = 40
        else if (tcp.getHeaderLength() == 15) {
            tcp.setOptions(Arrays.copyOfRange(this.data, position, position = position + 40));
        }
        if (position + 1 <= end) {
            if (tcp.getSourcePort() == 21 || tcp.getDestinationPort() == 21) {
                FTP ftp = new FTP();
                ftp.setPayload(Arrays.copyOfRange(this.data, position, position = end));
                tcp.ftp = ftp;

            } else if (tcp.getSourcePort() == 80 || tcp.getDestinationPort() == 80 || tcp.getSourcePort() == 8080
                    || tcp.getDestinationPort() == 8080) {
                HTTP http = new HTTP();
                http.setPayload(Arrays.copyOfRange(this.data, position, position = end));
                tcp.http = http;
            }
            else
                tcp.setTcpPayload(Arrays.copyOfRange(this.data, position, position = end));
        }
        return tcp;
    }
}