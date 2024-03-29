package com.chumyuenlaw.rpc.util;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <pre>
 * ReflectUtil 类
 * </pre>
 *
 * @author luojunyuan chumyuenlaw@qq.com
 * @version 1.00.00
 * @date 2022/2/28 15:06
 *
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ReflectUtil
{
    public static String getStackTrace()
    {
        StackTraceElement[] stack = new Throwable().getStackTrace();
        return stack[stack.length - 1].getClassName();
    }

    public static Set<Class<?>> getClasses(String packageName)
    {
        Set<Class<?>> classes = new LinkedHashSet<>();
        boolean recursive = true;
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;

        try
        {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements())
            {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol))
                {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
                }
                else if("jar".equals(protocol))
                {
                    JarFile jar;
                    try
                    {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries =jar.entries();
                        while(entries.hasMoreElements())
                        {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/')
                                name = name.substring(1);

                            if (name.startsWith(packageDirName))
                            {
                                int index = name.lastIndexOf('/');
                                if (index != -1)
                                    packageName = name.substring(0, index).replace('/', '.');
                                if (index != -1 || recursive)
                                {
                                    if (name.endsWith(".class") && !entry.isDirectory())
                                    {
                                        String className = name.substring(packageName.length() + 1, name.length() - 1);
                                        try
                                        {
                                            classes.add(Class.forName(className));
                                        } catch (ClassNotFoundException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return classes;
    }

    public static void findAndAddClassesInPackageByFile(String packageName, String packagePath,
                                                        final boolean recursive, Set<Class<?>> classes)
    {
        File dir = new File(packagePath);

        if (!dir.exists() || !dir.isDirectory())
        {
            return;
        }

        File[] dirFiles = dir.listFiles(file -> (recursive && file.isDirectory()) || (file.getName().endsWith(".class")));

        for (File file : dirFiles)
        {
            if (file.isDirectory())
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath(), recursive, classes);
            else
            {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try
                {
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                } catch (ClassNotFoundException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
