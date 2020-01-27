import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrintTCP {

    public ArrayList<RecordHeader> records;
    private int nbIndex;
    private int sNumber;
    private HashMap<String, byte[]> indexNumber;
    private HashMap<String, String> seqNumber;
    private HashMap<String, String> nxtSeqNumber;

    public PrintTCP(){
        this.records = new ArrayList<RecordHeader>();
        this.nbIndex = 0;
        this.sNumber = 0;
        this.indexNumber = new HashMap<String, byte[]>();
        this.seqNumber = new HashMap<String, String>();
        this.nxtSeqNumber = new HashMap<String, String>();
    }

    public void ftpSYN(RecordHeader record){
        record.ethernet.ipv4.tcp.streamIndex = ++this.nbIndex;
        indexNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), record.ethernet.ipv4.tcp.sequenceNumber);
        record.ethernet.ipv4.tcp.rSequenceNumber = 0;
        record.ethernet.ipv4.tcp.rAcknowledgmentNumber = 0;
        System.out.print("\033[0;30m \033[48;5;241m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [SYN]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
    }

    public void ftpSYNACK(RecordHeader record){
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            value[3] += 0x1;
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                record.ethernet.ipv4.tcp.rSequenceNumber = 0;
                record.ethernet.ipv4.tcp.rAcknowledgmentNumber = ++this.sNumber;;
                this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(this.sNumber));
                this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                System.out.print("\033[0;30m \033[48;5;241m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [SYN, ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                break;
            }
            else{
                //SYN ACK WITHOUT SYN
            }
        } 
    }

    public void ftpACK(RecordHeader record){
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.sequenceNumber, value) || Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }else{
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }
                System.out.print("\033[0;30m \033[48;5;105m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                break;
            }
            else{
                //ACK WITHOUT SYN, SYN ACK
            }
        }
    }

    public void ftpFINACK(RecordHeader record){
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.sequenceNumber, value) || Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                this.indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + 1));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }else{
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + 1));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }
                System.out.print("\033[0;30m \033[48;5;105m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [FIN, ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
            }
        }
    }

    public void ftpPSHACK(RecordHeader record){
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.sequenceNumber, value) || Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.ftp == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.ftp.payload.length));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }else{
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.ftp == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.ftp.payload.length));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }
                if(record.ethernet.ipv4.tcp.getSourcePort() == 21){
                    System.out.print("\033[0;30m \033[48;5;105m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "FTP" + "\t" + "Response: " + record.ethernet.ipv4.tcp.ftp.getPayload().replace("\n", " ").replace("\r", "") + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                }
                else if(record.ethernet.ipv4.tcp.getDestinationPort() == 21){
                    System.out.print("\033[0;30m \033[48;5;105m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "FTP" + "\t" + "Request: " + record.ethernet.ipv4.tcp.ftp.getPayload().replace("\n", " ").replace("\r", "") + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                }
            }
        }
    }

    public void httpSYN(RecordHeader record){
        record.ethernet.ipv4.tcp.streamIndex = ++this.nbIndex;
        this.indexNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), record.ethernet.ipv4.tcp.sequenceNumber);
        record.ethernet.ipv4.tcp.rSequenceNumber = 0;
        record.ethernet.ipv4.tcp.rAcknowledgmentNumber = 0;
        this.seqNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
        System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [SYN]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
    }

    public void httpSYNACK(RecordHeader record){
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            value[3] += 0x1;
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                record.ethernet.ipv4.tcp.rSequenceNumber = 0;
                record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey())) + 1;
                this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [SYN, ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                break;
            }
            else{
                //SYN ACK WITHOUT SYN
            }
        } 
    }

    public void httpACK(RecordHeader record){
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.sequenceNumber, value) || Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                    if(record.ethernet.ipv4.tcp.http == null){
                        record.ethernet.ipv4.tcp.rSequenceNumber = 1;
                        record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                        this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.http == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
                        this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                    } else {
                        record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                        record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                        this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
                        this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                    }
                }else{
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.http == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }
                System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                break;
            }
            else{
                //ACK WITHOUT SYN, SYN ACK
            }
        }
    }

    public void httpFINACK(RecordHeader record){
        boolean isIn = false;
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.sequenceNumber, value) || Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                this.indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + 1));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }else{
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.http == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + 1):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }
                System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [FIN, ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                isIn = true;
                break;
            }
        }
        if(!isIn){
            record.ethernet.ipv4.tcp.streamIndex = ++this.nbIndex;
            this.indexNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), record.ethernet.ipv4.tcp.acknowledgmentNumber);
            record.ethernet.ipv4.tcp.rSequenceNumber = 1;
            record.ethernet.ipv4.tcp.rAcknowledgmentNumber = 1;
            this.nxtSeqNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + 1));
            this.seqNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
            System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [FIN, ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
        }
    }

    public void httpPSHACK(RecordHeader record){
        boolean isIn = false;
        for(Map.Entry<String,byte[]> e : this.indexNumber.entrySet()){
            int key = Integer.parseInt(e.getKey());
            byte[] value = e.getValue();
            if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.sequenceNumber, value) || Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                indexNumber.put(String.valueOf(key), record.ethernet.ipv4.tcp.acknowledgmentNumber);
                record.ethernet.ipv4.tcp.streamIndex = key;
                if(Helper.compare32Bytes(record.ethernet.ipv4.tcp.acknowledgmentNumber, value)){
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.http == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }else{
                    record.ethernet.ipv4.tcp.rSequenceNumber = Integer.parseInt(this.seqNumber.get(e.getKey()));
                    record.ethernet.ipv4.tcp.rAcknowledgmentNumber = Integer.parseInt(this.nxtSeqNumber.get(e.getKey()));
                    this.nxtSeqNumber.put(String.valueOf(key), (record.ethernet.ipv4.tcp.http == null)?String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber):String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
                    this.seqNumber.put(String.valueOf(key), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
                }
                if(record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080){
                    System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "HTTP" + "\t" +  ((record.ethernet.ipv4.tcp.http.getPayload().split("\n")[0].length() > 50)?record.ethernet.ipv4.tcp.http.getPayload().split("\n")[0].replace("\n", " ").replace("\r", "").substring(0, 50) + " ...":record.ethernet.ipv4.tcp.http.getPayload().split("\n")[0].replace("\n", " ").replace("\r", "")) + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                }
                else if(record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080){
                    System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "TCP" + "\t" + record.ethernet.ipv4.tcp.getSourcePort() + " → " + record.ethernet.ipv4.tcp.getDestinationPort() + " [PSH, ACK]" + " Seq=" + record.ethernet.ipv4.tcp.rSequenceNumber + " Ack=" + record.ethernet.ipv4.tcp.rAcknowledgmentNumber + " Win=" + record.ethernet.ipv4.tcp.getWindowSizeValue() + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
                }
                isIn = true;
                break;
            }
        }
        if(!isIn){
            record.ethernet.ipv4.tcp.streamIndex = ++this.nbIndex;
            this.indexNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), record.ethernet.ipv4.tcp.acknowledgmentNumber);
            record.ethernet.ipv4.tcp.rSequenceNumber = 1;
            record.ethernet.ipv4.tcp.rAcknowledgmentNumber = 1;
            this.nxtSeqNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), String.valueOf(record.ethernet.ipv4.tcp.rSequenceNumber + record.ethernet.ipv4.tcp.http.payload.length));
            this.seqNumber.put(String.valueOf(record.ethernet.ipv4.tcp.streamIndex), String.valueOf(record.ethernet.ipv4.tcp.rAcknowledgmentNumber));
            System.out.print("\033[0;30m \033[48;5;155m" + record.ethernet.ipv4.getSourceIPAddress() + " -> " + record.ethernet.ipv4.getDestinationIPAddress() + "\t" + "HTTP" + "\t" + ((record.ethernet.ipv4.tcp.http.getPayload().split("\n")[0].length() > 50)?record.ethernet.ipv4.tcp.http.getPayload().split("\n")[0].replace("\n", " ").replace("\r", "").substring(0, 50) + " ...":record.ethernet.ipv4.tcp.http.getPayload().split("\n")[0].replace("\n", " ").replace("\r", "")) + " [Stream index: " + record.ethernet.ipv4.tcp.streamIndex + "]" + "\033[0m" + "\n");
        }
    }

    public void print(RecordHeader record, String protocol){
        //SYN
        if(record.ethernet.ipv4.tcp.flags.syn == 0x1 && record.ethernet.ipv4.tcp.flags.ack == 0x0 && record.ethernet.ipv4.tcp.flags.psh == 0x0 && record.ethernet.ipv4.tcp.flags.fin == 0x0){
            if(record.ethernet.ipv4.tcp.getSourcePort() == 21 || record.ethernet.ipv4.tcp.getDestinationPort() == 21 && (protocol.equals("") || protocol.equals("ftp")))
                ftpSYN(record);
            else if(record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080 || record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080 && (protocol.equals("") || protocol.equals("http")))
                httpSYN(record);
        }
        //SYN ACK
        else if(record.ethernet.ipv4.tcp.flags.syn == 0x1 && record.ethernet.ipv4.tcp.flags.ack == 0x1 && record.ethernet.ipv4.tcp.flags.psh == 0x0 && record.ethernet.ipv4.tcp.flags.fin == 0x0){
            if(record.ethernet.ipv4.tcp.getSourcePort() == 21 || record.ethernet.ipv4.tcp.getDestinationPort() == 21 && (protocol.equals("") || protocol.equals("ftp")))
                ftpSYNACK(record);
            else if(record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080 || record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080 && (protocol.equals("") || protocol.equals("http")))
                httpSYNACK(record);
        }
        //ACK
        else if(record.ethernet.ipv4.tcp.flags.syn == 0x0 && record.ethernet.ipv4.tcp.flags.ack == 0x1 && record.ethernet.ipv4.tcp.flags.psh == 0x0 && record.ethernet.ipv4.tcp.flags.fin == 0x0 && record.ethernet.ipv4.tcp.getTcpPayload() == ""){
            if(record.ethernet.ipv4.tcp.getSourcePort() == 21 || record.ethernet.ipv4.tcp.getDestinationPort() == 21 && (protocol.equals("") || protocol.equals("ftp")))
                ftpACK(record);
            else if(record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080 || record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080 && (protocol.equals("") || protocol.equals("http")))
                httpACK(record);
        }
        //PSH ACK
        else if(record.ethernet.ipv4.tcp.flags.psh == 0x1 && record.ethernet.ipv4.tcp.flags.ack == 0x1 && record.ethernet.ipv4.tcp.flags.syn == 0x0 && record.ethernet.ipv4.tcp.flags.fin == 0x0){
            if(record.ethernet.ipv4.tcp.getSourcePort() == 21 || record.ethernet.ipv4.tcp.getDestinationPort() == 21 && (protocol.equals("") || protocol.equals("ftp")))
                ftpPSHACK(record);
            else if(record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080 || record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080 && (protocol.equals("") || protocol.equals("http")))
                httpPSHACK(record);
        }
        //FIN ACK
        else if(record.ethernet.ipv4.tcp.flags.ack == 0x1 && record.ethernet.ipv4.tcp.flags.fin == 0x1 && record.ethernet.ipv4.tcp.flags.syn == 0x0 && record.ethernet.ipv4.tcp.flags.psh == 0x0){
            if(record.ethernet.ipv4.tcp.getSourcePort() == 21 || record.ethernet.ipv4.tcp.getDestinationPort() == 21 && (protocol.equals("") || protocol.equals("ftp")))
                ftpFINACK(record);
            else if(record.ethernet.ipv4.tcp.getDestinationPort() == 80 || record.ethernet.ipv4.tcp.getDestinationPort() == 8080 || record.ethernet.ipv4.tcp.getSourcePort() == 80 || record.ethernet.ipv4.tcp.getSourcePort() == 8080 && (protocol.equals("") || protocol.equals("http")))
                httpFINACK(record);
        }
    }
}


