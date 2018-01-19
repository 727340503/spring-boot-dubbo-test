package com.cherrypicks.tcc.util;

import java.security.SecureRandom;


/**
 *
 * @author Kelvin.tie
 *
 */
public class RandomUtil {

	private final static  String[] ALLCHAR = {"0","1","2","3","4","5","6","7","8","9",
											"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
											"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	/*private final static  String[] ALLCHAR = {"0","1","2","3","4","5","6","7","8","9",
		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	private final static  String[] ALLCHAR = {"1","2","3","4","5","6","7","8","9",
		"a","b","c","d","e","f","g","h","j","k","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};*/

	private final static  String[] NUMCHAR = {"0","1","2","3","4","5","6","7","8","9"};

	private final static  String[] LETTERCHAR = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
													"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private final static String[] SPECIALCHARACTERS={"!","@","#","$","%","&"};

	public final static int TYPE_NUM_AND_CHAR=1;
	public final static int TYPE_CHAR=2;
	public final static int TYPE_CHAR_LOWER=3;
	public final static int TYPE_CHAR_UPPER=4;
	public final static int TYPE_NUM=5;
	public final static int TYPE_SPECTAL=6;
	/**
	  * 生成制定位数，
	  * 1。 数字加字母
	  * 2.大小写字母
	  * 3.小写字母
	  * 4.大写字母
	  * @param randomLenght
	  * @param randomType
	  * @return
	  */
	 public static String RandomCode(final int randomLenght,final int randomType) {

		 switch (randomType) {
			case TYPE_NUM_AND_CHAR:
				return generateString(randomLenght);
			case TYPE_CHAR:
				return generateCharString(randomLenght);
			case TYPE_CHAR_LOWER:
				return generateLowerCharString(randomLenght);
			case TYPE_CHAR_UPPER:
				return generateUpperCharString(randomLenght);
			case TYPE_NUM:
				return generateNumString(randomLenght);
			case TYPE_SPECTAL:
				return generateSpectal(randomLenght);
			default:
				throw new RuntimeException("未知的生成格式");
		}
	 }

	/**
	  * 返回一个定长的随机字符串(只包含大小写字母、数字)
	  *
	  * @param length
	  *            随机字符串长度
	  * @return 随机字符串
	  */
	public static String generateString(final int length) {
		  final StringBuffer sb = new StringBuffer();
		  final SecureRandom random = new SecureRandom();
		  for (int i = 0; i < length; i++) {
		   sb.append(ALLCHAR[random.nextInt(ALLCHAR.length)]);
		  }
		  return sb.toString();
	}
	/**
	 * 随机获取特殊字符
	 * @param length
	 * @return
	 */
	public static String generateSpectal(final int length) {
		  final StringBuffer sb = new StringBuffer();
		  final SecureRandom random = new SecureRandom();
		  for (int i = 0; i < length; i++) {
		   sb.append(SPECIALCHARACTERS[random.nextInt(SPECIALCHARACTERS.length)]);
		  }
		  return sb.toString();
	}
	/**
	  * 返回一个定长的随机字符串(只包数字)
	  *
	  * @param length
	  *            随机字符串长度
	  * @return 随机字符串
	  */
	public static String generateNumString(final int length) {
		  final StringBuffer sb = new StringBuffer();
		  final SecureRandom random = new SecureRandom();
		  for (int i = 0; i < length; i++) {
		   sb.append(NUMCHAR[random.nextInt(NUMCHAR.length)]);
		  }
		  return sb.toString();
	}

	 /**
	  * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	  *
	  * @param length
	  *            随机字符串长度
	  * @return 随机字符串
	  */
	public static String generateCharString(final int length) {
		  final StringBuffer sb = new StringBuffer();
		  final SecureRandom random = new SecureRandom();
		  for (int i = 0; i < length; i++) {
		   sb.append(LETTERCHAR[random.nextInt(LETTERCHAR.length)]);
		  }
		  return sb.toString();
	}

	/**
	  * 返回一个定长的随机纯大写字母字符串(只包含大小写字母)
	  *
	  * @param length
	  *            随机字符串长度
	  * @return 随机字符串
	  */
	public static String generateLowerCharString(final int length) {
		 return generateCharString(length).toLowerCase();
	}

	 /**
	  * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	  *
	  * @param length
	  *            随机字符串长度
	  * @return 随机字符串
	  */
	public static String generateUpperCharString(final int length) {
		 return generateCharString(length).toUpperCase();
	}

	 /**
	  * 生成一个定长的纯0字符串
	  *
	  * @param length
	  *            字符串长度
	  * @return 纯0字符串
	  */
	public static String generateZeroString(final int length) {
		  final StringBuffer sb = new StringBuffer();
		  for (int i = 0; i < length; i++) {
		   sb.append('0');
		  }
		  return sb.toString();
	}

	/**
	  * 根据数字生成一个定长的字符串，长度不够前面补0
	  *
	  * @param num
	  *            数字
	  * @param fixdlenth
	  *            字符串长度
	  * @return 定长的字符串
	  */
	 public static String toFixdLengthString(final long num, final int fixdlenth) {
	  final StringBuffer sb = new StringBuffer();
		  final String strNum = String.valueOf(num);
		  if (fixdlenth - strNum.length() >= 0) {
		   sb.append(generateZeroString(fixdlenth - strNum.length()));
		  } else {
		   throw new RuntimeException("将数字" + num + "转化为长度为" + fixdlenth
		     + "的字符串发生异常！");
		  }
		  sb.append(strNum);
		  return sb.toString();
	 }

	 /**
	  * random 8 char
	  * [1-2]:[A-Z][a-z]
	  * [3-4]:[0-9]
	  * [5-6]:[A-Z][a-z]
	  * [7]:[!@#$%&]
	  * [8]:[A-Z]
	  *
	  * @return
	  */
	 public static String generate8BitChar(){
		 final StringBuffer sb = new StringBuffer();
		 sb.append(RandomCode(2,TYPE_CHAR));
		 sb.append(RandomCode(2,TYPE_NUM));
		 sb.append(RandomCode(2,TYPE_CHAR));
		 sb.append(RandomCode(1,TYPE_SPECTAL));
		 sb.append(RandomCode(1,TYPE_CHAR_UPPER));
		 return sb.toString();
	 }

    public static String getIdByUUID() {
        final String uuid = java.util.UUID.randomUUID().toString();
        final byte[] digest = uuid.getBytes();
        final long ll = java.nio.ByteBuffer.wrap(digest).asLongBuffer().get();
        final String trunc10 = ("" + ll).substring(0, 10);
        return trunc10;
    }

    /*  private static final long LIMIT = 10000000000L;
    private static long last = 0;

    public static String generate10NumString() {
        // 10 digits.
        long id = System.currentTimeMillis() % LIMIT;
        if (id <= last) {
            id = (last + 1) % LIMIT;
        }
        last = id;
        return addZeroForNum(String.valueOf(last),10);
    }*/

    public static String addZeroForNum(String str, final int strLength) {
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                final StringBuilder sb = new StringBuilder();
                sb.append("0").append(str);// 左补0
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }


	 public static void main(final String[] args) {
	     System.out.println(System.currentTimeMillis());
		 //System.out.println(generate10NumString());
	}
}