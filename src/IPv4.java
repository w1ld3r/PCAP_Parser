public class IPv4{
    private byte[] version;
    //private byte[] internetHeaderLength;
    private byte[] differentialServicesFiled;
    //private byte[] explicitCongestionNotification;
    private byte[] totalLength;
    private byte[] identification;
    private byte[] flags;
    //private byte[] fragmentOffset;
    private byte[] timeToLive;
    private byte[] protocol;
    private byte[] headerChecksum;
    private byte[] sourceIPAddress;
    private byte[] destinationIPAddress;

    public HelperIPv4 helperIPv4 = new HelperIPv4();

    public ICMP icmp = null;
    public UDP udp = null;
    public TCP tcp = null;

    

    public void setVersion(byte[] i) {
        this.version = i;
        helperIPv4.formatVersion(this.version);
    }
    /*public void setInternetHeaderLength(byte[] i){
        this.internetHeaderLength = i;
    }*/
    public void setDifferentialServicesFiled(byte[] i){
        this.differentialServicesFiled = i;
        helperIPv4.formatDifferentialServicesField(this.differentialServicesFiled);
    }
    /*public void setExplicitCongestionNotification(byte[] i){
        this.explicitCongestionNotification = i;
    }*/
    public void setTotalLength(byte[] i){
        this.totalLength = i;
    }
    public void setIdentification(byte[] i){
        this.identification = i;
    }
    public void setFlags(byte[] i){
        this.flags = i;
        helperIPv4.formatFlag(this.flags);
    }
    /*public void setFragmentOffset(byte[] i){
        this.fragmentOffset = i;
    }*/
    public void setTimeToLive(byte[] i){
        this.timeToLive = i;
    }
    public void setProtocol(byte[] i){
        this.protocol = i;
    }
    public void setHeaderChecksum(byte[] i){
        this.headerChecksum = i;
    }
    public void setSourceIPAddress(byte[] i){
        this.sourceIPAddress = i;
    }
    public void setDestinationIPAddress(byte[] i){
        this.destinationIPAddress = i;
    }
    public String getVersion(){
        return helperIPv4.formatVersion(this.version);
    }
    /*public byte[] getInternetHeaderLength(){
        return this.internetHeaderLength;
    }*/
    public String getDifferentialServicesFiled(){
        return helperIPv4.formatDifferentialServicesField(this.differentialServicesFiled);
    }
    /*public byte[] getExplicitCongestionNotification(){
        return this.explicitCongestionNotification;
    }*/
    public int getTotalLength(){
        return Helper.convertByteArrayToInt(this.totalLength);
    }
    public byte[] getIdentification(){
        return this.identification;
    }
    public byte[] getFlags(){
        return this.flags;
    }
    /*public byte[] getFragmentOffset(){
        return this.fragmentOffset;
    }*/
    public byte[] getTimeToLive(){
        return this.timeToLive;
    }
    public byte[] getProtocol(){
        return this.protocol;
    }
    public byte[] getHeaderChecksum(){
        return this.headerChecksum;
    }
    public String getSourceIPAddress(){
        return Helper.formatIpv4Addr(this.sourceIPAddress);
    }
    public String getDestinationIPAddress(){
        return Helper.formatIpv4Addr(this.destinationIPAddress);
    }
    @Override
    public String toString(){
        return "\nInternet Protocol:" + getVersion() + /*"\n- IHL: " + Helper.convertByteArrayToInt(getInternetHeaderLength()) +*/ getDifferentialServicesFiled() + /*"\n- ECN: " + Helper.convertByteArrayToInt(getExplicitCongestionNotification()) +*/ "\n- Total Length: " + getTotalLength() + "\n- Identification: 0x" + Helper.byteArrayToString(getIdentification()) + " (" + Helper.convertByteArrayToInt(getIdentification()) + ")" + "\n- Flags: 0x" + Helper.byteArrayToString(getFlags()) + /*HelperIPv4.formatFlag(getFlags()) + "\n- Fragment Offset: " + Helper.convertByteArrayToInt(getFragmentOffset()) +*/ "\n- Time To Live: " + Helper.convertByteArrayToInt(getTimeToLive()) + "\n- Protocol: " + ProtocolConstants.PROTOCOL_LIST.get(Helper.convertByteArrayToInt(getProtocol())) + "\n- Header Checksum: 0x" + Helper.byteArrayToString(getHeaderChecksum()) + "\n- Source IP Address: " + getSourceIPAddress() + "\n- Destination IP Address: " + getDestinationIPAddress() +"\n";
    }
}