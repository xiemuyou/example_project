package com.library.thread;

/**
 * 安全线程
 *
 * @author xiemy2
 */
public abstract class AbstractSafeThread implements Runnable {

    public Object value;

    public AbstractSafeThread() {

    }

    public AbstractSafeThread(Object value) {
        this.value = value;
    }

    /**
     * 运行(已捕获异常)
     */
    public abstract void deal();

    @Override
    public void run() {
        try {
            deal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            test(String.valueOf(i + 1));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void test(final String v) {
        ThreadPool.execute(new AbstractSafeThread(v) {
            @Override
            public void deal() {
                System.out.println("thread --" + v + "--");
            }
        });
    }
}
