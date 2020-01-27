import java.util.Arrays;
import java.util.ArrayList;

public class DNS {
    private byte[] transaction;
    private byte[] flags;
    private byte[] question;
    private byte[] answerRRs;
    private byte[] authorityRRs;
    private byte[] additionalRRs;
    private byte[] queriesName;
    private byte[] queriesType;
    private byte[] queriesClass;
    public ArrayList<DNSAnswer> dnsAnswers;
    public ArrayList<DNSAdditionalRecords> dnsAdditionalRecords;

    public DNS() {
        this.dnsAnswers = new ArrayList<DNSAnswer>();
        this.dnsAdditionalRecords = new ArrayList<DNSAdditionalRecords>();
    }

    public void setTransaction(byte[] i) {
        this.transaction = i;
    }

    public void setFlags(byte[] i) {
        this.flags = i;
    }

    public void setQuestion(byte[] i) {
        this.question = i;
    }

    public void setAnswerRRs(byte[] i) {
        this.answerRRs = i;
    }

    public void setAuthorityRRs(byte[] i) {
        this.authorityRRs = i;
    }

    public void setAdditionalRRs(byte[] i) {
        this.additionalRRs = i;
    }

    public void setQueriesName(byte[] i) {
        this.queriesName = i;
    }

    public void setQueriesType(byte[] i) {
        this.queriesType = i;
    }

    public void setQueriesClass(byte[] i) {
        this.queriesClass = i;
    }

    public byte[] getTransaction() {
        return this.transaction;
    }

    public byte[] getFlags() {
        return this.flags;
    }

    public byte[] getQuestion() {
        return this.question;
    }

    public byte[] getAnswerRRs() {
        return this.answerRRs;
    }

    public byte[] getAuthorityRRs() {
        return this.authorityRRs;
    }

    public byte[] getAdditionalRRs() {
        return this.additionalRRs;
    }

    public String getQueriesName() {
        byte[] t = Arrays.copyOfRange(this.queriesName, 1, this.queriesName.length - 1);
        for (int i = 0; i < t.length; i++) {
            if ((byte) 0x30 > t[i])
                t[i] = 0x2E;
        }
        String s = new String(t);
        return s;
    }

    public byte[] getQueriesType() {
        return this.queriesType;
    }

    public byte[] getQueriesClass() {
        return this.queriesClass;
    }

    @Override
    public String toString() {

        String sDnsAnswers = "";
        for (int i = 0; i < dnsAnswers.size(); i++) {
            DNSAnswer dnsAnswer = this.dnsAnswers.get(i);
            sDnsAnswers += dnsAnswer;
        }

        String sDnsAdditionalRecords = "";
        for (int i = 0; i < this.dnsAdditionalRecords.size(); i++) {
            DNSAdditionalRecords dnsAdditionalRecord = this.dnsAdditionalRecords.get(i);
            sDnsAdditionalRecords += dnsAdditionalRecord;
        }

        String s = "";
        if((Helper.convertByteArrayToInt(getFlags()) & 0x7800) == 0x0000){
            s += "Standard querry ";
        }
        else
            s += "0x" + Helper.byteArrayToString(getFlags());

        if((Helper.convertByteArrayToInt(getFlags()) & 0x8000) == 0x8000 && Helper.convertByteArrayToInt(getAnswerRRs()) != 0x00){
            s += "response ";
            s += sDnsAnswers;
        }
        else if((Helper.convertByteArrayToInt(getFlags()) & 0x8000) == 0x8000 && Helper.convertByteArrayToInt(getAnswerRRs()) == 0x00){
            s += "response ";
            if((Helper.convertByteArrayToInt(getFlags()) & 0x3) == 0x3)
                s += "No such name ";
            s += DNSTypeConstants.DNS_TYPE.get(Helper.convertByteArrayToInt(getQueriesType())) + " ";
            s += getQueriesName();
        }
        else{
            s += DNSTypeConstants.DNS_TYPE.get(Helper.convertByteArrayToInt(getQueriesType())) + " ";
            s += getQueriesName();
        }

        return s;

        /*return "\nDomain Name System:" + "\n- Transaction ID: 0x" + Helper.byteArrayToString(getTransaction())
                + "\n- Flags: 0x" + Helper.byteArrayToString(getFlags()) + "\n- Questions: "
                + Helper.convertByteArrayToInt(getQuestion()) + "\n- Answer RRs: "
                + Helper.convertByteArrayToInt(getAnswerRRs()) + "\n- Authority RRs: "
                + Helper.convertByteArrayToInt(getAuthorityRRs()) + "\n- Additional RRs: "
                + Helper.convertByteArrayToInt(getAdditionalRRs()) + "\n- Queries:" + "\n\t- Name: " + getQueriesName()
                + "\n\t- Type: " + DNSTypeConstants.DNS_TYPE.get(Helper.convertByteArrayToInt(getQueriesType())) + "\n\t- Class: "
                +  DNSClassConstants.DNS_CLASS.get(Helper.convertByteArrayToInt(getQueriesClass())) + ((sDnsAnswers != "")?"\n- Answers:" + sDnsAnswers:"") + ((sDnsAdditionalRecords != "")?"\n- Additional records:" + sDnsAdditionalRecords:"");*/

    }
}