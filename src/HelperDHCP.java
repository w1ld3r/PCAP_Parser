public class HelperDHCP{
    public static String formatFlag(byte[] data){
        int broadcastFlag = 0;
        int reservedFlags = 0;
        broadcastFlag = data[0] >> 7;
        reservedFlags += data[1];
        reservedFlags += data[0] << 8 & 0x1F00;
        byte[] bReservedFlags = new byte[] {
            (byte)((reservedFlags >> 24) & 0xff),
            (byte)((reservedFlags >> 16) & 0xff),
            (byte)((reservedFlags >> 8) & 0xff),
            (byte)((reservedFlags >> 0) & 0xff),
        };
        return "\n\t- Broadcast flag: " + ((broadcastFlag == 0)?"Unicast":"Broadcast") + "\n\t- Reserved flags: 0x" + Helper.byteArrayToString(bReservedFlags);
    }
}