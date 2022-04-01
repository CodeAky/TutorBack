package com.example.tutorback.utils;

/**
 * @author Aky
 * @version 1.0
 * @date 2022/3/30 14:04
 */
public class getNameFromOriginalFilename {
    public static String getUsername(String originalFilename){                      //获得上传者的用户名
        StringBuffer username = new StringBuffer();     //上传者的用户名
        char[] chars = originalFilename.toCharArray();
        for (int i = 0;i < chars.length;i++){
            if (chars[i] == '$' && chars[i+1] == '$' && chars[i+2] == '$'){           //此时chars[i] 之前为 上传者的用户名
                for (int j = 0; j < i;j++){
                    username.append(chars[j]);
                }
                break;
            }

        }
        return username.toString();
    }



    public static String getFilename(String originalFilename) {                  //获得上传文件的文件名
        StringBuffer filename = new StringBuffer();     //上传文件的文件名
        char[] chars = originalFilename.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '$' && chars[i + 1] == '$' && chars[i + 2] == '$') {           //此时chars[i+2] 之后为 上传文件的文件名
                for (int j = i+3;j < chars.length;j++){
                    filename.append(chars[j]);
                }
                break;
            }
        }
        return filename.toString();
    }
}
