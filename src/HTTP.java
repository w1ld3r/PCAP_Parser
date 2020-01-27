public class HTTP {
    public byte[] payload;

    public void setPayload(byte[] i){
        this.payload = i;
    }
    public String getPayload(){
        String s = new String(this.payload);
        return s;
    }

    @Override
    public String toString(){
        return "\nHypertext Transfer Protocol: \n" + getPayload();
    }
}