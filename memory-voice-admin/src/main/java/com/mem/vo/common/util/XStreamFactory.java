package com.mem.vo.common.util;


/**
 * Description: com.mem.vo.common.util
 * User: baiyuehui
 * Date: 2019/7/3
 */
public class XStreamFactory {
 /*   public static final String CDATA_PREFIX = "<![CDATA[";
    public static final String CDATA_SUFFIX = "]]>";
    public static XStream getXStream() {
        final NameCoder nameCoder = new NoNameCoder();
        XStream xStream = new XStream(new XppDomDriver(nameCoder) {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, nameCoder) {
                    boolean cdataFlag = false;
                    Class<?> targetClass = null;
                    @Override
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                        if (targetClass == null) {
                            targetClass = clazz;
                        }
                        cdataFlag = isCDATA(targetClass, name);
                    }
                    @Override
                    public void writeText(QuickWriter writer, String text) {
                        if (cdataFlag) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        return xStream;
    }
    private static boolean isCDATA(Class<?> clazz, String fieldAlias) {
        //检查类本身
        boolean cdataFlag = isExistCDATA(clazz, fieldAlias);
        if (cdataFlag) {
            return cdataFlag;
        }
        //继续检查父类
        Class<?> superClazz = clazz.getSuperclass();
        while (!superClazz.equals(Object.class)) {
            cdataFlag = isExistCDATA(superClazz, fieldAlias);
            if (cdataFlag) {
                return cdataFlag;
            }
            superClazz = superClazz.getClass().getSuperclass();
        }
        return false;
    }
    /**
     * 检查是否有 @XStreamCDATA 注解
     * @param clazz clazz
     * @param fieldAlias fieldAlias
     * @return
     */
/*    private static boolean isExistCDATA(Class<?> clazz, String fieldAlias) {
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getAnnotation(XStreamCDATA.class) != null) {
                XStreamAlias xStreamAlias = field.getAnnotation(XStreamAlias.class);
                if (xStreamAlias != null && fieldAlias.equals(xStreamAlias.value())) {
                    return true;
                } else {
                    if (fieldAlias.equals(field.getName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }*/

}