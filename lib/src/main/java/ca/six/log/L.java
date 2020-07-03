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
        sb.append(className);
        sb.append(".java:");
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
            sb.append(lineNumber);
            sb.append(")");
        }
        Log.d(tag, sb.toString());
    }
}

/*
 现在问题
 1. 新版AndroidStudio中, 日志不再是可点击的
 2. MainActivity$onCreate$1这样的, 能否优化下, 比如显示类包之类的
 3. 新加traceFunction()
 */