package com.redsun.platf.util.string;

import java.util.TreeMap;

public class EnglishCaptial {
    private static final TreeMap<Integer, String> cardinal;
           static {
                   cardinal = new TreeMap<Integer, String>();
                   cardinal.put(1, "one");
                   cardinal.put(2, "two");
                   cardinal.put(3, "three");
                   cardinal.put(4, "four");
                   cardinal.put(5, "five");
                   cardinal.put(6, "six");
                   cardinal.put(7, "seven");
                   cardinal.put(8, "eight");
                   cardinal.put(9, "nine");
                   cardinal.put(10, "ten");
                   cardinal.put(11, "eleven");
                   cardinal.put(12, "twelve");
                   cardinal.put(13, "thirteen");
                   cardinal.put(14, "fourteen");
                   cardinal.put(15, "fifteen");
                   cardinal.put(16, "sixteen");
                   cardinal.put(17, "seventeen");
                   cardinal.put(18, "eighteen");
                   cardinal.put(19, "nineteen");
                   cardinal.put(20, "twenty");
                   cardinal.put(30, "thirty");
                   cardinal.put(40, "forty");
                   cardinal.put(50, "fifty");
                   cardinal.put(60, "sixty");
                   cardinal.put(70, "seventy");
                   cardinal.put(80, "eighty");
                   cardinal.put(90, "ninety");
           }

           private static final TreeMap<String, String> ordinal;
           static {
                   ordinal = new TreeMap<String, String>();
                   ordinal.put("one", "first");
                   ordinal.put("two", "second");
                   ordinal.put("three", "third");
                   ordinal.put("four", "fourth");
                   ordinal.put("five", "fifth");
                   ordinal.put("six", "sixth");
                   ordinal.put("seven", "seventh");
                   ordinal.put("eight", "eighth");
                   ordinal.put("nine", "ninth");
                   ordinal.put("ten", "tenth");
                   ordinal.put("eleven", "eleventh");
                   ordinal.put("twelve", "twelfth");
                   ordinal.put("thirteen", "thirteenth");
                   ordinal.put("fourteen", "fourteenth");
                   ordinal.put("fifteen", "fifteenth");
                   ordinal.put("sixteen", "sixteenth");
                   ordinal.put("seventeen", "seventeenth");
                   ordinal.put("eighteen", "eighteenth");
                   ordinal.put("nineteen", "nineteenth");
                   ordinal.put("twenty", "twentieth");
                   ordinal.put("thrity", "thirtieth");
                   ordinal.put("forty", "fortieth");
                   ordinal.put("fifty", "fiftieth");
                   ordinal.put("sixty", "sixtieth");
                   ordinal.put("seventy", "seventieth");
                   ordinal.put("eighty", "eightieth");
                   ordinal.put("ninety", "ninetieth");
                   ordinal.put("hundred", "hundredth");
                   ordinal.put("hundreds", "hundredth");
                   ordinal.put("thousand", "thousandth");
                   ordinal.put("million", "millionth");
                   ordinal.put("millions", "millionth");
                   ordinal.put("billion", "billionth");
                   ordinal.put("billions", "billionth");
           }



    /**
     * 将基数词专为英文大写的序数词，支持到billion
     *
     * @param number 基数数值
     * @return 英文大写的序数词
     */
    public static String toEnglishCapital(int number) {
        if (number == 0) {
            return "zero";
        }
        int billion = number / 1000000000;
        int million = (number / 1000000) % 1000;
        int thousand = (number / 1000) % 1000;
        int remainder = number % 1000;

        StringBuffer sb = new StringBuffer();
        if (billion > 0) {
            sb.append(String.format("%s %s ", toEnglishCapital3Digit(billion), billion > 1 ? "billions" : "billion"));
        }
        if (million > 0) {
            if (million < 100 & billion > 0) {
                sb.append("and ");
            }
            sb.append(String.format("%s %s ", toEnglishCapital3Digit(million), million > 1 ? "millions" : "million"));
        }
        if (thousand > 0) {
            if (thousand < 100 & (million > 0 || billion > 0)) {
                sb.append("and ");
            }
            sb.append(String.format("%s %s ", toEnglishCapital3Digit(thousand), thousand > 1 ? "thousands" : "thousand"));
        }
        if (remainder > 0) {
            if (remainder < 100 & (thousand > 0 || million > 0 || billion > 0)) {
                sb.append("and ");
            }
            sb.append(toEnglishCapital3Digit(remainder));
        }

        return sb.toString().trim();
    }

    public static String toEnglishCaptitalOrdinal(int number) {
        String cardinal = toEnglishCapital(number);

        int lastSpace = cardinal.lastIndexOf(" ");
        int lastHyphen = cardinal.lastIndexOf("-");

        if (lastSpace == -1 && lastHyphen == -1) {
            return ordinal.get(cardinal);
        } else {
            int pos = Math.max(lastHyphen, lastSpace) + 1;
            StringBuffer sb = new StringBuffer();
            sb.append(cardinal.substring(0, pos));
            sb.append(ordinal.get(cardinal.substring(pos, cardinal.length())));
            return sb.toString();
        }


    }

    static String toEnglishCapital3Digit(int number) {
        if (number == 0) {
            return "zero";
        }
        if (number > 1000) {
            number = number % 1000;
        }

        int hundred = 0;
        if (number > 99) {
            hundred = number / 100;
        }
        int remainder = number - hundred * 100;

        StringBuffer sb = new StringBuffer();
        if (hundred == 1) {
            sb.append(String.format("%s %s", cardinal.get(hundred), "hundred"));
        } else if (hundred > 1) {
            sb.append(String.format("%s %s", cardinal.get(hundred), "hundreds"));
        }

        if (hundred > 0 && remainder > 0) {
            sb.append(" and ");
        }

        if (remainder > 0) {
            if (remainder < 20) {
                sb.append(cardinal.get(remainder));
            } else {
                int ten = remainder / 10;
                if (remainder % 10 > 0) {
                    sb.append(String.format("%s-%s", cardinal.get(ten * 10), cardinal.get(remainder % 10)));
                } else {
                    sb.append(cardinal.get(ten * 10));
                }
            }
        }
        return sb.toString();
    }
}