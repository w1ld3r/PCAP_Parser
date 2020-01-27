public class UDP{
    
    private byte[] sourcePort;
    private byte[] destinationPort;
    private byte[] length;
    private byte[] checksum;
    private byte[] data;

    public DNS dns;
    public DHCP dhcp;

    public void setSourcePort(byte[] i) {
        this.sourcePort = i;
    }
    public void setDestinationPort(byte[] i) {
        this.destinationPort = i;
    }
    public void setLength(byte[] i) {
        this.length = i;
    }
    public void setChecksum(byte[] i) {
        this.checksum = i;
    }
    public void setData(byte[] i) {
        this.data = i;
    }
    public int getSourcePort(){
        return Helper.convertByteArrayToInt(this.sourcePort);
    }
    public int getDestinationPort(){
        return Helper.convertByteArrayToInt(this.destinationPort);
    }
    public int getLength(){
        return Helper.convertByteArrayToInt(this.length);
    }
    public String getCheckSum(){
        return "0x" + Helper.byteArrayToString(this.checksum);
    }
    public byte[] getData(){
        return this.data;
    }

    @Override
    public String toString(){
        return "\nUser Datagram Protocol:" + "\n- Source Port: " + getSourcePort() + "\n- Destination Port: " + getDestinationPort() + "\n- Length: " + getLength() + "\n- Checksum: " + getCheckSum() /*+ "\n- Data: " + Helper.byteArrayToString(getData())*/;
    }
}