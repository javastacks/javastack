Base64是一种能将任意Binary资料用64种字元组合成字串的方法，而这个Binary资料和字串资料彼此之间是可以互相转换的，十分方便。在实际应用上，Base64除了能将Binary资料可视化之外，也常用来表示字串加密过后的内容。如果要使用Java 程式语言来实作Base64的编码与解码功能，可以参考本篇文章的作法。

#### 早期作法

早期在Java上做Base64的编码与解码，会使用到JDK里sun.misc套件下的BASE64Encoder和BASE64Decoder这两个类别，用法如下：

```
final BASE64Encoder encoder = new BASE64Encoder();
final BASE64Decoder decoder = new BASE64Decoder();
final String text = "Java技术栈";
final byte[] textByte = text.getBytes("UTF-8");

//编码
final String encodedText = encoder.encode(textByte);
System.out.println(encodedText);

//解码
System.out.println(new String(decoder.decodeBuffer(encodedText), "UTF-8"));

final BASE64Encoder encoder = new BASE64Encoder();
final BASE64Decoder decoder = new BASE64Decoder();
final String text = "Java技术栈";
final byte[] textByte = text.getBytes("UTF-8");

//编码
final String encodedText = encoder.encode(textByte);
System.out.println(encodedText);

//解码
System.out.println(new String(decoder.decodeBuffer(encodedText), "UTF-8"));
```


从以上程式可以发现，在Java用Base64一点都不难，不用几行程式码就解决了！只是这个sun.mis c套件所提供的Base64功能，编码和解码的效率并不太好，而且在以后的Java版本可能就不被支援了，完全不建议使用。

#### Apache Commons Codec作法

Apache Commons Codec有提供Base64的编码与解码功能，会使用到org.apache.commons.codec.binary套件下的Base64类别，用法如下：

```
final Base64 base64 = new Base64();
final String text = "Java技术栈";
final byte[] textByte = text.getBytes("UTF-8");

//编码
final String encodedText = base64.encodeToString(textByte);
System.out.println(encodedText);

//解码
System.out.println(new String(base64.decode(encodedText), "UTF-8"));

final Base64 base64 = new Base64();
final String text = "Java技术栈";
final byte[] textByte = text.getBytes("UTF-8");

//编码
final String encodedText = base64.encodeToString(textByte);
System.out.println(encodedText);

//解码
System.out.println(new String(base64.decode(encodedText), "UTF-8"));
```

以上的程式码看起来又比早期用sun.mis c套件还要更精简，效能实际执行起来也快了不少。缺点是需要引用Apache Commons Codec，很麻烦。

#### Java 8之后的作法

Java 8的java.util套件中，新增了Base64的类别，可以用来处理Base64的编码与解码，用法如下：

```
final Base64.Decoder decoder = Base64.getDecoder();
final Base64.Encoder encoder = Base64.getEncoder();
final String text = "Java技术栈";
final byte[] textByte = text.getBytes("UTF-8");

//编码
final String encodedText = encoder.encodeToString(textByte);
System.out.println(encodedText);

//解码
System.out.println(new String(decoder.decode(encodedText), "UTF-8"));

final Base64.Decoder decoder = Base64.getDecoder();
final Base64.Encoder encoder = Base64.getEncoder();
final String text = "Java技术栈";
final byte[] textByte = text.getBytes("UTF-8");

//编码
final String encodedText = encoder.encodeToString(textByte);
System.out.println(encodedText);

//解码
System.out.println(new String(decoder.decode(encodedText), "UTF-8"));
```


与sun.mis c套件和Apache Commons Codec所提供的Base64编解码器来比较的话，Java 8提供的Base64拥有更好的效能。实际测试编码与解码速度的话，Java 8提供的Base64，要比sun.mis c套件提供的还要快至少11倍，比Apache Commons Codec提供的还要快至少3倍。**因此在Java上若要使用Base64，这个Java 8底下的java .util套件所提供的Base64类别绝对是首选！**

来源:https://magiclen.org/java-base64/