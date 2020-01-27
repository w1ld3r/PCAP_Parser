public class Helper {

    // Compare two 32 bytes array
    public static boolean compare32Bytes(byte[] data, byte[] comparedArray) {
        if (data.length == 4 && comparedArray.length == 4) {
            for (int i = 0; i < 4; i++) {
                if (data[i] == comparedArray[i]) {
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
        return false;
    }

    // Convert a byte array to integer
    public static int convertByteArrayToInt(byte[] array) {
		int ret = 0;
		for (int i = 0; i < array.length; i++) {
			ret += (array[i] & 0x0000000000FF) << ((array.length - 1 - i) * 8);
		}
		return ret;
    }

    // Build a byte array to string
    public static String byteArrayToString(byte[] array) {
		String log = "";
		if (array != null) {
			for (int count = 0; count < array.length; count++) {
				log += (Helper.convertFromIntToHexa(array[count]));
			}
		}
		return log;
    }
    
    // Convert from int data into String hexadecimal (ex 255 => "0xFF")
    public static String convertFromIntToHexa(byte data) {
		int dataTmp = data & 0xFF;
		// Put character in uppercase
		String value = Integer.toHexString(dataTmp).toUpperCase();
		// Add 0 if length equal to 1
		if (value.length() == 1) {
			value = "0" + value;
		}
		return value;
    }

    // Convert Little indian byte array to big endian byte array
    public static byte[] convertLeToBe(byte[] data) {
		byte[] temp = new byte[data.length];
		for (int i = data.length - 1; i >= 0; i--) {
			temp[data.length - 1 - i] = data[i];
		}
		return temp;
  }
  
  // Format Mac Address
  public static String formatMacAddr(byte[] macAddr)
	{
		String macAddrStr="";
		for (int i = 0;i < macAddr.length;i++)
		{
			macAddrStr+=convertFromIntToHexa(macAddr[i])+":";
		}
		
		return macAddrStr.substring(0, macAddrStr.length()-1);
  }
  
// Format IPv4 Address
  public static String formatIpv4Addr(byte[] ip)
	{
		String ipStr = "";
		
		for (int i = 0; i < ip.length;i++)
		{
			ipStr+=(ip[i] & 0xFF)+".";
		}
		return ipStr.substring(0, ipStr.length()-1);
	}

	// Format IPv6 Address
	public static String formatIpv6Addr(byte[] ip)
	{
		String ipStr ="";
		
		for (int i = 0; i  < 16;i=i+2)
		{
			ipStr+=Helper.convertFromIntToHexa(ip[i]) + Helper.convertFromIntToHexa(ip[i+1]) + ":";
		}

		return ipStr.substring(0, ipStr.length()-1);
	}
}