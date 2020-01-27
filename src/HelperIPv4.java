public class HelperIPv4{
    int version;
    int headerLength;
    int dscp;
    int ecn;
    int reservedBit;
    int dontFragment;
    int moreFragments;
    int fragmentOffset;

    public String formatVersion(byte[] data){
        this.version = data[0] >> 4;
        this.headerLength = data[0] & 0xF;
        return "\n- Version: " + this.version + "\n- Header Length: " +this. headerLength;
    }

    public String formatDifferentialServicesField(byte[] data){
        this.dscp = data[0] >> 2;
        this.ecn = data[0] & 0x3;
        return "\n- Differentiated Services Code Point: " + this.dscp + "\n- Explicit Congestion Notification: " + this.ecn;
    }

    public String formatFlag(byte[] data){
        this.reservedBit = data[0] >> 7;
        this.dontFragment = data[0] >> 6 & 0x1;
        this.moreFragments = data[0] >> 5 & 0x1;
        this.fragmentOffset += data[1];
        this.fragmentOffset += data[0] << 8 & 0x1F00;
        return "\n\t- Reserved bit: " + ((reservedBit == 1)?"Set":"Not set") + "\n\t- Don't fragment: " + ((dontFragment == 1)?"Set":"Not set") + "\n\t- More fragments: " + ((moreFragments == 1)?"Set":"Not set") + "\n\t- Fragment offset: " + fragmentOffset;
    }

    public int getVersion(){
        return this.version;
    }
    public int getHeaderLength(){
        return this.headerLength;
    }
    public int getDSCP(){
        return this.dscp;
    }
    public int getECN(){
        return this.ecn;
    }
    public int getReservedBit(){
        return this.reservedBit;
    }
    public int getDontFragment(){
        return this.dontFragment;
    }
    public int getMoreFragments(){
        return this.moreFragments;
    }
    public int getFragmentOffset(){
        return this.fragmentOffset;
    }
}