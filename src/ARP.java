public class ARP{
    private byte[] hardwareType; // Hardware type HTYPE
    private byte[] protocolType; // Protocol type PTYPE
    private byte[] hardwareSize; // Hardware address length (HLEN) 
    private byte[] protocolSize; // Protocol address length (PLEN)
    private byte[] operation; // Operation (OPER) 
    private byte[] senderMACAddress; // Sender hardware address (SHA) 
    private byte[] senderIPAddress; // Sender protocol address (SPA)
    private byte[] targetMACAddress; // Target hardware address (HHA) 
    private byte[] targetIPAddress; // Target protocol address (TPA)

    public void setHardwareType(byte[] i) {
        this.hardwareType = i;
    }
    public void setProtocolType(byte[] i) {
        this.protocolType = i;
    }
    public void setHardwareSize(byte[] i) {
        this.hardwareSize = i;
    }
    public void setProtocolSize(byte[] i) {
        this.protocolSize = i;
    }
    public void setOperation(byte[] i) {
        this.operation = i;
    }
    public void setSenderMACAddress(byte[] i) {
        this.senderMACAddress = i;
    }
    public void setSenderIPAddress(byte[] i) {
        this.senderIPAddress = i;
    }
    public void setTargetMACAddress(byte[] i) {
        this.targetMACAddress = i;
    }
    public void setTargetIPAddress(byte[] i) {
        this.targetIPAddress = i;
    }
    public byte[] getHardwareType(){
        return this.hardwareType;
    }
    public byte[] getProtocolType(){
        return this.protocolType;
    }
    public byte[] getHardwareSize(){
        return this.hardwareSize;
    }
    public byte[] getProtocolSize(){
        return this.protocolSize;
    }
    public byte[] getOperation(){
        return this.operation;
    }
    public byte[] getSenderMACAddress(){
        return this.senderMACAddress;
    }
    public byte[] getSenderIPAddress(){
        return this.senderIPAddress;
    }
    public byte[] getTargetMACAddress(){
        return this.targetMACAddress;
    }
    public byte[] getTargetIPAddress(){
        return this.targetIPAddress;
    }
    @Override
    public String toString(){
        if(Helper.convertByteArrayToInt(getOperation()) == 0x1)
            return "\033[0;30m \033[48;5;223m" + Helper.formatMacAddr(getSenderMACAddress()) + " -> " + ((Helper.formatMacAddr(getTargetMACAddress()).equals("00:00:00:00:00:00"))?"Boradcast":Helper.formatMacAddr(getTargetMACAddress())) + ((Helper.formatMacAddr(getTargetMACAddress()).equals("00:00:00:00:00:00"))?"\t\t":"\t") + ((Helper.convertByteArrayToInt(getProtocolType()) == 0x0800)?"ARP":Helper.byteArrayToString(getProtocolType())) + "\t" + "Who has " + Helper.formatIpv4Addr(getTargetIPAddress()) + "? Tell " + Helper.formatIpv4Addr(getSenderIPAddress()) + "\033[0m" + "\n";
        else if(Helper.convertByteArrayToInt(getOperation()) == 0x2)
            return "\033[0;30m \033[48;5;223m" + Helper.formatMacAddr(getSenderMACAddress()) + " -> " + ((Helper.formatMacAddr(getTargetMACAddress()).equals("00:00:00:00:00:00"))?"Boradcast":Helper.formatMacAddr(getTargetMACAddress())) + ((Helper.formatMacAddr(getTargetMACAddress()).equals("00:00:00:00:00:00"))?"\t\t":"\t") + ((Helper.convertByteArrayToInt(getProtocolType()) == 0x0800)?"ARP":Helper.byteArrayToString(getProtocolType())) + "\t" + Helper.formatIpv4Addr(getSenderIPAddress()) + " is at " + Helper.formatMacAddr(getSenderMACAddress()) +"\033[0m" + "\n";
        else
            return "";
        //return "\nAddress Resolution Protocol:" + "\n- Hardware  type: " + LinkLayerConstants.LINK_LAYER_LIST.get(Helper.convertByteArrayToInt(getHardwareType())) + "\n- Protocol  type: " + ((Helper.convertByteArrayToInt(getProtocolType()) == 0x0800)?"IPv4":Helper.byteArrayToString(getProtocolType())) + "\n- Hardware Size: " + Helper.convertByteArrayToInt(getHardwareSize()) + "\n- Protocol Size: " + Helper.convertByteArrayToInt(getProtocolSize()) + "\n- Operation: " + (Helper.convertByteArrayToInt(getOperation()) == 1?"Request (" + Helper.convertByteArrayToInt(getOperation()) + ")":"Reply (" + Helper.convertByteArrayToInt(getOperation()) + ")") + "\n- Sender MAC Address: " + Helper.formatMacAddr(getSenderMACAddress()) + "\n- Sender IP Address: " + Helper.formatIpv4Addr(getSenderIPAddress()) + "\n- Target MAC Address: " + Helper.formatMacAddr(getTargetMACAddress()) + "\n- Target IP Address: " + Helper.formatIpv4Addr(getTargetIPAddress()) + "\n";
    }
}