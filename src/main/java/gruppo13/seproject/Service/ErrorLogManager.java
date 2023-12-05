package gruppo13.seproject.Service;

import gruppo13.seproject.Service.GUIExcecutor.GUIExecutor;
import gruppo13.seproject.essential.ErrorLog;

import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ErrorLogManager extends TimerTask {

    private ErrorLog errorLog;
    private ExecutorService executorService;
    private GUIExecutor guiExecutor;

    public ErrorLogManager() {
        this.errorLog = ErrorLog.getInstance();
        this.executorService = Executors.newCachedThreadPool();
        this.guiExecutor = GUIExecutor.getInstance();
    }

    @Override
    public synchronized void run() {
        executorService.submit(() -> {
            if (!errorLog.isEmpty()) {
                Exception e = errorLog.getException();
                if (e != null) {
                    guiExecutor.notifyError(null, e);
                }
            }
        });
    }
}
