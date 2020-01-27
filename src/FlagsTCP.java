public class FlagsTCP{

    int reserved ;
    int ns;
    int cwr;
    int ece;
    int urg;
    int ack;
    int psh;
    int rst;
    int syn;
    int fin;

    public FlagsTCP(byte[] i){
        this.reserved = i[0] >> 1 & 0x07;
        this.ns = i[0] & 0x01;
        this.cwr = i[1] >> 7 & 0x01;
        this.ece = i[1] >> 6 & 0x01;
        this.urg = i[1] >> 5 & 0x01;
        this.ack = i[1] >> 4 & 0x01;
        this.psh = i[1] >> 3 & 0x01;
        this.rst = i[1] >> 2 & 0x01;
        this.syn = i[1] >> 1 & 0x01;
        this.fin = i[1] & 0x01;
    }

    @Override
    public String toString(){
        return "\n\t- Reserved: " + ((this.reserved == 0x00)?"Not set":"Set") + "\n\t- Nonce: " + ((this.ns == 0x00)?"Not set":"Set") + "\n\t- Congection Window Reduced: " + ((this.cwr == 0x00)?"Not set":"Set") + "\n\t- ECN-Echo: " + ((this.ece == 0x00)?"Not set":"Set") + "\n\t- Urgent: " + ((this.urg == 0x00)?"Not set":"Set") + "\n\t- Acknowledgment: " + ((this.ack == 0x00)?"Not set":"Set") + "\n\t- Push: " + ((this.psh == 0x00)?"Not set":"Set") + "\n\t- Reset: " + ((this.rst == 0x00)?"Not set":"Set") + "\n\t- Sync: " + ((this.syn == 0x00)?"Not set":"Set") + "\n\t- Fin: " + ((this.fin == 0x00)?"Not set":"Set");  
    }
}