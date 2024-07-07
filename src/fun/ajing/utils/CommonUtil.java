package fun.ajing.utils;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications.Bus;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.psi.PsiFile;


public class CommonUtil {

    public static PsiFile getSelectedFile(AnActionEvent event, boolean showNotifications) {
        PsiFile selectedFile = (PsiFile)event.getData(LangDataKeys.PSI_FILE);
        if (selectedFile == null) {
            if (showNotifications) {
                Bus.notify(new Notification("fun.ajing", "No file selected", "Please select file first", NotificationType.ERROR));
            }

            return null;
        } else {
            return selectedFile;
        }
    }

    public static String getFileType(AnActionEvent event, boolean showNotifications) {
        PsiFile selectedFile = getSelectedFile(event, showNotifications);
        return null == selectedFile ? null : selectedFile.getFileType().getName();
    }

//    public static <T, K> void writer(String filename, Consumer<K> consumer, Map<T, K> trees){
//        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
//            trees.values().forEach(consumer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
