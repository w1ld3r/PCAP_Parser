public class HelperARP{
    
    public static String formatSize(byte[] data){
        int hardwareSize = 0;
        int protocolSize = 0;
        hardwareSize = data[0] >> 4;
        protocolSize = data[0] & 0xF;
        return "\n- Hardware size: " + hardwareSize + "\n- Protocol Size: " + protocolSize;
    }
}