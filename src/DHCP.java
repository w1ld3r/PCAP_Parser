import java.util.ArrayList;

public class DHCP{
    private byte[] messageType;
    private byte[] hardwareType;
    private byte[] hardwareAddressLength;
    private byte[] hops;
    private byte[] transactionID;
    private byte[] secondsElapsed;
    private byte[] bootpFlags;
    private byte[] clientIPAddress;
    private byte[] yourIPAddress;
    private byte[] nextServerIPAddress;
    private byte[] relayAgentIPAddress;
    private byte[] clientMACAddress;
    private byte[] clientHardwareAddressPadding;
    private byte[] serverHostName;
    private byte[] bootFileName;
    private byte[] magicCookie;
    public ArrayList<DHCPOption> options;

    public DHCP(){
        this.options = new ArrayList<DHCPOption>();
    }
    public void setMessageType(byte[] i){
        this.messageType = i;
    }
    public void setHardwareType(byte[] i){
        this.hardwareType = i;
    }
    public void setHardwareAddressLength(byte[] i){
        this.hardwareAddressLength = i;
    }
    public void setHops(byte[] i){
        this.hops = i;
    }
    public void setTransactionID(byte[] i){
        this.transactionID = i;
    }
    public void setSecondsElapsed(byte[] i){
        this.secondsElapsed = i;
    }
    public void setBootpFlags(byte[] i){
        this.bootpFlags = i;
    }
    public void setClientIPAddress(byte[] i){
        this.clientIPAddress = i;
    }
    public void setYourIPAddress(byte[] i){
        this.yourIPAddress = i;
    }
    public void setNextServerIPAddress(byte[] i){
        this.nextServerIPAddress = i;
    }
    public void setRelayAgentIPAddress(byte[] i){
        this.relayAgentIPAddress = i;
    }
    public void setClientMACAddress(byte[] i){
        this.clientMACAddress = i;
    }
    public void setClientHardwareAddressPadding(byte[] i){
        this.clientHardwareAddressPadding = i;
    }
    public void setServerHostname(byte[] i){
        this.serverHostName = i;
    }
    public void setBootFileName(byte[] i){
        this.bootFileName = i;
    }
    public void setMagicCookie(byte[] i){
        this.magicCookie = i;
    }
    public String getMessageType(){
        return DHCPMessageTypeConstants.DHCP_MESSAGE_TYPE.get(Helper.convertByteArrayToInt(this.messageType));
    }
    public String getHardwareType(){
        return LinkLayerConstants.LINK_LAYER_LIST.get(Helper.convertByteArrayToInt(this.hardwareType));
    }
    public int getHardwareAddressLength(){
        return Helper.convertByteArrayToInt(this.hardwareAddressLength);
    }
    public int getHops(){
        return Helper.convertByteArrayToInt(this.hops);
    }
    public String getTransactionID(){
        return "0x" + Helper.byteArrayToString(this.transactionID);
    }
    public int getSecondsElapsed(){
        return Helper.convertByteArrayToInt(this.secondsElapsed);
    }
    public String getBootpFlags(){
        return HelperDHCP.formatFlag(this.bootpFlags);
    }
    public String getClientIPAddress(){
        return Helper.formatIpv4Addr(this.clientIPAddress);
    }
    public String getYourIPAddress(){
        return Helper.formatIpv4Addr(this.yourIPAddress);
    }
    public String getNextServerIPAddress(){
        return Helper.formatIpv4Addr(this.nextServerIPAddress);
    }
    public String getRelayAgentIPAddress(){
        return Helper.formatIpv4Addr(this.relayAgentIPAddress);
    }
    public String getClientMACAddress(){
        return Helper.formatMacAddr(this.clientMACAddress);
    }
    public String getClientHardwareAddressPadding(){
        return Helper.byteArrayToString(this.clientHardwareAddressPadding);
    }
    public String getServerHostname(){
        String s = "";
        for (byte b : this.serverHostName) {
            if (b != 0)
                s += Helper.convertFromIntToHexa(b);
        }
        if(s.equals(""))
            return "not given";
        else
            return s;
    }
    public String getBootFileName(){
        String s = "";
        for (byte b : this.bootFileName) {
            if (b != 0)
                s += Helper.convertFromIntToHexa(b);
        }
        if(s.equals(""))
            return "not given";
        else
            return s;
    }
    public String getMagicCookie(){
        byte[] dhcp = new byte[] { (byte)0x63, (byte)0x82, (byte)0x53, (byte)0x63 };
        if(Helper.compare32Bytes(this.magicCookie, dhcp))
            return "DHCP";
        else
            return "unknow";
    }

    @Override
    public String toString(){
        String s = "";
        for(DHCPOption option : options){
            s += option.toString();
        }
        return getMagicCookie() + " " + s;
        //return "\nDynamic Host Configuration Protocol:" + "\n- Message type: " + getMessageType() + "\n- Hardware type: " + getHardwareType() + "\n- Hardware address length: " + getHardwareAddressLength() + "\n- Hops: " + getHops() + "\n- Transaction ID: " + getTransactionID() + "\n- Seconds elapsed: " + getSecondsElapsed() + "\n- Bootp flags: " + getBootpFlags() + "\n- Client IP address: " + getClientIPAddress() + "\n- Your (client) IP address: " + getYourIPAddress() + "\n- Next server IP address: " + getNextServerIPAddress() + "\n- Relay agent IP address: " + getRelayAgentIPAddress() + "\n- Client MAC address: " + getClientMACAddress() + "\n- Client hardware address padding: " + getClientHardwareAddressPadding() + "\n- Server host name " + getServerHostname() + "\n- Boot file name " + getBootFileName() + "\n- Magic cookie: " + getMagicCookie() + s;
    }
}