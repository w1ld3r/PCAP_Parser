public class RecordHeader{
     private int ts_sec;    /* timestamp seconds */
     private int ts_usec;   /* timestamp microseconds */
     private int incl_len;  /* number of octets of packet saved in file */
     private int orig_len;  /* actual length of packet */

     public Ethernet2Frame ethernet;

     public void RecordHeder(){
     }

     public void setTs_sec(int i){
        this.ts_sec = i;
     }
     public void setTs_usec(int i){
        this.ts_sec = i;
    }
    public void setIncl_len(int i){
        this.incl_len = i;
    }
    public void setOrig_len(int i){
        this.orig_len = i;
    }
    public int getTs_sec(){
        return this.ts_sec;
    }
    public int getTs_usec(){
        return this.ts_usec;
    }
    public int getIncl_len(){
        return this.incl_len;
    }
    public int getOrig_len(){
        return this.orig_len;
    }

    @Override
    public String toString(){
        return "\nRecord Header:\n" + "- Timestamp seconds: " + getTs_sec() + "\n- Timestamp microseconds: " + getTs_usec() + "\n- Number of octets of packet saved in file: " + getIncl_len() + "\n- Actual length of packet: " + getOrig_len() + "\n";
    }
}