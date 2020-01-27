import java.util.Arrays;

public class DNSAnswer {
    private String name;
    private byte[] type;
    private byte[] dclass;
    private byte[] timeToLive;
    private byte[] dataLength;
    private byte[] preference;
    private byte[] data;

    public void setName(String i){
        this.name = i;
    }
    public void setType(byte[] i){
        this.type = i;
    }
    public void setdClass(byte[] i){
        this.dclass = i;
    }
    public void setTimeToLive(byte[] i){
        this.timeToLive = i;
    }
    public void setDataLength(byte[] i){
        this.dataLength = i;
    }
    public void setPreference(byte[] i){
        this.preference = i;
    }
    public void setData(byte[] i){
        this.data = i;
    }
    public String getName(){
        return this.name;
    }
    public byte[] getType(){
        return this.type;
    }
    public byte[] getdClass(){
        return this.dclass;
    }
    public byte[] getTimeToLive(){
        return this.timeToLive;
    }
    public byte[] getDataLength(){
        return this.dataLength;
    }
    public byte[] getPreference(){
        return this.preference;
    }
    public String getData(){
        byte[] t;
        if(Helper.convertByteArrayToInt(this.type) == 28){
            t = Arrays.copyOfRange(this.data, 0, this.data.length);
            return Helper.formatIpv6Addr(t);
        }
        else if(Helper.convertByteArrayToInt(this.type) == 1){
            t = Arrays.copyOfRange(this.data, 0, this.data.length);
           return Helper.formatIpv4Addr(t);
        }
        else if(Helper.convertByteArrayToInt(this.type) == 16){
            t = Arrays.copyOfRange(this.data, 0, this.data.length - 1);
            for (int i = 0; i < t.length; i++) {
            if ((byte) 0x30 > t[i])
                t[i] = 0x20;
            }
            String s = new String(t);
            return s;
        }
        else if(Helper.convertByteArrayToInt(this.type) == 15){
            t = Arrays.copyOfRange(this.data, 3, this.data.length-1);
            for (int i = 0; i < t.length; i++) {
            if ((byte) 0x20 > t[i])
                t[i] = 0x2E;
            }
            String s = new String(t);
            return s + this.name;
        }
        else if(Helper.convertByteArrayToInt(this.type) == 5){
            t = Arrays.copyOfRange(this.data, 3, this.data.length-1);
            for (int i = 0; i < t.length; i++) {
            if ((byte) 0x20 > t[i])
                t[i] = 0x2E;
            }
            String s = new String(t);
            return s + this.name.replace("www.", "");
        }
        else if(Helper.convertByteArrayToInt(this.type) == 2){
            t = Arrays.copyOfRange(this.data, 1, this.data.length-1);
            for (int i = 0; i < t.length; i++) {
            if ((byte) 0x20 > t[i])
                t[i] = 0x2E;
            }
            String s = new String(t);
            return s + this.name;
        }
        else{
            t = Arrays.copyOfRange(this.data, 1, this.data.length-1);
            for (int i = 0; i < t.length; i++) {
            if ((byte) 0x20 > t[i])
                t[i] = 0x2E;
            }
            String s = new String(t);
            return s;
        }
    }

    @Override
    public String toString(){
        return DNSTypeConstants.DNS_TYPE.get(Helper.convertByteArrayToInt(getType())) + " " + getName() + " ";
        //return "\n\t- Name: " + getName() + "\n\t- Type: " + DNSTypeConstants.DNS_TYPE.get(Helper.convertByteArrayToInt(getType())) + "\n\t- Class: " + DNSClassConstants.DNS_CLASS.get(Helper.convertByteArrayToInt(getdClass())) + "\n\t- Time to live: " + Helper.convertByteArrayToInt(getTimeToLive()) + "\n\t- Data length: " + Helper.convertByteArrayToInt(getDataLength()) /*+ "\n\t- Preference: " + Helper.convertByteArrayToInt(getPreference())*/ + "\n\t- " + DNSTypeConstants.DNS_TYPE.get(Helper.convertByteArrayToInt(getType())) + ": " + getData();
    }


}