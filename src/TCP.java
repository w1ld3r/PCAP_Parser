public class TCP{
    public byte[] sourcePort;
    public byte[] destinationPort;
    public byte[] sequenceNumber;
    public byte[] acknowledgmentNumber;
    public byte[] headerLength;
    public FlagsTCP flags;
    public byte[] windowSizeValue;
    public byte[] checksum;
    public byte[] urgentPointer;
    public byte[] options;
    public byte[] tcpPayload = null;

    public FTP ftp = null;
    public HTTP http = null;

    public int streamIndex = 0;
    public int rSequenceNumber;
    public int rAcknowledgmentNumber;

    public void setSourcePort(byte[] i){
        this.sourcePort = i;
    }
    public void setDestinationPort(byte[] i){
        this.destinationPort = i;
    }
    public void setSequenceNumber(byte[] i){
        this.sequenceNumber = i;
    }
    public void setAcknowledgmentNumber(byte[] i){
        this.acknowledgmentNumber = i;
    }
    public void setHeaderLength(byte[] i){
        this.headerLength = i;
    }
    public void setFlags(byte[] i){
        this.flags = new FlagsTCP(i);
    }
    public void setWindowSizeValue(byte[] i){
        this.windowSizeValue = i;
    }
    public void setChecksum(byte[] i){
        this.checksum = i;
    }
    public void setOptions(byte[] i){
        this.options = i;
    }
    public void setUrgentPointer(byte[] i){
        this.urgentPointer = i;
    }
    public void setTcpPayload(byte[] i){
        this.tcpPayload = i;
    }
    public int getSourcePort(){
        return Helper.convertByteArrayToInt(this.sourcePort);
    }
    public int getDestinationPort(){
        return Helper.convertByteArrayToInt(this.destinationPort);
    }
    public int getSequenceNumber(){
        return Helper.convertByteArrayToInt(this.sequenceNumber);
    }
    public int getAcknowledgmentNumber(){
        return Helper.convertByteArrayToInt(this.acknowledgmentNumber);
    }
    public int getHeaderLength(){
        int i = this.headerLength[0] >> 4 & 0xF;
        return i;
    }
    public FlagsTCP getFlags(){
        return this.flags;
    }
    public int getWindowSizeValue(){
        return Helper.convertByteArrayToInt(this.windowSizeValue);
    }
    public String getChecksum(){
        return Helper.byteArrayToString(this.checksum);
    }
    public int getUrgentPointer(){
        return Helper.convertByteArrayToInt(this.urgentPointer);
    }
    public String getOptions(){
        //TODO
        return "";
    }
    public String getTcpPayload(){
        if(this.tcpPayload != null){
            String s = new String(this.tcpPayload);
            return s;
        } else{
            return "";
        }
    }

    @Override
    public String toString(){
        return "\nTransmission Control Protocol:" + "\n- Source Port: " + getSourcePort() + "\n- Destination Port: " + getDestinationPort() + "\n- Sequence number: " + getSequenceNumber() + "\n- Acknowledgment number: " + getAcknowledgmentNumber() + "\n- Header Length: " + getHeaderLength()*32/8 + " bytes (" + getHeaderLength() + ")" + "\n- Flags: " + getFlags() + "\n- Window size: " + getWindowSizeValue() + "\n- Checksum: 0x" + getChecksum() + "\n- Urgent pointer: " + getUrgentPointer() /*+ "\n- Payload:\n" + getTcpPayload()*/;
    }
}