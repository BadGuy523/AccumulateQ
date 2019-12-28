package com.zjq.codedemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**

 *InputStreamReader参数charset要跟文件编码格式一致。 InputStreamReader读的时候才不会乱码。

 *OutputStreamWriter参数charset设置编码例如UTF-8。在操作系统打开文件时候也要用相应编码打开才不会乱码。

 *InputStreamReader：读操作时编码要与文件编码一致。OutputStreamWriter写操作时设置编码打开文件时也要以相同编码打开。

 */

@Controller
public class FileController {

    @RequestMapping("/upload")
    public String upload(MultipartFile file) throws Exception {
        FileInputStream inputStream = (FileInputStream) file.getInputStream();
        //以文件的编码格式读取文件内容
        String code = getCode(inputStream);
        System.out.println("该文件编码为：" + code);
        FileInputStream inputStream2 = (FileInputStream) file.getInputStream();
        InputStreamReader reader = new InputStreamReader(inputStream2,code);
        String path = System.getProperty("user.dir") + "\\测试.txt";
        File uploadFile = new File(path);
        FileOutputStream outputStream = new FileOutputStream(uploadFile);
        System.out.println("系统默认编码为："+System.getProperty("file.encoding"));
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,System.getProperty("file.encoding"));
        int length = 0;
        char[] buffer = new char[1024];
        while ((length = reader.read(buffer, 0, 1024)) != -1) {
            writer.write(buffer, 0, 1024);
        }
        writer.flush();
        writer.close();
        outputStream.close();
        return "redirect:/index.html";
    }

    @RequestMapping("/download")
    public void download(HttpServletResponse response) throws Exception {

        response.reset();         //清楚空格等操作
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName="+new String("测试".getBytes(),"iso-8859-1")+".txt");

        File file = new File(System.getProperty("user.dir") + "\\测试.txt");
        FileInputStream inputStream = new FileInputStream(file);
        String code = getCode(inputStream);
        System.out.println("文件编码为："+code);
        FileInputStream inputStream2 = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(inputStream2,System.getProperty("file.encoding"));
        ServletOutputStream outputStream = response.getOutputStream();
        System.out.println("系统默认编码为："+System.getProperty("file.encoding"));
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,System.getProperty("file.encoding"));
        int length = 0;
        char[] buffer = new char[1024];
        while ((length = reader.read(buffer, 0, 1024)) != -1) {
            writer.write(buffer, 0, 1024);
        }
        writer.flush();
        writer.close();
        outputStream.close();
    }

    @RequestMapping("/download2")
    public void download2(HttpServletResponse response) throws Exception {

        response.reset();         //清楚空格等操作
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //2.设置文件头：最后一个参数是设置下载文件名(假如我们叫a.pdf)
        response.setHeader("Content-Disposition", "attachment;fileName="+new String("测试".getBytes(),"iso-8859-1")+".txt");

        File file = new File(System.getProperty("user.dir") + "\\测试.txt");
        FileInputStream inputStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        int length = 0;
        byte[] buffer = new byte[1024];
        while ((length = inputStream.read(buffer, 0, 1024)) != -1) {
            outputStream.write(buffer, 0, 1024);
        }
        outputStream.flush();
        outputStream.close();
        outputStream.close();
    }

    /**
     * 获得文件编码
     * @param inputStream
     * @return
     * @throws Exception
     */
    private String getCode(FileInputStream inputStream) throws Exception {
        BufferedInputStream bin = new BufferedInputStream(inputStream);
        int p = (bin.read() << 8) + bin.read();
        bin.close();
        String code = null;

        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }

        return code;
    }
}
