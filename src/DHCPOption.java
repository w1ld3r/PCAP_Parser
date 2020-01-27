public class DHCPOption{
    private byte[] id;
    private byte[] length;
    private byte[] option;

    public void setId(byte[] i){
        this.id = i;
    }
    public void setLength(byte[] i){
        this.length = i;
    }
    public void setOption(byte[] i){
        this.option = i;
    }
    public String getId(){
        String s = "";
        if(Helper.convertByteArrayToInt(this.id) == 0x01)
            s = "Subnet mask";
        if(Helper.convertByteArrayToInt(this.id) == 0x32)
            s = "Requested IP address";
        else if(Helper.convertByteArrayToInt(this.id) == 0x33)
            s = "IP address lease time";
        else if(Helper.convertByteArrayToInt(this.id) == 0x35)
            s = "DHCP message type";
        else if(Helper.convertByteArrayToInt(this.id) == 0x36)
            s = "DHCP Server identifier";
        else if(Helper.convertByteArrayToInt(this.id) == 0x37)
            s = "Parameter request list";
        else if(Helper.convertByteArrayToInt(this.id) == 0x3A)
            s = "Renewal (T1) time value";
        else if(Helper.convertByteArrayToInt(this.id) == 0x3B)
            s = "Rebinding (T2) time value";
        else if(Helper.convertByteArrayToInt(this.id) == 0x3D)
            s = "Client-identifier";         

        return "- Option: (" + Helper.convertByteArrayToInt(this.id) + ") " + s;
    }
    public int getEndId(){
        
        return Helper.convertByteArrayToInt(this.id);
    }
    public int getLength(){
        return Helper.convertByteArrayToInt(this.length);
    }
    public String getOption(){
        String s = "";
        if(Helper.convertByteArrayToInt(this.id) == 0x01)
            s = "- Subnet mask: " + Helper.formatIpv4Addr(this.option);
        if(Helper.convertByteArrayToInt(this.id) == 0x32)
            s = "- Requested IP address: " + Helper.formatIpv4Addr(this.option);
        else if(Helper.convertByteArrayToInt(this.id) == 0x33)
            s = "- IP address lease time: (" + Helper.convertByteArrayToInt(this.option) + "s) " + Helper.convertByteArrayToInt(this.option)/60 + " minutes";
        else if(Helper.convertByteArrayToInt(this.id) == 0x35)
            s = "- DHCP message type: " + DHCPTypeConstants.DHCP_TYPE.get(Helper.convertByteArrayToInt(this.option));
        else if(Helper.convertByteArrayToInt(this.id) == 0x36)
            s = "- DHCP Server identifier: " + Helper.formatIpv4Addr(this.option);
        else if(Helper.convertByteArrayToInt(this.id) == 0x37){
            byte[] a;
            for(int i = 0; i < this.option.length; i++){
                a = new byte[] {this.option[i]};
                s += "- Parameter request list item: " + DHCPParameterRequestConstants.DHCP_PARAMETER_REQUEST.get(Helper.convertByteArrayToInt(a)) + "\n\t";
            }
        }
        else if(Helper.convertByteArrayToInt(this.id) == 0x3A)
            s = "- Renewal time value: (" + Helper.convertByteArrayToInt(this.option) + "s) " + Helper.convertByteArrayToInt(this.option)/60 + " minutes";
        else if(Helper.convertByteArrayToInt(this.id) == 0x3B)
            s = "- Rebinding time value: (" + Helper.convertByteArrayToInt(this.option) + "s) " + Helper.convertByteArrayToInt(this.option)/60 + " minutes";
        else if(Helper.convertByteArrayToInt(this.id) == 0x3D){
            byte[] a = new byte[] {this.option[0]};
            byte[] b = new byte[] {this.option[1], this.option[2], this.option[3], this.option[4], this.option[5], this.option[6]};
            s = "- Hardware type: " + LinkLayerConstants.LINK_LAYER_LIST.get(Helper.convertByteArrayToInt(a)) + "\n\t" + "Client MAC address: " + Helper.formatMacAddr(b);
        }
        return s;
    }

    public String getDHCPMessageType(){
        if(Helper.convertByteArrayToInt(this.id) == 0x35)
            return DHCPTypeConstants.DHCP_TYPE.get(Helper.convertByteArrayToInt(this.option));
        else
            return "";
    }

    @Override
    public String toString(){
        return getDHCPMessageType();
        //return "\n" + getId() + "\n\t- Length: " + getLength() + "\n\t" + getOption();
    }

}