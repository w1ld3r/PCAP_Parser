public class Ethernet2Frame{
    private byte[] destinationMACAddress;
    private byte[] sourceMACAddress;
    private byte[] ethernetType;
    private byte[] payload;
    private byte[] frameCheckSequence;
    
    public IPv4 ipv4;
    public ARP arp;

    public void setDestinationMACAdress(byte[] i) {
        this.destinationMACAddress = i;
    }
    public void setSourceMACAdress(byte[] i){
        this.sourceMACAddress = i;
    }
    public void setEthernetType(byte[] i){
        this.ethernetType = i;
    }
    public void setPayload(byte[] i){
        this.payload = i;
    }
    public void setFrameCheckSequence(byte[] i){
        this.frameCheckSequence = i;
    }
    public byte[] getDestinationMACAdress(){
        return this.destinationMACAddress;
    }
    public byte[] getSourceMACAdress(){
        return this.sourceMACAddress;
    }
    public byte[] getEthernetType(){
        return this.ethernetType;
    }
    public byte[] getPayload(){
        return this.payload;
    }
    public byte[] getFrameCheckSequence(){
        return this.frameCheckSequence;
    }
    @Override
    public String toString(){
        return "\nEthernet II:\n" + "- Destination MAC Adress: " + Helper.formatMacAddr(getDestinationMACAdress()) + "\n- Source MAC Address: " + Helper.formatMacAddr(getSourceMACAdress()) + "\n- Ethernet Type: " + ((Helper.convertByteArrayToInt(getEthernetType()) == 0x0800)?"IPv4":((Helper.convertByteArrayToInt(getEthernetType()) == 0x0806)?"ARP":Helper.byteArrayToString(getEthernetType()))) + "\n" /*+ "\n- Payload: " + Helper.byteArrayToString(getPayload()) + "\n"*/;
    }
}