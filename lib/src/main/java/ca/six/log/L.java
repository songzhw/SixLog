package ca.six.log;


import android.util.Log;

/**
 * Created by songzhw on 4/13/16.
 */
public class L {
    public static String tag = "szw";

    public static void d(String msg) {
        StackTraceElement[] traces = Thread.currentThread().getStackTrace();
        if (traces == null || traces.length < 8) {
            Log.d(tag, "(normal) " + msg);
            return;
        }

        StringBuilder sb = new StringBuilder();
        StackTraceElement element = traces[3];

        String classWholeName = element.getClassName(); //=> ca.six.demo.MainActivity$onCreate$1
        String className = classWholeName.substring(classWholeName.lastIndexOf(".") + 1);

        sb.append(className);
        sb.append(" || ");
        sb.append(element.getMethodName());
        sb.append("() || ( ");
        sb.append(element.getFileName()); //现在可能是java, 也可能是kt文件
        sb.append(":");
        sb.append(element.getLineNumber());
        sb.append(")\n");
        sb.append(msg);

        Log.d(tag, sb.toString());
    }

    public static void trace(String prefix) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        System.out.println("szw stack = " + stacks.length);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);

        // 第0,1项是Thread的信息, 第2项是L.trace(), 第三项开始是正式内容, 如SomeHelper || work(7)
        for (int i = 3; i < stacks.length; i++) {
            StackTraceElement item = stacks[i];
            String className = item.getClassName();
            String methodName = item.getMethodName();
            int lineNumber = item.getLineNumber();

            // 再往后就是Handler.dispatchMessage(), ActivitiyThread.main(), ZygoteInit.main()等方法, 就可以略过了
            boolean threshold = className.equals("android.os.Handler") && lineNumber == 107;
            if (threshold) {
                break;
            }

            sb.append("\nszw: ");
            sb.append(className);
            sb.append(" || ");
            sb.append(methodName);
            sb.append("(");
            sb.append(item.getFileName());
            sb.append(":");
            sb.append(lineNumber);
            sb.append(")");
        }
        Log.d(tag, sb.toString());
    }
}

/*
 现在问题
 1. ✔ 新版AndroidStudio中, 日志不再是可点击的
        : 点击失败. 是因为现在我用于kt文件中, 但L.d()中仍是写死了.java文件, 所以找不到
 2. x MainActivity$onCreate$1这样的, 能否优化下, 比如显示类包之类的
        : 失败. 因为StackTraceElement并没有提供superclass name这样的getter
 3. ✔ 新加traceFunction()
 4. ✔ traceFunction()里新加可点击
        : 而且它会只能在应用的类才能点击. 像Actiivty, AsyncTask这些系统类是不能点击的
 */