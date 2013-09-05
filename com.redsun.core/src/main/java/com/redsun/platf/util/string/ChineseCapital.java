package com.redsun.platf.util.string;

public class ChineseCapital {
    private static final String [] CHINESE_CAPITAL_NUMBER = {
                "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"
        };

    private static String [] CHINESE_CAPITAL_UNIT = {"", "拾", "佰", "仟"};

    public static String toChineseCapitalAmount(Double d) {
        String number = String.format("%.2f", d);
        StringBuffer buffer = new StringBuffer();

        String integer = number.substring(0, number.length() - 3);
        if ("0".equals(integer)) {
            return buffer.toString();
        }
        if (integer.length() > 8) {
            buffer.append(toChineseCapital4(integer.substring(0, integer.length() - 8)));
            buffer.append("亿");
            integer = integer.substring(integer.length() - 8, integer.length());
        }
        if (integer.length() > 4) {
            buffer.append(toChineseCapital4(integer.substring(0, integer.length() - 4)));
            buffer.append("万");
            integer = integer.substring(integer.length() - 4, integer.length());
        }
        buffer.append(toChineseCapital4(integer));
        buffer.append("圆");

        String jiao = number.substring(number.length() - 2, number.length() - 1);
        String fen = number.substring(number.length() - 1, number.length());
        if ("0".equals(jiao)) {
            if ("0".equals(fen)) {
                buffer.append("整");
            } else {
                buffer.append("零");
                buffer.append(CHINESE_CAPITAL_NUMBER[Integer.parseInt(fen)]);
                buffer.append("分整");
            }
        } else {
            buffer.append(CHINESE_CAPITAL_NUMBER[Integer.parseInt(jiao)]);
            buffer.append("角");
            if (!"0".equals(fen)) {
                buffer.append(CHINESE_CAPITAL_NUMBER[Integer.parseInt(fen)]);
                buffer.append("分");
            }
            buffer.append("整");
        }
        return buffer.toString();
    }

    private static String toChineseCapital4(String str) {
        if (str.length() > 4) {
            str = str.substring(str.length() - 4, str.length());
        }
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < str.length() && i < 4; i++) {
            toChineseCapitalIteration(buffer, str, i);
        }

        if (buffer.charAt(buffer.length() - 1) == '零') {
            buffer.delete(buffer.length() - 1, buffer.length());
        }

        return buffer.toString();
    }

    private static void toChineseCapitalIteration(StringBuffer buffer, String str, int index) {
        if (str.charAt(index) == '0') {
            if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == '零') {
                return;
            } else {
                buffer.append("零");
                return;
            }
        } else {
            buffer.append(CHINESE_CAPITAL_NUMBER[Integer.parseInt(str.substring(index, index + 1))]);
            buffer.append(CHINESE_CAPITAL_UNIT[str.length() - index - 1]);
            return;
        }

    }




    private static String [] CHINESE_NUMBER = new String [] {
                "零", "一", "二", "三", "四", "五", "六", "七", "八", "九"
        };

        public static String toChineseSerial(int number) {
                StringBuffer buffer = new StringBuffer();

                while(number > 0) {
                        int remainer = number % 10;
                        buffer.insert(0, CHINESE_NUMBER[remainer]);
                        number = number / 10;
                }

                return buffer.toString();
        }

        public static String toChinese(int number) {
                if(number == 0) {
                        return "零";
                }

                int yi = number / 100000000;
                int wan = number / 10000 % 10000;
                int remainer = number % 10000;

                StringBuffer buffer = new StringBuffer();
                if(yi > 0) {
                        buffer.append(toChinese4Digit(yi)).append("亿");
                }
                if(wan > 0) {
                        if(yi > 0 && wan < 1000) {
                                buffer.append("零");
                        }
                        buffer.append(toChinese4Digit(wan)).append("万");
                }
                if(remainer > 0) {
                        if((yi > 0 || wan > 0) && remainer < 1000) {
                                buffer.append("零");
                        }
                        buffer.append(toChinese4Digit(remainer));
                }

                return buffer.toString();
        }

        private static String toChinese4Digit(int number) {
                if(number > 10000) {
                        number = number % 10000;
                }

                int thousand = number / 1000;
                int hundred = number / 100 % 10;
                int ten = number / 10 % 10;
                int remainer = number % 10;

                StringBuffer buffer = new StringBuffer();

                if(thousand > 0) {
                        buffer.append(CHINESE_NUMBER[thousand]).append("千");
                }
                if(hundred > 0) {
                        buffer.append(CHINESE_NUMBER[hundred]).append("百");
                }
                if(ten > 0) {
                        if(thousand > 0 && hundred == 0) {
                                buffer.append("零");
                        }
                        if(ten > 1) {
                                buffer.append(CHINESE_NUMBER[ten]);
                        }
                        buffer.append("十");
                }
                if(remainer > 0) {
                        if(ten == 0 && (thousand > 0 || hundred > 0)) {
                                buffer.append("零");
                        }
                        buffer.append(CHINESE_NUMBER[remainer]);
                }

                return buffer.toString();
        }
}