public class GlobalHeader {
    
    private int magic_number; /* magic number */
    private short version_major; /* major version number */
    private short version_minor; /* minor version number */
    private int thiszone; /* GMT to local correction */
    private int sigfigs; /* accuracy of timestamps */
    private int snaplen; /* max length of captured packets, in octets */
    private int network; /* data link type */

    public void GlobalHEader(){
    }

    public void setMagic_number(int i){
        this.magic_number = i;
    }
    public void setVersionMajor(short i){
        this.version_major = i;
    }
    public void setVersionMinor(short i){
        this.version_minor = i;
    }
    public void setThiszone(int i){
        this.thiszone = i;
    }
    public void setSigFigs(int i){
        this.sigfigs = i;
    }
    public void setSnaplen(int i){
        this.snaplen = i;
    }
    public void setNetwork(int i){
        this.network = i;
    }
    public int getMagic_number(){
        return(magic_number);
    }
    public int getVersionMajor(){
        return(version_major);
    }
    public int getVersionMinor(){
        return(version_minor);
    }
    public int getThiszone(){
        return(thiszone);
    }
    public int getSigFigs(){
        return(sigfigs);
    }
    public int getSnaplen(){
        return(snaplen);
    }
    public int getNetwork(){
        return(network);
    }

    @Override
    public String toString(){
        return "\nGlobal Header:\n- Major Version Number: " + getVersionMajor() + "\n"+ "- Minor Version Number: " + getVersionMinor() + "\n"+ "- GMT to Local Correction: " + getThiszone() + "\n"+ "- Accuracy of Timestamps: " + getSigFigs() + "\n"+ "- Max Length of Captured Packets, In Octets: " + getSnaplen() + "\n"+ "- Data Link Type: " + LinkLayerConstants.LINK_LAYER_LIST.get(getNetwork()) + "\n";
    }
}