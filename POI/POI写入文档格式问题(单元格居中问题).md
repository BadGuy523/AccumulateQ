### 使用POI3.7或4.1.0版本的jar包
###### pom文件(关于poi的部分)
```
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.7</version>
        </dependency>

        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>3.7</version>
        </dependency>

        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml-schemas</artifactId>
            <version>3.7</version>
        </dependency>

        <dependency>
            <groupId>org.apache.xmlbeans</groupId>
            <artifactId>xmlbeans</artifactId>
            <version>2.6.0</version>
        </dependency>
        
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>ooxml-schemas</artifactId>
            <version>1.3</version>
        </dependency>
```
###### 主体功能代码
```
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STVerticalJc;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;

/**
 * POI写入word设置格式测试
 */
public class Test {

    public static void main(String[] args) throws IOException {
        //新建文件输出流
        FileOutputStream outputStream = new FileOutputStream("D:/test/测试.docx");
        //新建文档
        XWPFDocument document = new XWPFDocument();
        //新建表格：两行两列
        XWPFTable table = document.createTable(2,2);
        //设置表格整体宽度
        CTTblWidth ctTblWidth = table.getCTTbl().addNewTblPr().addNewTblW();
        ctTblWidth.setType(STTblWidth.DXA);
        ctTblWidth.setW(BigInteger.valueOf(8000));

        /** 长文本单元格 **/
        //获取第一行第二列单元格
        XWPFTableCell cell = table.getRow(0).getCell(1);
        //移除单元格原有的段落
        cell.removeParagraph(0);
        //设置单元格垂直居中：这种方式必须映入ooxml-schemas的jar包
        CTTcPr ctTcPr = cell.getCTTc().addNewTcPr();
        ctTcPr.addNewVAlign().setVal(STVerticalJc.CENTER);
        //在单元格中新建段落
        XWPFParagraph paragraph =  cell.addParagraph();
        //设置段落水平居中与垂直居中
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
        //在段落中新建文本
        XWPFRun run = paragraph.createRun();
        //设置文本样式与内容
        run.setFontSize(9);
        run.setBold(false);
        run.setFontFamily("宋体");
        run.setText("测试一个长文本内容：卡萨丁防火卷帘刷卡和法律上的康复和老师都符合历史的风格化双方都给客户收到反馈个");

        /** 短文本单元格 **/
        //获取第一行第一列单元格
        XWPFTableCell cell2 = table.getRow(0).getCell(0);
        //移除单元格原有的段落
        cell2.removeParagraph(0);
        //设置单元格垂直居中
        CTTcPr ctTcPr2 = cell2.getCTTc().addNewTcPr();
        ctTcPr2.addNewVAlign().setVal(STVerticalJc.CENTER);
        //在单元格中新建段落
        XWPFParagraph paragraph2 =  cell2.addParagraph();
        //设置段落水平居中与垂直居中
        paragraph2.setAlignment(ParagraphAlignment.CENTER);
        paragraph2.setVerticalAlignment(TextAlignment.CENTER);
        //在段落中新建文本
        XWPFRun run2 = paragraph2.createRun();
        //设置文本的样式与内容
        run2.setFontSize(9);
        run2.setBold(false);
        run2.setFontFamily("宋体");
        run2.setText("测试短文本内容");

        //将文档内容写入输出流
        document.write(outputStream);
        //关闭输出流
        outputStream.close();
    }

}
```
### 使用POI4.1.0版本的jar包
###### pom文件(关于POI部分)
```
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>4.1.0</version>
        </dependency>

        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>4.1.0</version>
        </dependency>

        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml-schemas</artifactId>
            <version>4.1.0</version>
        </dependency>
        <!--要根据poi版本更新该jar包版本号，否则会引起jar包冲突-->
        <dependency>
            <groupId>org.apache.xmlbeans</groupId>
            <artifactId>xmlbeans</artifactId>
            <version>3.1.0</version>
        </dependency>
```
###### 主体功能代码
```
//设置单元格垂直居中
CTTcPr ctTcPr = cell.getCTTc().addNewTcPr();
ctTcPr.addNewVAlign().setVal(STVerticalJc.CENTER);
//将第一种方式中的以上代码改为以下代码
//设置单元格垂直居中
cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
```
- 3.7版本jar包无法使用第二种方式，而第一种方式必须引入ooxml-schemas的jar包
- 4.1.0版本jar包两种方式都可以，==其余使用异同点待补充==