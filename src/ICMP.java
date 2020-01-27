public class ICMP{
    private byte[] type;
    private byte[] code;
    private byte[] checksum;
    private byte[] restOfHeader;
    private byte[] data;

    public void setType(byte[] i) {
        this.type = i;
    }
    public void setCode(byte[] i) {
        this.code = i;
    }
    public void setChecksum(byte[] i) {
        this.checksum = i;
    }
    public void setRestOfHeader(byte[] i) {
        this.restOfHeader = i;
    }
    public void setData(byte[] i) {
        this.data = i;
    }
    public byte[] getType() {
        return this.type;
    }
    public byte[] getCode() {
        return this.code;
    }
    public byte[] getChecksum() {
        return this.checksum;
    }
    public byte[] getRestOfHeader() {
        return this.restOfHeader;
    }
    public String getData() {
        String s = new String(this.data);
        return s.replace("�","•");
    }

    @Override
    public String toString(){
        return "\nInternet Control Message Protocol:" + "\n- Type: " + ICMPTypeConstants.ICMP_TYPE.get(Helper.convertByteArrayToInt(getType())) + "\n- Code: " + Helper.convertByteArrayToInt(getCode()) + "\n- Checksum: 0x" + Helper.byteArrayToString(getChecksum()) + "\n- Rest of header: " + Helper.byteArrayToString(getRestOfHeader()) + "\n- Data: " + getData();
    }
}