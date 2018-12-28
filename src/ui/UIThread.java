package ui;

public class UIThread {
    private Boolean _shouldExecute;
    private UIRunnable _r;
    private Thread _t;

    public UIThread(UIRunnable r) {
        _shouldExecute = true;
        _r = r;
    }

    public void start() {
        _t = new Thread(() -> {
           while(_shouldExecute) {
               try {
                   _r.run();
               } catch (InterruptedException e) {
                   _shouldExecute = false;
                   break;
               }
           }
        });
        _t.start();
    }

    public void terminate() {
        _shouldExecute = false;
        if(_t != null) _t.interrupt();
    }
}
